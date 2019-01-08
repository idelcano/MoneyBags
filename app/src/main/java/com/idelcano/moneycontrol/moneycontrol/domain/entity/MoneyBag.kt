package com.idelcano.moneycontrol.moneycontrol.domain.entity

import com.idelcano.moneycontrol.moneycontrol.utils.DhisCodeGenerator
import java.util.Date
import java.util.Calendar

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
        val cal = getTodayDateWhitoutTime()
        val diff = dateLimit.getTime() - cal.time.time
        val days = getDaysFromDiff(diff)
        return days.toInt()
    }

    fun remainingMoney(): Long {
        var result = amount
        for (moneyAmount in amountList) {
            result -= moneyAmount.amount
        }
        return result
    }

    private fun getDaysFromDiff(diff: Long): Long {
        val secs = diff / 1000
        val min = secs / 60
        val hours = min / 60
        val days = hours / 24
        return days
    }

    private fun getTodayDateWhitoutTime(): Calendar {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal
    }
}
