package com.icarasia.social.socialmediaapp.Login

import android.os.CountDownTimer
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.registerIdlingResources
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.icarasia.social.socialmediaapp.R.id.*
import kotlinx.android.synthetic.main.activity_splash_scree.*
import org.hamcrest.Matchers.not
import org.junit.Before

import org.junit.Assert.*
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