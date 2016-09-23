package com.codepath.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by akueisara on 9/15/2016.
 */
public class TodoAdapter extends ArrayAdapter<TodoItem> {
    private Context mContext;
    private String mCurrentTime;

    public TodoAdapter(Context context, ArrayList<TodoItem> items, String currentTime) {
        super(context, 0, items);
        this.mContext= context;
        this.mCurrentTime= currentTime;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tv_body);
//        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tv_status);

        // Populate the data into the template view using the data object
        tvTitle.setText(item.title);
        tvTitle.setTextColor(setColor(item.priority));

        // If item expires, set title color as gray and item priority as low
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            if(sdf.parse(item.dueDate).before(sdf.parse(mCurrentTime))) {
//                SpannableString sp = new SpannableString(item.title);
//                sp.setSpan(new StrikethroughSpan(), 0, item.title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                tvTitle.setText(sp);
                tvTitle.setTextColor(Color.parseColor("#A0A0A0"));
                item.status = 3 ;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvBody.setText(item.body);
//        tvPriority.setText(item.getPriority(item.priority));
//        tvPriority.setTextColor(setColor(item.priority));
        tvDate.setText(item.dueDate);
        tvStatus.setText(item.getStatus(item.status));

        // Return the completed view to render on screen
        return convertView;
    }

    private int setColor(int priority) {
        if(priority == 1)
            // color red
            return ContextCompat.getColor(mContext, R.color.colorPriorityHigh);
        else if (priority == 2)
            // color orange
            return ContextCompat.getColor(mContext, R.color.colorPriorityMid);
        else if(priority == 3)
            // color green
            return ContextCompat.getColor(mContext, R.color.colorPriorityLow);
        return -1;
    }
}