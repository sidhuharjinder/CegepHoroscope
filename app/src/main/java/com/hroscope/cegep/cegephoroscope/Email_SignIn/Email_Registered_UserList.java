package com.hroscope.cegep.cegephoroscope.Email_SignIn;

/**
 * Created by SACHIN on 7/3/2017.
 */

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Email_Registered_UserList {

  //To retrieve data following variable name must matches with the firebase key.
    public String first_name;
    public String Last_name;
    public String Email;
    public String date_of_birth;


    public Email_Registered_UserList() {
        // Default constructor required for calls to DataSnapshot.getValue(Email_Registered_UserList.class)
    }

    public Email_Registered_UserList(String first_name, String Last_name, String Email, String date) {
        this.first_name = first_name;
        this.Last_name = Last_name;
        this.Email = Email;
        this.date_of_birth = date;
    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("First_name", first_name);
        result.put("Last_name", Last_name);
        result.put("Email_address", Email);
        result.put("Birthdate", date_of_birth);


        return result;
    }


}
