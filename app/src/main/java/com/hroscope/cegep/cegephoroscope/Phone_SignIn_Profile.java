package com.hroscope.cegep.cegephoroscope;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Phone_SignIn_Profile extends Fragment implements View.OnClickListener{
    private View view;
    private Button mSignOutButton;
    private EditText username;
    private FirebaseAuth firebaseAuth;

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
        view= inflater.inflate(R.layout.fragment_phone_signin_profile, container, false);
         firebaseAuth = FirebaseAuth.getInstance();

       setControll();
        ReadFirebase_setProfileData();


        return view;
    }
    public void setControll()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        mSignOutButton = (Button)view. findViewById(R.id.phonesignout);
        mSignOutButton.setOnClickListener(this);
        username = (EditText) view.findViewById(R.id.email);

    }
    public void ReadFirebase_setProfileData()
    {
        username.setText(firebaseAuth.getCurrentUser().getPhoneNumber().toString());
    }

    @Override
    public void onClick(View view) {
        if(view==mSignOutButton)
        {
            firebaseAuth.signOut();
            Fragment fragment = new SignupFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
            Toast.makeText(getActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();

        }
    }
}
