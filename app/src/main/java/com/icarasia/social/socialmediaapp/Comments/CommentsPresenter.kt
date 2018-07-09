package com.icarasia.social.socialmediaapp.Comments

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.extensions.onObservData


class CommentsPresenter(val view: CommentsViewCotract,val repo : DataSourece){

    fun callComments(postId: Int) {
        view.showProgressBar()
        repo.getCommetsForPost(postId).onObservData{whenCommentsReceved(it)}
    }

    fun whenCommentsReceved(arrayList: ArrayList<Comment>): ArrayList<Comment> {
        view.addDataToAddapter(arrayList)
        view.hidProgressBar()
        return arrayList
    }
}