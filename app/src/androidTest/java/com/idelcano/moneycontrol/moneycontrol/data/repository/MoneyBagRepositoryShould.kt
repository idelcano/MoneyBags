package com.idelcano.moneycontrol.moneycontrol.data.repository

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.idelcano.moneycontrol.moneycontrol.data.database.DBController
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.IMoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class MainActivityShould {
    lateinit var testMoneyBag : MoneyBag
    lateinit var testMoneyBag2 : MoneyBag

    @Before
    fun setup(){
        DBController(InstrumentationRegistry.getTargetContext(), true).initDB()
    }

    @Test
    fun `persist_money_bag_in_local_source`() {
        //given
        initMoneyBags()

        //when
        val repository : IMoneyBagRepository = MoneyBagRepository()
        repository.save(testMoneyBag);

        //then
        assertEquals(testMoneyBag, repository.get(uid = testMoneyBag.uid))
    }

    @Test
    fun `return_a_null_money_bag_after_save_and_delete_it_on_database`() {
        //given
        initMoneyBags()

        //when
        val repository : IMoneyBagRepository = MoneyBagRepository()
        repository.save(testMoneyBag2);
        repository.delete(testMoneyBag2);

        //then
        assertEquals(null, repository.get(uid = testMoneyBag2.uid))
    }

    @Test
    fun `recovery_all_the_money_bags_stored_in_local_source`() {
        //given
        initMoneyBags()

        //when
        val repository : IMoneyBagRepository = MoneyBagRepository()
        repository.save(testMoneyBag);
        repository.save(testMoneyBag2);

        //then
        val list : List<MoneyBag?> = repository.getAll()
        assertEquals(2, list.size)
        assertEquals(testMoneyBag, list.get(0))
        assertEquals(testMoneyBag2, list.get(1))
    }

    fun initMoneyBags(){
        testMoneyBag = MoneyBag(name="name", amount=10, dateLimit = Date(), createdDate = Date(), iconUId = "fakeiconpath", priority = 1)
        testMoneyBag2 = MoneyBag(name="name 2", amount=20, dateLimit = Date(), createdDate = Date(), iconUId = "fakeiconpath2", priority = 2)
    }
}