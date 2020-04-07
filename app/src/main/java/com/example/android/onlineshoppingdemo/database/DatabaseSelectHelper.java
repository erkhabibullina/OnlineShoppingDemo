package com.example.android.onlineshoppingdemo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.onlineshoppingdemo.inventory.Inventory;
import com.example.android.onlineshoppingdemo.inventory.InventoryImpl;
import com.example.android.onlineshoppingdemo.inventory.Item;
import com.example.android.onlineshoppingdemo.inventory.ItemImpl;
import com.example.android.onlineshoppingdemo.store.Sale;
import com.example.android.onlineshoppingdemo.store.SaleImpl;
import com.example.android.onlineshoppingdemo.store.SalesLog;
import com.example.android.onlineshoppingdemo.store.SalesLogImpl;
import com.example.android.onlineshoppingdemo.users.User;
import com.example.android.onlineshoppingdemo.users.UserFactory;
import com.example.android.onlineshoppingdemo.validation.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseSelectHelper {

    /**
     * Method to return the roleId from role name
     *
     * @param roleName the role name
     * @return the roleId
     */
    public static int getRoleIdFromName(String roleName, Context context) {
        for (int roleId : DatabaseSelectHelper.getRoleIds(context)) {
            if (roleName.contentEquals(DatabaseSelectHelper.getRoleName(roleId, context))) {
                return roleId;
            }
        }
        return -1;
    }

    public static List<Integer> getRoleIds(Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
        Cursor cursor = myDB.getRoles();
        List<Integer> ids = new ArrayList<>();

        while (cursor.moveToNext()) {
            ids.add(cursor.getInt(cursor.getColumnIndex("ID")));
        }
        cursor.close();
        myDB.close();
        return ids;
    }

    public static String getRoleName(int roleId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
        String role;
        role = myDB.getRole(roleId);
        myDB.close();
        return role;
    }

    public static int getUserRoleId(int userId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
        int roleId;
        roleId = myDB.getUserRole(userId);
        myDB.close();
        return roleId;
    }

    public static List<Integer> getUsersByRole(int roleId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
        Cursor cursor;
        List<Integer> userIds;

        cursor = myDB.getUsersByRole(roleId);
        userIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            userIds.add(cursor.getInt(cursor.getColumnIndex("USERID")));
        }
        myDB.close();
        cursor.close();
        return userIds;
    }

    public static List<User> getUsersDetails(Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

        Cursor cursor;
        List<User> users;

        cursor = myDB.getUsersDetails();
        users = new ArrayList<>();
        while (cursor.moveToNext()) {
            int userId = cursor.getInt(cursor.getColumnIndex("ID"));
            String name = cursor.getString(cursor.getColumnIndex("NAME"));
            int age = cursor.getInt(cursor.getColumnIndex("AGE"));
            String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
            System.out.println(userId + name + age + address);

            int roleId = DatabaseSelectHelper.getUserRoleId(userId, context);
            String roleName = DatabaseSelectHelper.getRoleName(roleId, context);
            User user = UserFactory.createUser(roleName, userId, name, age, address, context);

            users.add(user);
        }
        cursor.close();
        myDB.close();
        return users;
    }

    public static User getUserDetails(int userId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

        Cursor cursor;
        User user = null;
        cursor = myDB.getUserDetails(userId);

        if (cursor.moveToFirst()) {
            int roleId = DatabaseSelectHelper.getUserRoleId(userId, context);
            if (roleId != -1) {
                String roleName = DatabaseSelectHelper.getRoleName(roleId, context);

                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                int age = cursor.getInt(cursor.getColumnIndex("AGE"));
                String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));

                user = UserFactory.createUser(roleName, id, name, age, address, context);
            }

        }
        cursor.close();
        myDB.close();
        return user;
    }

    public static String getPassword(int userId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

        String password;
        password = myDB.getPassword(userId);
        myDB.close();
        return password;
    }

    public static List<Item> getAllItems(Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

        Cursor cursor;
        List<Item> items;

        cursor = myDB.getAllItems();
        items = new ArrayList<>();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndex("ID"));
            String name = cursor.getString(cursor.getColumnIndex("NAME"));
            BigDecimal price = new BigDecimal(cursor.getString(cursor.getColumnIndex("PRICE")));
            Item item = new ItemImpl(itemId, name, price);
            items.add(item);
        }
        cursor.close();
        myDB.close();
        return items;
    }

    public static Item getItem(int itemId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

        Item item = null;
        Cursor cursor;

        cursor = myDB.getItem(itemId);
        if (cursor.moveToFirst()) {
            item = new ItemImpl();
            item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
            item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
            item.setPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("PRICE"))));
        }
        cursor.close();
        myDB.close();
        return item;
    }

    public static Inventory getInventory(Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

        Inventory inventory;
        Cursor cursor;

        cursor = myDB.getInventory();
        inventory = new InventoryImpl();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndex("ITEMID"));
            int quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
            Item item = DatabaseSelectHelper.getItem(itemId, context);
            inventory.updateMap(item, quantity);
        }
        cursor.close();
        myDB.close();
        return inventory;
    }

    public static int getInventoryQuantity(int itemId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

        int quantity;
        quantity = myDB.getInventoryQuantity(itemId);
        myDB.close();
        return quantity;
    }

    public static SalesLog getSales(Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

        Cursor cursor;
        SalesLog salesLog;

        cursor = myDB.getSales();
        salesLog = new SalesLogImpl();
        while (cursor.moveToNext()) {
            Sale sale = new SaleImpl();
            int saleId = cursor.getInt(cursor.getColumnIndex("ID"));
            int userId = cursor.getInt(cursor.getColumnIndex("USERID"));
            User user = DatabaseSelectHelper.getUserDetails(userId, context);
            BigDecimal totalPrice = new BigDecimal(
                    cursor.getString(cursor.getColumnIndex("TOTALPRICE")));
            Sale itemizedSale = DatabaseSelectHelper.getItemizedSaleById(saleId, context);
            sale.setId(saleId);
            sale.setUser(user);
            sale.setTotalPrice(totalPrice);
            sale.setItemMap(itemizedSale.getItemMap());
            salesLog.updateLog(sale);
        }
        cursor.close();
        myDB.close();
        return salesLog;
    }

    public static Sale getSaleById(int saleId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

        Cursor cursor;
        Sale sale = null;

        cursor = myDB.getSaleById(saleId);
        if (cursor.moveToFirst()) {
            User user = DatabaseSelectHelper
                    .getUserDetails(cursor.getInt(cursor.getColumnIndex("USERID")), context);
            BigDecimal totalPrice = new BigDecimal(cursor.getString(cursor.getColumnIndex("TOTALPRICE")));
            sale = new SaleImpl();
            sale.setId(saleId);
            sale.setUser(user);
            sale.setTotalPrice(totalPrice);
            cursor.close();
        }
        myDB.close();
        return sale;
    }

    public static Sale getItemizedSaleById(int saleId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
        Cursor cursor;
        Sale sale;

        cursor = myDB.getItemizedSaleById(saleId);
        sale = new SaleImpl();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndex("ITEMID"));
            Item item = DatabaseSelectHelper.getItem(itemId, context);
            Integer quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
            sale.updateMap(item, quantity);
        }
        sale.setId(saleId);
        cursor.close();
        myDB.close();
        return sale;
    }

    public static int getUserAccounts(int userId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
        Cursor cursor;
        int accId = -1;

        if (Validator.validateUserId(userId, context)) {
            cursor = myDB.getUserAccounts(userId);
            while (cursor.moveToNext()) {
                accId = cursor.getInt(cursor.getColumnIndex("ID"));
            }
            cursor.close();
        }
        myDB.close();
        return accId;
    }

    public static HashMap<Integer, List<Integer>> getAccountMap(Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
        Cursor cursor;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> accIds = new ArrayList<>();
        int customerId = getRoleIdFromName("CUSTOMER", context);

        for (int userId : getUsersByRole(customerId, context)) {
            if (Validator.validateUserId(userId, context)) {
                cursor = myDB.getUserAccounts(userId);
                while (cursor.moveToNext()) {
                    accIds.add(cursor.getInt(cursor.getColumnIndex("ID")));
                }
                map.put(userId, accIds);
                cursor.close();
            }
        }
        myDB.close();
        return map;
    }

    public static HashMap<Item, Integer> getAccountDetails(int accountId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
        Cursor cursor;
        HashMap<Item, Integer> items;

        cursor = myDB.getAccountDetails(accountId);
        items = new HashMap<>();
        while (cursor.moveToNext()) {
            int itemId = cursor.getInt(cursor.getColumnIndex("ITEMID"));
            int quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
            items.put(DatabaseSelectHelper.getItem(itemId, context), quantity);
        }
        cursor.close();
        myDB.close();
        return items;
    }

    public static List<Integer> getActiveAccounts(int userId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
        List<Integer> activeAccounts = new ArrayList<>();
        Cursor cursor = myDB.getUserActiveAccounts(userId);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int activeStatus = cursor.getInt(cursor.getColumnIndex("ACTIVE"));
                System.out.println(activeStatus);
                int accId = cursor.getInt(cursor.getColumnIndex("ID"));
                if (activeStatus == 1) {
                    activeAccounts.add(accId);
                }
            }
        }
        return activeAccounts;
    }

    public static List<Integer> getInactiveAccounts(int userId, Context context) {
        DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
        Cursor cursor;
        List<Integer> inactiveAccounts = new ArrayList<>();
        cursor = myDB.getUserInactiveAccounts(userId);
        while (cursor.moveToNext()) {
            int activeStatus = cursor.getInt(cursor.getColumnIndex("ACTIVE"));
            int accId = cursor.getInt(cursor.getColumnIndex("ID"));
            if (activeStatus == 0) {
                inactiveAccounts.add(accId);
            }
        }
        return inactiveAccounts;
    }

    public static List<Integer> getNonMembers(Context context) {
        DatabaseDriverAndroid myDb = new DatabaseDriverAndroid(context);
        Cursor cursor;
        List<Integer> nonMembers = new ArrayList<>();
        cursor = myDb.getNonMembers();
        while (cursor.moveToNext()) {
            int userId = cursor.getInt(cursor.getColumnIndex("USERID"));
            nonMembers.add(userId);
        }
        return nonMembers;
    }

    public static List<Integer> getMembers(Context context) {
        DatabaseDriverAndroid myDb = new DatabaseDriverAndroid(context);
        Cursor cursor;
        List<Integer> members = new ArrayList<>();
        cursor = myDb.getMembers();
        while (cursor.moveToNext()) {
            int userId = cursor.getInt(cursor.getColumnIndex("USERID"));
            members.add(userId);
        }
        return members;
    }

    public static List<Integer> getReturns(Context context) {
        DatabaseDriverAndroid myDb = new DatabaseDriverAndroid(context);
        Cursor cursor;
        List<Integer> returns = new ArrayList<>();
        cursor = myDb.getReturns();
        while (cursor.moveToNext()) {
            int saleId = cursor.getInt(cursor.getColumnIndex("SALEID"));
            returns.add(saleId);
        }
        return returns;
    }

    public static SQLiteDatabase getDatabase(Context context) {
        DatabaseDriverAndroid myDb = new DatabaseDriverAndroid(context);
        return myDb.getWritableDatabase();
    }

    public static int getDatabaseVersion(Context context) {
        DatabaseDriverAndroid myDb = new DatabaseDriverAndroid(context);
        return myDb.getDatabaseVersion();
    }
}