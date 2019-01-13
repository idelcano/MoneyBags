package com.idelcano.moneycontrol.moneycontrol.data.repositories

import com.idelcano.moneycontrol.moneycontrol.data.database.model.DayCounterDB
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.IDayCounterRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.DayCounter
import com.raizlabs.android.dbflow.sql.language.OrderBy
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.sql.language.Select

class DayCounterRepository : IDayCounterRepository {
    override fun save(dayCounter: DayCounter) {
        val moneyBagDB: DayCounterDB = map(dayCounter)
        moneyBagDB.save()
    }

    override fun delete(dayCounter: DayCounter) {
        SQLite.delete(DayCounterDB::class.java)
            .where(DayCounterDB_Table.uid.`is`(dayCounter.uid))
            .execute()
    }

    override fun get(uid: String): DayCounter? {
        var dayCounterDB: DayCounterDB? = Select()
            .from(DayCounterDB::class.java)
            .where(DayCounterDB_Table.uid.`is`(uid))
            .querySingle()
        return mapFromDB(dayCounterDB)
    }

    override fun getAll(): List<DayCounter?> {
        val dayCounterDBs: List<DayCounterDB?> = Select()
            .from(DayCounterDB::class.java)
            .orderBy(OrderBy.fromProperty(DayCounterDB_Table.priority))
            .queryList()
        return mapToList(dayCounterDBs)
    }

    private fun mapToList(dayCounterDBs: List<DayCounterDB?>): MutableList<DayCounter> {
        val dayCounters: MutableList<DayCounter> = ArrayList()
        for (dayCounterDb: DayCounterDB? in dayCounterDBs) {
            dayCounterDb.let {
                dayCounters.add(mapFromDB(dayCounterDb)!!)
            }
        }
        return dayCounters
    }

    private fun mapFromDB(dayCounterDB: DayCounterDB?): DayCounter? {
        if (dayCounterDB == null)
            return null
        return DayCounter(dayCounterDB.uid,
            dayCounterDB.name!!, dayCounterDB.createdDate!!,
            dayCounterDB.iconUId!!, dayCounterDB.priority!!)
    }

    private fun map(dayCounter: DayCounter): DayCounterDB {
        return DayCounterDB(dayCounter.uid,
            dayCounter.name, dayCounter.createdDate,
            dayCounter.iconPath, dayCounter.priority)
    }
}