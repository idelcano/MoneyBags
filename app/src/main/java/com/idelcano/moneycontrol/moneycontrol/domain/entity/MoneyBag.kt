package com.idelcano.moneycontrol.moneycontrol.domain.entity

import com.idelcano.moneycontrol.moneycontrol.utils.DateParser
import com.idelcano.moneycontrol.moneycontrol.utils.DhisCodeGenerator
import java.util.Date

data class MoneyBag(
    val uid: String = DhisCodeGenerator.generateCode(),
    val name: String,
    val amount: Long,
    val dateLimit: Date,
    val createdDate: Date,
    val iconPath: String,
    val priority: Int,
    var amountList: List<MoneyAmount> = ArrayList()
) {

    companion object {
        const val MAXIMUN_RANGE = 5
        const val MINIMUN_RANGE = 0
    }

    init {
        if (!(priority in MINIMUN_RANGE..MAXIMUN_RANGE)) {
            throw IllegalArgumentException("$priority is not a valid priority range")
        }
    }
    fun result(): Long {
        var result: Long = 0
        for (moneyAmount in amountList) {
            result += moneyAmount.amount
        }
        return result
    }

    fun remainingTime(): Int {
        val cal = DateParser().getTodayDateWhitoutTime()
        val diff = dateLimit.getTime() - cal.time.time
        val days = DateParser().getDaysFromDiff(diff)
        return days.toInt()
    }

    fun remainingMoney(): Long {
        var result = amount
        for (moneyAmount in amountList) {
            result -= moneyAmount.amount
        }
        return result
    }
}
