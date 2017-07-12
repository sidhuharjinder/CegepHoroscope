package com.hroscope.cegep.cegephoroscope;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hroscope.cegep.cegephoroscope.zodiacDetail.ZodiaDetail;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    public static Profile newInstance() {
        Profile fragment = new Profile();
        return fragment;
    }

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        LinearLayout zondicLinearLayout = (LinearLayout) view.findViewById(R.id.zondicLinearLayout);
        zondicLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ZodiaDetail.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
