package com.icarasia.social.socialmediaapp.Login

import android.content.Context
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.app.WindowDecorActionBar
import android.view.Window
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.R.id.*
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.regex.Matcher


@Suppress("DEPRECATION")
class LoginActivityTest {


    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @Before
    fun setUp() {
    }

    @Test
    fun espressoClikesOnSkip() {
        LoginActivity.erraseUserDetails(activityTestRule.activity.baseContext.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE))

        onView(withId(skipText)).perform(click())
    }

    @Test
    fun logginwithemptyusername(){
        LoginActivity.erraseUserDetails(activityTestRule.activity.baseContext.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE))

        onView(withId(nameEditText)).perform(typeText(""))
        onView(withId(loginButton)).perform(click())
        onView(withId(nameEditText)).check(matches(ViewMatchers.hasErrorText(activityTestRule.activity.getString(R.string.nameError))))
    }

    @Test
    fun logginwithFullusername() {
        LoginActivity.getUserlogedIn(activityTestRule.activity.baseContext)?.let {
            registerIdlingResources(activityTestRule.activity.countingIdlingResource)

            onView(withId(nameEditText)).perform(typeText("Bret"))
            onView(withId(mainLoginActivity)).perform(ViewActions.closeSoftKeyboard())
            onView(withId(loginButton)).perform(click())

            onView(withId(R.id.addNewPost))
                    .check(matches(isDisplayed()))
        }
    }

}