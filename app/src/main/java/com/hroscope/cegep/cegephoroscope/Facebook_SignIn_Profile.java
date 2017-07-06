package com.hroscope.cegep.cegephoroscope;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SACHIN on 6/30/2017.
 */

public class Facebook_SignIn_Profile extends Fragment {
    private View view;

    public static Facebook_SignIn_Profile newInstance() {
        Facebook_SignIn_Profile fragment = new Facebook_SignIn_Profile();
        return fragment;
    }

    public Facebook_SignIn_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_email_signin_profile, container, false);

        return view;
    }
}
