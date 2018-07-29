package com.icarasia.social.socialmediaapp.Dagger2.daggerPostFragmet

import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.Posts.PostsFragment
import com.icarasia.social.socialmediaapp.Posts.PostsViewModel
import dagger.Module
import dagger.Provides

@Module
class PostFragmetModule(private val postsFragment: PostsFragment){
    @Provides
    fun getPostsViewModel(): PostsViewModel {
        return PostsViewModel(postsFragment,RepoDataSource)
    }
}