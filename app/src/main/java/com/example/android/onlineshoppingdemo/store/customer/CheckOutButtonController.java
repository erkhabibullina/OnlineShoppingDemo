package com.example.android.onlineshoppingdemo.store.customer;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.database.DatabaseUpdateHelper;
import com.example.android.onlineshoppingdemo.inventory.Item;
import com.example.android.onlineshoppingdemo.store.ShoppingCart;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CheckOutButtonController implements View.OnClickListener {

    private Context appContext;
    private ShoppingCart cart;

    public CheckOutButtonController(Context context, ShoppingCart cart) {
        appContext = context;
        this.cart = cart;
    }


    @Override
    public void onClick(View v) {
        boolean purchasable = true;
        HashMap<Item, Integer> itemMap = cart.getItemMap();
        if (!itemMap.isEmpty()) {
            Item problemItem = null;
            int dbQuantity = -1;

            for (Item item : itemMap.keySet()) {
                dbQuantity = DatabaseSelectHelper.getInventoryQuantity(item.getId(), appContext);
                if (dbQuantity < itemMap.get(item)) {
                    problemItem = item;
                    purchasable = false;
                    break;
                }
            }

            if (purchasable) {
                if (cart.checkOut(appContext)) {
                    if (cart.getRestoredCart()) {
                        int userId = cart.getCustomer().getId();
                        List<Integer> accIds = DatabaseSelectHelper.getActiveAccounts(userId, appContext);
                        DatabaseUpdateHelper.updateAccountStatus(Collections.max(accIds), false, appContext);
                    }
                    Toast.makeText(appContext, "Thank you for shopping with us!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(appContext, CustomerUIActivity.class);
                    ((ShoppingCartActivity) appContext).finish();
                    intent.putExtra("user", cart.getCustomer());
                    appContext.startActivity(intent);
                }
            } else {
                String formattedItemName = (problemItem.getName().substring(0, 1).toUpperCase()
                        + problemItem
                        .getName().substring(1).toLowerCase()).replace("_", " ");
                String msg = "Our inventory has only " + dbQuantity + " of " + formattedItemName;

                Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(appContext, "Your cart is empty!", Toast.LENGTH_SHORT).show();
        }
    }
}
