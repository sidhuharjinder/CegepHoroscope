package com.hroscope.cegep.cegephoroscope.Phone_SignIn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.hroscope.cegep.cegephoroscope.R;

import java.util.concurrent.TimeUnit;

public class phLogFragment extends Fragment implements View.OnClickListener  {

    private static final String TAG = "PhoneAuthActivity";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    private static final int initialized = 1;
    private static final int code_sent = 2;
    private static final int verify_fail = 3;
    private static final int verify_success = 4;
    private static final int signIn_failed = 5;
    private static final int signIn_success = 6;
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private static String phoneNumber;
    private ViewGroup mPhoneNumberViews;
    private ViewGroup mSignedInViews;
    private TextView mStatusText;
    private TextView mDetailText;
    private EditText mPhoneNumberField;
    private EditText mVerificationField;
    private Button mStartButton;
    private Button mVerifyButton;
    private Button mResendButton;
    private Button mSignOutButton;
    private View view;


    public static phLogFragment newInstance() {
        phLogFragment fragment = new phLogFragment();
        return fragment;
    }

    public phLogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragement_phoneauth, container, false);
        setControlls();
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        InitialCodeVerification();

        return view;
    }


    public void InitialCodeVerification()
    {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);
                mVerificationInProgress = false;
                updateUI(verify_success, credential);
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
                updateUI(verify_fail);
            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "onCodeSent:" + verificationId);
                mVerificationId = verificationId;
                mResendToken = token;
                updateUI(code_sent);
            }
        };
    }

    public void setControlls()
    {
        mPhoneNumberViews = (ViewGroup)view.findViewById(R.id.phone_auth_fields);
        mSignedInViews = (ViewGroup)view. findViewById(R.id.signed_in_buttons);
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

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
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
                            Toast.makeText(getContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();
                            updateUI(signIn_success, user);

                        PhoneList ph=new PhoneList();
                            ph.setPhone_number(user.getPhoneNumber());

                            phoneNumber=user.getPhoneNumber();
                            Toast.makeText(getContext(), user.getPhoneNumber(), Toast.LENGTH_SHORT).show();

                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                mVerificationField.setError("Invalid code.");
                            }

                            updateUI(signIn_failed);
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(initialized);
    }

    private Fragment createFragment()
    {
        Bundle bundle=new Bundle();
        bundle.putString("phone",phoneNumber);


        phProfile facebookProfileFragment=new phProfile();
        facebookProfileFragment.setArguments(bundle);
        return facebookProfileFragment;

    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(signIn_success, user);
        } else {
            updateUI(initialized);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case initialized:
                enableViews(mStartButton, mPhoneNumberField);
                disableViews(mVerifyButton, mResendButton, mVerificationField);
                mDetailText.setText(null);
                break;
            case code_sent:
                enableViews(mVerifyButton, mResendButton, mPhoneNumberField, mVerificationField);
                disableViews(mStartButton);
               // mDetailText.setText("status_code_sent");
                Toast.makeText(getContext(), "OTP SENT", Toast.LENGTH_SHORT).show();
                break;
            case verify_fail:
                enableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,
                        mVerificationField);
              //  mDetailText.setText("status_verification_failed");

                Toast.makeText(getContext(), "Verification Failed", Toast.LENGTH_SHORT).show();
                break;
            case verify_success:
                disableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,
                        mVerificationField);
             //   mDetailText.setText("status_verification_succeeded");
                Toast.makeText(getContext(), "Verification Succeed", Toast.LENGTH_SHORT).show();
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        mVerificationField.setText(cred.getSmsCode());
                    } else {
                        mVerificationField.setText("instant_validation");
                    }
                }

                break;
            case signIn_failed:
                mDetailText.setText(R.string.status_sign_in_failed);
                break;
            case signIn_success:
                break;
        }
        if (user == null) {
            mPhoneNumberViews.setVisibility(View.VISIBLE);
            mSignedInViews.setVisibility(View.GONE);

           // mStatusText.setText("signed_out");
            Toast.makeText(getContext(), "Sign Out", Toast.LENGTH_SHORT).show();
        } else {

            mPhoneNumberViews.setVisibility(View.GONE);
            mSignedInViews.setVisibility(View.VISIBLE);

            enableViews(mPhoneNumberField, mVerificationField);
            mPhoneNumberField.setText(null);
            mVerificationField.setText(null);
            //mStatusText.setText("signed_in");
            mStatusText.setText(user.getPhoneNumber());
            Toast.makeText(getContext(), "Logged In", Toast.LENGTH_SHORT).show();
          // Fragment fragment = new phProfile();
            //FragmentManager fragmentManager = getFragmentManager();
            //fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame_layout,createFragment()).commit();

        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberField.setError("Invalid phone number.");
            return false;
        }

        return true;
    }

    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_start_verification:

                if (!validatePhoneNumber()) {
                    return;
                }

                startPhoneNumberVerification(mPhoneNumberField.getText().toString());
                break;
            case R.id.button_verify_phone:
                String code = mVerificationField.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    mVerificationField.setError("Cannot be empty.");
                    return;
                }

                verifyPhoneNumberWithCode(mVerificationId, code);
                break;
            case R.id.button_resend:
                resendVerificationCode(mPhoneNumberField.getText().toString(), mResendToken);
                break;
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }
}