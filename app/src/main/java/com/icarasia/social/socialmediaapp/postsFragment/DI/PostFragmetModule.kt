package com.icarasia.social.socialmediaapp.postsFragment.DI

import com.icarasia.social.socialmediaapp.dataSource.DataSourece
import com.icarasia.social.socialmediaapp.postsFragment.PostsFragment
import com.icarasia.social.socialmediaapp.postsFragment.PostsViewModel
import dagger.Module
import dagger.Provides

@Module
class PostFragmetModule(private val postsFragment: PostsFragment){
    @Provides
    fun getPostsViewModel(repo : DataSourece): PostsViewModel {
        return PostsViewModel(postsFragment,repo)
    }
}