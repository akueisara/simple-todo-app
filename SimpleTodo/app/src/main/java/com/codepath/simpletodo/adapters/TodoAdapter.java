package com.codepath.simpletodo.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.models.TodoItem;

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
        TextView tvDate = (TextView) convertView.findViewById(R.id.tv_date);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tv_status);

        // Populate the data into the template view using the data object
        tvTitle.setText(item.title);
        tvTitle.setTextColor(setColor(item.priority));

        tvBody.setText(item.body);
        tvBody.setTextColor(ContextCompat.getColor(mContext, R.color.colorBody));

        tvDate.setText(item.dueDate);

        if(item.status == 2) {
            tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorDisable));
            tvBody.setTextColor(ContextCompat.getColor(mContext, R.color.colorDisable));
        }

        // If item expires, set title color as gray
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            if(sdf.parse(item.dueDate).before(sdf.parse(mCurrentTime))) {
                tvTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorDisable));
                tvBody.setTextColor(ContextCompat.getColor(mContext, R.color.colorDisable));
                item.status = 3 ;
                tvStatus.setText(mContext.getResources().getString(R.string.expired));
            }
            else {
                tvStatus.setText("");
                if(item.status == 2) {
                    tvStatus.setText(mContext.getResources().getString(R.string.done));
                    tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorDoneStatus));
                }
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
}