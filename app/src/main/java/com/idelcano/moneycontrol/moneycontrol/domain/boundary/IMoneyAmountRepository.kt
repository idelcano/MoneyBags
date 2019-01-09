package com.idelcano.moneycontrol.moneycontrol.domain.boundary

import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyAmount

interface IMoneyAmountRepository {
    fun save(moneyAmount: MoneyAmount)
    fun delete(moneyAmount: MoneyAmount)
    fun getAll(): List<MoneyAmount>
}