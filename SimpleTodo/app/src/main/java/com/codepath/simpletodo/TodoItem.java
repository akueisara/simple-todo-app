package com.codepath.simpletodo;

/**
 * Created by akueisara on 9/14/2016.
 */
public class TodoItem {
    public String title;
    public String body;
    public int priority;
    public String dueDate;
    public int status;

    public TodoItem(String title, String body, int priority, String dueDate, int status) {
        this.title = title;
        this.body = body;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getStatus(int status) {
        if(status == 1) {
            return "TODO";
        }
        else if(status == 2) {
            return "DONE";
        }
        else if(status == 3) {
            return "EXPIRED";
        }
        return "";
    }
}
