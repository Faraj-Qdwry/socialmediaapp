package com.icarasia.social.socialmediaapp.commentsActivity

interface CommentsViewCotract {
    fun hidProgressBar()
    fun showProgressBar()
    fun addDataToAddapter(it: ArrayList<Comment>)
}