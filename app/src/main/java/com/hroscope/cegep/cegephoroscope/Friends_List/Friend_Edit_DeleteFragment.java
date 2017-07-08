package com.hroscope.cegep.cegephoroscope.Friends_List;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.hroscope.cegep.cegephoroscope.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

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
        if (firebaseAuth.getCurrentUser() != null) {
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

        }
       // ReadFirebase_setProfileData();
        //setDataTime();
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

    public void upload()
    {
        if(filePath != null) {
            pd.show();

            String useruid=firebaseAuth.getCurrentUser().getUid();


            StorageReference databaseReference = storageRef.child("Email_Registration").child("Users_Friend_Images").child(useruid).child("image.jpg");

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
            upload();
            button_gallary.setVisibility(view.VISIBLE);

        }
        if(view==editName)
        {

        }
        if(view==calender)
        {

        }
        if(view==editDate)
        {

        }
        if(view==zodSign)
        {

        }

        if(view==editZodSign)
        {

        }
        if(view==chineseSign)
        {

        }
        if(view==editChineseSign)
        {

        }
        if(view==save)
        {

        }
        if(view==delete)
        {

        }


    }
}


