package com.icarasia.social.socialmediaapp.Posts

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import com.icarasia.social.socialmediaapp.Home.HomeActivity
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.R.id.*
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class PostsFragmentTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)

    @Before
    fun setUp() {
    }

    @Test
    fun AddNewPost(){
        LoginActivity.getUserlogedIn(activityTestRule.activity.applicationContext)?.let {
            onView(withId(addNewPost)).perform(click())
            onView(withId(postTitle)).perform(ViewActions.typeText("Faraj"))
            onView(withId(postBody)).perform(ViewActions.typeText("is the best"))
            onView(withId(android.R.id.button1)).perform(click())
            Espresso.onView(ViewMatchers.withId(R.id.postsFragmentRecyclerView)).perform(ViewActions.swipeDown())
            Espresso.onView(ViewMatchers.withId(R.id.postsFragmentRecyclerView)).perform(ViewActions.swipeDown())
            Thread.sleep(1000)
        }
    }

    @Test
    fun addNewPostWhenLogedOut(){
        LoginActivity.getUserlogedIn(activityTestRule.activity.applicationContext).let {
            if (it == null){
                onView(withId(android.R.id.button1)).perform(click())
                onView(withId(skipText)).check(matches(isDisplayed()))
            }
        }

        }




    fun swipupRecyclerViewAndSelect(){
        Espresso.onView(ViewMatchers.withId(R.id.postsFragmentRecyclerView)).perform(ViewActions.swipeUp())
        Espresso.onView(ViewMatchers.withId(R.id.postsFragmentRecyclerView)).perform(ViewActions.longClick())
        Thread.sleep(1000)
    }


}