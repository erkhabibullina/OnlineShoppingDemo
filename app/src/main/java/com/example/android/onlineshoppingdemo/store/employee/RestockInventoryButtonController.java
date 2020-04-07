package com.example.android.onlineshoppingdemo.store.employee;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android.onlineshoppingdemo.users.Employee;

public class RestockInventoryButtonController implements View.OnClickListener {

    private Context appContext;
    private Employee employee;

    public RestockInventoryButtonController(Context context, Employee employee) {
        appContext = context;
        this.employee = employee;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(appContext, RestockInventoryActivity.class);
        intent.putExtra("user", employee);
        appContext.startActivity(intent);
    }
}
