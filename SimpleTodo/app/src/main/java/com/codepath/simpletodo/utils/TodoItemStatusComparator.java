package com.codepath.simpletodo.utils;

import com.codepath.simpletodo.models.TodoItem;

import java.util.Comparator;

/**
 * Created by akueisara on 9/22/2016.
 */
public class TodoItemStatusComparator implements Comparator<TodoItem> {
    @Override
    public int compare(TodoItem one, TodoItem another) {
        return one.status - another.status;
    }
}
