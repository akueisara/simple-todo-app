package com.codepath.simpletodo;

import android.content.Context;
import android.graphics.Color;
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
    public TodoAdapter(Context context, ArrayList<TodoItem> items) {
        super(context, 0, items);
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
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
//        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
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
            return Color.RED;
        else if (priority == 2)
            return Color.argb(255,255,105,0);
        else if(priority == 3)
            return Color.argb(255,0,128,0);
        return -1;
    }
}