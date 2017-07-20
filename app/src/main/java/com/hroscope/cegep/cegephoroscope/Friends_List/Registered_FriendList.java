package com.hroscope.cegep.cegephoroscope.Friends_List;

/**
 * Created by SACHIN on 7/3/2017.
 */

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class Registered_FriendList {

  //To retrieve data following variable name must matches with the firebase key.
    public String Name;
    public String date_of_birth;
    public String zodiac_sign;
    public String chinese_zodiac_sign;


    public String getSign_name() {
        return sign_name;
    }

    public void setSign_name(String sign_name) {
        this.sign_name = sign_name;
    }

    public String sign_name;


    public Registered_FriendList() {
        // Default constructor required for calls to DataSnapshot.getValue(Email_Registered_UserList.class)
    }

    public Registered_FriendList(String first_name,  String date, String zodiac_sign, String chinese_zodiac_sign) {
        this.Name = first_name;

        this.date_of_birth = date;
        this.zodiac_sign=zodiac_sign;
        this.chinese_zodiac_sign=chinese_zodiac_sign;

    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("First_name", Name);
        result.put("Birthdate", date_of_birth);
        result.put("Zodiac_Sign",zodiac_sign);
        result.put("Chinese_Zodiac_Sign",chinese_zodiac_sign);


        return result;
    }


}
