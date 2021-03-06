package com.example.android.onlineshoppingdemo.store.employee;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.store.signup.AccountCreationActivity;
import com.example.android.onlineshoppingdemo.users.Roles;

public class AddCustomerButtonController implements View.OnClickListener {

    private Context appContext;

    public AddCustomerButtonController(Context context) {
        appContext = context;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(appContext, AccountCreationActivity.class);
        intent.putExtra("sign_up_display",
                appContext.getResources().getString(R.string.customer_sign_up));
        intent.putExtra("role", Roles.CUSTOMER.name());
        intent.putExtra("access", "employeeAccess");
        intent.putExtra("backPress", "yes");
        appContext.startActivity(intent);
    }
}
