package com.idelcano.moneycontrol.moneycontrol.domain.entity

import org.junit.Assert
import org.junit.Test
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date

class DayCounterShould() {

    @Test
    fun `return 10 days when the today date is one plus day than the creation date`() {
        val date = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val todayLocalDateMinusOneDay = date.minus(10, ChronoUnit.DAYS)
        val dateAsDate = Date.from(todayLocalDateMinusOneDay.atStartOfDay(ZoneId.systemDefault()).toInstant())

        val dayCounter = DayCounter(name = name, createdDate = dateAsDate, iconPath = iconPath, priority = 0)

        Assert.assertTrue(dayCounter.passTime() == 10)
    }

    @Test
    fun `return 1 days when the today date is one plus day than the creation date`() {
        val date = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val todayLocalDateMinusOneDay = date.minus(1, ChronoUnit.DAYS)
        val dateAsDate = Date.from(todayLocalDateMinusOneDay.atStartOfDay(ZoneId.systemDefault()).toInstant())

        val dayCounter = DayCounter(name = name, createdDate = dateAsDate, iconPath = iconPath, priority = 0)

        Assert.assertTrue(dayCounter.passTime() == 1)
    }

    @Test
    fun `return 0 days when the today date is one plus day than the creation date`() {
        val date = Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        val dateAsDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant())

        val dayCounter = DayCounter(name = name, iconPath = iconPath, priority = 0)

        Assert.assertTrue(dayCounter.passTime() == 0)
    }
}
