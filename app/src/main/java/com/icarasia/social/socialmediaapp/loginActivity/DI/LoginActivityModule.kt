package com.icarasia.social.socialmediaapp.loginActivity.DI

import com.icarasia.social.socialmediaapp.dataSource.DataSourece
import com.icarasia.social.socialmediaapp.loginActivity.LoginActivity
import com.icarasia.social.socialmediaapp.loginActivity.LoginViewModel
import dagger.Module
import dagger.Provides

@Module
class LoginActivityModule(private val loginActivity: LoginActivity) {
    @Provides
    fun getLoginViewModle(repo : DataSourece): LoginViewModel {
        return LoginViewModel(loginActivity,repo)
    }
}