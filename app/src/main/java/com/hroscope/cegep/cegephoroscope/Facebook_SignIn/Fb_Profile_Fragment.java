package com.hroscope.cegep.cegephoroscope.Facebook_SignIn;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hroscope.cegep.cegephoroscope.Email_SignIn.Email_Registered_UserList;
import com.hroscope.cegep.cegephoroscope.Friend_Save_FriendList.FriendLIstActivity;
import com.hroscope.cegep.cegephoroscope.R;
import com.hroscope.cegep.cegephoroscope.SignupFragment;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by SACHIN on 6/24/2017.
 */

public class Fb_Profile_Fragment extends Fragment implements View.OnClickListener // implements FragmentCommunicator
{
    private FirebaseAuth.AuthStateListener firebaseauthlistener;
    private GoogleApiClient googleApiClient;
    private View view;
    private Context context;
    static boolean clicked=false;
    private Button facebook_logout;
    private EditText username;
    private CircleImageView faebookimage;
    public static String name;
    public static Uri photo;

    //for profile
    private CircleImageView profile;

    private ImageView signout,editEmail,calender,editDate, regZodiac,ChiZodiac,friendImg,EditFriend,update_profile,forward_zodiac,forward_chinese;
    private EditText currentUserEmail,dateOfBirth,regZodSign,chiZodSign;
    private TextView friend_email,initials;
    private FirebaseAuth firebaseAuth;

    int chinese_img,zodiac_imag;
    Uri filePath,zodiacImage;
    ProgressDialog pd;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    FirebaseUser user;
    String nme;
    String zodiac_sign_name,chi_zodiac_sign_name;
    String zod_name="",chi_name="";
   //firebase
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String userUID,birthdate,updated_email;





    public static Fb_Profile_Fragment newInstance() {
        Fb_Profile_Fragment fragment = new Fb_Profile_Fragment();
        return fragment;
    }

    public Fb_Profile_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart()
    {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseauthlistener);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_facebook_signin_profile, container, false);

       setControlls();

        facebook_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                LoginManager.getInstance().logOut();
                clicked=true;


            }
        });

           setFacebookProfileData();
            setDataTime();



        return view;
    }

    public void setControlls()
   {
    FacebookSdk.sdkInitialize(getApplicationContext());
    firebaseAuth=FirebaseAuth.getInstance();
    facebook_logout=(LoginButton)view.findViewById(R.id.fb);
    username=(EditText) view.findViewById(R.id.email);
    faebookimage=(CircleImageView) view.findViewById(R.id.rabbitButton);

      //profile data
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
       forward_zodiac=(ImageView)view.findViewById(R.id.forwardzodiac);
       forward_chinese=(ImageView)view.findViewById(R.id.forwardchinese);
       currentUserEmail =(EditText)view.findViewById(R.id.email);

       dateOfBirth=(EditText)view.findViewById(R.id.dateOfBirth);
       dateOfBirth.setInputType(InputType.TYPE_NULL);
       dateOfBirth.requestFocus();

       regZodSign=(EditText)view.findViewById(R.id.zodiac);
       chiZodSign=(EditText)view.findViewById(R.id.chineseHoroscope);
       friend_email=(TextView)view.findViewById(R.id.friends);

       profile=(CircleImageView) view.findViewById(R.id.rabbitButton);



       profile.setOnClickListener(this);

       calender.setOnClickListener(this);
       editDate.setOnClickListener(this);
       regZodiac.setOnClickListener(this);
       ChiZodiac.setOnClickListener(this);
       friendImg.setOnClickListener(this);
       forward_zodiac.setOnClickListener(this);
       forward_chinese.setOnClickListener(this);
       EditFriend.setOnClickListener(this);
       update_profile.setOnClickListener(this);
       update_profile.setVisibility(view.INVISIBLE);
       currentUserEmail.setEnabled(false);

       pd = new ProgressDialog(getActivity());
       pd.setMessage("Uploading....");
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

    private void updateData_loadTofirebase() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Updating....");
        birthdate=dateOfBirth.getText().toString();


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
        regZodSign.setText(zodiac_sign_name);
        chiZodSign.setText(chi_zodiac_sign_name);

        //update Firebase data storage
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("date_of_birth").setValue(birthdate);
                dataSnapshot.getRef().child("zodiac_sign").setValue(zodiac_sign_name);
                dataSnapshot.getRef().child("chinese_zodiac_sign").setValue(chi_zodiac_sign_name);
                pd.dismiss();
                Toast.makeText(getActivity(), "Data Updated", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });



    }



    public void setFacebookProfileData()
    {
        firebaseauthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser firebaseuser=firebaseAuth.getCurrentUser();
                if(firebaseuser!=null)
                {

                    for(UserInfo userInfo:firebaseuser.getProviderData())
                    {
                        Log.d("TAG",userInfo.getProviderId());
                    }

                    name=firebaseuser.getDisplayName();
                    photo=firebaseuser.getPhotoUrl();
                    username.setText(firebaseuser.getDisplayName());
                  //set initials
                    String nameInitial=firebaseuser.getDisplayName().substring(0,1);
                    initials.setGravity(Gravity.CENTER);
                    initials.setTextSize(40);
                    initials.setTextColor(Color.rgb(255,255,255));
                    //make initial Bold
                    final SpannableStringBuilder sb = new SpannableStringBuilder(nameInitial);
                    final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
                    initials.setText(nameInitial.toUpperCase());
                    Picasso.with(getActivity()).load(photo).resize(400,100).centerInside().into(faebookimage);

                }
                else if(firebaseuser==null){


                    Fragment fragment = new SignupFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();

                }


            }

        };

        //Retrieve all set of data from firebase
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Email_Registered_UserList user = dataSnapshot.getValue(Email_Registered_UserList.class);
                if(dataSnapshot.hasChild("date_of_birth")&&dataSnapshot.hasChild("chinese_zodiac_sign"))
                {
                    dateOfBirth.setText(user.date_of_birth);
                    regZodSign.setText(user.zodiac_sign);
                    chiZodSign.setText(user.chinese_zodiac_sign);
                    //using those string variable for child name to get zod and chinese zod sign image from database
                    zod_name = user.zodiac_sign.toLowerCase();
                    chi_name = user.chinese_zodiac_sign.toLowerCase();

                    //load appropriate image to zodiac sign imageview
                    storageRef.child("Signs").child("Zodiac_Signs").child(zod_name + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.with(getActivity()).load(uri.toString()).resize(600, 200).centerInside().into(regZodiac);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // File not found
                        }
                    });

                    //load appropriate image to Chinese zodiac sign imageview
                    storageRef.child("Signs").child("Chinese_Signs").child(chi_name + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.with(getActivity()).load(uri.toString()).resize(600, 200).centerInside().into(ChiZodiac);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // File not found
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(postListener);


    }


    @Override
    public void onClick(View view) {


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

        if(view==forward_zodiac)
        {

        }

        if(view==forward_chinese)
        {

        }
        if(view==EditFriend)
        {
           /* Fragment fragment = new Friend_ListFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();*/
            startActivity(new Intent(getActivity(),FriendLIstActivity.class));

        }
        if(view==update_profile)
        {
            String date_of_birth=dateOfBirth.getText().toString();
            if (date_of_birth.isEmpty()) {


                dateOfBirth.setError("Choose your Birthdate");
            }
            else {
                updateData_loadTofirebase();
            }


            pd.dismiss();

        }


    }
}
