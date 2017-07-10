package com.hroscope.cegep.cegephoroscope;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.hroscope.cegep.cegephoroscope.Email_SignIn.Email_Sigin_Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment implements View.OnClickListener{
    private View view;
    private Button email_login_button, phone_login_Button;
    public Button facebook_login_button;
    private Button google_login_button;
    public LoginButton facebook_login;
    private TextView register;

    //Facebook Widgets
    LoginButton loginButton;
    TextView textView;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    String Birthday;
    boolean clicked=false;
    private FirebaseAuth.AuthStateListener firebaseauthlistener;
    Fb_Profile_Fragment fba=new Fb_Profile_Fragment();



    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        return fragment;
    }
    public SignupFragment() {


    }

    public void initializeButtons()
    {
       facebook_login=(LoginButton)view.findViewById(R.id.facebook_login);
       // facebook_login_button =(Button) view.findViewById(R.id.facebook_button);
        google_login_button =(Button) view.findViewById(R.id.googlePlus);

        email_login_button =(Button)view.findViewById(R.id.email);
        phone_login_Button =(Button)view.findViewById(R.id.phoneNumber);
        register=(TextView)view.findViewById(R.id.register);

       // facebook_login_button.setOnClickListener(this);
        google_login_button.setOnClickListener(this);
        email_login_button.setOnClickListener(this);
        phone_login_Button.setOnClickListener(this);
        register.setOnClickListener(this);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_signup, container, false);

        initializeButtons();
      //setcontrolls();
       // LoginFacebook();

        return view;
    }
/*
    //facebook login section
    private void setcontrolls()
    {
        if(getActivity().getIntent().hasExtra("logout"))
        {
            Toast.makeText(getActivity(), "Inside LOGOUT", Toast.LENGTH_LONG).show();
            LoginManager.getInstance().logOut();




        }

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
    public void onPause()
    {
        super.onPause();
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
                    Log.v(",","signInWithCredential",task.getException());
                    Toast.makeText(getActivity(),"Authentication Successful",Toast.LENGTH_SHORT).show();


                }
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
*/
    //facebook login section ends

    @Override
    public void onClick(View view) {
      /*  if(view== facebook_login_button)
        {
            Fragment fragment = new Fbfrag();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();

         //   startActivity(new Intent(getActivity(), MainActivity.class));

        }*/
        if(view== google_login_button)
        {
            startActivity(new Intent(getActivity(), GoogleLoginActivity.class));
        }
        if(view== email_login_button)
        {
            Fragment fragment = new Email_Sigin_Fragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();

        }
        if(view== phone_login_Button)
        {

            Fragment fragment = new PhoneLoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();

        }
        if(view==register)
        {
            Fragment fragment = new RegisterFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
        }


    }
}
