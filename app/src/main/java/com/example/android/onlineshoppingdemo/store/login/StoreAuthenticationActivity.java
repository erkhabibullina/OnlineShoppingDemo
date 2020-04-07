package com.example.android.onlineshoppingdemo.store.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.onlineshoppingdemo.R;

public class StoreAuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new LoginButtonController(this));

        EditText userId = findViewById(R.id.login_userId);
        userId.setTransformationMethod(null);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        if (intent.getStringExtra("backPress").equals("no")) {
            return;
        } else {
            super.onBackPressed();
        }
    }
}
