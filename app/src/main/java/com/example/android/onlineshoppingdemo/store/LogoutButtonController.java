package com.example.android.onlineshoppingdemo.store;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.android.onlineshoppingdemo.store.login.StoreAuthenticationActivity;

public class LogoutButtonController implements View.OnClickListener {

    private Context appContext;

    public LogoutButtonController(Context context) {
        appContext = context;
    }

    @Override
    public void onClick(View view) {
        Toast toast = Toast.makeText(appContext, "Logging out...", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(appContext, StoreAuthenticationActivity.class);
        intent.putExtra("backPress", "no");
        appContext.startActivity(intent);
    }
}
