package com.example.android.onlineshoppingdemo.store;

import com.example.android.onlineshoppingdemo.inventory.Item;
import com.example.android.onlineshoppingdemo.users.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Interface for a Sale
 */
public interface Sale extends Serializable {

    /**
     * Method for returning sold item id
     *
     * @return the sold item id
     */
    int getId();

    /**
     * Method for setting or changing the sold item id
     *
     * @param id the sold item id
     */
    void setId(int id);

    /**
     * Method for getting the user
     *
     * @return the user
     */
    User getUser();

    /**
     * Method for setting or changing the user
     *
     * @param user the user
     */
    void setUser(User user);

    /**
     * Method for returning the total price
     *
     * @return the total price
     */
    BigDecimal getTotalPrice();

    /**
     * Method for setting or changing the total price
     *
     * @param price the total price
     */
    void setTotalPrice(BigDecimal price);

    /**
     * Method for returning the item map
     *
     * @return the item map
     */
    HashMap<Item, Integer> getItemMap();

    /**
     * Method for setting or changing the item map
     *
     * @param itemMap the item map
     */
    void setItemMap(HashMap<Item, Integer> itemMap);

    /**
     * Method for updating the item map
     *
     * @param item     the item
     * @param quantity the quantity of the item
     */
    void updateMap(Item item, Integer quantity);
}
