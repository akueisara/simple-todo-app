package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
        if(item.getItemId() == R.id.icon_button_add) {
            Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
            intent.putExtra("title", "");
            intent.putExtra("body", "");
            intent.putExtra("priority", 1);
            intent.putExtra("date", getCurrentDateTime());
            intent.putExtra("status", 1);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }
        if(item.getItemId() == R.id.sort_title) {
            Collections.sort(todoItems, new TodoItemTitleComparator());
//            Collections.sort(todoItems, new TodoItemStatusComparator());
            adapter.notifyDataSetChanged();
            writeItemsToDB();
            return true;
        }
        if (item.getItemId() == R.id.sort_priority) {
            Collections.sort(todoItems, new TodoItemDateComparator());
            Collections.sort(todoItems, new TodoItemPriorityComparator());
//            Collections.sort(todoItems, new TodoItemStatusComparator());
            adapter.notifyDataSetChanged();
            writeItemsToDB();
            return true;
        }
        if (item.getItemId() == R.id.sort_date) {
            Collections.sort(todoItems, new TodoItemPriorityComparator());
            Collections.sort(todoItems, new TodoItemDateComparator());
//            Collections.sort(todoItems, new TodoItemStatusComparator());
            adapter.notifyDataSetChanged();
            writeItemsToDB();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                int itemStatus = data.getExtras().getInt("itemStatus");
                if (data.hasExtra("pos")) {
                    int pos = data.getExtras().getInt("pos", 0);
                    todoItems.set(pos, new TodoItem(itemTitle, itemBody, itemPriority, itemDate, itemStatus));
                } else {
                    todoItems.add(new TodoItem(itemTitle, itemBody, itemPriority, itemDate, itemStatus));
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

    private void populateItemsList() {
        // Construct the data source
        todoItems = new ArrayList<TodoItem>();
        readItemsFromDB();
        // Create the adapter to convert the array to views
        adapter = new TodoAdapter(this, todoItems, getCurrentDateTime());
        // Attach the adapter to a ListView
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(adapter);
    }

    private void setupListViewListener() {
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
                intent.putExtra("status", todoItems.get(pos).status);
                intent.putExtra("pos", pos);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
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
