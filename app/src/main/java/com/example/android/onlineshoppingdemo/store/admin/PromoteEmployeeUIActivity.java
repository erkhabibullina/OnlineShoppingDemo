package com.example.android.onlineshoppingdemo.store.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.users.Admin;
import com.example.android.onlineshoppingdemo.users.Roles;
import com.example.android.onlineshoppingdemo.users.User;

import java.util.List;


public class PromoteEmployeeUIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promote_employee);

        EditText id = findViewById(R.id.admin_employee_id_input);
        id.setTransformationMethod(null);
        TextView employeeView = findViewById(R.id.employee_list);

        int roleId = DatabaseSelectHelper.getRoleIdFromName(Roles.EMPLOYEE.name(), this);
        List<Integer> employees = DatabaseSelectHelper.getUsersByRole(roleId, this);

        StringBuilder employeeList = new StringBuilder("");
        User user;

        for (int userId : employees) {
            user = DatabaseSelectHelper.getUserDetails(userId, this);
            employeeList.append(user.getId());
            employeeList.append(" - ");
            employeeList.append(user.getName());
            employeeList.append("\n");
        }

        Button promote = findViewById(R.id.promote_button);
        promote.setOnClickListener(
                new PromoteButtonController(this, (Admin) getIntent().getSerializableExtra("user")));
        employeeView.setText(employeeList);

    }

    @Override
    public void onResume() {
        super.onResume();
        TextView employeeView = findViewById(R.id.employee_list);

        int roleId = DatabaseSelectHelper.getRoleIdFromName(Roles.EMPLOYEE.name(), this);
        List<Integer> employees = DatabaseSelectHelper.getUsersByRole(roleId, this);

        StringBuilder employeeList = new StringBuilder("");
        User user;

        for (int userId : employees) {
            user = DatabaseSelectHelper.getUserDetails(userId, this);
            employeeList.append(user.getId());
            employeeList.append(" - ");
            employeeList.append(user.getName());
            employeeList.append("\n");
        }

        employeeView.setText(employeeList);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

