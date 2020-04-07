package com.example.android.onlineshoppingdemo.store.admin;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.onlineshoppingdemo.store.Serialize;
import com.example.android.onlineshoppingdemo.store.SerializeImpl;
import com.example.android.onlineshoppingdemo.store.login.StoreAuthenticationActivity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ImportDataButtonController extends AppCompatActivity implements View.OnClickListener {

    private Context appContext;

    public ImportDataButtonController(Context context) {
        appContext = context;
    }

    @Override
    public void onClick(View v) {
        Serialize backUp = new SerializeImpl();
        backUp.serializeDatabase(appContext);
        String ImFileName = "database_copy.ser";
        Serialize ImFile = new SerializeImpl();
        boolean back = false;
        try {
            FileInputStream file = appContext.openFileInput(ImFileName);
            ObjectInputStream in = new ObjectInputStream(file);
            ImFile = (Serialize) in.readObject();

            in.close();
            file.close();

            Toast.makeText(appContext, "Database import successful", Toast.LENGTH_SHORT).show();
        } catch (IOException | ClassNotFoundException e) {
            Toast.makeText(appContext, "Database import unsuccessful", Toast.LENGTH_SHORT).show();
            back = true;
            e.printStackTrace();
        }
        if (ImFile != null && !back) {
            ImFile.deserializeDatabase(appContext);
        } else if (back) {
            backUp.deserializeDatabase(appContext);
        }
        Toast toast = Toast.makeText(appContext, "Logging out...", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(appContext, StoreAuthenticationActivity.class);
        intent.putExtra("backPress", "no");
        appContext.startActivity(intent);
    }
}
