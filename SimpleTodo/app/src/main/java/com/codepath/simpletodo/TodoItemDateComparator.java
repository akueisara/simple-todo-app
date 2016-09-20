package com.codepath.simpletodo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Created by akueisara on 9/20/2016.
 */
public class TodoItemDateComparator implements Comparator<TodoItem> {
    @Override
    public int compare(TodoItem one, TodoItem another) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            if(sdf.parse(one.dueDate).before(sdf.parse(another.dueDate)))
                return -1;
            else
                return 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}