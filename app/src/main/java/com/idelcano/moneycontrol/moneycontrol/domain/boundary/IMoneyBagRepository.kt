package com.idelcano.moneycontrol.moneycontrol.domain.boundary

import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag

interface IMoneyBagRepository {
    fun save(moneyBag: MoneyBag)
    fun delete(moneyBag: MoneyBag)
    fun get(uid:String): MoneyBag?
    fun getAll(): List<MoneyBag?>;
}