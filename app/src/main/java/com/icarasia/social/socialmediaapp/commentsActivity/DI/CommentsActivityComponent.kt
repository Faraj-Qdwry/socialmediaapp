package com.icarasia.social.socialmediaapp.commentsActivity.DI

import com.icarasia.social.socialmediaapp.commentsActivity.CommentsActivity
import com.icarasia.social.socialmediaapp.application.DI.AppComponent
import dagger.Component

@Component (modules = [CommentsActivityModule::class], dependencies = [AppComponent::class])
interface CommentsActivityComponent {
    fun  inject(commentsActivity: CommentsActivity)
}