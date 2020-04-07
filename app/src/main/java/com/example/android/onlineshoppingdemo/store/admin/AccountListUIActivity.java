package com.example.android.onlineshoppingdemo.store.admin;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.onlineshoppingdemo.R;


public class AccountListUIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_account_list);

        Button active = findViewById(R.id.admin_active_accounts);
        active.setOnClickListener(new ActiveAccountButtonController(this));
        Button inactive = findViewById(R.id.admin_inactive_accounts);
        inactive.setOnClickListener(new InactiveAccountButtonController(this));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}

