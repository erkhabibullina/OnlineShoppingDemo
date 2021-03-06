package com.example.android.onlineshoppingdemo.store.admin;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android.onlineshoppingdemo.users.Admin;

public class AdminLoginButtonController implements View.OnClickListener {

    private Context appContext;
    private Admin admin;

    public AdminLoginButtonController(Context context, Admin admin) {
        appContext = context;
        this.admin = admin;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(appContext, AdminUIActivity.class);
        intent.putExtra("user", admin);
        appContext.startActivity(intent);
    }
}
