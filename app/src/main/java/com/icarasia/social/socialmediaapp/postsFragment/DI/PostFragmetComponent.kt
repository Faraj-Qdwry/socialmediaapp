package com.icarasia.social.socialmediaapp.postsFragment.DI

import com.icarasia.social.socialmediaapp.application.DI.AppComponent
import com.icarasia.social.socialmediaapp.postsFragment.PostsFragment
import dagger.Component

@Component(modules = [PostFragmetModule::class], dependencies = arrayOf(AppComponent::class))
interface PostFragmetComponent {
    fun inject(postsFragment: PostsFragment)
}