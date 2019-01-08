package com.idelcano.moneycontrol.moneycontrol.data.mappers

import com.idelcano.moneycontrol.moneycontrol.data.database.model.MoneyAmountDB
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyAmount

class MoneyAmountMapper() {

    fun mapToList(moneyAmountDBs: List<MoneyAmountDB>?): MutableList<MoneyAmount> {
        val moneyBags: MutableList<MoneyAmount> = ArrayList()
        moneyAmountDBs?.let {
            for (moneyAmountDb: MoneyAmountDB? in moneyAmountDBs) {
                moneyAmountDb.let {
                    moneyBags.add(mapFromDB(moneyAmountDb)!!)
                }
            }
        }
        return moneyBags
    }

    private fun mapFromDB(moneyAmountDB: MoneyAmountDB?): MoneyAmount? {
        if (moneyAmountDB == null)
            return null
        return MoneyAmount(
            moneyAmountDB.amountUid!!,
            moneyAmountDB.name!!, moneyAmountDB.amount!!,
            moneyAmountDB!!.creationDate, moneyAmountDB.moneyBagUid!!
        )
    }

    fun map(moneyAmount: MoneyAmount): MoneyAmountDB {
        return MoneyAmountDB(
            moneyAmount.uid,
            moneyAmount.name, moneyAmount.amount,
            moneyAmount.creationDate, moneyAmount.moneyBagUid
        )
    }
}