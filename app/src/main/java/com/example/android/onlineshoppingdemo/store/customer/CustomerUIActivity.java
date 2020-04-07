package com.example.android.onlineshoppingdemo.store.customer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.onlineshoppingdemo.R;
import com.example.android.onlineshoppingdemo.database.DatabaseSelectHelper;
import com.example.android.onlineshoppingdemo.exceptions.AuthenticationException;
import com.example.android.onlineshoppingdemo.inventory.Item;
import com.example.android.onlineshoppingdemo.inventory.ItemImpl;
import com.example.android.onlineshoppingdemo.inventory.ItemTypes;
import com.example.android.onlineshoppingdemo.inventory.MemberItemTypes;
import com.example.android.onlineshoppingdemo.store.LogoutButtonController;
import com.example.android.onlineshoppingdemo.store.ShoppingCart;
import com.example.android.onlineshoppingdemo.users.Customer;

import java.math.BigDecimal;
import java.util.List;

public class CustomerUIActivity extends AppCompatActivity {

    private void renderShop(ShoppingCart cart) {
        int count = 1;
        for (ItemTypes itemType : ItemTypes.values()) {
            String itemIdName = "customer_itemname" + count;
            String priceIdName = "customer_itemprice" + count;
            String frameIdName = "customer_frame" + count;
            String itemLayoutIdName = "customer_item" + count;

            int itemId = getResources().getIdentifier(itemIdName, "id", getPackageName());
            int priceId = getResources().getIdentifier(priceIdName, "id", getPackageName());
            int frameId = getResources().getIdentifier(frameIdName, "id", getPackageName());
            int itemLayoutId = getResources().getIdentifier(itemLayoutIdName, "id", getPackageName());

            FrameLayout frameLayout = findViewById(frameId);
            LinearLayout itemLayout = findViewById(itemLayoutId);
            TextView itemTextView = findViewById(itemId);
            TextView priceTextView = findViewById(priceId);

            List<Item> allItems = DatabaseSelectHelper.getAllItems(this);
            BigDecimal price = BigDecimal.ZERO;

            Item item = null;
            for (Item i : allItems) {
                if (i.getName().equals(itemType.name())) {
                    item = i;
                    price = i.getPrice();
                    break;
                }
            }
            priceTextView.setText(price.toString());

            String itemName = (itemType.name().substring(0, 1).toUpperCase() + itemType.name()
                    .substring(1).toLowerCase()).replace("_", " ");
            itemTextView.setText(itemName);

            if (item != null && DatabaseSelectHelper.getInventoryQuantity(item.getId(), this) > 0) {
                itemLayout
                        .setOnClickListener(new ItemLayoutController(this, (ItemImpl) item, itemName, cart));
            } else {
                frameLayout.setVisibility(View.GONE);
            }

            count++;
        }
    }

    private void renderExclusive(ShoppingCart cart, Context context, int userId) {
        int count = 6;
        for (MemberItemTypes memberItemType : MemberItemTypes.values()) {
            String itemIdName = "customer_itemname" + count;
            String priceIdName = "customer_itemprice" + count;
            String frameIdName = "customer_frame" + count;
            String itemLayoutIdName = "customer_item" + count;

            int itemId = getResources().getIdentifier(itemIdName, "id", getPackageName());
            int priceId = getResources().getIdentifier(priceIdName, "id", getPackageName());
            int frameId = getResources().getIdentifier(frameIdName, "id", getPackageName());
            int itemLayoutId = getResources().getIdentifier(itemLayoutIdName, "id", getPackageName());

            FrameLayout frameLayout = findViewById(frameId);
            LinearLayout itemLayout = findViewById(itemLayoutId);
            TextView itemTextView = findViewById(itemId);
            TextView priceTextView = findViewById(priceId);

            List<Item> allItems = DatabaseSelectHelper.getAllItems(this);
            BigDecimal price = BigDecimal.ZERO;

            Item item = null;
            for (Item i : allItems) {
                if (i.getName().equals(memberItemType.name())) {
                    item = i;
                    price = i.getPrice();
                    break;
                }
            }

            priceTextView.setText(price.toString());

            String itemName = (memberItemType.name().substring(0, 1).toUpperCase() + memberItemType
                    .name().substring(1).toLowerCase()).replace("_", " ");
            itemTextView.setText(itemName);

            List<Integer> members = DatabaseSelectHelper.getMembers(context);

            if (item != null && DatabaseSelectHelper.getInventoryQuantity(item.getId(), this) > 0
                    && members.contains(userId)) {
                itemLayout
                        .setOnClickListener(new ItemLayoutController(this, (ItemImpl) item, itemName, cart));
            } else {
                frameLayout.setVisibility(View.GONE);
            }

            count++;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);

        TextView greeting = findViewById(R.id.employee_greeting);
        ImageView logout = findViewById(R.id.customer_logout);
        ImageView cartButton = findViewById(R.id.customer_cart);

        Intent intent = getIntent();
        Customer customer = (Customer) intent.getSerializableExtra("user");
        String greetingText = "Hi " + customer.getName() + ",";
        ShoppingCart cart = null;

        try {
            cart = new ShoppingCart(customer);
        } catch (AuthenticationException ignored) {
        }

        greeting.setText(greetingText);
        logout.setOnClickListener(new LogoutButtonController(this));
        cartButton.setOnClickListener(new CartButtonController(this, cart));

        renderShop(cart);
        renderExclusive(cart, this, customer.getId());
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int ADD_TO_CART_REQUEST = 1;
        int VIEW_CART_REQUEST = 2;

        if ((requestCode == ADD_TO_CART_REQUEST || requestCode == VIEW_CART_REQUEST)
                && resultCode == RESULT_OK) {
            ShoppingCart cart = (ShoppingCart) data.getSerializableExtra("cart");
            ImageView cartButton = findViewById(R.id.customer_cart);
            cartButton.setOnClickListener(new CartButtonController(this, cart));
            renderShop(cart);
            renderExclusive(cart, this, cart.getCustomer().getId());
        }
    }
}
