package com.idelcano.moneycontrol.moneycontrol.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class DateParser {
    val format: String = "dd/MM/yyyy"

    fun formatToUI(date: Date): String {
        val format = SimpleDateFormat(format)
        return format.format(date)
    }
    fun parseFromUI(dateValue: String): Date {
        return SimpleDateFormat(format).parse(dateValue)
    }

    fun getDaysFromDiff(diff: Long): Long {
        val secs = diff / 1000
        val min = secs / 60
        val hours = min / 60
        val days = hours / 24
        return days
    }

    fun getTodayDateWhitoutTime(): Calendar {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal
    }
}