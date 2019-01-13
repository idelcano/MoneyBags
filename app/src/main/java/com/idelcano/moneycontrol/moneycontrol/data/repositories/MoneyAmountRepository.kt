package com.idelcano.moneycontrol.moneycontrol.data.repositories

import com.idelcano.moneycontrol.moneycontrol.data.database.model.MoneyAmountDB
import com.idelcano.moneycontrol.moneycontrol.data.mappers.MoneyAmountMapper
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.IMoneyAmountRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyAmount
import com.raizlabs.android.dbflow.sql.language.Select

class MoneyAmountRepository : IMoneyAmountRepository {
    override fun getAll(): List<MoneyAmount> {
        return MoneyAmountMapper().mapToList(
            Select().from(MoneyAmountDB::class.java).queryList())
    }

    override fun save(moneyAmount: MoneyAmount) {
        val moneyAmountDB: MoneyAmountDB = MoneyAmountMapper().map(moneyAmount)
        moneyAmountDB.save()
    }

    override fun delete(moneyAmount: MoneyAmount) {
        val moneyAmountDB: MoneyAmountDB = MoneyAmountMapper().map(moneyAmount)
        moneyAmountDB.delete()
    }
}