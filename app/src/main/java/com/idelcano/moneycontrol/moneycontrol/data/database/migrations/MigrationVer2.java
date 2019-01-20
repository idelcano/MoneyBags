package com.idelcano.moneycontrol.moneycontrol.data.database.migrations;

import com.idelcano.moneycontrol.moneycontrol.data.database.MoneyHelperDatabase;
import com.idelcano.moneycontrol.moneycontrol.data.database.model.MoneyAmountDB;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

@Migration(version = 2, database = MoneyHelperDatabase.class)
public class MigrationVer2 extends AlterTableMigration<MoneyAmountDB> {

    public MigrationVer2(Class<MoneyAmountDB> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        super.onPreMigrate();
        addColumn(SQLiteType.INTEGER, "isPositive");
    }
}