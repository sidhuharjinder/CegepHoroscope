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
            Picasso.with(mContext).load(dataModel.getProfileImage()).into(viewHolder.profilePicture);
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
