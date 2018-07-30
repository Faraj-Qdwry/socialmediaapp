package com.icarasia.social.socialmediaapp.loginActivity.DI

import com.icarasia.social.socialmediaapp.application.DI.AppComponent
import com.icarasia.social.socialmediaapp.loginActivity.LoginActivity
import dagger.Component

@Component(modules = [LoginActivityModule::class],dependencies = arrayOf(AppComponent::class))
interface LoginActivityComponent {
    fun inject(loginActivity: LoginActivity)
}