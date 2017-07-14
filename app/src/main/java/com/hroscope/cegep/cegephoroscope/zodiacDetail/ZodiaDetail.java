package com.hroscope.cegep.cegephoroscope.zodiacDetail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hroscope.cegep.cegephoroscope.PagerContainer;
import com.hroscope.cegep.cegephoroscope.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class ZodiaDetail extends Activity {
   //firebase
   FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    //widets for dayone
    public ImageView todayPersonProfile,todayShare,todayZodImage;
    String zod_name="";
    TextView titlezod;
    View  dayone,daytwo,daythree,dayfour,dayfive,daysix,dayseven,week;
    PagerContainer mContainer;
    ViewPager pager;
    static String sdl;
    PagerAdapter adapter;
    int[] mResources = {
            R.layout.dayone,
            R.layout.daytwo,
            R.layout.daythree,
            R.layout.dayfour,
            R.layout.dayfive,
            R.layout.daysix,
            R.layout.dayseven,
            R.layout.week};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zodia_detail);
        initializeviews();


    }



    private void initializeviews() {

        mContainer = (PagerContainer) findViewById(R.id.pager_container);
        pager = mContainer.getViewPager();
        adapter = new MyPagerAdapter(this);
        pager.setAdapter(adapter);
        //Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        pager.setOffscreenPageLimit(adapter.getCount());
        //A little space between pages
        pager.setPageMargin(50);
        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        pager.setClipChildren(false);
    }

//Nothing special about this adapter, just throwing up colored views for demo

    public class MyPagerAdapter extends  PagerAdapter {

        public TextView dayoneDate,daytwoDate,daythreeDate,dayfourDate,dayfiveDate,datesixDate,daysevenDate,weekdate;
        public TextView dayoneInfo,daytwoInfo,daythreeInfo,dayfourInfo,dayfiveInfo,daysixInfo,daysevenInfo,weekinfo;
        public TextView dayOneTitle,dayTwoTitle,dayThreeTitle,dayFourTitle,dayFiveTitle,daySixTitle,daySevenTitle,weektitle;

        Context mContext;
        LayoutInflater mLayoutInflater;

        public MyPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        public void initializeFirebase()
        {
            firebaseAuth= FirebaseAuth.getInstance();
            storage = FirebaseStorage.getInstance();
            storageRef = storage.getReferenceFromUrl("gs://cegephoroscope-3dcdb.appspot.com/");
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("Horoscope");

        }

        public void setDataToWidgets()
        {
            //set data for Today
            //Zodiac Title Text
            final String zodiac_title = getIntent().getStringExtra("text");
            titlezod.setGravity(Gravity.CENTER);
            titlezod.setTextSize(30);
            titlezod.setTextColor(Color.rgb(255,255,255));
            //make title Bold
            final SpannableStringBuilder sb = new SpannableStringBuilder(zodiac_title);
            final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

            //curent day
            final SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date d = new Date();
            String dayOfTheWeek = sdf.format(d);
            titlezod.setText(zodiac_title );
            todayZodImage.setImageResource(R.mipmap.aries);

            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                    sdl = user.summary.toString();
                    dayoneInfo.setText(sdl);
                    Toast.makeText(ZodiaDetail.this, user.summary,Toast.LENGTH_SHORT).show();
                    //dayoneInfo.setText(user.summary.toString());
                    // dayoneInfo.setText("Hello");
                    //load appropriate image to zodiac sign imageview
//                    storageRef.child("Signs").child("Zodiac_Signs").child(zod_name + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            Picasso.with(ZodiaDetail.this).load(uri.toString()).resize(600, 200).centerInside().into(todayZodImage);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            // File not found
//                        }
//                    });


                }



                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                }
            };
            databaseReference.child("Zodiac").child(zodiac_title).child("Daily").child("today").addValueEventListener(postListener);

        }
        public void setContolls()
        {
            titlezod = (TextView)findViewById(R.id.titlezod);
            todayPersonProfile = (ImageView)findViewById(R.id.todaypersonimg);
            todayShare = (ImageView)findViewById(R.id.todayimgeshare);
            todayZodImage = (ImageView)findViewById(R.id.todayzodimage);
            //day one
            dayOneTitle = (TextView)dayone.findViewById(R.id.dayOneTitle);
            dayoneInfo = (TextView)dayone.findViewById(R.id.datoneInfo);
            dayoneDate = (TextView)dayone.findViewById(R.id.dayOneDate);
            //day Two
            dayTwoTitle = (TextView)daytwo.findViewById(R.id.daytwoTitle);
            daytwoInfo = (TextView)daytwo.findViewById(R.id.tdaytwoInfo);
            daytwoDate = (TextView)daytwo.findViewById(R.id.daytwoDate);
            //day Three
            dayThreeTitle = (TextView)daythree.findViewById(R.id.daythreetitle);
            daythreeInfo = (TextView)daythree.findViewById(R.id.daythreeinfo);
            daythreeDate = (TextView)daythree.findViewById(R.id.daythreedate);
            //day four
            dayFourTitle = (TextView)dayfour.findViewById(R.id.dayfourTitle);
            dayfourInfo = (TextView)dayfour.findViewById(R.id.dayfourinfo);
            dayfourDate = (TextView)dayfour.findViewById(R.id.dayfourDate);
            //day five
            dayFiveTitle = (TextView)dayfive.findViewById(R.id.dayfiveTitle);
            dayfiveInfo = (TextView)dayfive.findViewById(R.id.dayfiveinfo);
            dayfiveDate = (TextView)dayfive.findViewById(R.id.dayfiveDate);
            //day six
            daySixTitle = (TextView)daysix.findViewById(R.id.daysixTitle);
            daysixInfo = (TextView)daysix.findViewById(R.id.daysixinfo);
            datesixDate = (TextView)daysix.findViewById(R.id.daysixDate);
            //day seven
            daySevenTitle = (TextView)dayseven.findViewById(R.id.daysevenTitle);
            daysevenInfo = (TextView)dayseven.findViewById(R.id.dayseveninfo);
            daysevenDate = (TextView)dayseven.findViewById(R.id.daysevenDate);
            //week
            weektitle= (TextView)dayseven.findViewById(R.id.weekTitle);
            weekinfo = (TextView)dayseven.findViewById(R.id.weekInfo);
            weekdate = (TextView)dayseven.findViewById(R.id.weekdate);

        }
        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            dayone = mLayoutInflater.inflate(R.layout.dayone, container, false);
            daytwo = mLayoutInflater.inflate(R.layout.daytwo, container, false);
            daythree = mLayoutInflater.inflate(R.layout.daythree, container, false);
            dayfour = mLayoutInflater.inflate(R.layout.dayfour, container, false);
            dayfive = mLayoutInflater.inflate(R.layout.dayfive, container, false);
            daysix = mLayoutInflater.inflate(R.layout.daysix, container, false);
            dayseven = mLayoutInflater.inflate(R.layout.dayseven, container, false);
            week = mLayoutInflater.inflate(R.layout.week, container, false);
            View viewrrr [] ={dayone,daytwo,daythree,dayfour,dayfive,daysix,dayseven,week};
            setContolls();
            //dayoneInfo.setText("Hello");
            initializeFirebase();
            setDataToWidgets();
            dayoneInfo.setText(sdl);
            container.addView(viewrrr[position]);

            return viewrrr[position];
        }



        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
