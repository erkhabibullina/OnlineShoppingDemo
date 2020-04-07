package com.example.android.onlineshoppingdemo.store.employee;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.users.Roles;
import com.example.android.onlineshoppingdemo.users.User;

import java.util.List;

public class AddNewAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account);

        TextView customerView = findViewById(R.id.employee_add_account_users);
        EditText userIdInput = findViewById(R.id.employee_add_account_user_id);
        userIdInput.setTransformationMethod(null);

        int roleId = DatabaseSelectHelper.getRoleIdFromName(Roles.CUSTOMER.name(), this);
        List<Integer> customers = DatabaseSelectHelper.getUsersByRole(roleId, this);

        String customerList = "";
        User user;
        for (int userId : customers) {
            user = DatabaseSelectHelper.getUserDetails(userId, this);
            customerList += user.getId() + " - " + user.getName() + "\n";
        }

        Button addAccountButton = findViewById(R.id.employee_add_account_button);
        addAccountButton.setOnClickListener(new AddAccountButton(this, customers));
        customerView.setText(customerList);
    }
}
