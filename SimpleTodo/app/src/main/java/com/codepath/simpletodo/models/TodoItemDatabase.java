package com.codepath.simpletodo.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by akueisara on 9/11/2016.
 */
public class TodoItemDatabase extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "todoItem_db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TODO_TABLE_NAME = "todoList";
    private static final String SORT_TABLE_NAME = "todoSort";

    // todoList Table columns
    private static final String KEY_TODO_ID = "id";
    private static final String KEY_TODO_TITLE = "title";
    private static final String KEY_TODO_BODY = "body";
    private static final String KEY_TODO_PRIORITY = "priority";
    private static final String KEY_TODO_DUE_DATE = "date";
    private static final String KEY_TODO_STATUS = "status";
    private static final String KEY_SORT_ID = "id";
    private static final String KEY_SORT_OPTION = "option";

    private static final String TAG = TodoItemDatabase.class.getName();

    private static TodoItemDatabase sInstance;

    public static synchronized TodoItemDatabase getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new TodoItemDatabase(context.getApplicationContext());
        }
        return sInstance;
    }

    public TodoItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TODO_TABLE_NAME +
                "(" +
                KEY_TODO_ID + " INTEGER PRIMARY KEY, " + // Define a primary key
                KEY_TODO_TITLE + " TEXT, " +
                KEY_TODO_BODY + " TEXT, " +
                KEY_TODO_PRIORITY + " INTEGER, " +
                KEY_TODO_DUE_DATE + " TEXT, " +
                KEY_TODO_STATUS + " INTEGER" +
                ")";

        db.execSQL(CREATE_TODO_TABLE);

        String CREATE_SORT_TABLE = "CREATE TABLE " + SORT_TABLE_NAME +
                "(" +
                KEY_SORT_ID + " INTEGER PRIMARY KEY, " + // Define a primary key
                KEY_SORT_OPTION + " INTEGER " +
                ")";

        db.execSQL(CREATE_SORT_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + SORT_TABLE_NAME);
            onCreate(db);
        }
    }

    public ArrayList<TodoItem> getAllItems() {
        ArrayList<TodoItem> items = new ArrayList<TodoItem>();

        // SELECT * FROM todoList
        String ITEMS_SELECT_QUERY = "SELECT * FROM " + TODO_TABLE_NAME;

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY, null);
        try {
            int rows_num = cursor.getCount();
            if (rows_num != 0) {
                cursor.moveToFirst();
                for (int i = 0; i < rows_num; i++) {
                    String title = cursor.getString(cursor.getColumnIndex(KEY_TODO_TITLE));
                    String body = cursor.getString(cursor.getColumnIndex(KEY_TODO_BODY));
                    int priority = cursor.getInt(cursor.getColumnIndex(KEY_TODO_PRIORITY));
                    String dueDate = cursor.getString(cursor.getColumnIndex(KEY_TODO_DUE_DATE));
                    int status = cursor.getInt(cursor.getColumnIndex(KEY_TODO_STATUS));
                    TodoItem item = new TodoItem(title, body, priority, dueDate, status);
                    items.add(item);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }

    // Insert a item in the database
    public void addItems(ArrayList<TodoItem> items) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        //  db.execSQL("delete from "+ TABLE_NAME);
        db.delete(TODO_TABLE_NAME, null, null);
        try {
            ContentValues values = new ContentValues();
            for (TodoItem item : items) {
                values.put(KEY_TODO_TITLE, item.title);
                values.put(KEY_TODO_BODY, item.body);
                values.put(KEY_TODO_PRIORITY, item.priority);
                values.put(KEY_TODO_DUE_DATE, item.dueDate);
                values.put(KEY_TODO_STATUS, item.status);
                db.insertOrThrow(TODO_TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
    }

    public int getOption() {
        // SELECT * FROM todoSortOption
        String ITEMS_SELECT_QUERY = "SELECT * FROM " + SORT_TABLE_NAME;
        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY, null);
        int option = 0;
        try {
            int rows_num = cursor.getCount();
            if (rows_num != 0) {
                cursor.moveToFirst();
                for (int i = 0; i < rows_num; i++) {
                    option = cursor.getInt(cursor.getColumnIndex(KEY_SORT_OPTION));
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return option;
    }

    // Insert a item in the database
    public void updateOption(int option) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        db.delete(SORT_TABLE_NAME, null, null);
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_SORT_OPTION, option);
            db.insertOrThrow(SORT_TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
    }
}