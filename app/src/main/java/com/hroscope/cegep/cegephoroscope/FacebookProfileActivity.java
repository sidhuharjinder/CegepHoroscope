package com.hroscope.cegep.cegephoroscope;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

/**
 * Created by SACHIN on 6/3/2017.
 */

public class FacebookProfileActivity extends AppCompatActivity{

    private FirebaseAuth.AuthStateListener firebaseauthlistener;
    private FirebaseAuth firebaseAuth;
    private Button facebook_logout,google_logout;
    private TextView username,email;
    private ImageView faebookimage;
    private GoogleApiClient googleApiClient;
    static boolean clicked=false;
    FragmentCommunicator fragmentCommunicator;
    public static String name;
    public static String email_address;
    public static Uri photo;


    FacebookProfileFragment facebookProfileFragment;
    @Override
    public void onStart()
    {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseauthlistener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.facebook_profile);
        firebaseAuth=FirebaseAuth.getInstance();
        facebook_logout=(LoginButton)findViewById(R.id.fb);
        google_logout=(Button)findViewById(R.id.google_signout) ;
        username=(TextView)findViewById(R.id.username);
        email=(TextView)findViewById(R.id.email);
        faebookimage=(ImageView)findViewById(R.id.facebook_profile);



        facebook_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                LoginManager.getInstance().logOut();

                // LoginManager.getInstance().logOut();

                clicked=true;


            }
        });

        // Configure Google Sign out
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(FacebookProfileActivity.this,"Get Error",Toast.LENGTH_LONG).show();
            }
        })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        google_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();

                Auth.GoogleSignInApi.signOut(googleApiClient);
            }
        });


        firebaseauthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser firebaseuser=firebaseAuth.getCurrentUser();
                if(firebaseuser!=null)
                {

                    for(UserInfo userInfo:firebaseuser.getProviderData())
                    {
                        Log.d("TAG",userInfo.getProviderId());
                    }

                /*    Bundle bundle=new Bundle();
                    bundle.putString("name", "Sachin");
                    //set Fragmentclass Arguments
                    FacebookProfileFragment fragobj=new FacebookProfileFragment();
                    fragobj.setArguments(bundle);*/
                      name=firebaseuser.getDisplayName();
                    email_address=firebaseuser.getEmail();
                    photo=firebaseuser.getPhotoUrl();

                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,createFragment()).commit();
                    username.setText(firebaseuser.getDisplayName());
                    email.setText(firebaseuser.getEmail());



                  //  FacebookLoginActivity fbl=new FacebookLoginActivity();
                    /// username.setText(fbl.personname);
                    //email.setText(personEmail);
                    //  Picasso.with(FacebookProfileActivity.this).load(fbl.photo).resize(250,280).into(faebookimage);


                }
                else if(firebaseuser==null){

                    Intent intnt = new Intent(FacebookProfileActivity.this, HomeScreen.class);
                    intnt.putExtra("logout",true);
                    startActivity(intnt);

                    finish();
                }
            }

        };


    }


    private Fragment createFragment()
    {
        Bundle bundle=new Bundle();
        bundle.putString("name",name);
        bundle.putString("email",email_address);
        bundle.putString("photo", String.valueOf(photo));

        FacebookProfileFragment facebookProfileFragment=new FacebookProfileFragment();
        facebookProfileFragment.setArguments(bundle);
        return facebookProfileFragment;

    }




}
