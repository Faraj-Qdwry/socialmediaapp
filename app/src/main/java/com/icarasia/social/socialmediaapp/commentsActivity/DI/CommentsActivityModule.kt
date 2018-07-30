package com.icarasia.social.socialmediaapp.commentsActivity.DI

import com.icarasia.social.socialmediaapp.dataSource.DataSourece
import com.icarasia.social.socialmediaapp.commentsActivity.CommentsRecyclerViewAdapter
import com.icarasia.social.socialmediaapp.commentsActivity.CommentsViewCotract
import com.icarasia.social.socialmediaapp.commentsActivity.CommentsViewModel
import dagger.Module
import dagger.Provides

@Module
class CommentsActivityModule(val context: CommentsViewCotract) {

    @Provides
    fun getCommentsRecyclerViewAdapter(): CommentsRecyclerViewAdapter = CommentsRecyclerViewAdapter()


    @Provides
    fun getCommentsViewModel(repo: DataSourece): CommentsViewModel = CommentsViewModel(context, repo)


}