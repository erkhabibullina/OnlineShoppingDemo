package com.example.android.onlineshoppingdemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.store.login.StoreAuthenticationActivity;
import com.example.android.onlineshoppingdemo.store.signup.AccountCreationActivity;
import com.example.android.onlineshoppingdemo.users.Roles;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startUp();
    }

    private void startUp() {
        int adminId = DatabaseSelectHelper.getRoleIdFromName(Roles.ADMIN.name(), this);
        int employeeId = DatabaseSelectHelper.getRoleIdFromName(Roles.EMPLOYEE.name(), this);
        if (adminId == -1) {
            Intent intent = new Intent(this, AccountCreationActivity.class);
            intent.putExtra("sign_up_display", getResources().getString(R.string.admin_sign_up));
            intent.putExtra("role", Roles.ADMIN.name());
            intent.putExtra("access", "init");
            intent.putExtra("backPress", "no");
            startActivity(intent);
        } else if (employeeId == -1) {
            Intent intent = new Intent(this, AccountCreationActivity.class);
            intent.putExtra("sign_up_display", getResources().getString(R.string.employee_sign_up));
            intent.putExtra("role", Roles.EMPLOYEE.name());
            intent.putExtra("access", "init");
            intent.putExtra("backPress", "no");
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, StoreAuthenticationActivity.class);
            intent.putExtra("backPress", "no");
            startActivity(intent);
        }
    }
}
