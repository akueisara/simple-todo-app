package com.codepath.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by akueisara on 9/20/2016.
 */
public class AddItemActivity extends AppCompatActivity implements EditDialogFragment.EditDialogListener {
    private int priority;

    private static int[] PRIORITY_COLORS = new int[]{
            R.color.colorPriorityHigh,
            R.color.colorPriorityMid,
            R.color.colorPriorityLow,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        setTitle("Add Item");

        // Title
        String title = getIntent().getStringExtra("title");
        EditText titleEditText = (EditText) findViewById(R.id.addItemTitleEditText);
        titleEditText.setText(title);

        // Body
        String body = getIntent().getStringExtra("body");
        EditText bodyEditText = (EditText) findViewById(R.id.addItemBodyEditText);
        bodyEditText.setText(body);

        // Priority
        priority = getIntent().getIntExtra("priority", 0);
        Spinner spinner = (Spinner) findViewById(R.id.addPrioritySpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(priority-1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(ContextCompat.getColor(getApplicationContext(), PRIORITY_COLORS[position]));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //  Date
        String date = getIntent().getStringExtra("date");
        TextView dateTextView = (TextView) findViewById(R.id.addEditDateTextView);
        dateTextView.setText(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.escButton) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAdd(View v) {
        EditText etTitle = (EditText) findViewById(R.id.addItemTitleEditText);
        EditText etBody = (EditText) findViewById(R.id.addItemBodyEditText);
        TextView tvDate = (TextView) findViewById(R.id.addEditDateTextView);
        Spinner spinner = (Spinner) findViewById(R.id.addPrioritySpinner);
        if (!"".equals(etTitle.getText().toString())) {
            Intent data = new Intent();
            data.putExtra("itemTitle", etTitle.getText().toString());
            data.putExtra("itemBody", etBody.getText().toString());
            data.putExtra("itemPriority", spinner.getSelectedItemPosition()+1);
            data.putExtra("itemDate", tvDate.getText().toString());
            setResult(RESULT_OK, data);
            finish();
        }
        else {
            Context context = getApplicationContext();
            String text = "Please enter a valid item name";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            // Reset title
            String title = getIntent().getStringExtra("title");
            EditText titleEditText = (EditText) findViewById(R.id.addItemTitleEditText);
            titleEditText.setText(title);

            // Reset body
            String body = getIntent().getStringExtra("body");
            EditText bodyeditText = (EditText) findViewById(R.id.addItemBodyEditText);
            bodyeditText.setText(body);
        }
    }

    public void onCancel(View v) {
        finish();
    }

    public void onDatePick(View view) {
        showEditDialog();
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        TextView tvDate = (TextView) findViewById(R.id.addEditDateTextView);
        EditDialogFragment editItemDialog = EditDialogFragment.newInstance(tvDate.getText().toString());
        editItemDialog.show(fm, "fragment_edit_date");
    }

    @Override
    public void onFinishEditDialog(String date) {
        TextView tvDate = (TextView) findViewById(R.id.addEditDateTextView);
        tvDate.setText(date);
    }
}
