package com.icarasia.social.socialmediaapp.Dagger2

import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.Comments.CommentsViewModel
import com.icarasia.social.socialmediaapp.Comments.CommentsRecyclerViewAdapter
import com.icarasia.social.socialmediaapp.Comments.CommentsActivityView
import com.icarasia.social.socialmediaapp.Home.HomeActivity
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.Login.LoginViewModel
import com.icarasia.social.socialmediaapp.Posts.PostsFragment
import com.icarasia.social.socialmediaapp.Posts.PostsViewModel
import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsFragment
import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsViewModel

object ValusesInjector {

    fun inject(loginActivity: LoginActivity) {
        with(loginActivity){
            loginViewModel = LoginViewModel(loginActivity, RepoDataSource)
            mBinder.loginViewModel = loginViewModel
        }
    }

    fun inject(commintsActivityView: CommentsActivityView) {
        with(commintsActivityView){
            commentadapter = CommentsRecyclerViewAdapter()
            commentsPresenter = CommentsViewModel(this,RepoDataSource)
        }
    }

    fun inject(pFragment: PostsFragment) {
        with(pFragment){
            postsViewModel = PostsViewModel(this, RepoDataSource)
        }
    }

    fun inject(userDetailsFragment: UserDetailsFragment) {
        with(userDetailsFragment){
            userDetailsViewModel = UserDetailsViewModel(this)
            mBinder.userDetalsViewModel = userDetailsViewModel
        }
    }

}

