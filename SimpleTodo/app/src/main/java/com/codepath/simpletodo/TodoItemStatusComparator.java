package com.codepath.simpletodo;

import java.util.Comparator;

/**
 * Created by akueisara on 9/22/2016.
 */
public class TodoItemStatusComparator implements Comparator<TodoItem> {
    @Override
    public int compare(TodoItem one, TodoItem another) {
        if(one.status == 3)
        return another.status - one.status;
        else
            return 0;
    }
}
