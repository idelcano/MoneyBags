package com.idelcano.moneycontrol.moneycontrol

import android.app.Application
import com.idelcano.moneycontrol.moneycontrol.data.database.DBController

class App : Application() {

    lateinit var dbController: DBController
    override fun onCreate() {
        super.onCreate()
        dbController = DBController(this, false)
        dbController.initDB()
    }

    override fun onTerminate() {
        super.onTerminate()
        dbController.destroy()
    }
}