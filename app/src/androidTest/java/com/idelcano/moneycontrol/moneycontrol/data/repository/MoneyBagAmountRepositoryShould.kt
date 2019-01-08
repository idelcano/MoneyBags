package com.idelcano.moneycontrol.moneycontrol.data.repository

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.idelcano.moneycontrol.moneycontrol.data.database.DBController
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyAmountRepository
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.IMoneyAmountRepository
import com.idelcano.moneycontrol.moneycontrol.domain.boundary.IMoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyAmount
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class MoneyBagAmountRepositoryShould {
    lateinit var testMoneyBag: MoneyBag
    lateinit var testMoneyAmount: MoneyAmount
    lateinit var testMoneyAmount2: MoneyAmount

    @Before
    fun setup() {
        DBController(InstrumentationRegistry.getTargetContext(), true).initDB()
    }

    @Test
    fun `persist_money_amount_in_local_source`() {
        // given
        initMoneyAmounts()

        // when
        val bagRepository: IMoneyBagRepository = MoneyBagRepository()
        bagRepository.save(testMoneyBag)
        val repository: IMoneyAmountRepository = MoneyAmountRepository()
        repository.save(testMoneyAmount)
        repository.save(testMoneyAmount2)

        // then
        var moneyBag: MoneyBag? = bagRepository.get(uid = testMoneyBag.uid)
        moneyBag!!.amountList
        assertEquals(2, moneyBag!!.amountList!!.size)
    }

    @Test
    fun `return_a_null_money_amount_after_save_it_and_delete_the_money_bag_on_database`() {
        // given
        initMoneyAmounts()

        // when
        val bagRepository: IMoneyBagRepository = MoneyBagRepository()
        bagRepository.save(testMoneyBag)
        val repository: IMoneyAmountRepository = MoneyAmountRepository()
        repository.save(testMoneyAmount)
        repository.save(testMoneyAmount2)
        bagRepository.delete(testMoneyBag)

        // then
        assertEquals(0, repository.getAll().size)
    }

    @Test
    fun `recovery_all_the_money_bags_stored_in_local_source`() {
        // given
        initMoneyAmounts()

        // when
        val bagRepository: IMoneyBagRepository = MoneyBagRepository()
        bagRepository.save(testMoneyBag)
        val repository: IMoneyAmountRepository = MoneyAmountRepository()
        repository.save(testMoneyAmount)
        repository.save(testMoneyAmount2)
        // then
        assertEquals(2, repository.getAll().size)
        assertEquals(2, bagRepository.get(testMoneyBag.uid)!!.amountList.size)
    }

    @Test
    fun `recovery_null_money_amount_after_delete_it_in_local_source`() {
        // given
        initMoneyAmounts()

        // when
        val bagRepository: IMoneyBagRepository = MoneyBagRepository()
        bagRepository.save(testMoneyBag)
        val repository: IMoneyAmountRepository = MoneyAmountRepository()
        repository.save(testMoneyAmount)
        repository.delete(testMoneyAmount)

        // then
        assertEquals(0, bagRepository.get(testMoneyBag.uid)!!.amountList.size)
    }

    fun initMoneyAmounts() {
        testMoneyBag = MoneyBag(name = "name", amount = 35, dateLimit = Date(), createdDate = Date(), iconPath = "fakeiconpath", priority = 1)

        testMoneyAmount = MoneyAmount(name = "name", amount = 10, creationDate = Date(), moneyBagUid = testMoneyBag.uid)
        testMoneyAmount2 = MoneyAmount(name = "name 2", amount = 20, creationDate = Date(), moneyBagUid = testMoneyBag.uid)
    }
}