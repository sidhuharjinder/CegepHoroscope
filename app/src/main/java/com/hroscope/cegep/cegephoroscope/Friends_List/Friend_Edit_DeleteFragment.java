package com.hroscope.cegep.cegephoroscope.Friends_List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hroscope.cegep.cegephoroscope.R;

/**
 * Created by SACHIN on 7/7/2017.
 */


public class Friend_Edit_DeleteFragment extends Fragment {

    public static Friend_Edit_DeleteFragment newInstance() {
        Friend_Edit_DeleteFragment fragment = new Friend_Edit_DeleteFragment();
        return fragment;
    }

    public Friend_Edit_DeleteFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.activity_editand_delete_friend, container, false);

        return view;
    }

}


