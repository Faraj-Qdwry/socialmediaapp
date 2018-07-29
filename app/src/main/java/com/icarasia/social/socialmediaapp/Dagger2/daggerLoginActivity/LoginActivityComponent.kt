package com.icarasia.social.socialmediaapp.Dagger2.daggerLoginActivity

import com.icarasia.social.socialmediaapp.Login.LoginActivity
import dagger.Component

@Component(modules = [LoginActivityModule::class])
interface LoginActivityComponent {
    fun inject(loginActivity: LoginActivity)
}