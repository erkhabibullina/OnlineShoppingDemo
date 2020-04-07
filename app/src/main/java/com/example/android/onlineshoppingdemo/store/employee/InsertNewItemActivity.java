package com.example.android.onlineshoppingdemo.store.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.users.Employee;


public class InsertNewItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_item);

        Intent intent = getIntent();
        Employee employee = (Employee) intent.getSerializableExtra("user");
        EditText quantity = findViewById(R.id.employee_insert_item_quantity);
        quantity.setTransformationMethod(null);

        Button submitButton = findViewById(R.id.employee_insert_item_submit_button);
        submitButton.setOnClickListener(new InsertItemSubmitButton(this, employee));
    }
}
