package com.icarasia.social.socialmediaapp

import android.support.v7.app.ActionBarDrawerToggle
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.Comments.CommentsPresenter
import com.icarasia.social.socialmediaapp.Comments.CommentsRecyclerViewAdapter
import com.icarasia.social.socialmediaapp.Comments.CommintsActivityView
import com.icarasia.social.socialmediaapp.Home.HomeActivity
import com.icarasia.social.socialmediaapp.Home.SplashScreeActivity
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.Login.LoginPresenter
import com.icarasia.social.socialmediaapp.Posts.PostsFragment
import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsFragment
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaActivity
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaFragment
import kotlinx.android.synthetic.main.activity_post_commints.*
import kotlinx.android.synthetic.main.activity_splash_scree.*
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.home_navigation_avtivity.*

object ValusesInjector {

    fun inject(loginActivity: LoginActivity) {
        loginActivity.loginPresenter = LoginPresenter(loginActivity, RepoDataSource)
    }

    fun inject(socialMediaFragment: SocialMediaFragment) {
        socialMediaFragment.activity = socialMediaFragment.context as SocialMediaActivity
    }

    fun inject(homeActivity: HomeActivity) {
        with(homeActivity){
            fragmentPost = PostsFragment.newInstance()
            fragmentUserDetails = UserDetailsFragment.newInstance()
            toggle = ActionBarDrawerToggle(
                    this, homeActivity.drawer_layout, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close)

            showActionbar = { (supportActionBar)?.let { it.show(); it.title = "Posts" } }
            hidActionbar = { (supportActionBar)?.hide() }
            fragmentPost.setShowHidActionBar(showActionbar, hidActionbar)
        }
    }

    fun inject(splashScreeActivity: SplashScreeActivity) {
        with(splashScreeActivity){
            this.arrayOfTextView = ArrayList()
            with(arrayOfTextView){
                        add(textView2)
                        add(textView3)
                        add(textView4)
                    add(textView5)
            }
        }
    }

    fun inject(commintsActivityView: CommintsActivityView) {
        with(commintsActivityView){
            commentRecyclerView =   findViewById(R.id.commentsRecyclerView)
            commentadapter = CommentsRecyclerViewAdapter()

            commentsPresenter = CommentsPresenter(this,RepoDataSource)

            with(intent){
                postTitleComment.text = getStringExtra("title")
                postBodyComment.text = getStringExtra("body")
                postId = getStringExtra("id")
            }

        }
    }
}