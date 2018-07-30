package com.icarasia.social.socialmediaapp.homeActivity.DI

import com.icarasia.social.socialmediaapp.postsFragment.PostsFragment
import com.icarasia.social.socialmediaapp.userDetailsFragmet.UserDetailsFragment
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