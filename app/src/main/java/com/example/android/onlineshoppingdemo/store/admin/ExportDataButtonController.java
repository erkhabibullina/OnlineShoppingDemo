package com.example.android.onlineshoppingdemo.store.admin;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.android.onlineshoppingdemo.store.Serialize;
import com.example.android.onlineshoppingdemo.store.SerializeImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ExportDataButtonController implements View.OnClickListener {

    private Context appContext;

    public ExportDataButtonController(Context context) {
        appContext = context;
    }

    @Override
    public void onClick(View v) {
        Serialize export = new SerializeImpl();
        String ExFileName = "database_copy.ser";
        export.serializeDatabase(appContext);
        try {
            FileOutputStream file = appContext.openFileOutput(ExFileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(export);
            out.close();
            file.close();
            Toast.makeText(appContext, "Database export successful", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(appContext, "Database export unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }
}
