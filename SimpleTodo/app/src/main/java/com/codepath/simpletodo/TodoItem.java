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

    public static ArrayList<TodoItem> getItems() {
        ArrayList<TodoItem> items = new ArrayList<TodoItem>();
        items.add(new TodoItem("Item 1", "Task 1", 1, "09/18/2016"));
        items.add(new TodoItem("Item 2", "Task 2", 2, "09/19/2016"));
        items.add(new TodoItem("Item 3", "Task 3", 3, "09/20/2016"));
        return items;
    }

    public String getPriority(int priority) {
        if(priority == 1)
            return "High";
        else if (priority == 2)
            return "Medium";
        else if (priority == 3)
            return "Low";
        return "";
    }
}
