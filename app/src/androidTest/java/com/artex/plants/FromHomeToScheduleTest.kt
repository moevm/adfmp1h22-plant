package com.artex.plants

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FromHomeToScheduleTest {

    @get : Rule
    var mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun checkTransitionBetweenFragments() {
        onView(withId(R.id.home_page)).
        check(matches(isDisplayed()))

        onView(withId(R.id.scheduleBtn)).
        check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.schedule_page)).
        check(matches(isDisplayed()))

        onView(withId(R.id.schedule_title)).
        check(matches(isDisplayed()))
            .check(matches(withText("Shared Schedule")))

        pressBack()

        onView(withId(R.id.home_page)).
        check(matches(isDisplayed()))
    }
}