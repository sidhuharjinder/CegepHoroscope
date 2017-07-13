package com.hroscope.cegep.cegephoroscope.GoogleLogin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.hroscope.cegep.cegephoroscope.R;
import com.hroscope.cegep.cegephoroscope.SignupFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by SACHIN on 6/24/2017.
 */

public class Google_SignIn_Profile extends Fragment // implements FragmentCommunicator
{
    private FirebaseAuth.AuthStateListener firebaseauthlistener;
    private GoogleApiClient googleApiClient;

    private View view;
    private Context context;
    static boolean clicked=false,logoutclick=true;
    private FirebaseAuth firebaseAuth;
    private Button facebook_logout,google_logout;
    private EditText username, emailTextview;
    private CircleImageView faebookimage;
    public static String name;

    public static Uri photo;
    String email_address;



    public static Google_SignIn_Profile newInstance() {
        Google_SignIn_Profile fragment = new Google_SignIn_Profile();
        return fragment;
    }

    public Google_SignIn_Profile() {
        // Required empty public constructor
    }

    @Override
    public void onStart()
    {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseauthlistener);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_google_signin_profile, container, false);

        FacebookSdk.sdkInitialize(getApplicationContext());
        firebaseAuth=FirebaseAuth.getInstance();
        facebook_logout=(LoginButton)view.findViewById(R.id.fb);
        // google_logout=(Button) view.findViewById(R.id.google_signout) ;
        username=(EditText) view.findViewById(R.id.email);
        faebookimage=(CircleImageView) view.findViewById(R.id.rabbitButton);


        facebook_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                LoginManager.getInstance().logOut();
                Fragment fragment = new SignupFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();

                // LoginManager.getInstance().logOut();

                clicked=true;


            }
        });

        // Configure Google Sign out
        /* GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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

       google_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();

                Auth.GoogleSignInApi.signOut(googleApiClient);
            }
        });*/


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

                    name=firebaseuser.getDisplayName();
                    //  email_address=firebaseuser.getEmail();
                    photo=firebaseuser.getPhotoUrl();



                    username.setText(firebaseuser.getDisplayName());

                    Picasso.with(getActivity()).load(photo).resize(400,100).centerInside().into(faebookimage);


                }
                else if(firebaseuser==null){




                }
            }

        };




        return view;
    }








}
