package com.icarasia.social.socialmediaapp.Fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.icarasia.social.socialmediaapp.LoginLogout.UserDetailsFragment
import com.icarasia.social.socialmediaapp.Posts.PostsFragment
import com.icarasia.social.socialmediaapp.R
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        openFragment(PostsFragment.newInstance())

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }




    companion object {
        fun StartActivity(context: Context){
            context.startActivity(Intent(context,NavigationActivity::class.java))
        }
    }

    fun openFragment(fragment:Fragment){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.framLayout,fragment)
                .commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                openFragment(PostsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                openFragment(UserDetailsFragment.newInstance("sdad","asd"))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
