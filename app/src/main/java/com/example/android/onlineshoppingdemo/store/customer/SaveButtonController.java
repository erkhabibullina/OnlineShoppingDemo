package com.example.android.onlineshoppingdemo.store.customer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseInsertHelper;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.inventory.Item;
import com.example.android.onlineshoppingdemo.store.ShoppingCart;

import java.util.HashMap;

public class SaveButtonController implements View.OnClickListener {

    private Context appContext;
    private ShoppingCart shoppingCart;

    public SaveButtonController(Context context, ShoppingCart shoppingCart) {
        appContext = context;
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void onClick(View v) {
        TextView error = ((ShoppingCartActivity) appContext).findViewById(R.id.cart_error);
        int userId = shoppingCart.getCustomer().getId();
        int accId = DatabaseSelectHelper.getUserAccounts(userId, appContext);

        if (shoppingCart.getItemMap().isEmpty()) {
            error.setText(R.string.empty_error);
            return;
        }

        if (!DatabaseSelectHelper.getAccountDetails(accId, appContext).equals(new HashMap<>())) {
            error.setText(R.string.save_error);
        } else if (accId != -1) {
            HashMap<Item, Integer> saveItems = shoppingCart.getItemMap();
            for (Item saveItem : saveItems.keySet()) {
                int saveId = saveItem.getId();
                int saveQuantity = saveItems.get(saveItem);
                DatabaseInsertHelper.insertAccountLine(accId, saveId, saveQuantity, appContext);
            }
            Toast toast = Toast.makeText(appContext, "Saving cart...", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            error.setText(R.string.account_error);
        }
    }
}
