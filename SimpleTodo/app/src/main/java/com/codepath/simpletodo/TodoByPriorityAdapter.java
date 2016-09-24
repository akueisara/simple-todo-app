package com.codepath.simpletodo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
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
public class TodoByPriorityAdapter extends ArrayAdapter<TodoItem> {
    private Context mContext;
    private String mCurrentTime;

    public TodoByPriorityAdapter(Context context, ArrayList<TodoItem> items, String currentTime) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo_by_priority, parent, false);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tv_body);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tv_sort_by_priority);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tv_status);

        // Populate the data into the template view using the data object
        tvTitle.setText(item.title);
        tvTitle.setTextColor(setColor(item.priority));

        tvBody.setText(item.body);
        tvBody.setTextColor(ContextCompat.getColor(mContext, R.color.colorBody));

        tvDate.setText(item.dueDate);

        setPriority(tvPriority, item);

        if(item.status == 2) {
            tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorDisable));
            tvBody.setTextColor(ContextCompat.getColor(mContext, R.color.colorDisable));
        }

        // If item expires, set title color as gray and item priority as low
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            if(sdf.parse(item.dueDate).before(sdf.parse(mCurrentTime))) {
                item.status = 3 ;
                tvStatus.setText(mContext.getResources().getString(R.string.expired));
                tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorDisable));
                tvBody.setTextColor(ContextCompat.getColor(mContext, R.color.colorDisable));
            }
            else {
                tvStatus.setText("");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    private void setPriority(TextView tvPriority, TodoItem item) {
        if(item.priority == 1) {
            tvPriority.setVisibility(View.VISIBLE);
            tvPriority.setText(mContext.getResources().getString(R.string.high));
            tvPriority.setBackgroundColor(setColor(item.priority));
        }
        else if(item.priority == 2) {
            tvPriority.setVisibility(View.VISIBLE);
            tvPriority.setText(mContext.getResources().getString(R.string.medium));
            tvPriority.setBackgroundColor(setColor(item.priority));
        }
        else if(item.priority == 3) {
            tvPriority.setVisibility(View.VISIBLE);
            tvPriority.setText(mContext.getResources().getString(R.string.low));
            tvPriority.setBackgroundColor(setColor(item.priority));
        }
    }
}
