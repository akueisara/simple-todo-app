package com.codepath.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        setTitle("Edit item");
        String text = getIntent().getStringExtra("text");
        EditText editText = (EditText) findViewById(R.id.editItem);
        editText.setText(text);
    }

    public void onSave(View v) {
        // closes the activity and returns to first screen
        EditText etName = (EditText) findViewById(R.id.editItem);
        if (!"".equals(etName.getText().toString())) {
            Intent data = new Intent();
            data.putExtra("itemName", etName.getText().toString());
            int pos = getIntent().getIntExtra("pos", 0);
            data.putExtra("pos", pos);
            setResult(RESULT_OK, data);
            finish();
        }
        else {
            Context context = getApplicationContext();
            String text = "Please enter a valid item name";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            text = getIntent().getStringExtra("text");
            EditText editText = (EditText) findViewById(R.id.editItem);
            editText.setText(text);
        }
    }
}
