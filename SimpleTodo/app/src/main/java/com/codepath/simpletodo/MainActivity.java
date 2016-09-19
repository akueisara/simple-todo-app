package com.codepath.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TodoAdapter adapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;
    private TodoItemDatabase mDbHelper;
    ArrayList<TodoItem> todoItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateItemsList();
        setupListViewListener();
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


    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.btnAddItem);
        String itemText = etNewItem.getText().toString();

        // get current date
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
        String datetime = dateformat.format(c.getTime());

        if (!"".equals(itemText)) {
            todoItems.add(new TodoItem(itemText, "", 1, datetime));
            adapter.notifyDataSetChanged();
            etNewItem.setText("");
            writeItemsToDB();
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Please enter a valid item name";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
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
            // Extract name value from result extras
            String itemTitle = data.getExtras().getString("itemTitle");
            String itemBody = data.getExtras().getString("itemBody");
            int itemPriority = data.getExtras().getInt("itemPriority");
            String itemDate = data.getExtras().getString("itemDate");
            int pos = data.getExtras().getInt("pos", 0);
            todoItems.set(pos, new TodoItem(itemTitle, itemBody, itemPriority, itemDate));
            adapter.notifyDataSetChanged();
            writeItemsToDB();
        }
    }

    private void readItemsFromDB() {
        mDbHelper = TodoItemDatabase.getInstance(this);
        todoItems = mDbHelper.getAllItems();
    }

    private void writeItemsToDB() {
        mDbHelper = TodoItemDatabase.getInstance(this);
        mDbHelper.addItems(todoItems);
    }
}
