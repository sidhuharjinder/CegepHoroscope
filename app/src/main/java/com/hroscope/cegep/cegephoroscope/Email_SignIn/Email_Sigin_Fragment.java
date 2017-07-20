package com.hroscope.cegep.cegephoroscope.Email_SignIn;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hroscope.cegep.cegephoroscope.ForgotPasswordFragment;
import com.hroscope.cegep.cegephoroscope.R;
import com.hroscope.cegep.cegephoroscope.RegisterFragment;
import com.hroscope.cegep.cegephoroscope.Validation;

/**
 * Created by SACHIN on 6/22/2017.
 */

public class Email_Sigin_Fragment extends Fragment implements View.OnClickListener{
    private View view;
    Validation vr=new Validation();
    private Button buttonLogin;
    private EditText editTextemail,editTextPassword;
    private TextView textViewNo_member,textViewForgot_passsword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    public static boolean emailLoginStatus;

    public static Email_Sigin_Fragment newInstance() {
        Email_Sigin_Fragment fragment = new Email_Sigin_Fragment();
        return fragment;
    }

    public Email_Sigin_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.email_signin, container, false);
        setControlls();


       if (firebaseAuth.getCurrentUser() != null) {
            Fragment fragment = new Email_Signin_Profile_Fragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
        }

        return view;
    }

    private void setControlls() {
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        buttonLogin = (Button)view.findViewById(R.id.button_login);
        editTextemail = (EditText)view.findViewById(R.id.email_id);
        editTextPassword = (EditText)view.findViewById(R.id.email_password);
        textViewNo_member = (TextView)view.findViewById(R.id.nomember);
        textViewForgot_passsword=(TextView)view.findViewById(R.id.forgetPassword);

        buttonLogin.setOnClickListener(this);
        textViewNo_member.setOnClickListener(this);
        textViewForgot_passsword.setOnClickListener(this);

    }


    private void SignInUser()
    {
        final String email= editTextemail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
       if(!vr.validationSignin(editTextemail,editTextPassword)) {
            onSignupFailed();
            return;
        }

     /*  if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getActivity(),"Please enter email",Toast.LENGTH_SHORT).show();
            return;
        }
        }*/

        //if validation is ok
        //show progressbar
        buttonLogin.setEnabled(false);
        progressDialog.setMessage("Signing User..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login Successfull",
                                    Toast.LENGTH_SHORT).show();
                            emailLoginStatus=true;
                            Fragment fragment = new Email_Signin_Profile_Fragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
                        }

                        else
                        {
                            emailLoginStatus=false;
                            Toast.makeText(getActivity(), "Registration Failed",
                                    Toast.LENGTH_SHORT).show();
                            buttonLogin.setEnabled(true);

                        }
                        progressDialog.dismiss();

                    }
                });

    }

    private void onSignupFailed() {
        Toast.makeText(getActivity(), "Either Email or Password is Incorrect", Toast.LENGTH_LONG).show();

        buttonLogin.setEnabled(true);
    }
    public void onSignupSuccess() {
        Toast.makeText(getActivity(), "Registration Completed", Toast.LENGTH_LONG).show();

        buttonLogin.setEnabled(true);
    }

   @Override
    public void onClick(View view) {
        if(view==buttonLogin)
        {

         SignInUser();
        }
        if(view==textViewNo_member)
        {
            Fragment fragment = new RegisterFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();

        }
        if(view==textViewForgot_passsword)
        {
            Fragment fragment = new ForgotPasswordFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();

        }

    }
}
