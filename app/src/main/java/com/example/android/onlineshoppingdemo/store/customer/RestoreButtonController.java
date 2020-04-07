package com.example.android.onlineshoppingdemo.store.customer;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.inventory.Item;
import com.example.android.onlineshoppingdemo.store.ShoppingCart;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RestoreButtonController implements View.OnClickListener {

    private Context appContext;
    private ShoppingCart shoppingCart;


    public RestoreButtonController(Context context, ShoppingCart shoppingCart) {
        appContext = context;
        this.shoppingCart = shoppingCart;

    }

    @Override
    public void onClick(View v) {
        TextView error = ((ShoppingCartActivity) appContext).findViewById(R.id.cart_error);

        int userId = shoppingCart.getCustomer().getId();
        List<Integer> accIds = DatabaseSelectHelper.getActiveAccounts(userId, appContext);

        int accId = -1;
        if (!accIds.isEmpty()) {
            accId = Collections.max(accIds);
        }
        if (accId != -1) {
            shoppingCart.clearCart();
            HashMap<Item, Integer> resItems = DatabaseSelectHelper.getAccountDetails(accId, appContext);
            for (Item resItem : resItems.keySet()) {
                int resQuantity = resItems.get(resItem);
                shoppingCart.addItem(resItem, resQuantity);
            }
            if (shoppingCart.getItemMap().isEmpty()) {
                error.setText(R.string.no_save);
                return;
            }

            Toast toast = Toast.makeText(appContext, "Restoring cart...", Toast.LENGTH_SHORT);
            toast.show();

            shoppingCart.setRestoredCart(true);
            Intent i = new Intent();
            i.putExtra("cart", shoppingCart);
            ((ShoppingCartActivity) appContext).setResult(RESULT_OK, i);

            ((ShoppingCartActivity) appContext).finish();

        } else {
            error.setText(R.string.account_error);
        }
    }
}
