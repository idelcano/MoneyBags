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
import com.idelcano.moneycontrol.moneycontrol.domain.entity.DayCounter
import com.idelcano.moneycontrol.moneycontrol.presentation.views.MainActivity
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
class CreationDayCounterFragmentShould {
    val delay: Long = 500
    @Rule
    @JvmField var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(
        MainActivity::class.java)
    @Before
    fun setup() {
        DBController(InstrumentationRegistry.getTargetContext(), true).initDB()
    }

    @Test
    fun `has_visible_day_counter_dialog_fragment_after_CREATE`() {
        // given
        var expectedDayCounter: DayCounter = createExpectedDayCounter()

        onView(withId(R.id.fab))
            .perform(click())
        onView(withText(R.string.create_day_counter))
            .perform(click())

        onView(withId(R.id.edit_day_counter_name)).perform(clearText(), typeText(expectedDayCounter.name))
        pauseTestFor(delay)

        pauseTestFor(delay)
        onView(withId(R.id.priority_day_counter_seek_bar)).perform(GeneralClickAction(Tap.SINGLE, GeneralLocation.TOP_RIGHT, Press.FINGER))
        pauseTestFor(delay)
        onView(withId(R.id.save_day_counter)).perform(click())

        onView(withText(expectedDayCounter.name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun `hasnt_visible_item_after_remove_it`() {
        // given
        var expectedDayCounter: DayCounter = createExpectedDayCounter()

        onView(withId(R.id.fab))
            .perform(click())
        onView(withText(R.string.create_day_counter))
            .perform(click())

        onView(withId(R.id.edit_day_counter_name)).perform(clearText(), typeText(expectedDayCounter.name))
        pauseTestFor(delay)

        pauseTestFor(delay)
        onView(withId(R.id.priority_day_counter_seek_bar)).perform(GeneralClickAction(Tap.SINGLE, GeneralLocation.TOP_RIGHT, Press.FINGER))
        pauseTestFor(delay)
        onView(withId(R.id.save_day_counter)).perform(click())

        onView(withText(expectedDayCounter.name))
            .check(matches(isDisplayed()))

        onView(withId(R.id.delete_button)).perform(click())

        onView(withText(expectedDayCounter.name))
            .check(doesNotExist())
    }

    private fun createExpectedDayCounter(): DayCounter {
        return DayCounter(
            name = "testname", createdDate = Date(), iconPath = "iconpathtest",
            priority = 5
        )
    }

    fun pauseTestFor(miliseconds: Long) {
        try {
            Thread.sleep(miliseconds)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
