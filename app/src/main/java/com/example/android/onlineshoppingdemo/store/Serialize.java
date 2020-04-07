package com.example.android.onlineshoppingdemo.store;

import android.content.Context;

public interface Serialize {

    void setRoles(Context appContext);

    void setUserRole(Context appContext);

    void setUsers(Context appContext);

    void setPassword(Context appContext);

    void setItems(Context appContext);

    void setInventory(Context appContext);

    void setSalesLog(Context appContext);

    void setCarts(Context appContext);

    void setAccounts(Context appContext);

    void setActiveAccounts(Context appContext);

    void setMemberShip(Context appContext);

    void setReturns(Context appContext);

    void serializeDatabase(Context appContext);

    void deserializeDatabase(Context appContext);
}
