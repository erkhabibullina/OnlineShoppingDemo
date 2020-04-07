package com.example.android.onlineshoppingdemo.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Interface for an Item
 *
 */
public interface Item extends Serializable {

    /**
     * Method for returning the item id
     *
     * @return the item id
     */
    int getId();

    /**
     * Method for setting or changing the item id
     *
     * @param id the item id
     */
    void setId(int id);

    /**
     * Method for returning the item name
     *
     * @return the item name
     */
    String getName();

    /**
     * Method for setting or changing the item name
     *
     * @param name the item name
     */
    void setName(String name);

    /**
     * Method for returning the item price
     *
     * @return the item price
     */
    BigDecimal getPrice();

    /**
     * Method for setting or changing the item price
     *
     * @param price the item price
     */
    void setPrice(BigDecimal price);
}
