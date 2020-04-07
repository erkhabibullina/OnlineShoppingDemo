package com.example.android.onlineshoppingdemo.store.admin;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.users.Roles;

import java.util.List;

public class ActiveAccountButtonController implements View.OnClickListener {

    private Context appContext;

    public ActiveAccountButtonController(Context context) {
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
            List<Integer> activeAccounts = DatabaseSelectHelper.getActiveAccounts(userId, appContext);
            if (!activeAccounts.isEmpty()) {
                account_list.append("UserId ").append(userId).append(": \n");
                for (int activeAccount : activeAccounts) {
                    account_list.append("AccountId").append(": ").append(activeAccount).append("\n");
                }
                account_list.append("\n");
            }
        }
        accountView.setText(account_list);
    }
}
