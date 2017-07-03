package com.hroscope.cegep.cegephoroscope;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by SACHIN on 6/23/2017.
 */

public class ForgotPasswordFragment extends Fragment implements View.OnClickListener{
    private View view;
    private EditText email_address;
    private Button reset;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    Validation vr=new Validation();

    public static ForgotPasswordFragment newInstance() {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        return fragment;
    }

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.forgotpassword, container, false);

        setControlls();
        return view;
    }

    public void setControlls()
    {
        firebaseAuth=FirebaseAuth.getInstance();
        email_address=(EditText)view.findViewById(R.id.email);
        reset=(Button)view.findViewById(R.id.reset);

        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==reset)
        {
            resetPassword();
        }

    }



    public void resetPassword()
    {
        String emailaddress=email_address.getText().toString().trim();


        reset.setEnabled(false);

        if(!vr.validateResetpassword(email_address)) {
            onSignupFailed();
            return;
        }



        firebaseAuth.sendPasswordResetEmail(emailaddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Email", "Email sent.");
                            Toast.makeText(getActivity(), "Email Sent", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
              /* }
                else
                {
                    resetemail.setError("Invalid Email Address");
                }*/


    }

    private void onSignupFailed() {
        Toast.makeText(getActivity(), "Invalid Email Address", Toast.LENGTH_LONG).show();

        reset.setEnabled(true);
    }
}
