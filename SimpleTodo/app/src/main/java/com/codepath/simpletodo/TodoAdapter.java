package com.codepath.simpletodo;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by akueisara on 9/15/2016.
 */
public class TodoAdapter extends ArrayAdapter<TodoItem> {
    private Context mContext;

    public TodoAdapter(Context context, ArrayList<TodoItem> items) {
        super(context, 0, items);
        this.mContext= context;
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
        // Populate the data into the template view using the data object
        tvTitle.setText(item.title);
        tvTitle.setTextColor(setColor(item.priority));
        tvBody.setText(item.body);
//        tvPriority.setText(item.getPriority(item.priority));
//        tvPriority.setTextColor(setColor(item.priority));
        tvDate.setText(item.dueDate);
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