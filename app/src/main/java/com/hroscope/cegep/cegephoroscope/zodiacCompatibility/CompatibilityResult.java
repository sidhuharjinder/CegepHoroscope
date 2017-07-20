package com.hroscope.cegep.cegephoroscope.zodiacCompatibility;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hroscope.cegep.cegephoroscope.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class CompatibilityResult extends AppCompatActivity {
    private CircleImageView userzodiacImage,user_friendzodiacImage;
    private TextView compatibleresultTextviewl;
    private String zodiac_title,friend_zodiac_tile;
    private static String compresult;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private static String compatiblityData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatibility_result);


        setcontrolls();
        setResultTowidgets();
    }



    public void setcontrolls()
    {
        firebaseAuth= FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://cegephoroscope-3dcdb.appspot.com/");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Horoscope");
        userzodiacImage=(CircleImageView)findViewById(R.id.ariesCompatibilityButton);
        user_friendzodiacImage=(CircleImageView)findViewById(R.id.tauruscompatibilityButton);
        compatibleresultTextviewl=(TextView)findViewById(R.id.compatibleresult);


    }

    public void setResultTowidgets()
    {
        zodiac_title = getIntent().getStringExtra("friend_sign");
        friend_zodiac_tile=getIntent().getStringExtra("text");





         //Set Zodiac Image
        if (zodiac_title.equals("Aries"))
            userzodiacImage.setImageResource(R.mipmap.aries);

        else if (zodiac_title.equals("Taurus"))
            userzodiacImage.setImageResource(R.mipmap.taurus);

        else if (zodiac_title.equals("Gemini"))
            userzodiacImage.setImageResource(R.mipmap.gemini);


        else if (zodiac_title.equals("Cancer"))
            userzodiacImage.setImageResource(R.mipmap.cancer);


        else if (zodiac_title.equals("Leo"))
            userzodiacImage.setImageResource(R.mipmap.leo);


        else if (zodiac_title.equals("Virgo"))
            userzodiacImage.setImageResource(R.mipmap.virgo);


        else if (zodiac_title.equals("Libra"))
            userzodiacImage.setImageResource(R.mipmap.libra);


        else if (zodiac_title.equals("Scorpio"))
            userzodiacImage.setImageResource(R.mipmap.scorpio);


        else if (zodiac_title.equals("Sagittarus"))
            userzodiacImage.setImageResource(R.mipmap.sagittarius);


        else if (zodiac_title.equals("Capricorn"))
            userzodiacImage.setImageResource(R.mipmap.capricorn);


        else if (zodiac_title.equals("Aquarius"))
            userzodiacImage.setImageResource(R.mipmap.aquarius);


        else if (zodiac_title.equals("Pisces"))
            userzodiacImage.setImageResource(R.mipmap.pisces);



   //set friend zodiac Image
        if(friend_zodiac_tile.equals("Aries"))
            user_friendzodiacImage.setImageResource(R.mipmap.aries);
        else if(friend_zodiac_tile.equals("Taurus"))
            user_friendzodiacImage.setImageResource(R.mipmap.taurus);
        else if(friend_zodiac_tile.equals("Gemini"))
        user_friendzodiacImage.setImageResource(R.mipmap.gemini);
        else if(friend_zodiac_tile.equals("Cancer"))
        user_friendzodiacImage.setImageResource(R.mipmap.cancer);
        else if(friend_zodiac_tile.equals("Leo"))
        user_friendzodiacImage.setImageResource(R.mipmap.leo);
        else if(friend_zodiac_tile.equals("Virgo"))
            user_friendzodiacImage.setImageResource(R.mipmap.virgo);
        else if(friend_zodiac_tile.equals("Libra"))
            user_friendzodiacImage.setImageResource(R.mipmap.libra);
        else if(friend_zodiac_tile.equals("Scorpio"))
            user_friendzodiacImage.setImageResource(R.mipmap.scorpio);
        else if(friend_zodiac_tile.equals("Sagittarus"))
            user_friendzodiacImage.setImageResource(R.mipmap.sagittarius);
        else if(friend_zodiac_tile.equals("Capricorn"))
            user_friendzodiacImage.setImageResource(R.mipmap.capricorn);
        else if(friend_zodiac_tile.equals("Aquarius"))
            user_friendzodiacImage.setImageResource(R.mipmap.aquarius);
        else if(friend_zodiac_tile.equals("Pisces"))
            user_friendzodiacImage.setImageResource(R.mipmap.pisces);



        ValueEventListener postListenerYearly = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //ZodiacTodayList user = dataSnapshot.getValue(ZodiacTodayList.class);
                compresult = dataSnapshot.getValue().toString();
                compatibleresultTextviewl.setText(compresult);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.child("Compatibility").child(zodiac_title).child(friend_zodiac_tile).addListenerForSingleValueEvent(postListenerYearly);



    }


}
