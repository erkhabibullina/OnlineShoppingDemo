package com.example.android.onlineshoppingdemo.store.admin;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.users.Roles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InactiveAccountButtonController implements View.OnClickListener {

    private Context appContext;

    public InactiveAccountButtonController(Context context) {
        appContext = context;
    }

    @Override
    public void onClick(View v) {
        TextView accountView = ((AccountListUIActivity) appContext)
                .findViewById(R.id.admin_account_list_box);
        int customerRoleId = DatabaseSelectHelper
                .getRoleIdFromName(Roles.CUSTOMER.name(), appContext);
        List<Integer> customers = DatabaseSelectHelper.getUsersByRole(customerRoleId, appContext);
        StringBuilder account_list = new StringBuilder();
        for (int userId : customers) {
            List<Integer> inactiveAccounts = DatabaseSelectHelper
                    .getInactiveAccounts(userId, appContext);
            if (!inactiveAccounts.isEmpty()) {
                account_list.append("UserId ").append(userId).append(": \n");

                for (int inactiveAccount : inactiveAccounts) {

                    account_list.append("AccountID").append(": ").append(inactiveAccount).append("\n");

                }
                account_list.append("\n");
            }
        }
        accountView.setText(account_list);
    }
}
