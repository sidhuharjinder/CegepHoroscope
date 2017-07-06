package com.hroscope.cegep.cegephoroscope;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

/**
 * Created by SACHIN on 6/24/2017.
 */

public class FacebookProfileFragment extends Fragment // implements FragmentCommunicator
{
    private View view;
    private Context context;

    private FirebaseAuth firebaseAuth;
    private Button facebook_logout,google_logout;
    private TextView username, emailTextview;
    private ImageView faebookimage;
    Uri facebook_profile;
    String email_address,displayname;
    String sdata;



    public static FacebookProfileFragment newInstance() {
        FacebookProfileFragment fragment = new FacebookProfileFragment();
        return fragment;
    }

    public FacebookProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fbprofile, container, false);

         Bundle bundle=getArguments();
        String name=bundle.getString("name");
        String email_address=bundle.getString("email");
        Uri photo= Uri.parse(bundle.getString("photo"));


        facebook_logout=(LoginButton)view.findViewById(R.id.fb);
      //  google_logout=(Button)view.findViewById(R.id.google_signout) ;
        username=(TextView)view.findViewById(R.id.username);
        emailTextview =(TextView)view.findViewById(R.id.email);
        faebookimage=(ImageView)view.findViewById(R.id.facebook_profile);


       username.setText(name);
        emailTextview.setText(email_address);
        Picasso.with(getActivity()).load(photo).resize(250,280).into(faebookimage);


    //  Bundle bundle = this.getArguments();
        String myValue = getArguments().getString("name");


       // emailTextview.setText("smart.san");

      //Picasso.with(getActivity()).load(facebook_profile).resize(250,280).into(faebookimage);


        return view;
    }





}
