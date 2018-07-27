package com.icarasia.social.socialmediaapp.Dagger2.daggerCommentsActivity

import com.icarasia.social.socialmediaapp.Comments.CommentsActivityView
import dagger.Component

@Component (modules = [CommentsActivityModule::class])
interface CommentsActivityComponent {
    fun  inject(commentsViewCotract: CommentsActivityView)
}