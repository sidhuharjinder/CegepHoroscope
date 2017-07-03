package com.hroscope.cegep.cegephoroscope;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class FacebookLoginActivity extends AppCompatActivity {

    LoginButton loginButton;

    TextView textView;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;

    boolean clicked=false;
    private FirebaseAuth.AuthStateListener firebaseauthlistener;
    FacebookProfileActivity fba=new FacebookProfileActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.continue_facebook);

          if(getIntent().hasExtra("logout"))
          {
              LoginManager.getInstance().logOut();
              firebaseAuth.signOut();

          }

        setcontrolls();
        LoginFacebook();
    }
    private void setcontrolls()
    {
        FacebookSdk.sdkInitialize(getApplicationContext());


        firebaseAuth=FirebaseAuth.getInstance();
        callbackManager=CallbackManager.Factory.create();
       // textView=(TextView)findViewById(R.id.status);
        loginButton=(LoginButton)findViewById(R.id.facebook_login);

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
                       Intent i = new Intent(FacebookLoginActivity.this,FacebookProfileActivity.class);
                       startActivity(i);
                       String name=firebaseuser.getDisplayName();
                       startActivity(i);
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
                    Toast.makeText(FacebookLoginActivity.this,"Authentication Successful",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }





}
