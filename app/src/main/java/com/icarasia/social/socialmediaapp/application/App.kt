package com.icarasia.social.socialmediaapp.application

import android.app.Application
import com.icarasia.social.socialmediaapp.application.DI.AppComponent
import com.icarasia.social.socialmediaapp.application.DI.AppModule
import com.icarasia.social.socialmediaapp.application.DI.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule())
                .build()
    }
}

