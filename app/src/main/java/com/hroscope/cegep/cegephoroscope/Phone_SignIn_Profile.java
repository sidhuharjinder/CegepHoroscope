package com.hroscope.cegep.cegephoroscope;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class Phone_SignIn_Profile extends Fragment implements View.OnClickListener{
    private View view;
    private CircleImageView profile;
    private Button mSignOutButton;
    private EditText username;
    private FirebaseAuth firebaseAuth;
    private ImageButton button_gallary;
    private static final int PICK_IMAGE_REQUEST = 111,CAPTURE_IMG_REQUEST=120;
    Uri filePath;

    ProgressDialog pd;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String userUID,birthdate,updated_email;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    FirebaseUser user;

    public static Phone_SignIn_Profile newInstance() {
        Phone_SignIn_Profile fragment = new Phone_SignIn_Profile();
        return fragment;
    }

    public Phone_SignIn_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_phone_signin_profile, container, false);
         firebaseAuth = FirebaseAuth.getInstance();

       setControll();
        ReadFirebase_setProfileData();


        return view;
    }
    public void setControll()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        firebaseAuth= FirebaseAuth.getInstance();
        userUID=firebaseAuth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://cegephoroscope-3dcdb.appspot.com/");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Registration_Data").child(userUID);

        mSignOutButton = (Button)view. findViewById(R.id.phonesignout);
        mSignOutButton.setOnClickListener(this);
        username = (EditText) view.findViewById(R.id.email);
        button_gallary =(ImageButton)view.findViewById(R.id.gallary);
        button_gallary.setOnClickListener(this);
        button_gallary.setVisibility(view.INVISIBLE);
        profile=(CircleImageView) view.findViewById(R.id.rabbitButton);
        profile.setOnClickListener(this);


    }
    public void ReadFirebase_setProfileData()
    {
        username.setText(firebaseAuth.getCurrentUser().getPhoneNumber().toString());
    }

    //choose pic from gallary
    public void fromGallary()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        //to receive image result call this activity
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

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


    }


    @Override
    public void onClick(View view) {
        if(view==mSignOutButton)
        {
            firebaseAuth.signOut();
            Fragment fragment = new SignupFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
            Toast.makeText(getActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();

        }
        if(view==profile)
        {
            button_gallary.setVisibility(view.VISIBLE);



        }

        if(view==button_gallary)
        {
            fromGallary();
          //  button_capture.setVisibility(view.INVISIBLE);
            //button_upload.setVisibility(view.VISIBLE);


        }
    }
}
