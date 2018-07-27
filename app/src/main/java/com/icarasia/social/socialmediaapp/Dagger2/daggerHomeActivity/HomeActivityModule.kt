package com.icarasia.social.socialmediaapp.Dagger2.daggerHomeActivity

import com.icarasia.social.socialmediaapp.Posts.PostsFragment
import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsFragment
import dagger.Module
import dagger.Provides

@Module
class HomeActivityModule{
    @Provides
    fun getPostFragment(): PostsFragment {
        return PostsFragment.newInstance()
    }
    @Provides
    fun getUserDetailsFragment(): UserDetailsFragment {
        return UserDetailsFragment.newInstance()
    }
}