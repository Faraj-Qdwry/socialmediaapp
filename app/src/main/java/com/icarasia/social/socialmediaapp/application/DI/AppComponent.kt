package com.icarasia.social.socialmediaapp.application.DI

import com.icarasia.social.socialmediaapp.dataSource.DataSourece
import dagger.Component

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun getRepo(): DataSourece
}