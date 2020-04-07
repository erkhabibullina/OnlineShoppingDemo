package com.example.android.onlineshoppingdemo.store.employee;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android.onlineshoppingdemo.store.login.StoreAuthenticationActivity;


public class AuthenticateEmployeeButtonController implements View.OnClickListener {

    private Context appContext;

    public AuthenticateEmployeeButtonController(Context context) {
        appContext = context;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(appContext, StoreAuthenticationActivity.class);
        intent.putExtra("backPress", "yes");
        appContext.startActivity(intent);
    }
}
