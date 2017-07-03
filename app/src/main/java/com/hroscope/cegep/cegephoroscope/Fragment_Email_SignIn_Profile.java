package com.hroscope.cegep.cegephoroscope;

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
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * Created by SACHIN on 6/30/2017.
 */

public class Fragment_Email_SignIn_Profile extends Fragment implements View.OnClickListener{
    private CircleImageView profile;
    private ImageButton button_gallary,button_capture,button_upload;
    private View view;

    private ImageView signout,editEmail,calender,editDate, regZodiac,ChiZodiac,friendImg,EditFriend,update_profile;
    private EditText currentUserEmail,dateOfBirth,regZodSign,chiZodSign;
    private TextView friend_email,initials;
    private FirebaseAuth firebaseAuth;
    private static final int PICK_IMAGE_REQUEST = 111,CAPTURE_IMG_REQUEST=120;
    Uri filePath;
    ProgressDialog pd;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String userUID,birthdate,updated_email;

   // private EditText fromDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;





    public static Fragment_Email_SignIn_Profile newInstance() {

        Fragment_Email_SignIn_Profile fragment = new Fragment_Email_SignIn_Profile();
        return fragment;
    }



    public Fragment_Email_SignIn_Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_email_signin_profile, container, false);

        setControlls();

        if (firebaseAuth.getCurrentUser() != null) {
            //check image file is exist on location or not
            storageRef.child("Email_Registration").child(userUID).child("image.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                  /*  Glide.with(getActivity())
                            .using(new FirebaseImageLoader())
                            .load(storageRef).override(600, 200).fitCenter()
                            .into(profile);*/
                    Picasso.with(getActivity()).load(uri.toString()).resize(600,200).centerInside().into(profile);



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // File not found
                }
            });


        }
        setProfileData();
        setDataTime();

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
        databaseReference = database.getReference("Registration_Data").child(userUID);



        update_profile=(ImageView)view.findViewById(R.id.update);
        initials=(TextView) view.findViewById(R.id.initials);
        editEmail=(ImageView)view.findViewById(R.id.EditEmail);
        calender=(ImageView)view.findViewById(R.id.calender);
        editDate=(ImageView)view.findViewById(R.id.EditDate);
        regZodiac =(ImageView)view.findViewById(R.id.zodSign);
        ChiZodiac=(ImageView)view.findViewById(R.id.chineseSign);
        friendImg=(ImageView)view.findViewById(R.id.friendsimage);
        EditFriend=(ImageView)view.findViewById(R.id.EditFreinds);
        currentUserEmail =(EditText)view.findViewById(R.id.email);

        dateOfBirth=(EditText)view.findViewById(R.id.dateOfBirth);
        dateOfBirth.setInputType(InputType.TYPE_NULL);
        dateOfBirth.requestFocus();

        regZodSign=(EditText)view.findViewById(R.id.zodiac);
        chiZodSign=(EditText)view.findViewById(R.id.chineseHoroscope);
        friend_email=(TextView)view.findViewById(R.id.friends);

        profile=(CircleImageView) view.findViewById(R.id.rabbitButton);
        button_gallary =(ImageButton)view.findViewById(R.id.gallary);
        button_capture=(ImageButton)view.findViewById(R.id.capture);
        button_upload=(ImageButton)view.findViewById(R.id.upload);
        signout=(ImageView)view.findViewById(R.id.signout);
        button_gallary.setOnClickListener(this);
        button_capture.setVisibility(view.INVISIBLE);
        button_gallary.setVisibility(view.INVISIBLE);
        button_upload.setVisibility(view.INVISIBLE);
        profile.setOnClickListener(this);
        button_gallary.setOnClickListener(this);
        button_capture.setOnClickListener(this);
        button_upload.setOnClickListener(this);
        signout.setOnClickListener(this);

        editEmail.setOnClickListener(this);
        calender.setOnClickListener(this);
        editDate.setOnClickListener(this);
        regZodiac.setOnClickListener(this);
        ChiZodiac.setOnClickListener(this);
        friendImg.setOnClickListener(this);
        EditFriend.setOnClickListener(this);
        update_profile.setOnClickListener(this);
        update_profile.setVisibility(view.INVISIBLE);
        currentUserEmail.setEnabled(false);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Uploading....");


    }

    private void updateData() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Updating....");
        birthdate=dateOfBirth.getText().toString();
        updated_email=currentUserEmail.getText().toString();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("date_of_birth").setValue(birthdate);
                dataSnapshot.getRef().child("user_name").setValue(updated_email);
                pd.dismiss();
                Toast.makeText(getActivity(), "Data Updated", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
    }

    public void editeduploadTofirebase()
    {


      //  databaseReference.child("first_name").setValue(updated_email);
    }

    private void setDataTime() {
     //   fromDateEtxt.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateOfBirth.setGravity(Gravity.LEFT);
                dateOfBirth.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    public void setProfileData()
    {
        //set email addess
        String current_userEmail=firebaseAuth.getCurrentUser().getEmail();
        String split_email = current_userEmail.substring(0, current_userEmail.indexOf("@"));
        currentUserEmail.setGravity(Gravity.LEFT);
        currentUserEmail.setText(split_email.toUpperCase());
      //set initial
      String nameInitial=current_userEmail.substring(0,1);
        initials.setGravity(Gravity.CENTER);
        initials.setTextSize(40);
        initials.setTextColor(Color.rgb(255,255,255));
       //make initial Bold
        final SpannableStringBuilder sb = new SpannableStringBuilder(nameInitial);
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        initials.setText(nameInitial.toUpperCase());

    }

    public void fromGallary()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        //to receive image result call this activity
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

    }

    public void fromCamera()
    {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_IMG_REQUEST);
        button_upload.setVisibility(view.VISIBLE);
    }

    public void upload()
    {
        if(filePath != null) {
            pd.show();

            String useruid=firebaseAuth.getCurrentUser().getUid();


            StorageReference databaseReference = storageRef.child("Email_Registration").child(useruid).child("image.jpg");

            // StorageReference childRef = storageRef.child("image.jpg");

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                //Setting image to ImageView
                profile.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAPTURE_IMG_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profile.setImageBitmap(photo);
        }
        button_gallary.setVisibility(view.VISIBLE);
        button_capture.setVisibility(view.VISIBLE);


    }

    public void emailSignout()
    {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(), "User Sign out Successfully", Toast.LENGTH_LONG).show();
        Fragment fragment = new SignupFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }




    @Override
    public void onClick(View view) {
        if(view==profile)
        {
            button_gallary.setVisibility(view.VISIBLE);
            button_capture.setVisibility(view.VISIBLE);


        }
        if(view== button_gallary)
        {


            fromGallary();
            button_capture.setVisibility(view.INVISIBLE);
            button_upload.setVisibility(view.VISIBLE);

        }

        if(view==button_capture)
        {

            fromCamera();
            button_gallary.setVisibility(view.INVISIBLE);
            button_upload.setVisibility(view.VISIBLE);
        }
        if(view==button_upload)
        {
            upload();
            button_gallary.setVisibility(view.VISIBLE);
            button_capture.setVisibility(view.VISIBLE);
        }
        if(view==signout)
        {
            emailSignout();
        }

        if(view==signout)
        {
            emailSignout();
        }

        if(view==editEmail)
        {
            update_profile.setVisibility(view.VISIBLE);
           // currentUserEmail.setText("");
            currentUserEmail.setEnabled(true);
            currentUserEmail.setHint("Add New Email Address");

        }
        if(view==calender)
        {
            update_profile.setVisibility(view.VISIBLE);

            fromDatePickerDialog.show();
        }
        if(view==editDate)
        {
            update_profile.setVisibility(view.VISIBLE);
            fromDatePickerDialog.show();

        }
        if(view== regZodiac)
        {

        }
        if(view==ChiZodiac)
        {

        }
        if(view==friendImg)
        {

        }
        if(view==EditFriend)
        {

        }
        if(view==update_profile)
        {
         updateData();
          /*  Toast.makeText(getActivity(), "Click is happing", Toast.LENGTH_SHORT).show();
            pd = new ProgressDialog(getActivity());


            birthdate=dateOfBirth.getText().toString();
           // databaseReference.child("date_of_birth").setValue(birthdate);

            updated_email=currentUserEmail.getText().toString();

            databaseReference.child("date_of_birth").setValue(birthdate);
            databaseReference.child("user_name").setValue(updated_email);*/


            pd.dismiss();

        }

    }
}
