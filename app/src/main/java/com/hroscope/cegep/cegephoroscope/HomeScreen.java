package com.hroscope.cegep.cegephoroscope;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hroscope.cegep.cegephoroscope.zodiacDetail.ZodiaDetail;
import com.hroscope.cegep.cegephoroscope.zodiacDetail.ZodiacHoroscope;
import com.hroscope.cegep.cegephoroscope.zodiacDetail.ZodiacTodayList;

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
    private String[] zodiacName = {"Aries","Taurus"};
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
                                selectedFragment = Profile.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });


        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, SignupFragment.newInstance());
        transaction.commit();
        initializeFirebase();
        setDataToWidgetsARIES();

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
        databaseReference.child("Zodiac").child("Aries").child("Daily").child("16-07-2017").addListenerForSingleValueEvent(postListenerCurrent);

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
        databaseReference.child("Zodiac").child("Aries").child("Daily").child("15-07-2017").addListenerForSingleValueEvent(postListenerTomorrow);

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
        databaseReference.child("Zodiac").child("Aries").child("Daily").child("14-07-2017").addListenerForSingleValueEvent(postListenerYesterDay);

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

}
