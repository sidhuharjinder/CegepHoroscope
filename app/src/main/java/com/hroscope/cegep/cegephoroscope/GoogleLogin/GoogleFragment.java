package com.hroscope.cegep.cegephoroscope.GoogleLogin;

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

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hroscope.cegep.cegephoroscope.Facebook_SignIn.Fb_Profile_Fragment;
import com.hroscope.cegep.cegephoroscope.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by SACHIN on 6/24/2017.
 */

public class GoogleFragment extends Fragment {


    private SignInButton googlebutton;

    private static final int RC_SIGN_IN=1;
    private GoogleApiClient googleApiClient;
    private static String TAG="MainActivity";

   //fb
    LoginButton loginButton;
    TextView textView;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    String Birthday;
    boolean clicked=false;
    private FirebaseAuth.AuthStateListener firebaseauthlistener;
    Google_SignIn_Profile fba=new Google_SignIn_Profile();
    private View view;

    public static GoogleFragment newInstance() {
        GoogleFragment fragment = new GoogleFragment();
        return fragment;
    }

    public GoogleFragment() {


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

        setcontrollsforgoogle();
        Data();

        return view;
    }



    private void setcontrolls()
    {



        firebaseAuth=FirebaseAuth.getInstance();


    }

    private void setcontrollsforgoogle()
    {
        googlebutton=(SignInButton)view.findViewById(R.id.googlebutton);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(getActivity(),"Get Error",Toast.LENGTH_LONG).show();
            }
        })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        googlebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void Data()
    {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }

    }

    @Override
    public void onStart()
    {
        super.onStart();

      //  firebaseAuth.addAuthStateListener(firebaseauthlistener);
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

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
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
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                           // Toast.makeText(getActivity(), "Authentication failed.",
                             //       Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });
    }




}
