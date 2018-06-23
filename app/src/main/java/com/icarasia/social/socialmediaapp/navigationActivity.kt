package com.icarasia.social.socialmediaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsFragment
import com.icarasia.social.socialmediaapp.Posts.PostsFragment
import kotlinx.android.synthetic.main.activity_navigation2.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.content_navigation.*

class navigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val fragmentposts: PostsFragment by lazy { PostsFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation2)
        setSupportActionBar(toolbar)


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        with(this.supportActionBar!!) { show(); title = "Posts" }

        openFragment(fragmentposts)

        navigationNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }


    fun openFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.framLayoutNavigation, fragment)
                .commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                openFragment(PostsFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                openFragment(UserDetailsFragment.newInstance())
                this.supportActionBar!!.hide()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    companion object {
        fun StartActivity(context: Context) {
            context.startActivity(Intent(context, navigationActivity::class.java))
        }
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.orderAce -> {
                fragmentposts.postsAdapter.sortAsc()
            }
            R.id.orderDec -> {
                fragmentposts.postsAdapter.sortDec()
            }
            R.id.delete -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
