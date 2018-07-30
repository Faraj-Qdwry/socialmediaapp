package com.icarasia.social.socialmediaapp.homeActivity.DI

import com.icarasia.social.socialmediaapp.homeActivity.HomeActivity
import dagger.Component

@Component(modules = [HomeActivityModule::class])
interface HomeActivityComponent{
    fun inject(homeActivity : HomeActivity)
}