package com.hroscope.cegep.cegephoroscope;

import android.widget.EditText;

/**
 * Created by SACHIN on 3/29/2017.
 */

public class Validation {


    public boolean validationRegister(EditText firstname,EditText lastname,EditText email,EditText phone,EditText password,EditText repassword) {

        boolean status = true;

        String first_name = firstname.getText().toString();
        String last_name=lastname.getText().toString();
        String user_email = email.getText().toString();
        String user_phone = phone.getText().toString();
        String user_password = password.getText().toString();
        String confirm_password =repassword.getText().toString();


        if (first_name.isEmpty() || first_name.length() < 4) {
            firstname.setError("first name must be 4 characters long");
            status = false;
        } else {
            firstname.setError(null);
        }

        if (last_name.isEmpty() || last_name.length() < 4) {
            lastname.setError("last name must be 4 characters long");
            status = false;
        } else {
            lastname.setError(null);
        }


        if (user_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
            email.setError("enter a valid email address");
            status = false;
        } else {
            email.setError(null);
        }


        if (user_phone.isEmpty() || user_phone.length() < 10) {
            phone.setError("Phone no must be 10 numeric digits");
            status = false;
        } else {
            phone.setError(null);
        }


        if (user_password.isEmpty() || user_password.length() < 5 || user_password.length() > 12) {
            password.setError("Password must be 5 to 12 alphanumeric characters");
            status = false;
        } else {
            password.setError(null);
        }

        if (confirm_password.isEmpty() || confirm_password.length() < 5 || confirm_password.length() > 12 || !(confirm_password.equals(user_password))) {
            repassword.setError("Password Do not match");
            status = false;
        } else {
            repassword.setError(null);
        }

        return status;
    }


    public boolean validationSignin(EditText email,EditText password) {

        boolean status = true;


        String user_email = email.getText().toString();

        String user_password = password.getText().toString();


        if (user_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
            email.setError("enter a valid email address");
            status = false;
        } else {
            email.setError(null);
        }



        if (user_password.isEmpty() || user_password.length() < 5 || user_password.length() > 12) {
            password.setError("Password must be 5 to 12 alphanumeric characters");
            status = false;
        } else {
            password.setError(null);
        }
        return status;
    }

    public boolean validateResetpassword(EditText email) {

        boolean status = true;


        String user_email = email.getText().toString();




        if (user_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
            email.setError("enter a valid email address");
            status = false;
        } else {
            email.setError(null);
        }



        return status;
    }

}
