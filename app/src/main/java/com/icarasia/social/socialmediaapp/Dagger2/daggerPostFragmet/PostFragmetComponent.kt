package com.icarasia.social.socialmediaapp.Dagger2.daggerPostFragmet

import com.icarasia.social.socialmediaapp.Posts.PostsFragment
import dagger.Component

@Component(modules = [PostFragmetModule::class])
interface PostFragmetComponent {
    fun inject(postsFragment: PostsFragment)
}