package com.hroscope.cegep.cegephoroscope;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hroscope.cegep.cegephoroscope.Email_SignIn.Email_Sigin_Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment implements View.OnClickListener{
    private View view;
    private Button email_login_button, phone_login_Button;
    public Button facebook_login_button;
    private Button google_login_button;
    private TextView register;

    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();
        return fragment;
    }
    public SignupFragment() {

    }

    public void initializeButtons()
    {
        facebook_login_button =(Button) view.findViewById(R.id.facebook_button);
        google_login_button =(Button) view.findViewById(R.id.googlePlus);

        email_login_button =(Button)view.findViewById(R.id.email);
        phone_login_Button =(Button)view.findViewById(R.id.phoneNumber);
        register=(TextView)view.findViewById(R.id.register);

        facebook_login_button.setOnClickListener(this);
        google_login_button.setOnClickListener(this);
        email_login_button.setOnClickListener(this);
        phone_login_Button.setOnClickListener(this);
        register.setOnClickListener(this);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_signup, container, false);

        initializeButtons();
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view== facebook_login_button)
        {
            startActivity(new Intent(getActivity(), FacebookLoginActivity.class));

        }
        if(view== google_login_button)
        {
            startActivity(new Intent(getActivity(), GoogleLoginActivity.class));
        }
        if(view== email_login_button)
        {
            Fragment fragment = new Email_Sigin_Fragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();

        }
        if(view== phone_login_Button)
        {

            Fragment fragment = new PhoneLoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();

        }
        if(view==register)
        {
            Fragment fragment = new RegisterFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
        }


    }
}
