package com.hroscope.cegep.cegephoroscope;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChineseHoroscope extends Fragment {
    public static ChineseHoroscope newInstance() {
        ChineseHoroscope fragment = new ChineseHoroscope();
        return fragment;
    }

    public ChineseHoroscope() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chinese_horoscope, container, false);
    }

}
