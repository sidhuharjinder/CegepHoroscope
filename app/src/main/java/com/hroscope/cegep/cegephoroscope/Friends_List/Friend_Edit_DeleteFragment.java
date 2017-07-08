package com.hroscope.cegep.cegephoroscope.Friends_List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hroscope.cegep.cegephoroscope.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SACHIN on 7/7/2017.
 */


public class Friend_Edit_DeleteFragment extends Fragment implements View.OnClickListener  {
    private CircleImageView profile_Image;
    private ImageView editName,calender,editDate,zodSign,editZodSign,chineseSign,editChineseSign;
    private TextView initials;
    private EditText name,dateOfBirth,zodiacName,chineseZodName;
    private Button save,delete;
    private View view;

    public static Friend_Edit_DeleteFragment newInstance() {
        Friend_Edit_DeleteFragment fragment = new Friend_Edit_DeleteFragment();
        return fragment;
    }

    public Friend_Edit_DeleteFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.activity_editand_delete_friend, container, false);
         setControlls();
        return view;
    }
    public void setControlls()
    {
       //Imageview
        editName=(ImageView)view.findViewById(R.id.editname);
        editName.setOnClickListener(this);
        calender=(ImageView)view.findViewById(R.id.EditDate);
        calender.setOnClickListener(this);
        editDate=(ImageView)view.findViewById(R.id.EditDate);
        editDate.setOnClickListener(this);
        zodSign=(ImageView)view.findViewById(R.id.zodSign);
        zodSign.setOnClickListener(this);
        editZodSign=(ImageView)view.findViewById(R.id.editzod);
        editZodSign.setOnClickListener(this);
        chineseSign=(ImageView)view.findViewById(R.id.chineseSign);
        chineseSign.setOnClickListener(this);
        editChineseSign=(ImageView)view.findViewById(R.id.editchinesezod);
        editChineseSign.setOnClickListener(this);
        save=(Button)view.findViewById(R.id.buttonSave);
        save.setOnClickListener(this);
        delete=(Button)view.findViewById(R.id.buttonDelete);
        delete.setOnClickListener(this);
        //Textview
        initials=(TextView)view.findViewById(R.id.initials);
        //Edittext
        name=(EditText) view.findViewById(R.id.name);
        dateOfBirth=(EditText) view.findViewById(R.id.dateOfBirth);
        zodiacName=(EditText) view.findViewById(R.id.zodiac);
        chineseZodName=(EditText) view.findViewById(R.id.chineseHoroscope);



    }

    @Override
    public void onClick(View view) {
        if(view==editName)
        {

        }
        if(view==calender)
        {

        }
        if(view==editDate)
        {

        }
        if(view==zodSign)
        {

        }

        if(view==editZodSign)
        {

        }
        if(view==chineseSign)
        {

        }
        if(view==editChineseSign)
        {

        }
        if(view==save)
        {

        }
        if(view==delete)
        {

        }


    }
}


