package com.idelcano.moneycontrol.moneycontrol.data.database

import android.content.Context
import com.raizlabs.android.dbflow.config.DatabaseConfig
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager

class DBController(context: Context, isTest: Boolean) {
    val context : Context = context
    val databaseConfig : DatabaseConfig

    init {
        if (isTest) {
            databaseConfig = DatabaseConfig.inMemoryBuilder(
                MoneyHelperDatabase::class.java
            )
                .build()
        } else {
            databaseConfig = DatabaseConfig.builder(
                MoneyHelperDatabase::class.java
            )
                .build()
        }
    }

    fun initDB() {
        require(context != null) { "The DBController class must be initializated" }
        require(databaseConfig != null) { "The DBController class must be initializated" }
        context.let {
            FlowManager.init(
                FlowConfig.builder(context)
                    .addDatabaseConfig(
                        databaseConfig!!
                    )
                    .build()
            )
        }
    }
}