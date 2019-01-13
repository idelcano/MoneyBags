package com.idelcano.moneycontrol.moneycontrol.domain.entity

import com.idelcano.moneycontrol.moneycontrol.presentation.presenters.adapters.IListable
import com.idelcano.moneycontrol.moneycontrol.utils.DateParser
import com.idelcano.moneycontrol.moneycontrol.utils.DhisCodeGenerator
import java.util.Date

data class DayCounter(
    val uid: String = DhisCodeGenerator.generateCode(),
    val name: String,
    val createdDate: Date = Date(),
    val iconPath: String,
    val priority: Int
) : IListable {

    companion object {
        const val MAXIMUN_RANGE = 5
        const val MINIMUN_RANGE = 0
    }

    init {
        if (!(priority in MINIMUN_RANGE..MAXIMUN_RANGE)) {
            throw IllegalArgumentException("$priority is not a valid priority range")
        }
    }

    fun passTime(): Int {
        val cal = DateParser().getTodayDateWhitoutTime()
        val diff = cal.time.time - createdDate.getTime()
        val days = DateParser().getDaysFromDiff(diff)
        return days.toInt()
    }
}
