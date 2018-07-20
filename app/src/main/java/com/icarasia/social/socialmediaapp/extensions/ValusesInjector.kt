package com.icarasia.social.socialmediaapp.extensions

import android.support.v7.app.ActionBarDrawerToggle
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.Comments.CommentsPresenter
import com.icarasia.social.socialmediaapp.Comments.CommentsRecyclerViewAdapter
import com.icarasia.social.socialmediaapp.Comments.CommentsActivityView
import com.icarasia.social.socialmediaapp.Home.HomeActivity
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.Login.LoginViewModel
import com.icarasia.social.socialmediaapp.Posts.PostsFragment
import com.icarasia.social.socialmediaapp.Posts.PostsPresenter
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsFragment
import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsViewModel
import kotlinx.android.synthetic.main.app_bar_navigation.*
import kotlinx.android.synthetic.main.home_navigation_avtivity.*

object ValusesInjector {

    fun inject(loginActivity: LoginActivity) {
        with(loginActivity){
            loginPresenter = LoginViewModel(loginActivity, RepoDataSource)
            mBinder.loginViewModel = loginPresenter
        }
    }

    fun inject(homeActivity: HomeActivity) {
        with(homeActivity){
            fragmentPost = PostsFragment.newInstance()
            fragmentUserDetails = UserDetailsFragment.newInstance()
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

    fun inject(userDetailsFragment: UserDetailsFragment) {
        with(userDetailsFragment){
            userDetailsViewModel = UserDetailsViewModel(this)
            mBinder.userDetalsViewModel = userDetailsViewModel
        }
    }

}

