package com.example.android.onlineshoppingdemo.store.admin;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android.onlineshoppingdemo.store.employee.EmployeeUIActivity;
import com.example.android.onlineshoppingdemo.users.Admin;
import com.example.android.onlineshoppingdemo.users.Employee;


public class EmployeeLoginButtonController implements View.OnClickListener {

    private Context appContext;
    private Admin admin;

    public EmployeeLoginButtonController(Context context, Admin admin) {
        appContext = context;
        this.admin = admin;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(appContext, EmployeeUIActivity.class);
        Employee employee = new Employee(admin.getId(), admin.getName(), admin.getAge(),
                admin.getAddress(), true);
        intent.putExtra("user", employee);
        intent.putExtra("backPress", "yes");
        appContext.startActivity(intent);
    }
}
