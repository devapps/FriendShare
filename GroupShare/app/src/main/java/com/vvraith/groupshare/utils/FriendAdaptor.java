package com.vvraith.groupshare.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vvraith.groupshare.R;
import com.vvraith.groupshare.models.FriendDetails;

import java.util.List;

/**
 * Created by Administrator on 20/03/2016.
 */
public class FriendAdaptor extends ArrayAdapter<FriendDetails> {
    public FriendAdaptor(Context context, List<FriendDetails> friendDetails) {
        super(context, 0, friendDetails);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FriendDetails friendDetails = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_friend, parent, false);
        }
        // Lookup view for data population
        TextView friendvirtualid = (TextView) convertView.findViewById(R.id.friendvirtualid);
        TextView friendname = (TextView) convertView.findViewById(R.id.friendname);
        // Populate the data into the template view using the data object
        friendvirtualid.setText(friendDetails.getFriendVirtualId());
        friendname.setText(friendDetails.getFriendName());
        // Return the completed view to render on screen
        return convertView;
    }
}
