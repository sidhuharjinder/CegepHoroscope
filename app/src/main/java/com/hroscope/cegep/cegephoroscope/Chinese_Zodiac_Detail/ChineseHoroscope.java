package com.hroscope.cegep.cegephoroscope.Chinese_Zodiac_Detail;


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
public class ChineseHoroscope extends Fragment implements View.OnClickListener{
    private CircleImageView ratButton,oxButton,tigerButton,rabitButton,dragonButton,snakeButton,horseButton,goatButton,
    monkeyButton,roosterButton,dogButton,pigButton;
    private View view;
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
        view= inflater.inflate(R.layout.fragment_chinese_horoscope, container, false);
         setControll();
        return view;
    }

    public void setControll()
    {
        ratButton=(CircleImageView)view.findViewById(R.id.ratButton);
        oxButton=(CircleImageView)view.findViewById(R.id.oxButton);
        tigerButton=(CircleImageView)view.findViewById(R.id.tigerButton);
        rabitButton=(CircleImageView)view.findViewById(R.id.rabbitButton);

        dragonButton=(CircleImageView)view.findViewById(R.id.dragonButton);
        snakeButton=(CircleImageView)view.findViewById(R.id.SnakeButton);
        horseButton=(CircleImageView)view.findViewById(R.id.horseButton);
        goatButton=(CircleImageView)view.findViewById(R.id.goatButton);

        monkeyButton=(CircleImageView)view.findViewById(R.id.monkeyButton);
        roosterButton=(CircleImageView)view.findViewById(R.id.roosterButton);
        dogButton=(CircleImageView)view.findViewById(R.id.dogButton);
        pigButton=(CircleImageView)view.findViewById(R.id.pigButton);

        ratButton.setOnClickListener(this);
        oxButton.setOnClickListener(this);
        tigerButton.setOnClickListener(this);
        rabitButton.setOnClickListener(this);
        dragonButton.setOnClickListener(this);
        snakeButton.setOnClickListener(this);
        horseButton.setOnClickListener(this);
        goatButton.setOnClickListener(this);
        monkeyButton.setOnClickListener(this);
        roosterButton.setOnClickListener(this);
        dogButton.setOnClickListener(this);
        pigButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view==ratButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("text", "Rat");
            intent.putExtra("current", HomeScreen.ratCurrentYear);
            startActivity(intent);

        }
        if(view==oxButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("text", "Ox");
            intent.putExtra("current", HomeScreen.oxCurrentYear);
            startActivity(intent);

        }
        if(view==tigerButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("text", "Tiger");
            intent.putExtra("current", HomeScreen.tigerCurrentYear);
            startActivity(intent);

        }
        if(view==rabitButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("text", "Rabbit");
            intent.putExtra("current", HomeScreen.rabbitCurrentYear);
            startActivity(intent);

        }

        if(view==dragonButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("current", HomeScreen.dragonCurrentYear);
            intent.putExtra("text", "Dragon");
            startActivity(intent);

        }
        if(view==snakeButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("text", "Snake");
            intent.putExtra("current", HomeScreen.snakeCurrentYear);
            startActivity(intent);

        }

        if(view==horseButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("text", "Horse");
            intent.putExtra("current", HomeScreen.horseCurrentYear);
            startActivity(intent);

        }
        if(view==goatButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("text", "Sheep");
            intent.putExtra("current", HomeScreen.goatCurrentYear);
            startActivity(intent);

        }

        if(view==monkeyButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("text", "Monkey");
            intent.putExtra("current", HomeScreen.monkeyCurrentYear);
            startActivity(intent);

        }
        if(view==roosterButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("text", "Rooster");
            intent.putExtra("current", HomeScreen.roosterCurrentYear);
            startActivity(intent);

        }
        if(view==dogButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("text", "Dog");
            intent.putExtra("current", HomeScreen.dogCurrentYear);
            startActivity(intent);

        }
        if(view==pigButton)
        {
            Intent intent = new Intent(getActivity(),ChineseZodiaDetail.class);
            intent.putExtra("text", "Pig");
            intent.putExtra("current", HomeScreen.pigCurrentYear);
            startActivity(intent);

        }

    }

}
