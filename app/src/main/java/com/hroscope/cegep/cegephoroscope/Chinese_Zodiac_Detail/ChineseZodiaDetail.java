package com.hroscope.cegep.cegephoroscope.Chinese_Zodiac_Detail;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hroscope.cegep.cegephoroscope.R;

import java.util.Calendar;


public class ChineseZodiaDetail extends Activity {
   //firebase
   FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    public static String currentYear;

    //widets for today
    public ImageView todayPersonProfile,todayShare,todayZodImage;
    public TextView todayTitlezod,todayzodinfo,yearTextview;
    String zod_name="";
     static String summary="";
    String currentyear;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese_detail);


        setContolls();
        ReadFirebase_SetFriendList();

        initializeFirebase();

    }

   public void initializeFirebase()
   {
       firebaseAuth= FirebaseAuth.getInstance();

       storage = FirebaseStorage.getInstance();
       storageRef = storage.getReferenceFromUrl("gs://cegephoroscope-3dcdb.appspot.com/");
       database = FirebaseDatabase.getInstance();
       databaseReference = database.getReference("Horoscope");

   }
    public void setContolls()
    {
         todayPersonProfile = (ImageView)findViewById(R.id.todaypersonimg);
        todayShare = (ImageView)findViewById(R.id.todayimgeshare);

        todayZodImage = (ImageView)findViewById(R.id.todayzodimage);
        todayTitlezod=(TextView)findViewById(R.id.todaytitlezod);
        todayzodinfo=(TextView)findViewById(R.id.todayzodinfo);
        yearTextview=(TextView)findViewById(R.id.year);


    }
    public void setDataToWidgets()
    {

        final String zodiac_title = getIntent().getStringExtra("text");
        todayTitlezod.setGravity(Gravity.CENTER);
        todayTitlezod.setTextSize(30);
        todayTitlezod.setTextColor(Color.rgb(255,255,255));
        //make title Bold
        final SpannableStringBuilder sb = new SpannableStringBuilder(zodiac_title);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

        todayTitlezod.setText(zodiac_title );
        todayzodinfo.setText(zodiac_title);
         todayZodImage.setImageResource(R.mipmap.aries);
        zod_name=zodiac_title.toLowerCase();

        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String Syear= String.valueOf(year);
        yearTextview.setGravity(Gravity.CENTER);
        yearTextview.setTextSize(25);
        yearTextview.setTextColor(Color.rgb(255,255,255));
        yearTextview.setText(Syear);

        // todayTitlezod.setText(year);


    }

    public void ReadFirebase_SetFriendList()
    {
        final String zodiac_title = getIntent().getStringExtra("text");
        todayTitlezod.setGravity(Gravity.CENTER);
        todayTitlezod.setTextSize(30);
        todayTitlezod.setTextColor(Color.rgb(255,255,255));
        //make title Bold
        final SpannableStringBuilder sb = new SpannableStringBuilder(zodiac_title);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

        todayTitlezod.setText(zodiac_title );


        zod_name=zodiac_title.toLowerCase();

        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = Calendar.getInstance().get(Calendar.YEAR);
        final String Syear= String.valueOf(year);
        yearTextview.setGravity(Gravity.CENTER);
        yearTextview.setTextSize(25);
        yearTextview.setTextColor(Color.rgb(255,255,255));
        yearTextview.setText(Syear);

        currentYear = getIntent().getStringExtra("current");
         todayzodinfo.setText(currentYear);

        if (zodiac_title.equals("Rat"))
            todayZodImage.setImageResource(R.mipmap.rat);
        else if (zodiac_title.equals("Ox"))
            todayZodImage.setImageResource(R.mipmap.ox);
        else if (zodiac_title.equals("Tiger"))
            todayZodImage.setImageResource(R.mipmap.tiger);
        else if (zodiac_title.equals("Rabbit"))
            todayZodImage.setImageResource(R.mipmap.rabbit);
        else if (zodiac_title.equals("Dragon"))
            todayZodImage.setImageResource(R.mipmap.dragon);
        else if (zodiac_title.equals("Snake"))
            todayZodImage.setImageResource(R.mipmap.snake);
        else if (zodiac_title.equals("Horse"))
            todayZodImage.setImageResource(R.mipmap.horse);
        else if (zodiac_title.equals("Goat"))
            todayZodImage.setImageResource(R.mipmap.goat);
        else if (zodiac_title.equals("Monkey"))
            todayZodImage.setImageResource(R.mipmap.monkey);
        else if (zodiac_title.equals("Rooster"))
            todayZodImage.setImageResource(R.mipmap.rooster);
        else if (zodiac_title.equals("Dog"))
            todayZodImage.setImageResource(R.mipmap.dog);
        else if (zodiac_title.equals("Pig"))
            todayZodImage.setImageResource(R.mipmap.pig);







    }




    }

