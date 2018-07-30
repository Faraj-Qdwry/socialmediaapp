package com.icarasia.social.socialmediaapp.application.DI

import com.icarasia.social.socialmediaapp.dataSource.DataSourece
import com.icarasia.social.socialmediaapp.dataSource.RepoDataSource
import dagger.Module
import dagger.Provides

@Module
class AppModule() {

    @Provides
    fun getRepository(): DataSourece {
        return RepoDataSource
    }
}