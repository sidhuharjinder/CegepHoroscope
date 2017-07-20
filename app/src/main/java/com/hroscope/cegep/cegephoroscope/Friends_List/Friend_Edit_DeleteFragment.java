package com.hroscope.cegep.cegephoroscope.Friends_List;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by SACHIN on 7/7/2017.
 */


public class Friend_Edit_DeleteFragment extends Fragment implements View.OnClickListener  {
    private CircleImageView profile_Image;
    private ImageView editName,calender,editDate,zodSign,editZodSign,chineseSign,editChineseSign;

    private TextView initials;
    private EditText name,dateOfBirth,zodiacName,chineseZodName;
    private Button save,delete;
    private View view;

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

    public static Friend_Edit_DeleteFragment newInstance() {
        Friend_Edit_DeleteFragment fragment = new Friend_Edit_DeleteFragment();
        return fragment;
    }

    public Friend_Edit_DeleteFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.activity_editand_delete_friend, container, false);
         setControlls();
       /* if (firebaseAuth.getCurrentUser() != null) {
            //check image file is exist on location or not
            storageRef.child("Email_Registration").child("Users_Friend_Images").child(userUID).child("image.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(getActivity()).load(uri.toString()).resize(600,200).centerInside().into(profile_Image);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // File not found
                }
            });

        }*/
      //  ReadFirebase_setProfileData();
        setDataTime();
        pd.dismiss();
        return view;
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
        profile_Image=(CircleImageView)view.findViewById(R.id.profile);
        profile_Image.setOnClickListener(this);
        editName=(ImageView)view.findViewById(R.id.editname);
        editName.setOnClickListener(this);
        calender=(ImageView)view.findViewById(R.id.EditDate);
        calender.setOnClickListener(this);
        editDate=(ImageView)view.findViewById(R.id.EditDate);
        editDate.setOnClickListener(this);
        zodSign=(ImageView)view.findViewById(R.id.zodSign);
        zodSign.setOnClickListener(this);
        editZodSign=(ImageView)view.findViewById(R.id.editzod);
        editZodSign.setOnClickListener(this);
        chineseSign=(ImageView)view.findViewById(R.id.chineseSign);
        chineseSign.setOnClickListener(this);
        editChineseSign=(ImageView)view.findViewById(R.id.editchinesezod);
        editChineseSign.setOnClickListener(this);
        save=(Button)view.findViewById(R.id.buttonSave);
        save.setOnClickListener(this);
        delete=(Button)view.findViewById(R.id.buttonDelete);
        delete.setOnClickListener(this);
        //Textview
        initials=(TextView)view.findViewById(R.id.initials);
        //Edittext
        name=(EditText) view.findViewById(R.id.name);
        dateOfBirth=(EditText) view.findViewById(R.id.dateOfBirth);
        zodiacName=(EditText) view.findViewById(R.id.zodiac);
        chineseZodName=(EditText) view.findViewById(R.id.chineseHoroscope);

        button_gallary =(ImageButton)view.findViewById(R.id.gallary);
        button_gallary.setOnClickListener(this);
        button_gallary.setVisibility(view.INVISIBLE);
        button_upload=(ImageButton)view.findViewById(R.id.upload);
        button_gallary.setOnClickListener(this);
        button_upload.setOnClickListener(this);
        button_upload.setVisibility(view.INVISIBLE);




        //visibility
        name.setEnabled(false);
        dateOfBirth.setEnabled(false);
        zodiacName.setEnabled(false);
        chineseZodName.setEnabled(false);

        pd = new ProgressDialog(getActivity());
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
                    Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getActivity(), "Select an image", Toast.LENGTH_SHORT).show();
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                //Setting image to ImageView
                profile_Image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        button_gallary.setVisibility(view.VISIBLE);

    }

    public void updateData_loadTofirebase(String UniqueID)
    {

        pd.setMessage("Saving....");
        birthdate=dateOfBirth.getText().toString();
        updated_email=name.getText().toString();

        String nameInitial=updated_email.substring(0,1);
        initials.setGravity(Gravity.CENTER);
        initials.setTextSize(40);
        initials.setTextColor(Color.rgb(255,255,255));
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

      //  writeNewPost(updated_email,birthdate,zodiac_sign_name,chi_zodiac_sign_name);
        databaseReference.child(UniqueID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("Name").setValue(updated_email);
                dataSnapshot.getRef().child("date_of_birth").setValue(birthdate);
                dataSnapshot.getRef().child("zodiac_sign").setValue(zodiac_sign_name);
                dataSnapshot.getRef().child("chinese_zodiac_sign").setValue(chi_zodiac_sign_name);





                pd.dismiss();
                Toast.makeText(getActivity(), "Data Updated", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
                pd.dismiss();
            }
        });


    }

    public void deleteFirebaseData(String UniqueID)
    {

        databaseReference.child(UniqueID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               dataSnapshot.getRef().setValue(null);


                pd.dismiss();
                Toast.makeText(getActivity(), "User Record Deleted", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
                pd.dismiss();
            }
        });

    }

    private void writeNewPost(String Name, String date_of_birth, String zodiac_sign, String chinese_zodiac_sign) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String fname=name.getText().toString();
        String uniqueID = fname.concat(birthdate);
        String key = databaseReference.child(uniqueID).push().getKey();
        Registered_FriendList friend=new Registered_FriendList(Name,date_of_birth,zodiac_sign,chinese_zodiac_sign);

        Map<String, Object> postValues = friend.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Name/" + key, postValues);
        childUpdates.put("/date_of_birth/"  + key, postValues);
        childUpdates.put("/zodiac_sign/" + key, postValues);
        childUpdates.put("/chinese_zodiac_sign/"  + key, postValues);


        databaseReference.updateChildren(childUpdates);
    }

    private void setDataTime() {
        //   fromDateEtxt.setOnClickListener(this);
        final Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

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
        if(view==save)
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


            }

        }
        if(view==delete)
        {
            String fname=name.getText().toString();
            String uniqueID = fname.concat(birthdate);
            deleteFirebaseData(uniqueID);

        }


    }
}


