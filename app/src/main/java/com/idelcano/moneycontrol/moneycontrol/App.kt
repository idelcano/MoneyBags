package com.idelcano.moneycontrol.moneycontrol

import android.app.Application
import android.content.Context
import com.idelcano.moneycontrol.moneycontrol.data.database.DBController

class App(val context: Context) : Application() {

    override fun onCreate() {
        super.onCreate()
        DBController(this.context, false).initDB()
    }
}