package com.codepath.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;
    private TodoItemDatabase mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
//        readItems();
        readItemsFromDB();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
//        items.add("First Item");
//        items.add("Second Item");
        setupListViewListener();
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.btnAddItem);
        String itemText = etNewItem.getText().toString();
        if (!"".equals(itemText)) {
            items.add(itemText);
            itemsAdapter.notifyDataSetChanged();
            etNewItem.setText("");
//            writeItems();
            writeItemsToDB();
        }
        else {
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
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
//                        writeItems();
                        writeItemsToDB();
                        return true;
                    }
                });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick (AdapterView < ? > adapter, View view, int pos, long arg){
                    Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                    intent.putExtra("text",items.get(pos).toString());
                    intent.putExtra("pos",pos);
                    startActivityForResult(intent,REQUEST_CODE);
                }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String itemName = data.getExtras().getString("itemName");
            int pos = data.getExtras().getInt("pos", 0);
            items.set(pos, itemName);
            itemsAdapter.notifyDataSetChanged();
//            writeItems();
            writeItemsToDB();
        }
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
           FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    private void readItemsFromDB() {
        mDbHelper = TodoItemDatabase.getInstance(this);
        items = mDbHelper.getAllItems();
    }

    private void writeItemsToDB() {
        mDbHelper = TodoItemDatabase.getInstance(this);
        mDbHelper.addItems(items);
    }
}
