package com.idelcano.moneycontrol.moneycontrol.domain.entity

import org.junit.Assert
import org.junit.Test
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*




val uid:String = "Uid"
val name:String = "Name"
val amount:Long = 10L
val dateLimit: Date = Date()
val createdDate: Date = Date()
val iconPath:String = "iconPath"


class MoneyBagShould {

    @Test
    fun `return correct uid when create new money bag without uid`() {
        val moneyBag: MoneyBag
        moneyBag = MoneyBag(name= name, amount = amount, dateLimit = dateLimit, createdDate = createdDate, iconPath = iconPath, priority = 0)
        Assert.assertTrue(moneyBag.uid.length>0)
    }

    @Test
    fun `return correct values when create new money bag with minimun priority`() {
        val moneyBag: MoneyBag
        moneyBag = MoneyBag(uid, name, amount, dateLimit, createdDate, iconPath, 0)
        Assert.assertEquals(moneyBag.priority, 0)
    }

    @Test
    fun `return correct values when create new money bag with maximun priority`() {
        val moneyBag: MoneyBag
        moneyBag = MoneyBag(uid, name, amount, dateLimit, createdDate, iconPath, 5)
        Assert.assertEquals(moneyBag.priority, 5)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `return exception when priority is overranged`() {
        MoneyBag(uid, name, amount, dateLimit, createdDate, iconPath, 6)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `return exception when priority is negative`() {
        MoneyBag(uid, name, amount, dateLimit, createdDate, iconPath, -1)
    }

    @Test
    fun `return remaining time -1 days when the datelimite is today minus one`() {
        val date = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val todayLocalDateMinusOneDay = date.minus(1, ChronoUnit.DAYS)
        val dateAsDate = Date.from(todayLocalDateMinusOneDay.atStartOfDay(ZoneId.systemDefault()).toInstant())

        val moneyBag = MoneyBag(name= name, amount = amount, dateLimit = dateAsDate, createdDate = createdDate, iconPath = iconPath, priority = 0)

        Assert.assertTrue(moneyBag.remainingTime()==-1)
    }

    @Test
    fun `return remaining time 1 days when the datelimite is today plus one`() {
        val date = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val todayLocalDateMinusOneDay = date.plus(1, ChronoUnit.DAYS)
        val dateAsDate = Date.from(todayLocalDateMinusOneDay.atStartOfDay(ZoneId.systemDefault()).toInstant())

        val moneyBag = MoneyBag(name= name, amount = amount, dateLimit = dateAsDate, createdDate = createdDate, iconPath = iconPath, priority = 0)

        Assert.assertTrue(moneyBag.remainingTime()==1)
    }

    @Test
    fun `return remaining time 0 days when the datelimite is today`() {
        val date = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val dateAsDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())

        val moneyBag = MoneyBag(name= name, amount = amount, dateLimit = dateAsDate, createdDate = createdDate, iconPath = iconPath, priority = 0)

        Assert.assertTrue(moneyBag.remainingTime()==0)
    }

    @Test
    fun `return remaining money -1 when the amount is 1 minus 2`() {
        val moneyAmountBags: MutableList<MoneyAmount> = ArrayList()
        moneyAmountBags.add(MoneyAmount(name= "name", amount = 1, creationDate = Date(), moneyBagUid = "uid"))
        moneyAmountBags.add(MoneyAmount(name= "name", amount = 1, creationDate = Date(), moneyBagUid = "uid"))
        val moneyBag = MoneyBag(uid="uid", name= name, amount = 1, dateLimit = Date(), createdDate = createdDate, iconPath = iconPath, priority = 0, amountList = moneyAmountBags)

        Assert.assertTrue(moneyBag.remainingMoney()==-1L)
    }

    @Test
    fun `return remaining money 0 when the amount is 1 minus 1`() {
        val moneyAmountBags: MutableList<MoneyAmount> = ArrayList()
        moneyAmountBags.add(MoneyAmount(name= "name", amount = 1, creationDate = Date(), moneyBagUid = "uid"))
        val moneyBag = MoneyBag(uid="uid", name= name, amount = 1, dateLimit = Date(), createdDate = createdDate, iconPath = iconPath, priority = 0, amountList = moneyAmountBags)

        Assert.assertTrue(moneyBag.remainingMoney()==0L)
    }

    @Test
    fun `return remaining money 1 when the amount is 2 minus 1`() {
        val moneyAmountBags: MutableList<MoneyAmount> = ArrayList()
        moneyAmountBags.add(MoneyAmount(name= "name", amount = 1, creationDate = Date(), moneyBagUid = "uid"))
        val moneyBag = MoneyBag(uid="uid", name= name, amount = 2, dateLimit = Date(), createdDate = createdDate, iconPath = iconPath, priority = 0, amountList = moneyAmountBags)

        Assert.assertTrue(moneyBag.remainingMoney()==1L)
    }
}