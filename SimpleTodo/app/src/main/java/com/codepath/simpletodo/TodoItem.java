package com.codepath.simpletodo;

import java.util.ArrayList;

/**
 * Created by akueisara on 9/14/2016.
 */
public class TodoItem {
    public String title;
    public String body;
    public int priority;
    public String dueDate;

    public TodoItem(String title, String body, int priority, String dueDate) {
        this.title = title;
        this.body = body;
        this.priority = priority;
        this.dueDate = dueDate;
    }
}
