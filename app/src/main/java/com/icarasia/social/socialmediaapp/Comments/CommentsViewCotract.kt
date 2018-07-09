package com.icarasia.social.socialmediaapp.Comments

interface CommentsViewCotract {
    fun hidProgressBar()
    fun showProgressBar()
    fun addDataToAddapter(it: ArrayList<Comment>)
}