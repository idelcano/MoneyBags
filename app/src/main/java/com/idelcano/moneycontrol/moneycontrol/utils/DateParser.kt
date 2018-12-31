package com.idelcano.moneycontrol.moneycontrol.utils

import java.text.SimpleDateFormat
import java.util.*


class DateParser{
    val format : String = "dd/MM/yyyy"

    fun formatToUI(date : Date) : String{
        val format = SimpleDateFormat(format)
        return format.format(date)
    }
    fun parseFromUI(dateValue : String) : Date {
        return SimpleDateFormat(format).parse(dateValue)
    }
}