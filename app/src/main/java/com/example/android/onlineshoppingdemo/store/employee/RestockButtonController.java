package com.example.android.onlineshoppingdemo.store.employee;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.inventory.Inventory;
import com.example.android.onlineshoppingdemo.inventory.Item;
import com.example.android.onlineshoppingdemo.store.EmployeeInterface;
import com.example.android.onlineshoppingdemo.users.Employee;
import com.example.android.onlineshoppingdemo.validation.Validator;

import java.util.HashMap;

public class RestockButtonController implements View.OnClickListener {

    private Context appContext;
    private Employee employee;

    public RestockButtonController(Context context, Employee employee) {
        appContext = context;
        this.employee = employee;
    }

    @Override
    public void onClick(View v) {
        Inventory inventory = DatabaseSelectHelper.getInventory(appContext);
        EmployeeInterface employeeInterface = new EmployeeInterface(employee, inventory);

        EditText itemId = ((RestockInventoryActivity) appContext)
                .findViewById(R.id.employee_restock_item_id);
        EditText itemQuantity = ((RestockInventoryActivity) appContext)
                .findViewById(R.id.employee_restock_item_quantity);
        TextView error = ((RestockInventoryActivity) appContext)
                .findViewById(R.id.employee_restock_item_error);

        TextView inventoryList = ((RestockInventoryActivity) appContext)
                .findViewById(R.id.employee_restock_inventory);

        int parsedItemId = -1;
        int parsedItemQuantity = -1;

        if (!Validator.validateEmpty(itemId.getText().toString())) {
            parsedItemId = Integer.parseInt(itemId.getText().toString());
        }
        if (!Validator.validateEmpty(itemQuantity.getText().toString())) {
            parsedItemQuantity = Integer.parseInt(itemQuantity.getText().toString());
        }
        if (!Validator.validateItemId(parsedItemId, appContext)
                || DatabaseSelectHelper.getItem(parsedItemId, appContext) == null) {
            error.setText(R.string.item_id_error);
        } else if (!Validator.validateRestockQuantity(parsedItemQuantity)) {
            error.setText(R.string.item_quantity_error);
        } else {
            Item itemRestock = DatabaseSelectHelper.getItem(parsedItemId, appContext);
            boolean complete = employeeInterface
                    .restockInventory(itemRestock, parsedItemQuantity, appContext);

            if (complete) {
                inventory = DatabaseSelectHelper.getInventory(appContext);
                HashMap<Item, Integer> itemMap = inventory.getItemMap();

                Toast toast = Toast.makeText(appContext, "Restocking item...", Toast.LENGTH_SHORT);
                toast.show();

                String inventoryText = "";
                for (Item item : itemMap.keySet()) {
                    inventoryText +=
                            item.getId() + " - " + item.getName().replace("_", " ") + ": " + itemMap.get(item)
                                    + "\n";
                }
                inventoryList.setText(inventoryText);
                error.setText("");
            } else {
                error.setText(R.string.stock_overflow);
            }
        }
    }
}
