package com.example.android.onlineshoppingdemo.store.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.store.login.StoreAuthenticationActivity;
import com.example.android.onlineshoppingdemo.users.Admin;

public class AdminLoginUIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        Button admin = findViewById(R.id.admin_login);
        admin.setOnClickListener(
                new AdminLoginButtonController(this, (Admin) getIntent().getSerializableExtra("user")));
        Button employee = findViewById(R.id.employee_login);
        employee.setOnClickListener(
                new EmployeeLoginButtonController(this, (Admin) getIntent().getSerializableExtra("user")));
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(this, StoreAuthenticationActivity.class);
        intent.putExtra("backPress", "no");
        startActivity(intent);
    }
}
