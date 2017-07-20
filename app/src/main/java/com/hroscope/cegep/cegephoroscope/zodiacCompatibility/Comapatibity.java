package com.hroscope.cegep.cegephoroscope.zodiacCompatibility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hroscope.cegep.cegephoroscope.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comapatibity extends AppCompatActivity implements View.OnClickListener{
    private CircleImageView userzodiacImage,user_friendzodiacImage;
    private TextView compatibleresultTextviewl;
    private String zodiac_title;
    CircleImageView ariesButton,taurusButton,geminiButton,cancerButton,leoButton,virgoButton,libraButton,scorpioButton,
            sagittarusButton,capricornButton, aquariusButton,piscesButton;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private static String compatiblityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comapatibity);

        setControlls();
        setResultsTowidgets();
    }

    public void setControlls()
    {
        firebaseAuth= FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://cegephoroscope-3dcdb.appspot.com/");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Horoscope");

        userzodiacImage=(CircleImageView)findViewById(R.id.ariesCompatibilityButton);
        user_friendzodiacImage=(CircleImageView)findViewById(R.id.tauruscompatibilityButton);
        compatibleresultTextviewl=(TextView)findViewById(R.id.compatibleresult);

        ariesButton = (CircleImageView)findViewById(R.id.ariesButton);
        ariesButton.setOnClickListener(this);
        taurusButton = (CircleImageView)findViewById(R.id.taurusButton);
        taurusButton.setOnClickListener(this);
        geminiButton = (CircleImageView)findViewById(R.id.geminiButton);
        geminiButton.setOnClickListener(this);
        cancerButton = (CircleImageView)findViewById(R.id.cancerButton);
        cancerButton.setOnClickListener(this);
        leoButton = (CircleImageView)findViewById(R.id.leoButton);
        leoButton.setOnClickListener(this);
        virgoButton = (CircleImageView)findViewById(R.id.virgoButton);
        virgoButton.setOnClickListener(this);
        libraButton = (CircleImageView)findViewById(R.id.libraButton);
        libraButton.setOnClickListener(this);
        scorpioButton = (CircleImageView)findViewById(R.id.scorpioButton);
        scorpioButton.setOnClickListener(this);
        sagittarusButton = (CircleImageView)findViewById(R.id.sagittariusButton);
        sagittarusButton.setOnClickListener(this);
        capricornButton = (CircleImageView)findViewById(R.id.capricornButton);
        capricornButton.setOnClickListener(this);
        aquariusButton = (CircleImageView)findViewById(R.id.aquariusButton);
        aquariusButton.setOnClickListener(this);
        piscesButton = (CircleImageView)findViewById(R.id.piscesButton);
        piscesButton.setOnClickListener(this);

    }

    public void setResultsTowidgets()
    {

        zodiac_title = getIntent().getStringExtra("zodiac_click");


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



    }

    @Override
    public void onClick(View view) {
        if(view==ariesButton)
        {
            Toast.makeText(Comapatibity.this,"You are here1",Toast.LENGTH_SHORT).show();


           Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("text", "Aries");
            intent.putExtra("friend_sign",zodiac_title);
            startActivity(intent);
        }
        if(view==taurusButton)
        {

            Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("text", "Taurus");
            intent.putExtra("friend_sign",zodiac_title);
            startActivity(intent);
        }
        if(view==geminiButton)
        {

            Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("text", "Gemini");
            intent.putExtra("friend_sign",zodiac_title);
            startActivity(intent);
        }
        if(view==cancerButton)
        {

            Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("text", "Cancer");
            intent.putExtra("friend_sign",zodiac_title);
            startActivity(intent);
        }
        if(view==leoButton)
        {

            Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("text", "Leo");
            intent.putExtra("friend_sign",zodiac_title);
            startActivity(intent);
        }
        if(view==virgoButton)
        {

            Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("text", "Virgo");
            intent.putExtra("friend_sign",zodiac_title);
            startActivity(intent);
        }
        if(view==libraButton)
        {

            Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("friend_sign",zodiac_title);
            intent.putExtra("text", "Libra");
            startActivity(intent);
        }
        if(view==scorpioButton)
        {

            Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("friend_sign",zodiac_title);
            intent.putExtra("text", "Scorpio");
            startActivity(intent);
        }
        if(view==sagittarusButton)
        {

            Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("friend_sign",zodiac_title);
            intent.putExtra("text", "Sagittarus");
            startActivity(intent);
        }
        if(view==capricornButton)
        {

            Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("friend_sign",zodiac_title);
            intent.putExtra("text", "Capricorn");
            startActivity(intent);
        }
        if(view==aquariusButton)
        {

            Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("friend_sign",zodiac_title);
            intent.putExtra("text", "Aquarius");

            startActivity(intent);
        }
        if(view==piscesButton)
        {

            Intent intent = new Intent(Comapatibity.this,CompatibilityResult.class);
            intent.putExtra("friend_sign",zodiac_title);
            intent.putExtra("text", "Pisces");
            startActivity(intent);
        }

    }
}
