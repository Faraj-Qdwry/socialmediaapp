package com.icarasia.social.socialmediaapp.commentsActivity

import com.icarasia.social.socialmediaapp.dataSource.DataSourece
import com.icarasia.social.socialmediaapp.extensions.onObservData


class CommentsViewModel(val view: CommentsViewCotract, val repo : DataSourece){

    fun callComments(postId: Int) {
        view.showProgressBar()
        repo.getCommetsForPost(postId).onObservData{whenCommentsReceved(it)}
    }

    fun whenCommentsReceved(commentsList: ArrayList<Comment>): ArrayList<Comment> {
        if (commentsList.isNotEmpty()){
            view.addDataToAddapter(commentsList)
            view.hidProgressBar()
        }
        return commentsList
    }
}