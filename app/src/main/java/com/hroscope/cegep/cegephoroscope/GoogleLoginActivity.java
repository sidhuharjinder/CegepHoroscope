package com.hroscope.cegep.cegephoroscope;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;

public class GoogleLoginActivity extends AppCompatActivity {
    private SignInButton google_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.continue_google);

        google_login_button =(SignInButton)findViewById(R.id.googleplus);
        google_login_button.setSize(SignInButton.SIZE_WIDE);
        setGoogleTextAllCaps(google_login_button,true);
       setGoogleButtonText(google_login_button,"Continue with Google");

    }

    public static void setGoogleTextAllCaps(SignInButton signInButton, boolean allCaps)
    {
        for (int i = 0; i < signInButton.getChildCount(); i++)
        {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView)
            {
                TextView tv = (TextView) v;
                tv.setAllCaps(allCaps);
                return;
            }
        }
    }

    protected void setGoogleButtonText(SignInButton signInButton,
                                       String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setTextSize(20);


                tv.setTypeface(null, Typeface.NORMAL);
                tv.setText(buttonText);
                return;
            }
        }
    }





}
