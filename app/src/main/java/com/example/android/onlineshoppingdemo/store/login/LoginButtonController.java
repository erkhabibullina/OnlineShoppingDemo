package com.example.android.onlineshoppingdemo.store.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.store.admin.AdminLoginUIActivity;
import com.example.android.onlineshoppingdemo.store.customer.CustomerUIActivity;
import com.example.android.onlineshoppingdemo.store.employee.EmployeeUIActivity;
import com.example.android.onlineshoppingdemo.users.Admin;
import com.example.android.onlineshoppingdemo.users.Customer;
import com.example.android.onlineshoppingdemo.users.Employee;
import com.example.android.onlineshoppingdemo.users.Roles;
import com.example.android.onlineshoppingdemo.users.User;
import com.example.android.onlineshoppingdemo.validation.Validator;

public class LoginButtonController implements View.OnClickListener {

    private Context appContext;

    public LoginButtonController(Context context) {
        appContext = context;
    }

    @Override
    public void onClick(View v) {
        String TAG = "loginActivity";
        EditText userId = ((StoreAuthenticationActivity) appContext).findViewById(R.id.login_userId);
        EditText password = ((StoreAuthenticationActivity) appContext)
                .findViewById(R.id.login_password);
        TextView error = ((StoreAuthenticationActivity) appContext).findViewById(R.id.login_error);

        Integer parsedUserId = -1;
        if (!Validator.validateEmpty(userId.getText().toString())) {
            parsedUserId = Integer.parseInt(userId.getText().toString());
        }
        String parsedPassword = password.getText().toString();

        User user = DatabaseSelectHelper.getUserDetails(parsedUserId, appContext);
        if (user != null && user.authenticate(parsedPassword, appContext)) {

            int roleId = DatabaseSelectHelper.getUserRoleId(parsedUserId, appContext);
            String roleName = DatabaseSelectHelper.getRoleName(roleId, appContext);

            if (roleName.equals(Roles.ADMIN.name())) {
                Intent intent = new Intent(appContext, AdminLoginUIActivity.class);
                Admin admin = new Admin(user.getId(), user.getName(), user.getAge(),
                        user.getAddress(), true);
                intent.putExtra("user", admin);
                appContext.startActivity(intent);
            } else if (roleName.equals(Roles.EMPLOYEE.name())) {
                Intent intent = new Intent(appContext, EmployeeUIActivity.class);
                Employee employee = new Employee(user.getId(), user.getName(), user.getAge(),
                        user.getAddress(), true);
                intent.putExtra("user", employee);
                intent.putExtra("backPress", "no");
                appContext.startActivity(intent);
            } else if (roleName.equals(Roles.CUSTOMER.name())) {
                Intent intent = new Intent(appContext, CustomerUIActivity.class);
                Customer customer = new Customer(user.getId(), user.getName(), user.getAge(),
                        user.getAddress(), true, appContext);
                intent.putExtra("user", customer);
                appContext.startActivity(intent);
            }
        } else {
            error.setText(R.string.login_error);
        }
    }
}
