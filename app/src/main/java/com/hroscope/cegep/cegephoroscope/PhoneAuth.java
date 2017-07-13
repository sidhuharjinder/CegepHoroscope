package com.hroscope.cegep.cegephoroscope;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;


public class PhoneAuth extends Fragment implements View.OnClickListener {
    private View view;

    private TextView mStatusText;
    private TextView mDetailText;
    private EditText mPhoneNumberField;
    private EditText mVerificationField;
    private Button mStartButton;
    private Button mVerifyButton;
    private Button mResendButton;
    private Button mSignOutButton;
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";


    public static ForgotPasswordFragment newInstance() {
        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        return fragment;
    }

    public PhoneAuth() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragement_phoneauth, container, false);
        setControlls();
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        InitialCodeVerification();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(mPhoneNumberField.getText().toString());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);

    }


    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberField.setError("Invalid phone number.");
            return false;
        }

        return true;
    }

    public void InitialCodeVerification()
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null) {

            mStartButton.setEnabled(true);
            mPhoneNumberField.setEnabled(true);
            mVerifyButton.setEnabled(false);
            mResendButton.setEnabled(false);
            mVerificationField.setEnabled(false);
        }

        mDetailText.setText(null);
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;

                mStartButton.setEnabled(false);
                mPhoneNumberField.setEnabled(false);
                mVerifyButton.setEnabled(false);
                mResendButton.setEnabled(false);
                mVerificationField.setEnabled(false);
                mDetailText.setText("status_verification_succeeded");


                signInWithPhoneAuthCredential(credential);
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);
                mVerificationInProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    mPhoneNumberField.setError("Invalid phone number.");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar.make(view.findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                }

                mStartButton.setEnabled(true);
                mPhoneNumberField.setEnabled(true);
                mVerifyButton.setEnabled(true);
                mResendButton.setEnabled(true);
                mVerificationField.setEnabled(true);
                mDetailText.setText("status_verification_failed");

            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;

                mVerifyButton.setEnabled(true);
                mResendButton.setEnabled(true);
                mPhoneNumberField.setEnabled(true);
                mVerificationField.setEnabled(true);
                mStartButton.setEnabled(false);
                mDetailText.setText("status_code_sent");




            }
        };
    }
    public void setControlls()
    {

        mStatusText = (TextView) view.findViewById(R.id.status);
        mDetailText = (TextView)view. findViewById(R.id.detail);
        mPhoneNumberField = (EditText)view. findViewById(R.id.field_phone_number);
        mVerificationField = (EditText) view.findViewById(R.id.field_verification_code);
        mStartButton = (Button) view.findViewById(R.id.button_start_verification);

        mVerifyButton = (Button)view. findViewById(R.id.button_verify_phone);
        mResendButton = (Button) view.findViewById(R.id.button_resend);
        mSignOutButton = (Button)view. findViewById(R.id.sign_out_button);
        mStartButton.setOnClickListener(this);
        mVerifyButton.setOnClickListener(this);
        mResendButton.setOnClickListener(this);
        mSignOutButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                mCallbacks);
        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                mCallbacks,
                token);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity() , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Login Successfull",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();

                            Fragment fragment = new Phone_SignIn_Profile();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();


                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                mVerificationField.setError("Invalid code.");
                            }
                            mDetailText.setText(R.string.status_sign_in_failed);

                        }
                    }
                });
    }
    private void signOut() {
        mAuth.signOut();
        mStartButton.setEnabled(true);
        mPhoneNumberField.setEnabled(true);
        mVerifyButton.setEnabled(false);
        mResendButton.setEnabled(false);
        mVerificationField.setEnabled(false);

    }

    @Override
    public void onClick(View view) {
        if(view==mStartButton)
        {
            String phone=mPhoneNumberField.getText().toString();
            if(TextUtils.isEmpty(phone))
            {
                mVerificationField.setError("Number Cannot be Empty.");
                return;
            }
            startPhoneNumberVerification(phone);

        }
        if(view==mVerifyButton)
        {
            String code = mVerificationField.getText().toString();
            if(TextUtils.isEmpty(code))
            {
                mVerificationField.setError(" Code Cannot be Empty.");
                return;
            }
            verifyPhoneNumberWithCode(mVerificationId, code);

        }
        if(view==mResendButton)
        {

            resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);

        }
        if(view==mSignOutButton)
        {
            signOut();

        }

    }
}
