package com.icarasia.social.socialmediaapp.Dagger2.daggerLoginActivity

import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.Login.LoginViewModel
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule(private val loginActivity: LoginActivity) {
    @Provides
    fun getLoginViewModle(): LoginViewModel {
        return LoginViewModel(loginActivity,RepoDataSource)
    }
}