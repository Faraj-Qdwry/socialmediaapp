package com.icarasia.social.socialmediaapp.abstracts

import android.app.ProgressDialog
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.Login.LoginPresenter

object ValusesInjector {
    fun inject(loginPresenter: LoginPresenter){
        loginPresenter.albumsFinishFlage= false
        loginPresenter.todoFinishFlage = false
    }

    fun inject(loginActivity: LoginActivity) {
        loginActivity.loginPresenter = LoginPresenter(loginActivity, RepoDataSource)
    }

    fun inject(socialMediaFragment: SocialMediaFragment) {
        socialMediaFragment.activity = socialMediaFragment.context as SocialMediaActivity
    }
}