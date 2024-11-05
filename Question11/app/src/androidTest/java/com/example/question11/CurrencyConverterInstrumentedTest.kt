package com.example.question11

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrencyConverterInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testUIAllDisplayed() {
        // Test to make sure all important UI elements are displayed
        onView(withId(R.id.toCurrencyDropdownMenu)).check(matches(isDisplayed()))
        onView(withId(R.id.amount)).check(matches(isDisplayed()))
        onView(withId(R.id.currencyConversionButton)).check(matches(isDisplayed()))
        onView(withId(R.id.fromCurrencyDropdownMenu)).check(matches(isDisplayed()))
    }



    // Test for the case when no currencies or currency amount are selected
    @Test
    fun testErrorHandlingEmptyFields() {
        onView(withId(R.id.currencyConversionButton)).perform(click())
        onView(withId(R.id.output)).check(matches(withText("Please select both currencies and the amount to convert")))
    }
}
