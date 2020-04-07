package com.example.android.onlineshoppingdemo.store.employee;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.users.User;

import java.util.List;

public class AddNewMembershipActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_membership);

    TextView nonMembersView = findViewById(R.id.employee_add_membership_users);
    EditText userIdInput = findViewById(R.id.employee_add_membership_user_id);
    userIdInput.setTransformationMethod(null);

    List<Integer> nonMembers = DatabaseSelectHelper.getNonMembers(this);

    String nonMembersList = "";
    User user;
    for (int userId : nonMembers) {
      user = DatabaseSelectHelper.getUserDetails(userId, this);
      nonMembersList += user.getId() + " - " + user.getName() + "\n";
    }

    Button addMembershipButton = findViewById(R.id.employee_add_membership_button);
    addMembershipButton.setOnClickListener(new AddMembershipButtonController(this, nonMembers));
    nonMembersView.setText(nonMembersList);
  }
}
