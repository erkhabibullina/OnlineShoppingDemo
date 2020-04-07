package com.example.android.onlineshoppingdemo.store.customer;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.inventory.Item;
import com.example.android.onlineshoppingdemo.store.ShoppingCart;

public class AddToCartController implements View.OnClickListener {

    private Context appContext;
    private Item item;
    private ShoppingCart cart;

    public AddToCartController(Context context, Item item, ShoppingCart cart) {
        appContext = context;
        this.item = item;
        this.cart = cart;
    }

    @Override
    public void onClick(View v) {
        TextView quantityWantedView = ((AddItemActivity) appContext)
                .findViewById(R.id.add_item_quantity);
        int quantityWanted = Integer.parseInt(quantityWantedView.getText().toString());

        if (quantityWanted > 0) {
            cart.addItem(item, quantityWanted);

            Intent i = new Intent();
            i.putExtra("cart", cart);
            ((AddItemActivity) appContext).setResult(RESULT_OK, i);
        }
        ((AddItemActivity) appContext).finish();
    }
}
