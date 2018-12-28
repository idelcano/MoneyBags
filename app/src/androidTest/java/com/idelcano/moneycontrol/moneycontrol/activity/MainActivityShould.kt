package com.idelcano.moneycontrol.moneycontrol

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityShould {
    @Rule
    @JvmField var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test fun hasAActionButtonVisible() {
        onView(withId(R.id.fab))
                .perform(click())
                .check(matches(isDisplayed()))
    }

    @Test
    fun openMoneyBagDialogWhenCLickOnAddBagAction() {
        onView(withId(R.id.fab))
                .perform(click())

        onView(withText(R.string.create_money))
                .check(matches(isDisplayed()))

        onView(withText(R.string.add))
                .perform(click())

        onView(withId(R.id.create_money_bag_dialog))
                .check(matches(isDisplayed()))
    }

    @Test
    fun checkOnListviewANewMoneyBag() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.idelcano.moneycontrol.moneycontrol", appContext.packageName)
    }
}
