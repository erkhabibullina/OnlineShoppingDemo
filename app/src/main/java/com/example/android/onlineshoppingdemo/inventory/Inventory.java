package com.example.android.onlineshoppingdemo.inventory;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This is an interface of the inventory and the necessary methods within
 *
 */
public interface Inventory extends Serializable {

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
     * @param item  the item
     * @param value the amount of the item
     */
    void updateMap(Item item, Integer value);

    /**
     * Method for returning the total amount of items
     *
     * @ the total amount of items
     */
    int getTotalItems();

    /**
     * Method for returning the max amount of items
     *
     * @return the max amount of items
     */
    int getMaxItems();

    /**
     * Method for setting or changing the max amount of items
     *
     * @param max the max amount of items
     */
    void setMaxItems(int max);
}
