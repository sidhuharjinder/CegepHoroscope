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

    //widets for today
    public ImageView todayPersonProfile,todayShare,todayZodImage;
    public TextView todayTitlezod,todayzodinfo;
    String zod_name="";

    View  today,tomorrow;
    PagerContainer mContainer;
    ViewPager pager;
    PagerAdapter adapter;
    int[] mResources = {
            R.layout.today,
            R.layout.tomorrow,
            R.layout.today,
            R.layout.tomorrow,

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zodia_detail);
        initializeviews();
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
         todayPersonProfile = (ImageView)today.findViewById(R.id.todaypersonimg);
        todayShare = (ImageView)today.findViewById(R.id.todayimgeshare);
        todayZodImage = (ImageView)today.findViewById(R.id.todayzodimage);
        todayTitlezod=(TextView)today.findViewById(R.id.todaytitlezod);
        todayzodinfo=(TextView)today.findViewById(R.id.todayzodinfo);


    }
    public void setDataToWidgets()
    {
        //set data for Today
        //Zodiac Title Text
        String zodiac_title = getIntent().getStringExtra("text");
        todayTitlezod.setGravity(Gravity.CENTER);
        todayTitlezod.setTextSize(30);
        todayTitlezod.setTextColor(Color.rgb(255,255,255));
        //make title Bold
        final SpannableStringBuilder sb = new SpannableStringBuilder(zodiac_title);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);

        //curent day
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);



       // todayTitlezod.setText(zodiac_title );

         todayZodImage.setImageResource(R.mipmap.aries);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);

                  todayTitlezod.setText(user.summary);
                Toast.makeText(ZodiaDetail.this, user.summary,

                        Toast.LENGTH_SHORT).show();
                todayzodinfo.setText(user.summary.toString());



                    //load appropriate image to zodiac sign imageview
                    storageRef.child("Signs").child("Zodiac_Signs").child(zod_name + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.with(ZodiaDetail.this).load(uri.toString()).resize(600, 200).centerInside().into(todayZodImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // File not found
                        }
                    });


                }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Zodiac").child(zodiac_title).child("Daily").child("today").addValueEventListener(postListener);

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

    private  class MyPagerAdapter extends  PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public MyPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            today = mLayoutInflater.inflate(R.layout.today, container, false);
            tomorrow = mLayoutInflater.inflate(R.layout.tomorrow, container, false);
            View viewrrr [] ={today,tomorrow,today,tomorrow};


            setContolls();
           setDataToWidgets();
            container.addView(viewrrr[position]);

            return viewrrr[position];
        }



        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
