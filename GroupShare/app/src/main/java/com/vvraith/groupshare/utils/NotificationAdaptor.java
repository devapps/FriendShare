package com.vvraith.groupshare.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vvraith.groupshare.R;
import com.vvraith.groupshare.models.NotificationDetails;

import java.util.List;

public class NotificationAdaptor extends ArrayAdapter<NotificationDetails> {
    public NotificationAdaptor(Context context, List<NotificationDetails> notificationDetails) {
        super(context, 0, notificationDetails);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        NotificationDetails notificationDetails = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_notification, parent, false);
        }
        // Lookup view for data population
        TextView notification = (TextView) convertView.findViewById(R.id.notification);
        TextView remarks = (TextView) convertView.findViewById(R.id.remarks);

        if(notificationDetails.getNotification().startsWith("You paid") || notificationDetails.getNotification().startsWith(" from You"))
        {
            notification.setTextColor(Color.RED);
            remarks.setTextColor(Color.RED);//Color.parseColor(R.color.debit_text));
        }
        else
        {
            notification.setTextColor(convertView.getResources().getColor(R.color.credit_text));
            remarks.setTextColor(convertView.getResources().getColor(R.color.credit_text));
        }
        // Populate the data into the template view using the data object
        notification.setText(notificationDetails.getNotification());
        remarks.setText(notificationDetails.getRemarks());
        // Return the completed view to render on screen
        return convertView;
    }
}
