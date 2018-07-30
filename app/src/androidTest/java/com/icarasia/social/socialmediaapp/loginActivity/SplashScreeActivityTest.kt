package com.icarasia.social.socialmediaapp.loginActivity

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.registerIdlingResources
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.icarasia.social.socialmediaapp.R.id.*
import com.icarasia.social.socialmediaapp.splashScreen.SplashScreeActivity
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashScreeActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<SplashScreeActivity>(SplashScreeActivity::class.java)


    @Before
    fun setUp() {
    }

    @Test
    fun firtstTextShowUp(){

        registerIdlingResources(activityTestRule.activity.countIdling)

        onView(withId(loginButton)).check(matches(isDisplayed()))

    }




}