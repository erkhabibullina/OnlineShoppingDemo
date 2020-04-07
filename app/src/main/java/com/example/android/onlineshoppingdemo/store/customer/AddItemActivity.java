package com.example.android.onlineshoppingdemo.store.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.inventory.Item;
import com.example.android.onlineshoppingdemo.store.ShoppingCart;


public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);

        Intent intent = getIntent();

        Item item = (Item) intent.getSerializableExtra("item");
        String formattedItemName = intent.getStringExtra("itemName");
        ShoppingCart cart = (ShoppingCart) intent.getSerializableExtra("cart");
        int inventoryQuantity = DatabaseSelectHelper.getInventoryQuantity(item.getId(), this);

        TextView itemView = findViewById(R.id.add_item_itemname);
        TextView inventoryQuantityView = findViewById(R.id.add_item_inventoryquantity);
        TextView quantityWantedView = findViewById(R.id.add_item_quantity);
        TextView subTotal = findViewById(R.id.add_item_totalprice);
        Button addQuantity = findViewById(R.id.add_item_addquantity);
        Button subtractQuantity = findViewById(R.id.add_item_subtractquantity);
        Button addToCart = findViewById(R.id.add_item_addtocart);

        subTotal.setText(String.valueOf(0));
        itemView.setText(formattedItemName);
        inventoryQuantityView.setText(String.valueOf(inventoryQuantity));

        quantityWantedView.setText(String.valueOf(0));

        addQuantity.setOnClickListener(
                new QuantityButtonController(this, 1, inventoryQuantity, item.getPrice()));
        subtractQuantity.setOnClickListener(
                new QuantityButtonController(this, -1, inventoryQuantity, item.getPrice()));
        addToCart
                .setOnClickListener(new AddToCartController(this, item, cart));
    }
}

