package com.hroscope.cegep.cegephoroscope.Friends_List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final FriendListModel dataModel = getItem(position);
        final ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.model, parent, false);
            viewHolder.profilePicture = (ImageView) convertView.findViewById(R.id.friend_profile);
            viewHolder.name = (TextView) convertView.findViewById(R.id.friend_name);
            viewHolder.date = (TextView) convertView.findViewById(R.id.friend_birthdate);
            Picasso.with(mContext).load(dataModel.getProfileImage()).into(viewHolder.profilePicture);
            viewHolder.name.setText(dataModel.getListName());
            viewHolder.date.setText(dataModel.getListdate());
            viewHolder.name.setTextColor(Color.WHITE);
            viewHolder.date.setTextColor(Color.WHITE);
        }
        return convertView;
    }
}
