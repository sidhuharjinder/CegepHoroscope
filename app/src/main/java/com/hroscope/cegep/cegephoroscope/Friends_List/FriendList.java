package com.hroscope.cegep.cegephoroscope.Friends_List;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hroscope.cegep.cegephoroscope.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class FriendList extends ListFragment{
    FirebaseStorage storage;
    StorageReference storageRef;
    FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String userUID,birthdate,updated_email;
    static String FriendUniqueID;

    String[] players={"Ander Herera","Diego Costa","Juan Mata","David De Gea","Thibaut Courtouis","Van Persie","Oscar"};

    int[] images={R.mipmap.monkey, R.mipmap.monkey,R.mipmap.monkey,R.mipmap.monkey,R.mipmap.monkey,R.mipmap.monkey,R.mipmap.monkey};
    ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String,String>>();
    SimpleAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setControlls();
        ReadFirebase_SetFriendList();

        //MAP
     /*   HashMap<String, String> map=new HashMap<String, String>();
        //FILL
        for(int i=0;i<players.length;i++)
        {
            map=new HashMap<String, String>();
            map.put("Player", players[i]);
            map.put("Image", Integer.toString(images[i]));
            data.add(map);
        }
        //KEYS IN MAP
        String[] from={"Player","Image"};
        //IDS OF VIEWS
        int[] to={R.id.friend_name,R.id.friend_profile};
        //ADAPTER
        adapter=new SimpleAdapter(getActivity(), data, R.layout.model, from, to);
        setListAdapter(adapter);*/

        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        getListView().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity(), data.get(pos).get("Player"), Toast.LENGTH_SHORT).show();
            }
        });
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
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Log.v(TAG,"key"+ childDataSnapshot.getKey()); //displays the key for the node
                    Log.v(TAG,"name"+ childDataSnapshot.child("Name").getValue());
                   // Toast.makeText(getActivity(), childDataSnapshot.child("Name").getValue().toString() , Toast.LENGTH_SHORT).show();//gives the value for given keyname
                    //Toast.makeText(getActivity(), childDataSnapshot.getKey().toString() , Toast.LENGTH_SHORT).show();
                   //

                    HashMap<String, String> map=new HashMap<String, String>();
                    //FILL

                        map=new HashMap<String, String>();
                        map.put("Name", childDataSnapshot.child("Name").getValue().toString());
                       map.put("Date",childDataSnapshot.child("date_of_birth").getValue().toString() );
                        data.add(map);

                    //KEYS IN MAP

                    String[] from={"Name","Date"};

                    //IDS OF VIEWS
                    int[] to={R.id.friend_name,R.id.friend_birthdate};
                    //ADAPTER
                    adapter=new SimpleAdapter(getActivity(), data, R.layout.model, from,to);
                    setListAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}