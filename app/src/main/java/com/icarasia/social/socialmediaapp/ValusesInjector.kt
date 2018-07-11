package com.icarasia.social.socialmediaapp

import android.support.v7.app.ActionBarDrawerToggle
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.Comments.CommentsPresenter
import com.icarasia.social.socialmediaapp.Comments.CommentsRecyclerViewAdapter
import com.icarasia.social.socialmediaapp.Comments.CommentsActivityView
import com.icarasia.social.socialmediaapp.Home.HomeActivity
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.Login.LoginPresenter
import com.icarasia.social.socialmediaapp.Posts.PostsFragment
import com.icarasia.social.socialmediaapp.Posts.PostsPresenter
import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsFragment
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.home_navigation_avtivity.*

object ValusesInjector {

    fun inject(loginActivity: LoginActivity) {
        loginActivity.loginPresenter = LoginPresenter(loginActivity, RepoDataSource)
    }

    fun inject(homeActivity: HomeActivity) {
        with(homeActivity){
            fragmentPost = PostsFragment.newInstance()
            fragmentUserDetails = UserDetailsFragment.newInstance()
            toggle = ActionBarDrawerToggle(
                    this, homeActivity.drawer_layout, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close)

            fragmentPost.setShowHidActionBar(showActionbar, hidActionbar)
        }
    }

    fun inject(commintsActivityView: CommentsActivityView) {
        with(commintsActivityView){
            commentRecyclerView =   findViewById(R.id.commentsRecyclerView)
            commentadapter = CommentsRecyclerViewAdapter()

            commentsPresenter = CommentsPresenter(this,RepoDataSource)
        }
    }

    fun inject(pFragment: PostsFragment) {
        with(pFragment){
            postsPresenter = PostsPresenter(this, RepoDataSource)
        }
    }

}

