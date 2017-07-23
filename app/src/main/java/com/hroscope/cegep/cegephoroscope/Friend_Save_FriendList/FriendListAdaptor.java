package com.hroscope.cegep.cegephoroscope.Friend_Save_FriendList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hroscope.cegep.cegephoroscope.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by krishna on 2017-07-12.
 */

public class FriendListAdaptor extends ArrayAdapter<FriendListModel> implements View.OnClickListener {
    private ArrayList<FriendListModel> dataSet;
    Context mContext;
    public FriendListAdaptor(ArrayList<FriendListModel> data, Context context) {
        super(context, R.layout.model, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

    }

    private static class ViewHolder {
        private ImageView profilePicture;
        private TextView name;
        private TextView date;
        private LinearLayout model_friend_list;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final FriendListModel dataModel = getItem(position);
        //get zodiac picture based on date
        String birthdate=dataModel.getListdate();
        String zodiac_sign_name=null;

        if(birthdate!=null) {
            int day = Integer.parseInt(birthdate.substring(0, 2));
            int month = Integer.parseInt(birthdate.substring(3, 5));
            int year = Integer.parseInt(birthdate.substring(6, 10));

            if ((month == 12 && day >= 22 && day <= 31) || (month == 1 && day >= 01 && day <= 19))
                zodiac_sign_name = "Capricorn";

            else if ((month == 01 && day >= 20 && day <= 31) || (month == 2 && day >= 01 && day <= 17))
                zodiac_sign_name = "Aquarius";
            else if ((month == 02 && day >= 18 && day <= 29) || (month == 3 && day >= 01 && day <= 19))
                zodiac_sign_name = "Pisces";
            else if ((month == 03 && day >= 20 && day <= 31) || (month == 4 && day >= 01 && day <= 19))
                zodiac_sign_name = "Aries";
            else if ((month == 04 && day >= 20 && day <= 30) || (month == 5 && day >= 01 && day <= 20))
                zodiac_sign_name = "Taurus";
            else if ((month == 05 && day >= 21 && day <= 31) || (month == 6 && day >= 01 && day <= 20))
                zodiac_sign_name = "Gemini";
            else if ((month == 06 && day >= 21 && day <= 30) || (month == 7 && day >= 01 && day <= 22))
                zodiac_sign_name = "Cancer";
            else if ((month == 07 && day >= 23 && day <= 31) || (month == 8 && day >= 01 && day <= 22))
                zodiac_sign_name = "Leo";
            else if ((month == 8 && day >= 23 && day <= 31) || (month == 9 && day >= 01 && day <= 22))
                zodiac_sign_name = "Virgo";
            else if ((month == 9 && day >= 23 && day <= 30) || (month == 10 && day >= 01 && day <= 22))
                zodiac_sign_name = "Libra";
            else if ((month == 10 && day >= 23 && day <= 31) || (month == 11 && day >= 01 && day <= 21))
                zodiac_sign_name = "Scorpio";
            else if ((month == 11 && day >= 22 && day <= 30) || (month == 12 && day >= 01 && day <= 21))
                zodiac_sign_name = "Sagittarius";

        }
        /////



        final ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.model, parent, false);
            viewHolder.profilePicture = (ImageView) convertView.findViewById(R.id.friend_profile);
            viewHolder.name = (TextView) convertView.findViewById(R.id.friend_name);
            viewHolder.date = (TextView) convertView.findViewById(R.id.friend_birthdate);
            viewHolder.model_friend_list=(LinearLayout)convertView.findViewById(R.id.model_friend_list);
           // Picasso.with(mContext).load(dataModel.getProfileImage()).into(viewHolder.profilePicture);
            if(birthdate!=null) {
                if (zodiac_sign_name == "Capricorn")
                    Picasso.with(mContext).load(R.mipmap.capricorn).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == "Aquarius")
                    Picasso.with(mContext).load(R.mipmap.aquarius).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == "Pisces")
                    Picasso.with(mContext).load(R.mipmap.pisces).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == "Aries")
                    Picasso.with(mContext).load(R.mipmap.aries).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == "Taurus")
                    Picasso.with(mContext).load(R.mipmap.taurus).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == "Gemini")
                    Picasso.with(mContext).load(R.mipmap.gemini).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == "Cancer")
                    Picasso.with(mContext).load(R.mipmap.cancer).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == "Leo")
                    Picasso.with(mContext).load(R.mipmap.leo).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == "Virgo")
                    Picasso.with(mContext).load(R.mipmap.virgo).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == "Libra")
                    Picasso.with(mContext).load(R.mipmap.libra).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == "Scorpio")
                    Picasso.with(mContext).load(R.mipmap.scorpio).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == "Sagittarius")
                    Picasso.with(mContext).load(R.mipmap.sagittarius).into(viewHolder.profilePicture);
                else if (zodiac_sign_name == null)
                    Picasso.with(mContext).load(R.mipmap.adduser).into(viewHolder.profilePicture);
            }
            else
            {
                Picasso.with(mContext).load(dataModel.getProfileImage()).into(viewHolder.profilePicture);
            }

           // Log.d("Hello",dataModel.getListdate());
            viewHolder.name.setText(dataModel.getListName());
            viewHolder.date.setText(dataModel.getListdate());
            viewHolder.name.setTextColor(Color.WHITE);
            viewHolder.date.setTextColor(Color.WHITE);
            viewHolder.model_friend_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),EditandDeleteFriend.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if(dataModel.getListdate()==null &&dataModel.getListName()==null)
                    {
                        intent.putExtra("date","");
                        intent.putExtra("name","Empty List");
                        getContext().startActivity(intent);

                    }
                    else {
                        intent.putExtra("date", dataModel.getListdate());
                        intent.putExtra("name", dataModel.getListName());
                        getContext().startActivity(intent);
                    }

                }
            });
        }
        return convertView;
    }
}
