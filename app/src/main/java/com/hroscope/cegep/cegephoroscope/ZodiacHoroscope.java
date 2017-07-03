package com.hroscope.cegep.cegephoroscope;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZodiacHoroscope extends Fragment {

    public static ZodiacHoroscope newInstance() {
        ZodiacHoroscope fragment = new ZodiacHoroscope();
        return fragment;
    }
    public ZodiacHoroscope() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_zodiac_horoscope, container, false);
        CircleImageView ariesButton = (CircleImageView)view.findViewById(R.id.ariesButton);
        ariesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ZodiaDetail.class);
                startActivity(intent);
            }
        });
        return view;
    }

}