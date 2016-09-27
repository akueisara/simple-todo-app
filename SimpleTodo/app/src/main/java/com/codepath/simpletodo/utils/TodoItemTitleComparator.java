package com.codepath.simpletodo.utils;

import com.codepath.simpletodo.models.TodoItem;

import java.util.Comparator;

/**
 * Created by akueisara on 9/20/2016.
 */
public class TodoItemTitleComparator implements Comparator<TodoItem> {
    @Override
    public int compare(TodoItem one, TodoItem another) {
       return one.title.compareTo(another.title);
    }
}
