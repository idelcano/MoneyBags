package com.idelcano.moneycontrol.moneycontrol.data.repositories

import com.idelcano.moneycontrol.moneycontrol.data.database.model.MoneyBagDB
import com.idelcano.moneycontrol.moneycontrol.data.database.model.MoneyBagDB_Table
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.IMoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.sql.language.Select


class MoneyBagRepository : IMoneyBagRepository{
    override fun save(moneyBag: MoneyBag) {
        val moneyBagDB : MoneyBagDB = map(moneyBag)
        moneyBagDB.save()
    }

    override fun delete(moneyBag: MoneyBag) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(uid: String) : MoneyBag? {
        var moneyBagDB : MoneyBagDB? = Select()
            .from(MoneyBagDB::class.java)
            .where(MoneyBagDB_Table.uid.`is`(uid))
            .querySingle()
        return mapFromDB(moneyBagDB)
    }

    private fun mapFromDB(moneyBagDB: MoneyBagDB?): MoneyBag? {
        if(moneyBagDB == null)
            return null
        return MoneyBag(moneyBagDB.uid,
            moneyBagDB.name!!, moneyBagDB.amount!!, moneyBagDB.dateLimit!!, moneyBagDB.createdDate!!,
            moneyBagDB.iconUId!!, moneyBagDB.priority!!)
    }

    override fun getAll(): List<MoneyBag?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun map(moneyBag: MoneyBag) : MoneyBagDB {
        return MoneyBagDB(moneyBag.uid,
            moneyBag.name, moneyBag.amount, moneyBag.dateLimit, moneyBag.createdDate,
            moneyBag.iconUId, moneyBag.priority)
    }
}