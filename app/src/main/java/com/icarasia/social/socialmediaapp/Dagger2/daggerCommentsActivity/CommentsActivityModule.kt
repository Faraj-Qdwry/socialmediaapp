package com.icarasia.social.socialmediaapp.Dagger2.daggerCommentsActivity

import android.content.Context
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.Comments.CommentsActivityView
import com.icarasia.social.socialmediaapp.Comments.CommentsRecyclerViewAdapter
import com.icarasia.social.socialmediaapp.Comments.CommentsViewCotract
import com.icarasia.social.socialmediaapp.Comments.CommentsViewModel
import dagger.Module
import dagger.Provides

@Module
class CommentsActivityModule {
    private val context : CommentsViewCotract

    constructor(contextMenu: CommentsActivityView) {
        this.context = contextMenu
    }

    @Provides
    fun getCommentsRecyclerViewAdapter(): CommentsRecyclerViewAdapter {
        return CommentsRecyclerViewAdapter()
    }

    @Provides
    fun getCommentsViewModel(): CommentsViewModel {
        return CommentsViewModel(context,RepoDataSource)
    }

}