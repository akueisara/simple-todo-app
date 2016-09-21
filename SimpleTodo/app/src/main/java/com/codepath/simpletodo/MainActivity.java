package com.codepath.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    TodoAdapter adapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;
    private TodoItemDatabase dbHelper;
    ArrayList<TodoItem> todoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateItemsList();
        setupListViewListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addButton) {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            intent.putExtra("title", "");
            intent.putExtra("body", "");
            intent.putExtra("priority", 1);
            intent.putExtra("date", getCurrentDateTime());
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }
        else if(item.getItemId() == R.id.sort_title) {
            Collections.sort(todoItems, new TodoItemTitleComparator());
            adapter.notifyDataSetChanged();
            return true;
        }
        else if (item.getItemId() == R.id.sort_priority) {
            Collections.sort(todoItems, new TodoItemDateComparator());
            Collections.sort(todoItems, new TodoItemPriorityComparator());
            adapter.notifyDataSetChanged();
            return true;

        } else if (item.getItemId() == R.id.sort_date) {
            Collections.sort(todoItems, new TodoItemPriorityComparator());
            Collections.sort(todoItems, new TodoItemDateComparator());
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateItemsList() {
        // Construct the data source
        todoItems = new ArrayList<TodoItem>();
        readItemsFromDB();
        // Create the adapter to convert the array to views
        adapter = new TodoAdapter(this, todoItems);
        // Attach the adapter to a ListView
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(adapter);
    }

    public void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                        todoItems.remove(pos);
                        adapter.notifyDataSetChanged();
                        writeItemsToDB();
                        return true;
                    }
                });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long arg) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("title", todoItems.get(pos).title);
                intent.putExtra("body", todoItems.get(pos).body);
                intent.putExtra("priority", todoItems.get(pos).priority);
                intent.putExtra("date", todoItems.get(pos).dueDate);
                intent.putExtra("pos", pos);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("itemTitle")) {
                // Extract name value from result extras
                String itemTitle = data.getExtras().getString("itemTitle");
                String itemBody = data.getExtras().getString("itemBody");
                int itemPriority = data.getExtras().getInt("itemPriority");
                String itemDate = data.getExtras().getString("itemDate");
                if (data.hasExtra("pos")) {
                    int pos = data.getExtras().getInt("pos", 0);
                    todoItems.set(pos, new TodoItem(itemTitle, itemBody, itemPriority, itemDate));
                } else {
                    todoItems.add(new TodoItem(itemTitle, itemBody, itemPriority, itemDate));
                }
            }
            else {
                int pos = data.getExtras().getInt("pos", 0);
                todoItems.remove(pos);
            }
            adapter.notifyDataSetChanged();
            writeItemsToDB();
        }
    }

    private void readItemsFromDB() {
        dbHelper = TodoItemDatabase.getInstance(this);
        todoItems = dbHelper.getAllItems();
    }

    private void writeItemsToDB() {
        dbHelper = TodoItemDatabase.getInstance(this);
        dbHelper.addItems(todoItems);
    }

    private String getCurrentDateTime() {
        // get current date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateTime = dateFormat.format(c.getTime());
        return dateTime;
    }
}
