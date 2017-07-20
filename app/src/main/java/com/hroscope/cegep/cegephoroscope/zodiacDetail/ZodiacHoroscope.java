package com.hroscope.cegep.cegephoroscope.zodiacDetail;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hroscope.cegep.cegephoroscope.HomeScreen;
import com.hroscope.cegep.cegephoroscope.R;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZodiacHoroscope extends Fragment implements View.OnClickListener{
    private View view;
    CircleImageView ariesButton,taurusButton,geminiButton,cancerButton,leoButton,virgoButton,libraButton,scorpioButton,
    sagittarusButton,capricornButton, aquariusButton,piscesButton;
    Activity context;

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
        view =  inflater.inflate(R.layout.fragment_zodiac_horoscope, container, false);
       setControlls();

        return view;
    }

    public void setControlls()
    {
        ariesButton = (CircleImageView)view.findViewById(R.id.ariesButton);
        ariesButton.setOnClickListener(this);
        taurusButton = (CircleImageView)view.findViewById(R.id.taurusButton);
        taurusButton.setOnClickListener(this);
        geminiButton = (CircleImageView)view.findViewById(R.id.geminiButton);
        geminiButton.setOnClickListener(this);
        cancerButton = (CircleImageView)view.findViewById(R.id.cancerButton);
        cancerButton.setOnClickListener(this);
        leoButton = (CircleImageView)view.findViewById(R.id.leoButton);
        leoButton.setOnClickListener(this);
        virgoButton = (CircleImageView)view.findViewById(R.id.virgoButton);
        virgoButton.setOnClickListener(this);
        libraButton = (CircleImageView)view.findViewById(R.id.libraButton);
        libraButton.setOnClickListener(this);
        scorpioButton = (CircleImageView)view.findViewById(R.id.scorpioButton);
        scorpioButton.setOnClickListener(this);
        sagittarusButton = (CircleImageView)view.findViewById(R.id.sagittariusButton);
        sagittarusButton.setOnClickListener(this);
        capricornButton = (CircleImageView)view.findViewById(R.id.capricornButton);
        capricornButton.setOnClickListener(this);
        aquariusButton = (CircleImageView)view.findViewById(R.id.aquariusButton);
        aquariusButton.setOnClickListener(this);
        piscesButton = (CircleImageView)view.findViewById(R.id.piscesButton);
        piscesButton.setOnClickListener(this);





    }

    @Override
    public void onClick(View view) {
        if(view==ariesButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Aries");
            intent.putExtra("current", HomeScreen.ariesDailyCurrent);
            intent.putExtra("tomorrow", HomeScreen.ariesDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.ariesDailyYesterday);
            intent.putExtra("monthly", HomeScreen.ariesMonthly);
            intent.putExtra("yearly", HomeScreen.ariesYearly);
            intent.putExtra("weekly", HomeScreen.ariesWeakly);
            startActivity(intent);
        }
        if(view==taurusButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Taurus");
            intent.putExtra("current", HomeScreen.taurusDailyCurrent);
            intent.putExtra("tomorrow", HomeScreen.taurusDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.taurusDailyYesterday);
            intent.putExtra("monthly", HomeScreen.taurusMonthly);
            intent.putExtra("yearly", HomeScreen.taurusYearly);
            intent.putExtra("weekly", HomeScreen.taurusWeakly);
            startActivity(intent);
        }
        if(view==geminiButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Gemini");
            intent.putExtra("current", HomeScreen.geminiDailyCurrent);
            intent.putExtra("tomorrow", HomeScreen.geminiDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.geminiDailyYesterday);
            intent.putExtra("monthly", HomeScreen.geminiMonthly);
            intent.putExtra("yearly", HomeScreen.geminiYearly);
            intent.putExtra("weekly", HomeScreen.geminiWeakly);
            startActivity(intent);
        }
        if(view==cancerButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Cancer");
            intent.putExtra("current", HomeScreen.cancerDailyCurrent);
            intent.putExtra("tomorrow", HomeScreen.cancerDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.cancerDailyYesterday);
            intent.putExtra("monthly", HomeScreen.cancerMonthly);
            intent.putExtra("yearly", HomeScreen.cancerYearly);
            intent.putExtra("weekly", HomeScreen.cancerWeakly);
            startActivity(intent);
        }
        if(view==leoButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Leo");
            intent.putExtra("current", HomeScreen.leoDailyCurrent);
            intent.putExtra("tomorrow", HomeScreen.leoDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.leoDailyYesterday);
            intent.putExtra("monthly", HomeScreen.leoMonthly);
            intent.putExtra("yearly", HomeScreen.leoYearly);
            intent.putExtra("weekly", HomeScreen.leoWeakly);
            startActivity(intent);
        }
        if(view==virgoButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Virgo");
            intent.putExtra("current", HomeScreen.virgoDailyCurrent);
            intent.putExtra("tomorrow", HomeScreen.virgoDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.virgoDailyYesterday);
            intent.putExtra("monthly", HomeScreen.virgoMonthly);
            intent.putExtra("yearly", HomeScreen.virgoYearly);
            intent.putExtra("weekly", HomeScreen.virgoWeakly);
            startActivity(intent);
        }
        if(view==libraButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Libra");
            intent.putExtra("current", HomeScreen.libraDailyTomorrow);
            intent.putExtra("tomorrow", HomeScreen.libraDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.libraDailyYesterday);
            intent.putExtra("monthly", HomeScreen.libraMonthly);
            intent.putExtra("yearly", HomeScreen.libraYearly);
            intent.putExtra("weekly", HomeScreen.libraWeakly);
            startActivity(intent);
        }
        if(view==scorpioButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Scorpio");
            intent.putExtra("current", HomeScreen.scorpioDailyCurrent);
            intent.putExtra("tomorrow", HomeScreen.scorpioDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.scorpioDailyYesterday);
            intent.putExtra("monthly", HomeScreen.scorpioMonthly);
            intent.putExtra("yearly", HomeScreen.scorpioYearly);
            intent.putExtra("weekly", HomeScreen.scorpioWeakly);
            startActivity(intent);
        }
        if(view==sagittarusButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Sagittarus");
            intent.putExtra("current", HomeScreen.sagittariusDailyCurrent);
            intent.putExtra("tomorrow", HomeScreen.sagittariusDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.sagittariusDailyYesterday);
            intent.putExtra("monthly", HomeScreen.sagittariusMonthly);
            intent.putExtra("yearly", HomeScreen.sagittariusYearly);
            intent.putExtra("weekly", HomeScreen.sagittariusWeakly);
            startActivity(intent);
        }
        if(view==capricornButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Capricorn");
            intent.putExtra("current", HomeScreen.capriconDailyCurrent);
            intent.putExtra("tomorrow", HomeScreen.capriconDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.capriconDailyYesterday);
            intent.putExtra("monthly", HomeScreen.capriconMonthly);
            intent.putExtra("yearly", HomeScreen.capriconYearly);
            intent.putExtra("weekly", HomeScreen.capriconWeakly);
            startActivity(intent);
        }
        if(view==aquariusButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Aquarius");
            intent.putExtra("current", HomeScreen.aquariusDailyCurrent);
            intent.putExtra("tomorrow", HomeScreen.aquariusDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.aquariusDailyYesterday);
            intent.putExtra("monthly", HomeScreen.aquariusMonthly);
            intent.putExtra("yearly", HomeScreen.aquariusYearly);
            intent.putExtra("weekly", HomeScreen.aquariusWeakly);
            startActivity(intent);
        }
        if(view==piscesButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Pisces");
            intent.putExtra("current", HomeScreen.piccesDailyCurrent);
            intent.putExtra("tomorrow", HomeScreen.piccesDailyTomorrow);
            intent.putExtra("yesterday", HomeScreen.piccesDailyYesterday);
            intent.putExtra("monthly", HomeScreen.piccesMonthly);
            intent.putExtra("yearly", HomeScreen.piccesYearly);
            intent.putExtra("weekly", HomeScreen.piccesWeakly);
            startActivity(intent);
        }

    }
}