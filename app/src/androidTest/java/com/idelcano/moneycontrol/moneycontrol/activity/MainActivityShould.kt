package com.idelcano.moneycontrol.moneycontrol.activity

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.GeneralClickAction
import android.support.test.espresso.action.GeneralLocation
import android.support.test.espresso.action.Press
import android.support.test.espresso.action.Tap
import android.support.test.espresso.action.ViewActions.clearText
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.idelcano.moneycontrol.moneycontrol.R
import com.idelcano.moneycontrol.moneycontrol.data.database.DBController
import com.idelcano.moneycontrol.moneycontrol.data.repositories.MoneyBagRepository
import com.idelcano.moneycontrol.moneycontrol.domain.entity.MoneyBag
import com.idelcano.moneycontrol.moneycontrol.presentation.views.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityShould {
    val delay: Long = 500
    @Rule
    @JvmField var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java)
    @Before
    fun setup() {
        DBController(InstrumentationRegistry.getTargetContext(), true).initDB()
    }

    @Test fun `has_a_action_button_visible`() {
        onView(withId(R.id.fab))
                .check(matches(isDisplayed()))
    }

    @Test
    fun `open_money_bag_dialog_after_click_on_add_bag_action`() {
        onView(withId(R.id.fab))
                .perform(click())

        onView(withText(R.string.create_money))
                .check(matches(isDisplayed()))

        onView(withText(R.string.create_money))
                .perform(click())

        onView(withId(R.id.create_money_bag_dialog))
                .check(matches(isDisplayed()))
    }

    @Test
    fun `open_day_counter_dialog_after_click_on_add_bag_action`() {
        onView(withId(R.id.fab))
            .perform(click())

        onView(withText(R.string.create_day_counter))
            .check(matches(isDisplayed()))

        onView(withText(R.string.create_day_counter))
            .perform(click())

        onView(withId(R.id.create_day_counter_dialog))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `has_visible_money_bag_in_listview_after_create`() {
        // given
        var expectedMoneyBag: MoneyBag = createExpectedMoneyBag()

        onView(withId(R.id.fab))
            .perform(click())
        onView(withText(R.string.create_money))
            .perform(click())

        onView(withId(R.id.edit_name)).perform(clearText(), typeText(expectedMoneyBag.name))
        pauseTestFor(delay)
        onView(withId(R.id.edit_amount)).perform(clearText(), typeText(expectedMoneyBag.amount.toString()))
        pauseTestFor(delay)

        onView(withId(R.id.edit_date)).perform(click())
        onView(withText("OK")).perform(click())

        pauseTestFor(delay)
        onView(withId(R.id.priority_seek_bar)).perform(GeneralClickAction(Tap.SINGLE, GeneralLocation.TOP_RIGHT, Press.FINGER))
        pauseTestFor(delay)
        onView(withId(R.id.save_money_bag)).perform(click())

        val moneyBags: List<MoneyBag?> = MoneyBagRepository().getAll()
        assertEquals(1, moneyBags.size)
        val moneyBag: MoneyBag = moneyBags[0]!!
        assertEquals(expectedMoneyBag.name, moneyBag.name)
        assertEquals(expectedMoneyBag.amount, moneyBag.amount)
        assertEquals(expectedMoneyBag.dateLimit.day, moneyBag.dateLimit.day)
        assertEquals(expectedMoneyBag.dateLimit.month, moneyBag.dateLimit.month)
        assertEquals(expectedMoneyBag.dateLimit.year, moneyBag.dateLimit.year)
        assertEquals(expectedMoneyBag.priority, moneyBag.priority)
        onView(withText(expectedMoneyBag.name)).check(matches(isDisplayed()))
    }

    @Test
    fun `has_visible_edit_money_bag_dialog_fragment_after_click_on_add_button`() {
        // given
        var expectedMoneyBag: MoneyBag = createExpectedMoneyBag()

        onView(withId(R.id.fab))
            .perform(click())
        onView(withText(R.string.create_money))
            .perform(click())

        onView(withId(R.id.edit_name)).perform(clearText(), typeText(expectedMoneyBag.name))
        pauseTestFor(delay)
        onView(withId(R.id.edit_amount)).perform(clearText(), typeText(expectedMoneyBag.amount.toString()))
        pauseTestFor(delay)

        onView(withId(R.id.edit_date)).perform(click())
        onView(withText("OK")).perform(click())

        pauseTestFor(delay)
        onView(withId(R.id.priority_seek_bar)).perform(GeneralClickAction(Tap.SINGLE, GeneralLocation.TOP_RIGHT, Press.FINGER))
        pauseTestFor(delay)
        onView(withId(R.id.save_money_bag)).perform(click())

        onView(withId(R.id.add_button)).perform(click())

        onView(withId(R.id.create_money_amount_dialog)).check(matches(isDisplayed()))

        onView(withId(R.id.cancel_edit_dialog)).perform(click())

        onView(withText(expectedMoneyBag.name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `remove_money_bag_after_click_on_delete_button`() {
        // given
        var expectedMoneyBag: MoneyBag = createExpectedMoneyBag()

        onView(withId(R.id.fab))
            .perform(click())
        onView(withText(R.string.create_money))
            .perform(click())

        onView(withId(R.id.edit_name)).perform(clearText(), typeText(expectedMoneyBag.name))
        pauseTestFor(delay)
        onView(withId(R.id.edit_amount)).perform(clearText(), typeText(expectedMoneyBag.amount.toString()))
        pauseTestFor(delay)

        onView(withId(R.id.edit_date)).perform(click())
        onView(withText("OK")).perform(click())

        pauseTestFor(delay)
        onView(withId(R.id.priority_seek_bar)).perform(GeneralClickAction(Tap.SINGLE, GeneralLocation.TOP_RIGHT, Press.FINGER))
        pauseTestFor(delay)
        onView(withId(R.id.save_money_bag)).perform(click())

        onView(withId(R.id.delete_button)).perform(click())

        onView(withText("Yes")).perform(click())

        onView(withText(expectedMoneyBag.name)).check(doesNotExist())
    }

    private fun createExpectedMoneyBag(): MoneyBag {
        var expectedMoneyBag: MoneyBag = MoneyBag(
            name = "testname", amount = 15, dateLimit = Date(),
            createdDate = Date(), iconPath = "iconpathtest", priority = 5
        )
        return expectedMoneyBag
    }

    fun pauseTestFor(miliseconds: Long) {
        try {
            Thread.sleep(miliseconds)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
