package com.hroscope.cegep.cegephoroscope.zodiacDetail;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
            startActivity(intent);
        }
        if(view==taurusButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Taurus");
            startActivity(intent);
        }
        if(view==geminiButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Gemini");
            startActivity(intent);
        }
        if(view==cancerButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Cancer");
            startActivity(intent);
        }
        if(view==leoButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Leo");
            startActivity(intent);
        }
        if(view==virgoButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Virgo");
            startActivity(intent);
        }
        if(view==libraButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Libra");
            startActivity(intent);
        }
        if(view==scorpioButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Scorpio");
            startActivity(intent);
        }
        if(view==sagittarusButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Sagittarus");
            startActivity(intent);
        }
        if(view==capricornButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Capricorn");
            startActivity(intent);
        }
        if(view==aquariusButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "Aquarius");
            startActivity(intent);
        }
        if(view==piscesButton)
        {

            Intent intent = new Intent(getActivity(),ZodiaDetail.class);
            intent.putExtra("text", "pisces");
            startActivity(intent);
        }

    }
}