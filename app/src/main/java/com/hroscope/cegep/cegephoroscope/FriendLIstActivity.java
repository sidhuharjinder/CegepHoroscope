package com.hroscope.cegep.cegephoroscope;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hroscope.cegep.cegephoroscope.Friends_List.FriendList;
import com.hroscope.cegep.cegephoroscope.Friends_List.FriendListAdaptor;
import com.hroscope.cegep.cegephoroscope.Friends_List.FriendListModel;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class FriendLIstActivity extends AppCompatActivity  {
    public ImageView addfriend;
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String userUID,birthdate,updated_email;
    static String FriendUniqueID;
    String []User={"Friend List Empty"};
    int[]images={R.drawable.user};
    private ListView list;
    private ArrayList<FriendListModel> dataModels;
   // ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String,String>>();
//    SimpleAdapter adapter;
    private static FriendListAdaptor adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        addfriend=(ImageView)findViewById(R.id.addFriendButton);
        addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SaveFriend.class);
                startActivity(intent);

            }
        });
        list = (ListView) findViewById(R.id.participants_list);
        dataModels = new ArrayList<>();
        //set adapter


        adapter = new FriendListAdaptor(dataModels, getApplicationContext());
        list.setAdapter(adapter);
        setControlls();

        ReadFirebase_SetFriendList();
    }
    public void setControlls()
    {
        firebaseAuth= FirebaseAuth.getInstance();
        userUID=firebaseAuth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://cegephoroscope-3dcdb.appspot.com/");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Registration_Data").child("Users_Friend_List").child(userUID);



    }

    public void ReadFirebase_SetFriendList()
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()==false)
                {

                    HashMap<String, String> map=new HashMap<String, String>();
                    //FILL
                    for(int i=0;i<User.length;i++)
                    {
                        dataModels.add(new FriendListModel(User[i],Integer.toString(images[i]),null));

//                       map.put("Date", Integer.toString(images[i]));
//                        map.put("Name", User[i]);
//
//                        map.put("Date", Integer.toString(images[i]));
//
//                        data.add(map);

                    }
                    //adapter.notifyDataSetChanged();


                }

                for (final DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v(TAG, "key" + childDataSnapshot.getKey()); //displays the key for the node
                    Log.v(TAG, "name" + childDataSnapshot.child("Name").getValue());


                    if(childDataSnapshot.hasChild("date_of_birth")&&childDataSnapshot.hasChild("zodiac_sign")) {
                        // String zodnm = childDataSnapshot.child("zodiac_sign").getValue().toString().toLowerCase();
                        HashMap<String, String> map = new HashMap<String, String>();
                        //FILL
                        map = new HashMap<String, String>();
                        map.put("Name", childDataSnapshot.child("Name").getValue().toString());
                        map.put("Date", childDataSnapshot.child("date_of_birth").getValue().toString());
                        Log.d("Hello",map.toString());
//                        data.add(map);

                        dataModels.add(new FriendListModel(childDataSnapshot.child("Name").getValue().toString(),null,childDataSnapshot.child("date_of_birth").getValue().toString()));

                    }
                    else
                    {
                        for(int i=0;i<User.length;i++)
                        {
                            dataModels.add(new FriendListModel(User[i],Integer.toString(images[i]),null));

                        }
                       // adapter.notifyDataSetChanged();
                    }
                    adapter.notifyDataSetChanged();

                }

            }
            @Override
            public void onCancelled (DatabaseError databaseError){

            }


        });

    }
}
