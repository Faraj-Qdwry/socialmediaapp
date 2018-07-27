package com.icarasia.social.socialmediaapp.Dagger2.daggerHomeActivity

import com.icarasia.social.socialmediaapp.Home.HomeActivity
import dagger.Component

@Component(modules = [HomeActivityModule::class])
interface HomeActivityComponent{
    fun inject(homeActivity : HomeActivity)
}