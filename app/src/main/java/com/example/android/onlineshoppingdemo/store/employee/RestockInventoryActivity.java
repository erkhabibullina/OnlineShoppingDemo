package com.example.android.onlineshoppingdemo.store.employee;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.inventory.Inventory;
import com.example.android.onlineshoppingdemo.inventory.Item;
import com.example.android.onlineshoppingdemo.users.Employee;

import java.util.HashMap;

public class RestockInventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restock_inventory);

        TextView inventoryList = findViewById(R.id.employee_restock_inventory);
        EditText itemId = findViewById(R.id.employee_restock_item_id);
        EditText itemQuantity = findViewById(R.id.employee_restock_item_quantity);

        Intent intent = getIntent();
        Employee employee = (Employee) intent.getSerializableExtra("user");

        Button restockButton = findViewById(R.id.employee_restock_item_button);
        restockButton.setOnClickListener(new RestockButtonController(this, employee));

        itemId.setTransformationMethod(null);
        itemQuantity.setTransformationMethod(null);

        Inventory inventory = DatabaseSelectHelper.getInventory(this);
        HashMap<Item, Integer> itemMap = inventory.getItemMap();

        StringBuilder inventoryText = new StringBuilder();
        for (Item item : itemMap.keySet()) {
            inventoryText.append(item.getId()).append(" - ").append(item.getName().replace("_", " ")).append(": ").append(itemMap.get(item)).append("\n");
        }
        inventoryList.setText(inventoryText.toString());
    }
}
