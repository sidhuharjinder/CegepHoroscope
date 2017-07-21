package com.hroscope.cegep.cegephoroscope.Friend_Save_FriendList;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hroscope.cegep.cegephoroscope.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SaveFriend extends AppCompatActivity implements View.OnClickListener{
    private CircleImageView profile_Image;
    private ImageView editName,calender,editDate,zodSign,editZodSign,chineseSign,editChineseSign;

    private TextView initials;
    private EditText name,dateOfBirth,zodiacName,chineseZodName;
    private Button buttonsave;
    private FirebaseAuth firebaseAuth;
    private ImageButton button_gallary,button_upload;
    private static final int PICK_IMAGE_REQUEST = 111,CAPTURE_IMG_REQUEST=120;
    int chinese_img,zodiac_imag;
    Uri filePath,zodiacImage;
    ProgressDialog pd;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;

    DatabaseReference databaseReference;
    String userUID,birthdate,updated_email;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    FirebaseUser user;
    String nme;
    String zodiac_sign_name,chi_zodiac_sign_name;
    String zod_name="",chi_name="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_friend);
        setControlls();
        setDataTime();
        pd.dismiss();
    }

    public void setControlls()
    {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        firebaseAuth= FirebaseAuth.getInstance();
        userUID=firebaseAuth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://cegephoroscope-3dcdb.appspot.com/");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Registration_Data").child("Users_Friend_List").child(userUID);

        //Imageview
        profile_Image=(CircleImageView)findViewById(R.id.profile);
        profile_Image.setOnClickListener(this);
        editName=(ImageView)findViewById(R.id.editname);
        editName.setOnClickListener(this);
        calender=(ImageView)findViewById(R.id.EditDate);
        calender.setOnClickListener(this);
        editDate=(ImageView)findViewById(R.id.EditDate);
        editDate.setOnClickListener(this);
        zodSign=(ImageView)findViewById(R.id.zodSign);
        zodSign.setOnClickListener(this);
        editZodSign=(ImageView)findViewById(R.id.editzod);
        editZodSign.setOnClickListener(this);
        chineseSign=(ImageView)findViewById(R.id.chineseSign);
        chineseSign.setOnClickListener(this);
        editChineseSign=(ImageView)findViewById(R.id.editchinesezod);
        editChineseSign.setOnClickListener(this);
        buttonsave =(Button)findViewById(R.id.buttonSave);
        buttonsave.setOnClickListener(this);

        //Textview
        initials=(TextView)findViewById(R.id.initials);
        //Edittext
        name=(EditText)findViewById(R.id.name);
        dateOfBirth=(EditText)findViewById(R.id.dateOfBirth);
        zodiacName=(EditText)findViewById(R.id.zodiac);
        chineseZodName=(EditText)findViewById(R.id.chineseHoroscope);

        button_gallary =(ImageButton)findViewById(R.id.gallary);
        button_gallary.setOnClickListener(this);
        button_gallary.setVisibility(View.INVISIBLE);
        button_upload=(ImageButton)findViewById(R.id.upload);
        button_gallary.setOnClickListener(this);
        button_upload.setOnClickListener(this);
        button_upload.setVisibility(View.INVISIBLE);




        //visibility
        name.setEnabled(false);
        dateOfBirth.setEnabled(false);
        zodiacName.setEnabled(false);
        chineseZodName.setEnabled(false);

        pd = new ProgressDialog(SaveFriend.this);
        pd.setMessage("Uploading....");
    }


    public void fromGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        //to receive image result call this activity
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    public void upload(String UniqueID)
    {
        if(filePath != null) {
            pd.show();

            String useruid=firebaseAuth.getCurrentUser().getUid();


            StorageReference databaseReference = storageRef.child("Email_Registration").child("Users_Friend_Images").child(useruid).child(UniqueID).child("image.jpg");

            //uploading the image
            UploadTask uploadTask = databaseReference.putFile(filePath);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    Toast.makeText(SaveFriend.this, "Upload successful", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(SaveFriend.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(SaveFriend.this, "Select an image", Toast.LENGTH_SHORT).show();
        }

    }

    //Profile Picture Method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(SaveFriend.this.getContentResolver(), filePath);

                //Setting image to ImageView
                profile_Image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        button_gallary.setVisibility(View.VISIBLE);

    }

    public void updateData_loadTofirebase(String UniqueID)
    {

        pd.setMessage("Saving....");
        birthdate=dateOfBirth.getText().toString();
        updated_email=name.getText().toString();

        String nameInitial=updated_email.substring(0,1);
        initials.setGravity(Gravity.CENTER);
        initials.setTextSize(30);
        initials.setTextColor(Color.parseColor("#000000"));
        //make initial Bold
        final SpannableStringBuilder sb = new SpannableStringBuilder(nameInitial);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        initials.setText(nameInitial.toUpperCase());

        int day=Integer.parseInt(birthdate.substring(0,2));
        int month= Integer.parseInt(birthdate.substring(3,5));
        int year=Integer.parseInt(birthdate.substring(6,10));

        //Get date wise zodiac Sign
        if ((month == 12 && day >= 22 && day <= 31) || (month ==  1 && day >= 01 && day <= 19))
            zodiac_sign_name="Capricorn";

        else if ((month ==  01 && day >= 20 && day <= 31) || (month ==  2 && day >= 01 && day <= 17))
            zodiac_sign_name="Aquarius";
        else if ((month ==  02 && day >= 18 && day <= 29) || (month ==  3 && day >= 01 && day <= 19))
            zodiac_sign_name="Pisces";
        else if ((month ==  03 && day >= 20 && day <= 31) || (month ==  4 && day >= 01 && day <= 19))
            zodiac_sign_name="Aries";
        else if ((month ==  04 && day >= 20 && day <= 30) || (month ==  5 && day >= 01 && day <= 20))
            zodiac_sign_name="Taurus";
        else if ((month ==  05 && day >= 21 && day <= 31) || (month ==  6 && day >= 01 && day <= 20))
            zodiac_sign_name="Gemini";
        else if ((month ==  06 && day >= 21 && day <= 30) || (month ==  7 && day >= 01 && day <= 22))
            zodiac_sign_name="Cancer";
        else if ((month ==  07 && day >= 23 && day <= 31) || (month ==  8 && day >= 01 && day <= 22))
            zodiac_sign_name="Leo";
        else if ((month ==  8 && day >= 23 && day <= 31) || (month ==  9 && day >= 01 && day <= 22))
            zodiac_sign_name="Virgo";
        else if ((month ==  9 && day >= 23 && day <= 30) || (month == 10 && day >= 01 && day <= 22))
            zodiac_sign_name="Libra";
        else if ((month == 10 && day >= 23 && day <= 31) || (month == 11 && day >= 01 && day <= 21))
            zodiac_sign_name="Scorpio";
        else if ((month == 11 && day >= 22 && day <= 30) || (month == 12 && day >= 01 && day <= 21))
            zodiac_sign_name="Sagittarius";

        //Get Chinese Zodiac Sign
        String[] chinese_zodiac_name={"Monkey","Rooster","Dog","Pig","Rat","Ox","Tiger","Rabbit","Dragon","Snake","Horse","Sheep"};
        int count=0;
        for(int i=1920;i<=year;i++)
        {
            if(year==i)
            {
                chi_zodiac_sign_name=chinese_zodiac_name[count];
                if(count==0) {
                    chinese_img=R.mipmap.monkey;
                }

            }
            count++;
            if(count>11)
                count=0;
        }
        zodiacName.setText(zodiac_sign_name);
        chineseZodName.setText(chi_zodiac_sign_name);

        //update Firebase data storage
        databaseReference.child(UniqueID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("Name").setValue(updated_email);
                dataSnapshot.getRef().child("date_of_birth").setValue(birthdate);
                dataSnapshot.getRef().child("zodiac_sign").setValue(zodiac_sign_name);
                dataSnapshot.getRef().child("chinese_zodiac_sign").setValue(chi_zodiac_sign_name);


                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Data Updated", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
                pd.dismiss();
            }
        });


    }

    private void setDataTime() {
        //   fromDateEtxt.setOnClickListener(this);
        final Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(SaveFriend.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateOfBirth.setGravity(Gravity.LEFT);
                dateFormatter.format(newDate.getTime());
                int month = Calendar.MONTH;
                newCalendar.get(month);

                dateOfBirth.setText( dateFormatter.format(newDate.getTime()));



            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if(view==profile_Image)
        {
            button_gallary.setVisibility(view.VISIBLE);
        }

        if(view== button_gallary)
        {


            fromGallary();

            button_upload.setVisibility(view.VISIBLE);

        }


        if(view==button_upload)
        {
            //upload();
            button_gallary.setVisibility(view.VISIBLE);

        }
        if(view==editName)
        {
            name.setEnabled(true);


        }
        if(view==calender)
        {
            name.setEnabled(false);
            dateOfBirth.setEnabled(true);
            zodiacName.setEnabled(false);
            chineseZodName.setEnabled(false);
            fromDatePickerDialog.show();

        }
        if(view==editDate)
        {
            name.setEnabled(false);
            dateOfBirth.setEnabled(true);
            zodiacName.setEnabled(false);
            chineseZodName.setEnabled(false);
            fromDatePickerDialog.show();

        }
        if(view==zodSign)
        {


        }

        if(view==editZodSign)
        {
            name.setEnabled(true);
            dateOfBirth.setEnabled(false);
            zodiacName.setEnabled(true);
            chineseZodName.setEnabled(false);

        }
        if(view==chineseSign)
        {

        }
        if(view==editChineseSign)
        {
            name.setEnabled(true);
            dateOfBirth.setEnabled(false);
            zodiacName.setEnabled(false);
            chineseZodName.setEnabled(true);

        }
        if(view== buttonsave)
        {

            name.setEnabled(false);
            dateOfBirth.setEnabled(false);
            zodiacName.setEnabled(false);
            chineseZodName.setEnabled(false);
            String fname=name.getText().toString();
            String birthdate=dateOfBirth.getText().toString();

            if (fname.isEmpty()) {


                name.setError("Please Provide your Friend Name");
            }
            if(birthdate.isEmpty())
            {

                dateOfBirth.setError("Please Select Friend's Birthdate");
            }

            else {


                String uniqueID = fname.concat(birthdate);
                name.setError(null);
                dateOfBirth.setError(null);
                upload(uniqueID);
                updateData_loadTofirebase(uniqueID);
                startActivity(new Intent(SaveFriend.this,FriendLIstActivity.class));


            }

        }



    }
}
