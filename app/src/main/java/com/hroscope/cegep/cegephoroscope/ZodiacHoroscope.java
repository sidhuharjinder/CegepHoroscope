package com.hroscope.cegep.cegephoroscope;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
        return inflater.inflate(R.layout.fragment_zodiac_horoscope, container, false);
    }

}
