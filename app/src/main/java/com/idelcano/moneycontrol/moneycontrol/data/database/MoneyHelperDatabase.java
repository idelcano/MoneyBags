package com.idelcano.moneycontrol.moneycontrol.data.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(version = MoneyHelperDatabase.VERSION, foreignKeyConstraintsEnforced = true)
public class MoneyHelperDatabase {
    private MoneyHelperDatabase(){}
    public static final String NAME = "MoneyHelperDB";

    public static final int VERSION = 1;
}