package com.idelcano.moneycontrol.moneycontrol.data.repository

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.idelcano.moneycontrol.moneycontrol.data.database.DBController
import com.idelcano.moneycontrol.moneycontrol.data.repositories.DayCounterRepository
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.IDayCounterRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.DayCounter
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class DayCounterRepositoryShould {
    lateinit var testDayCounter: DayCounter
    lateinit var testDayCounter2: DayCounter
    @Before
    fun setup() {
        DBController(InstrumentationRegistry.getTargetContext(), true).initDB()
    }

    @Test
    fun `persist_day_counter_in_local_source`() {
        // given
        initDayCounters(Date())

        // when
        val repository: IDayCounterRepository = DayCounterRepository()
        repository.save(testDayCounter)

        // then
        var dayCounter: DayCounter? = repository.get(uid = testDayCounter.uid)
        var dayCounters: List<DayCounter?> = repository.getAll()
        assertEquals(1, dayCounters!!.size)
        assertEquals(dayCounter, dayCounters!!.get(0))
        assertEquals(dayCounter, testDayCounter)
    }

    @Test
    fun `return_a_null_day_counter_after_save_it_and_delete_it_on_database`() {
        // given
        initDayCounters(Date())

        // when
        val repository: IDayCounterRepository = DayCounterRepository()
        repository.save(testDayCounter)
        repository.delete(testDayCounter)

        // then
        var dayCounter: DayCounter? = repository.get(uid = testDayCounter.uid)
        var dayCounters: List<DayCounter?> = repository.getAll()
        assertEquals(0, dayCounters!!.size)
        assertNull(dayCounter)
    }

    @Test
    fun `recovery_all_the_money_bags_stored_in_local_source`() {
        // given
        initDayCounters(Date())

        // when
        val repository: IDayCounterRepository = DayCounterRepository()
        repository.save(testDayCounter)
        repository.save(testDayCounter2)

        // then
        assertEquals(2, repository.getAll().size)
        assertEquals(testDayCounter, repository.getAll().get(0))
        assertEquals(testDayCounter2, repository.getAll().get(1))
    }

    fun initDayCounters(date: Date) {
        testDayCounter = DayCounter(name = "name", createdDate = date, iconPath = "fakeiconpath", priority = 1)
        testDayCounter2 = DayCounter(name = "name 2", createdDate = date, iconPath = "fakeiconpath2", priority = 2)
    }
}