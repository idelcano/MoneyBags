package com.idelcano.moneycontrol.moneycontrol

import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import org.junit.Assert
import org.junit.Test
import java.util.*

val uid:String = "Uid"
val name:String = "Name"
val amount:Long = 10L
val dateLimit: Date = Date()
val createdDate: Date = Date()
val iconUid:String = "iconUid"


class MoneyBagShould {
    @Test
    fun `return correct values when create new money bag with minimun priority`() {
        val moneyBag: MoneyBag
        moneyBag = MoneyBag(uid, name, amount, dateLimit, createdDate, iconUid, 0)
        Assert.assertEquals(moneyBag.priority, 0)
    }

    @Test
    fun `return correct values when create new money bag with maximun priority`() {
        val moneyBag: MoneyBag
        moneyBag = MoneyBag(uid, name, amount, dateLimit, createdDate, iconUid, 5)
        Assert.assertEquals(moneyBag.priority, 5)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `return exception when priority is overranged`() {
        val moneyBag: MoneyBag
        moneyBag = MoneyBag(uid, name, amount, dateLimit, createdDate, iconUid, 6)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `return exception when priority is negative`() {
        val moneyBag: MoneyBag
        moneyBag = MoneyBag(uid, name, amount, dateLimit, createdDate, iconUid, -1)
    }
}