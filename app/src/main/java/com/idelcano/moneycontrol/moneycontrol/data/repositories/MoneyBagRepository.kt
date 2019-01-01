package com.idelcano.moneycontrol.moneycontrol.data.repositories

import com.idelcano.moneycontrol.moneycontrol.data.database.model.MoneyBagDB
import com.idelcano.moneycontrol.moneycontrol.data.database.model.MoneyBagDB_Table
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.IMoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.sql.language.Delete
import com.raizlabs.android.dbflow.sql.language.Select


class MoneyBagRepository : IMoneyBagRepository{
    override fun save(moneyBag: MoneyBag) {
        val moneyBagDB : MoneyBagDB = map(moneyBag)
        moneyBagDB.save()
    }

    override fun delete(moneyBag: MoneyBag) {
        Delete().from(MoneyBagDB::class.java)
            .where(MoneyBagDB_Table.uid.`is`(moneyBag.uid))
    }

    override fun get(uid: String) : MoneyBag? {
        var moneyBagDB : MoneyBagDB? = Select()
            .from(MoneyBagDB::class.java)
            .where(MoneyBagDB_Table.uid.`is`(uid))
            .querySingle()
        return mapFromDB(moneyBagDB)
    }

    override fun getAll(): List<MoneyBag?> {
        val moneyBagDBs : List<MoneyBagDB?> = Select()
            .from(MoneyBagDB::class.java)
            .queryList()
        return mapToList(moneyBagDBs)
    }

    private fun mapToList(moneyBagDBs: List<MoneyBagDB?>): MutableList<MoneyBag> {
        val moneyBags: MutableList<MoneyBag> = ArrayList()
        for (moneyBagDb: MoneyBagDB? in moneyBagDBs) {
            moneyBagDb.let {
                moneyBags.add(mapFromDB(moneyBagDb)!!)
            }
        }
        return moneyBags
    }

    private fun mapFromDB(moneyBagDB: MoneyBagDB?): MoneyBag? {
        if(moneyBagDB == null)
            return null
        return MoneyBag(moneyBagDB.uid,
            moneyBagDB.name!!, moneyBagDB.amount!!, moneyBagDB.dateLimit!!, moneyBagDB.createdDate!!,
            moneyBagDB.iconUId!!, moneyBagDB.priority!!)
    }

    private fun map(moneyBag: MoneyBag) : MoneyBagDB {
        return MoneyBagDB(moneyBag.uid,
            moneyBag.name, moneyBag.amount, moneyBag.dateLimit, moneyBag.createdDate,
            moneyBag.iconPath, moneyBag.priority)
    }
}