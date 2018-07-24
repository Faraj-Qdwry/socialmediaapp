package com.icarasia.social.socialmediaapp.Home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeUp
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.Posts.longClick
import com.icarasia.social.socialmediaapp.R.id.*
import kotlinx.android.synthetic.main.home_navigation_avtivity.*
import org.hamcrest.Matchers.not
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.ThreadLocalRandom

class HomeActivityTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)


    @Before
    fun setUp() {
    }

    @Test
    fun clickingOnPostNavigation(){
        onView(withId(navigation_home)).perform(click())
    }

    @Test
    fun clickingOnUserDetailsNavigation(){
        onView(withId(navigation_dashboard)).perform(click())
    }

    @Test
    fun addingNewPost(){
        onView(withId(addNewPost)).perform(click())
    }

    @Test
    fun testingNavigationDrawer(){
        onView(withId(drawer_layout)).perform(DrawerActions.open())
        onView(withId(drawer_layout)).perform(DrawerActions.close())
    }

    @Test
    fun navigattionDrawerAccendingOrder(){
        onView(withId(drawer_layout)).perform(DrawerActions.open())
        onView(withId(nav_view)).perform(NavigationViewActions.navigateTo(orderAce))
        onView(withId(drawer_layout)).perform(DrawerActions.close())
        Thread.sleep(1000)
    }

    @Test
    fun navigattionDrawerDesceingOrder(){
        onView(withId(drawer_layout)).perform(DrawerActions.open())
        onView(withId(nav_view)).perform(NavigationViewActions.navigateTo(orderDec))
        onView(withId(drawer_layout)).perform(DrawerActions.close())
        Thread.sleep(1000)
    }


    @Test
    fun navigattionDrawerDeletionModeLOgedOutCancle(){
        var out = true
        onView(withId(drawer_layout)).perform(DrawerActions.open())
        onView(withId(nav_view)).perform(NavigationViewActions.navigateTo(delete))
        onView(withId(drawer_layout)).perform(DrawerActions.close())
        LoginActivity.getUserlogedIn(activityTestRule.activity.applicationContext)?.let {
            out = false
        }

        if (out){
            onView(withId(deletionGroup)).check(matches(not(isDisplayed())))
        }
    }


    @Test
    fun navigattionDrawerDeletionModeLOgedInConfirm(){
        onView(withId(drawer_layout)).perform(DrawerActions.open())
        onView(withId(nav_view)).perform(NavigationViewActions.navigateTo(delete))
        onView(withId(drawer_layout)).perform(DrawerActions.close())
        Thread.sleep(1000)
        onView(withId(postsFragmentRecyclerView)).perform(click())
        onView(withId(deleteConfirmation)).perform(click())
        Thread.sleep(1000)
    }

    @Test
    fun navigattionDrawerDeletionModeLOgedInCancle(){
        onView(withId(drawer_layout)).perform(DrawerActions.open())
        Thread.sleep(1000)
        onView(withId(nav_view)).perform(NavigationViewActions.navigateTo(delete))
        onView(withId(drawer_layout)).perform(DrawerActions.close())
        Thread.sleep(1000)
        onView(withId(deleteCancelation)).perform(click())
        Thread.sleep(1000)
        onView(withId(deletionGroup)).check(matches(not(isDisplayed())))
    }

}