package com.hroscope.cegep.cegephoroscope.Facebook_SignIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hroscope.cegep.cegephoroscope.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by SACHIN on 6/24/2017.
 */

public class FacebookFragment extends Fragment {
    LoginButton loginButton;
    public static boolean facebookLoginStatus;
    TextView textView;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    String Birthday;
    boolean clicked=false;
    private FirebaseAuth.AuthStateListener firebaseauthlistener;
    Fb_Profile_Fragment fba=new Fb_Profile_Fragment();

    private View view;

    public static FacebookFragment newInstance() {
        FacebookFragment fragment = new FacebookFragment();
        return fragment;
    }

    public FacebookFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.continue_facebook, container, false);


        if(getActivity().getIntent().hasExtra("logout"))
        {
            LoginManager.getInstance().logOut();
            firebaseAuth.signOut();

        }


        setcontrolls();
        LoginFacebook();

        return view;
    }

    private void setcontrolls()
    {
        FacebookSdk.sdkInitialize(getApplicationContext());
        firebaseAuth=FirebaseAuth.getInstance();
        callbackManager=CallbackManager.Factory.create();
        // textView=(TextView)findViewById(R.id.status);
        loginButton=(LoginButton)view.findViewById(R.id.facebook_login);

        //with set fragment it goes to fragment by default OnActivityResult redirect to activity
        loginButton.setFragment(this);

    }

    private void LoginFacebook()
    {


        loginButton.setReadPermissions("email","user_birthday","public_profile");

      if(fba.clicked) {
            loginButton.setLoginBehavior(LoginBehavior.WEB_ONLY);
      }
        loginButton.callOnClick();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG","Facebook Login Successful"+loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {
                Log.d("TAG","Facebook Login Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG","Facebook Login Error",error);
            }
        });
        firebaseauthlistener=new FirebaseAuth.AuthStateListener()
        {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseuser=firebaseAuth.getCurrentUser();
                if(firebaseuser!=null)
                {
                    Log.d(",","onAuthStateChanged_signed_in"+firebaseuser.getUid());

                     facebookLoginStatus=true;
                    Fragment fragment = new Fb_Profile_Fragment();
                    FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();

                   // Intent in=new Intent(F.this,FacebookProfileActivity.class);
                    String email=firebaseuser.getEmail();
                    String name=firebaseuser.getDisplayName();
                     //  firebaseuser.getBirthday();

                  //  startActivity(in);
                   // finish();
                    Toast.makeText(getActivity(), "Facebook Login Successfull", Toast.LENGTH_LONG).show();



                }

                else{

                    facebookLoginStatus=false;
                    Log.d("Lable","Signed Out");
                }


            }
        };
    }

    @Override
    public void onStart()
    {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseauthlistener);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if(firebaseauthlistener!=null)
        {
            firebaseAuth.removeAuthStateListener(firebaseauthlistener);
        }
    }
    private void handleFacebookAccessToken(AccessToken token)
    {
        Log.d(",","handleFacebookAcessToken"+token);
        AuthCredential credential= FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(",","signInWithCredentialonComplete"+task.isSuccessful());
                if(task.isSuccessful())
                {
                  //  Toast.makeText(getActivity(),"Authentication Successful",Toast.LENGTH_SHORT).show();
                    Log.v(",","signInWithCredential",task.getException());
                 //


                }
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }






}
