package com.example.android.onlineshoppingdemo.store;

import com.example.android.onlineshoppingdemo.inventory.Item;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * Interface for a sales log
 */
public interface SalesLog extends Serializable {

    /**
     * Method for getting the sales log
     */
    List<Sale> getLog();

    /**
     * Method for updating the sales log
     */
    void updateLog(Sale sale);

    HashMap<Item, Integer> getTotalItemMap();

    /**
     * Method to return the total dollars in sales
     *
     * @return total dollars in sales
     */
    BigDecimal getTotalSales();
}