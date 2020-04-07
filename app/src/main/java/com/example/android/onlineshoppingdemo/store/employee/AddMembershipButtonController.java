package com.example.android.onlineshoppingdemo.store.employee;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseUpdateHelper;
import com.example.android.onlineshoppingdemo.validation.Validator;

import java.util.List;

public class AddMembershipButtonController implements View.OnClickListener {

    private Context appContext;
    List<Integer> nonMembers;

    public AddMembershipButtonController(Context context, List<Integer> nonMembers) {
        appContext = context;
        this.nonMembers = nonMembers;
    }

    @Override
    public void onClick(View v) {
        TextView userId = ((AddNewMembershipActivity) appContext)
                .findViewById(R.id.employee_add_membership_user_id);

        TextView error = ((AddNewMembershipActivity) appContext)
                .findViewById(R.id.employee_add_membership_error);

        Integer parsedUserId = -1;
        if (!Validator.validateEmpty(userId.getText().toString().trim())) {
            parsedUserId = Integer.parseInt(userId.getText().toString().trim());
        }

        if (!Validator.validateUserId(parsedUserId, appContext) || !nonMembers.contains(parsedUserId)) {
            error.setText(R.string.user_id_error);
        } else {
            boolean complete = DatabaseUpdateHelper.updateMembershipStatus(parsedUserId, 1, appContext);

            if (complete) {
                Toast toast = Toast.makeText(appContext, "Adding membership...", Toast.LENGTH_SHORT);
                toast.show();
                ((AddNewMembershipActivity) appContext).finish();
            } else {
                error.setText(R.string.membership_error);
            }
        }
    }
}
