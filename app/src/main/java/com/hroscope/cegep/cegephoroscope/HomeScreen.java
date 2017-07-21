package com.hroscope.cegep.cegephoroscope;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hroscope.cegep.cegephoroscope.Chinese_Zodiac_Detail.ChineseHoroscope;
import com.hroscope.cegep.cegephoroscope.Email_SignIn.Email_Sigin_Fragment;
import com.hroscope.cegep.cegephoroscope.Email_SignIn.Email_Signin_Profile_Fragment;
import com.hroscope.cegep.cegephoroscope.Facebook_SignIn.FacebookFragment;
import com.hroscope.cegep.cegephoroscope.Facebook_SignIn.Fb_Profile_Fragment;
import com.hroscope.cegep.cegephoroscope.Phone_SignIn.PhoneLoginFragment;
import com.hroscope.cegep.cegephoroscope.Phone_SignIn.Phone_SignIn_Profile;
import com.hroscope.cegep.cegephoroscope.zodiacDetail.ZodiacHoroscope;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class HomeScreen extends AppCompatActivity {
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    public static String ariesDailyCurrent;
    public static String ariesDailyTomorrow;
    public static String ariesDailyYesterday;
    public static String ariesWeakly;
    public static String ariesMonthly;
    public static String ariesYearly;
    public static String taurusDailyCurrent;
    public static String taurusDailyTomorrow;
    public static String taurusDailyYesterday;
    public static String taurusWeakly;

    public static String taurusMonthly;
    public static String taurusYearly;
    public static String geminiDailyCurrent;
    public static String geminiDailyTomorrow;
    public static String geminiDailyYesterday;
    public static String geminiWeakly;
    public static String geminiMonthly;
    public static String geminiYearly;
    public static String cancerDailyCurrent;
    public static String cancerDailyTomorrow;
    public static String cancerDailyYesterday;
    public static String cancerWeakly;
    public static String cancerMonthly;
    public static String cancerYearly;
    public static String leoDailyCurrent;
    public static String leoDailyTomorrow;
    public static String leoDailyYesterday;
    public static String leoWeakly;
    public static String leoMonthly;
    public static String leoYearly;
    public static String virgoDailyCurrent;
    public static String virgoDailyTomorrow;
    public static String virgoDailyYesterday;
    public static String virgoWeakly;
    public static String virgoMonthly;
    public static String virgoYearly;
    public static String libraoDailyCurrent;
    public static String libraDailyTomorrow;
    public static String libraDailyYesterday;
    public static String libraWeakly;
    public static String libraMonthly;
    public static String libraYearly;
    public static String scorpioDailyCurrent;
    public static String scorpioDailyTomorrow;
    public static String scorpioDailyYesterday;
    public static String scorpioWeakly;
    public static String scorpioMonthly;
    public static String scorpioYearly;
    public static String sagittariusDailyCurrent;
    public static String sagittariusDailyTomorrow;
    public static String sagittariusDailyYesterday;
    public static String sagittariusWeakly;
    public static String sagittariusMonthly;
    public static String sagittariusYearly;
    public static String capriconDailyCurrent;
    public static String capriconDailyTomorrow;
    public static String capriconDailyYesterday;
    public static String capriconWeakly;
    public static String capriconMonthly;
    public static String capriconYearly;
    public static String piccesDailyCurrent;
    public static String piccesDailyTomorrow;
    public static String piccesDailyYesterday;
    public static String piccesWeakly;
    public static String piccesMonthly;
    public static String piccesYearly;
    public static String aquariusDailyCurrent;
    public static String aquariusDailyTomorrow;
    public static String aquariusDailyYesterday;
    public static String aquariusWeakly;
    public static String aquariusMonthly;
    public static String aquariusYearly;

    //chinese zodiac
    public static String  ratCurrentYear;
    public static String  oxCurrentYear;
    public static String  tigerCurrentYear;
    public static String  rabbitCurrentYear;
    public static String  dragonCurrentYear;
    public static String  snakeCurrentYear;
    public static String  horseCurrentYear;
    public static String  goatCurrentYear;
    public static String  monkeyCurrentYear;
    public static String  roosterCurrentYear;
    public static String  dogCurrentYear;
    public static String  pigCurrentYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);



        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = ZodiacHoroscope.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = ChineseHoroscope.newInstance();
                                break;
                            case R.id.action_item3:
                                if(Email_Sigin_Fragment.emailLoginStatus)
                                    selectedFragment = Email_Signin_Profile_Fragment.newInstance();

                                else if(FacebookFragment.facebookLoginStatus)
                                    selectedFragment = Fb_Profile_Fragment.newInstance();

                                else if(PhoneLoginFragment.phoneLoginStatus)
                                    selectedFragment = Phone_SignIn_Profile.newInstance();
                                else
                                    selectedFragment = Profile.newInstance();

                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        transaction.addToBackStack(null);
                        return true;
                    }
                });


        //Manually displaying the first fragment - one time only
        if(Email_Sigin_Fragment.emailLoginStatus) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, Email_Signin_Profile_Fragment.newInstance());
            transaction.addToBackStack(null);
            transaction.commit();
        }

        else if(FacebookFragment.facebookLoginStatus)
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, Fb_Profile_Fragment.newInstance());
            transaction.addToBackStack(null);
            transaction.commit();
        }


        else if(PhoneLoginFragment.phoneLoginStatus)
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, Phone_SignIn_Profile.newInstance());
            transaction.addToBackStack(null);
            transaction.commit();
        }


        else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, SignupFragment.newInstance());

            transaction.commit();
        }


        initializeFirebase();
        setDataToWidgetsARIES();
        setDataToWidgetsTaurus();
        setDataToWidgetsGemini();
        setDataToWidgetsCancer();
        setDataToWidgetsLeo();
        setDataToWidgetsVirgo();
        setDataToWidgetsLibra();
        setDataToWidgetsSagittarius();
        setDataToWidgetsAquarious();
        setDataToWidgetsCanpricon();
        setDataToWidgetsPisces();
        setDataToWidgetsScorpio();
        setDatawidgetsChinese();
    }
    public void initializeFirebase()
    {

        firebaseAuth= FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://cegephoroscope-3dcdb.appspot.com/");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Horoscope");

    }
    public void setDataToWidgetsARIES()
    {

        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               Log.d("Hello",dataSnapshot.getValue().toString());
               ariesDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aries").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                ariesDailyTomorrow = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aries").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
               ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                ariesDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aries").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                ariesMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aries").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                ariesWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aries").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                ariesYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aries").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);

        }
        //taurus
    public void setDataToWidgetsTaurus()
    {
        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                taurusDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Taurus").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                taurusDailyTomorrow = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Taurus").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
        ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                taurusDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Taurus").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                taurusMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Taurus").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                taurusWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Taurus").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                taurusYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Taurus").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);

    }
    //gemini
    public void setDataToWidgetsGemini()
    {
        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                geminiDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Gemini").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                geminiDailyTomorrow = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Gemini").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
        ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                geminiDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Gemini").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                geminiMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Gemini").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                geminiWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Gemini").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                geminiYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Gemini").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);

    }
    //Cancer
    public void setDataToWidgetsCancer()
    {
        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                cancerDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Cancer").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                cancerDailyTomorrow = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Cancer").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
        ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                cancerDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Cancer").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                cancerMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Cancer").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                cancerWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Cancer").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                cancerYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Cancer").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);

    }

    //Leo
    public void setDataToWidgetsLeo()
    {
        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                leoDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Leo").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                leoDailyTomorrow = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Leo").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
        ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                leoDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Leo").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                leoMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Leo").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                leoWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Leo").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                leoYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Leo").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);

    }
    //Virgo
    public void setDataToWidgetsVirgo()
    {
        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                virgoDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Virgo").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                virgoDailyTomorrow = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Virgo").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
        ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                virgoDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Virgo").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                virgoMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Virgo").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                virgoWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Virgo").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                virgoYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Virgo").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);

    }
    //Libra
    public void setDataToWidgetsLibra()
    {
        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                libraoDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Libra").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                libraDailyTomorrow = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Libra").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
        ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                libraDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Libra").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                libraMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Libra").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                libraWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Libra").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                libraYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Libra").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);

    }
    //Scorpio
    public void setDataToWidgetsScorpio()
    {
        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                scorpioDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Scorpio").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                scorpioDailyTomorrow = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Scorpio").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
        ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                scorpioDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Scorpio").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                scorpioMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Scorpio").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                scorpioWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Scorpio").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                scorpioYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Scorpio").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);

    }
    //Sagittarius
    public void setDataToWidgetsSagittarius()
    {
        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                sagittariusDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Sagittarius").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                sagittariusDailyTomorrow = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Sagittarius").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
        ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                sagittariusDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Sagittarius").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                sagittariusMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Sagittarius").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                sagittariusWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Sagittarius").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                sagittariusYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Sagittarius").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);

    }

    //Aquarious
    public void setDataToWidgetsAquarious()
    {
        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                aquariusDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aquarius").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                aquariusDailyTomorrow = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aquarius").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
        ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                aquariusDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aquarius").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                aquariusMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aquarius").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                aquariusWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aquarius").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                aquariusYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Aquarius").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);

    }
    //Pisces
    public void setDataToWidgetsPisces()
    {
        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                piccesDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Pisces").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                piccesDailyTomorrow = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Pisces").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
        ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                piccesDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Pisces").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                piccesMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Pisces").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                piccesWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Pisces").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                piccesYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Pisces").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);

    }
    //Conpricon
    public void setDataToWidgetsCanpricon()
    {
        ValueEventListener postListenerCurrent = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                capriconDailyCurrent = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Capricorn").child("Daily").child(getTodaysDate()).addListenerForSingleValueEvent(postListenerCurrent);

        //tomorrow
        ValueEventListener postListenerTomorrow = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                capriconDailyTomorrow = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Capricorn").child("Daily").child(getTomorrowDate()).addListenerForSingleValueEvent(postListenerTomorrow);

        //yesterday
        ValueEventListener postListenerYesterDay = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                Log.d("Hello",dataSnapshot.getValue().toString());
                capriconDailyYesterday = dataSnapshot.getValue().toString();;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Capricorn").child("Daily").child(getYesterdayDate()).addListenerForSingleValueEvent(postListenerYesterDay);

        //monthly
        ValueEventListener postListenerMonthly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                capriconMonthly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Capricorn").child("Monthly").addListenerForSingleValueEvent(postListenerMonthly);

        //weekly
        ValueEventListener postListenerWeekly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                capriconWeakly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Capricorn").child("Weekly").addListenerForSingleValueEvent(postListenerWeekly);

        //yearly
        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                capriconYearly = dataSnapshot.getValue().toString();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child("Capricorn").child("Yearly").addListenerForSingleValueEvent(postListenerYearly);
    }

    public void setDatawidgetsChinese()
    {
        //chinese Zodiac

        //current year
        String year= String.valueOf(getCurrentYear());

        //Rat Current Year
        ValueEventListener ratChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                ratCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Rat").child("Yearly").child(year).addListenerForSingleValueEvent(ratChinesecurrentYear);

        //Ox Current Year
        ValueEventListener oxChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                oxCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Ox").child("Yearly").child(year).addListenerForSingleValueEvent(oxChinesecurrentYear);

        //TIger Current Year
        ValueEventListener tigerChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                tigerCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Tiger").child("Yearly").child(year).addListenerForSingleValueEvent(tigerChinesecurrentYear);

        //Rabbit Current Year
        ValueEventListener rabbitChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                rabbitCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Rabbit").child("Yearly").child(year).addListenerForSingleValueEvent(rabbitChinesecurrentYear);

        //Dragon Current Year
        ValueEventListener dragonChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                dragonCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Dragon").child("Yearly").child(year).addListenerForSingleValueEvent( dragonChinesecurrentYear);

        //Snake Current Year
        ValueEventListener snakeChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                snakeCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Snake").child("Yearly").child(year).addListenerForSingleValueEvent(snakeChinesecurrentYear);

        //Horse Current Year
        ValueEventListener horseChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                horseCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Horse").child("Yearly").child(year).addListenerForSingleValueEvent(horseChinesecurrentYear);

        //Goat Current Year
        ValueEventListener goatChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                goatCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Sheep").child("Yearly").child(year).addListenerForSingleValueEvent(goatChinesecurrentYear);

        //Monkey Current Year
        ValueEventListener monkeyChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                monkeyCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Monkey").child("Yearly").child(year).addListenerForSingleValueEvent(monkeyChinesecurrentYear);

        //Rooster Current Year
        ValueEventListener roosterChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                roosterCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Rooster").child("Yearly").child(year).addListenerForSingleValueEvent(roosterChinesecurrentYear);

        //dog Current Year
        ValueEventListener dogChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                dogCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Dog").child("Yearly").child(year).addListenerForSingleValueEvent(dogChinesecurrentYear);

        //Pig Current Year
        ValueEventListener pigChinesecurrentYear = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Hello",dataSnapshot.getValue().toString());
                pigCurrentYear = dataSnapshot.getValue().toString();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Chinese").child("Pig").child("Yearly").child(year).addListenerForSingleValueEvent(pigChinesecurrentYear);


    }



    //Zodiac


    //Zodiac get Yesterday's Today
    private  String getYesterdayDate()
    {
        Calendar cal  = Calendar.getInstance();
        //subtracting a day
        cal.add(Calendar.DATE,-1);

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        String result = s.format(new Date(cal.getTimeInMillis()));
        return result;

    }

    // get Today's date
    private  String getTodaysDate()
    {
        Calendar cal  = Calendar.getInstance();
        //subtracting a day
        cal.add(Calendar.DATE,0);

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        String result = s.format(new Date(cal.getTimeInMillis()));
        return result;

    }

    //Zodiac get Tommorrow's
    private  String getTomorrowDate()
    {
        Calendar cal  = Calendar.getInstance();
        //subtracting a day
        cal.add(Calendar.DATE,1);

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        String result = s.format(new Date(cal.getTimeInMillis()));
        return result;

    }



    //chinese get years
    private static int getPreviousYear() {
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, -1);
        return prevYear.get(Calendar.YEAR);

    }
    private static int getCurrentYear()
    {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = Calendar.getInstance().get(Calendar.YEAR);

      return year;
    }


    private static int getNextYear() {
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, +1);
        return prevYear.get(Calendar.YEAR);

    }

}
