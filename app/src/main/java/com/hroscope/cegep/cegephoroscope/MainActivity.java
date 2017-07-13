package com.hroscope.cegep.cegephoroscope;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    Button signup,signIn,loginPhone;
    private SignInButton googlebutton;

    private static final int RC_SIGN_IN=1;
    private GoogleApiClient googleApiClient;
    private static String TAG="MainActivity";

    //facebook widgets declaration
    LoginButton loginButton;
    TextView textView;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    boolean clicked=false,click=false;
    private TextView forgotpassword;
    private FirebaseAuth.AuthStateListener firebaseauthlistener;
    FacebookProfileActivity fba=new FacebookProfileActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
       //  customTheme();

        //facebook Login

        if(getIntent().hasExtra("logout"))
        {
            LoginManager.getInstance().logOut();

        }

        setcontrolls();
        LoginFacebook();
        setcontrollsforgoogle();

    }

    private void setcontrolls()
    {
        FacebookSdk.sdkInitialize(getApplicationContext());


        firebaseAuth=FirebaseAuth.getInstance();
        callbackManager=CallbackManager.Factory.create();
        // textView=(TextView)findViewById(R.id.status);
        loginButton=(LoginButton)findViewById(R.id.facebook_login);
    }

    private void setcontrollsforgoogle()
    {
        googlebutton=(SignInButton)findViewById(R.id.googlebutton);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
             Toast.makeText(MainActivity.this,"Get Error",Toast.LENGTH_LONG).show();
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


    private void LoginFacebook()
    {
        // loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.setReadPermissions("email","public_profile");
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
                   Intent in=new Intent(MainActivity.this,FacebookProfileActivity.class);
                    String email=firebaseuser.getEmail();
                    String name=firebaseuser.getDisplayName();
                   startActivity(in);
                    finish();

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
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(",","signInWithCredentialonComplete"+task.isSuccessful());
                if(task.isSuccessful())
                {
                    Log.v(",","signInWithCredential",task.getException());
                    Toast.makeText(MainActivity.this,"Authentication Successful",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);//for facebook

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

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }


}
