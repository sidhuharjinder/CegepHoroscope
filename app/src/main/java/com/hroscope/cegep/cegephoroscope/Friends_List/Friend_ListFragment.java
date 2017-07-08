package com.hroscope.cegep.cegephoroscope.Friends_List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hroscope.cegep.cegephoroscope.R;

/**
 * Created by SACHIN on 7/7/2017.
 */

public class Friend_ListFragment extends Fragment implements View.OnClickListener {
    public ImageView addfriend;
    private View view;


    public static Friend_ListFragment newInstance() {
        Friend_ListFragment fragment = new Friend_ListFragment();
        return fragment;
    }

    public Friend_ListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view =  inflater.inflate(R.layout.activity_friend_list, container, false);
        setControlls();

        return view;
    }

    public void setControlls()
    {
        addfriend=(ImageView)view.findViewById(R.id.addFriendButton);
        addfriend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==addfriend)
        {
            Fragment fragment = new Friend_Edit_DeleteFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
        }

    }
}
