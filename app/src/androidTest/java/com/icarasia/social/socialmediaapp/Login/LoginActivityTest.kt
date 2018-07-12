package com.icarasia.social.socialmediaapp.Login

import android.app.Instrumentation
import android.content.Context
import android.os.SystemClock
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.hasErrorText
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.widget.TextView
import com.icarasia.social.socialmediaapp.Home.HomeActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.R.id.*
import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsFragment
import com.icarasia.social.socialmediaapp.UserDetalsFragmet.erraseUserDetails
import kotlinx.android.synthetic.main.activity_main.view.*
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LoginActivityTest {


    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @Before
    fun setUp() {

    }

    @Test
    fun espressoClikesOnSkip() {
        Espresso.onView(withId(skipText)).perform(click())
    }

    @Test
    fun logginwithemptyusername(){
        onView(withId(nameEditText)).perform(typeText(""))

        onView(withId(loginButton)).perform(click())

        onView(withId(nameEditText)).check(matches(ViewMatchers.hasErrorText(activityTestRule.activity.getString(R.string.nameError))))
    }

    @Test
    fun logginwithFullusername(){

        onView(withId(nameEditText)).perform(typeText("Bret"))

        onView(withId(loginButton)).perform(ViewActions.pressBack())

        onView(withId(loginButton)).perform(click())
    }

}