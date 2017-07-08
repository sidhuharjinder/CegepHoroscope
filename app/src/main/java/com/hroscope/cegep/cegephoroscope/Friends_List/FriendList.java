package com.hroscope.cegep.cegephoroscope.Friends_List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.hroscope.cegep.cegephoroscope.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FriendList extends ListFragment{
    String[] players={"Ander Herera","Diego Costa","Juan Mata","David De Gea","Thibaut Courtouis","Van Persie","Oscar"};
    int[] images={R.mipmap.monkey, R.mipmap.monkey,R.mipmap.monkey,R.mipmap.monkey,R.mipmap.monkey,R.mipmap.monkey,R.mipmap.monkey};
    ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String,String>>();
    SimpleAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //MAP
        HashMap<String, String> map=new HashMap<String, String>();
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
        int[] to={R.id.nameTxt,R.id.imageView1};
        //ADAPTER
        adapter=new SimpleAdapter(getActivity(), data, R.layout.model, from, to);
        setListAdapter(adapter);
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
}