package com.example.android.onlineshoppingdemo.store.employee;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseInsertHelper;
import com.example.android.onlineshoppingdemo.validation.Validator;

import java.util.List;

public class AddAccountButton implements View.OnClickListener {

    private Context appContext;
    private List<Integer> customers;

    public AddAccountButton(Context context, List<Integer> customers) {
        appContext = context;
        this.customers = customers;
    }

    @Override
    public void onClick(View v) {
        EditText userId = ((AddNewAccountActivity) appContext)
                .findViewById(R.id.employee_add_account_user_id);

        TextView error = ((AddNewAccountActivity) appContext)
                .findViewById(R.id.employee_add_account_error);

        int parsedUserId = -1;
        if (!Validator.validateEmpty(userId.getText().toString().trim())) {
            parsedUserId = Integer.parseInt(userId.getText().toString().trim());
        }

        if (!Validator.validateUserId(parsedUserId, appContext) || !customers.contains(parsedUserId)) {
            error.setText(R.string.user_id_error);
        } else {
            int accId = DatabaseInsertHelper.insertAccount(parsedUserId, true, appContext);
            if (accId == -1) {
                error.setText(R.string.account_creation_error);
            } else {
                Toast toast = Toast.makeText(appContext, "Creating account...", Toast.LENGTH_SHORT);
                toast.show();
                ((AddNewAccountActivity) appContext).finish();
            }
        }
    }
}
