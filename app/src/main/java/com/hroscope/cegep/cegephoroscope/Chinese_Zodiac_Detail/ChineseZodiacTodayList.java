package com.hroscope.cegep.cegephoroscope.Chinese_Zodiac_Detail;

/**
 * Created by SACHIN on 7/3/2017.
 */

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

// [START post_class]
@IgnoreExtraProperties
public class ChineseZodiacTodayList {

  //To retrieve data following variable name must matches with the firebase key.
    public String fullDetail;
    public String summary;
    public String Email;
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


    public ChineseZodiacTodayList() {
        // Default constructor required for calls to DataSnapshot.getValue(Email_Registered_UserList.class)
    }

    public ChineseZodiacTodayList(String first_name, String Last_name, String Email, String date, String zodiac_sign, String chinese_zodiac_sign, String sign_name) {
        this.fullDetail = first_name;
        this.summary = Last_name;

    }

    // [START post_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Full_Detail", fullDetail);
        result.put("Summary", summary);
        return result;
    }


}
