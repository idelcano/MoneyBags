package com.idelcano.moneycontrol.moneycontrol.domain.boundary

import com.idelcano.moneycontrol.moneycontrol.domain.entity.DayCounter

interface IDayCounterRepository {
    fun save(dayCounter: DayCounter)
    fun delete(dayCounter: DayCounter)
    fun get(uid: String): DayCounter?
    fun getAll(): List<DayCounter?>
}