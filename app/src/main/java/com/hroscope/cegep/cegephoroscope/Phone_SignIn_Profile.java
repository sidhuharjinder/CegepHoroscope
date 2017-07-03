package com.hroscope.cegep.cegephoroscope;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class Phone_SignIn_Profile extends Fragment {
    private View view;

    public static Phone_SignIn_Profile newInstance() {
        Phone_SignIn_Profile fragment = new Phone_SignIn_Profile();
        return fragment;
    }

    public Phone_SignIn_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_facebook_signin_profile, container, false);
        return view;
    }

}
