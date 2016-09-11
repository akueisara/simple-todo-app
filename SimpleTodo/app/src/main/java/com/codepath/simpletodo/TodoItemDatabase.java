package com.codepath.simpletodo;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akueisara on 9/11/2016.
 */
public class TodoItemDatabase extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "todoItem_db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_NAME = "todoList";

    // todoList Table columns
    private static final String KEY_TODO_ID = "id";
    private static final String KEY_TODO_ITEM = "item";

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" +
                KEY_TODO_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_TODO_ITEM + " TEXT" +
                ")";

        Log.d("CREATE_TODO_TABLE", CREATE_TODO_TABLE);

        db.execSQL(CREATE_TODO_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public ArrayList<String> getAllItems() {
        ArrayList<String> items = new ArrayList<String>();

        // SELECT * FROM todoList
        String ITEMS_SELECT_QUERY =
                String.format("SELECT * FROM %s", TABLE_NAME);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY, null);
        try {
            int rows_num = cursor.getCount();
            if (rows_num != 0) {
                cursor.moveToFirst();
                for (int i = 0; i < rows_num; i++) {
                    String item = cursor.getString(cursor.getColumnIndex(KEY_TODO_ITEM));
                    items.add(item);
                    Log.d("item", item);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }

    // Insert a item in the database
    public void addItems(ArrayList<String> items) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        db.execSQL("delete from "+ TABLE_NAME);
//        db.delete(TABLE_NAME, null, null);
        try {
            ContentValues values = new ContentValues();
            for (String item : items) {
                values.put(KEY_TODO_ITEM, item);
                db.insertOrThrow(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
    }
}
