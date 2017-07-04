package com.hroscope.cegep.cegephoroscope;

import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    Validation vr=new Validation();
    private View view;
    private Button buttonRegister;
    private EditText editTextfirstname,editTextlastname,editTextemail,ediTextphone,editTextaddress,editTextrepassword,editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }
    public RegisterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.register_user, container, false);
        setControlls();


        return view;


    }

    public void setControlls()
    {
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        progressDialog=new ProgressDialog(getContext());
        buttonRegister =(Button)view.findViewById(R.id.register);
        editTextfirstname=(EditText)view.findViewById(R.id.first_name);
        editTextlastname=(EditText)view.findViewById(R.id.last_name);
        editTextemail =(EditText)view.findViewById(R.id.email);
        ediTextphone=(EditText)view.findViewById(R.id.phone);
        editTextPassword=(EditText)view.findViewById(R.id.password);
        editTextrepassword  =(EditText)view.findViewById(R.id.repassword);
        textViewSignin=(TextView)view.findViewById(R.id.status);

        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);
    }

    private void registerUser()
    {
        final String firstname=editTextfirstname.getText().toString().trim();
        final String lastname=editTextlastname.getText().toString().trim();
        final String phone=ediTextphone.getText().toString().trim();
        final String email= editTextemail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String repassword=editTextrepassword.getText().toString().trim();

        if(!vr.validationRegister(editTextfirstname,editTextlastname,editTextemail,ediTextphone,editTextPassword,editTextrepassword)) {
            onSignupFailed();
            return;
        }

        //if validation is ok
        //show progressbar
        buttonRegister.setEnabled(false);
        progressDialog.setMessage("Registering User..");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Register", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Registration Successfull",

                                    Toast.LENGTH_SHORT).show();
                            String useruid=firebaseAuth.getCurrentUser().getUid();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference databaseReference = database.getReference("Registration_Data").child(useruid);
                           // databaseReference.child("User_Id").setValue(useruid);
                            databaseReference.child("first_name").setValue(firstname);
                            databaseReference.child("Last_name").setValue(lastname);
                            databaseReference.child("Email").setValue(email);
                            databaseReference.child("phone_no").setValue(phone);

                            Fragment fragment = new ForgotPasswordFragment();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();


                        }


                        else
                        {

                            Toast.makeText(getActivity(), "Registration Failed",
                                    Toast.LENGTH_SHORT).show();
                            buttonRegister.setEnabled(true);
                        }

                        progressDialog.dismiss();


                        // ...
                    }
                });

    }

    private void onSignupFailed() {
        Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_LONG).show();

        buttonRegister.setEnabled(true);
    }
    public void onSignupSuccess() {
        Toast.makeText(getActivity(), "Registration Completed", Toast.LENGTH_LONG).show();

        buttonRegister.setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if(view== buttonRegister)
        {
            registerUser();
        }
        if(view==textViewSignin)
        {
            Fragment fragment = new ForgotPasswordFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();

        }

    }
}
