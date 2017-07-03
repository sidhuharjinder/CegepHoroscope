package com.hroscope.cegep.cegephoroscope;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SACHIN on 6/30/2017.
 */

public class Google_SignIn_Profile extends Fragment {
    private View view;

    public static Google_SignIn_Profile newInstance() {
        Google_SignIn_Profile fragment = new Google_SignIn_Profile();
        return fragment;
    }

    public Google_SignIn_Profile() {
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

