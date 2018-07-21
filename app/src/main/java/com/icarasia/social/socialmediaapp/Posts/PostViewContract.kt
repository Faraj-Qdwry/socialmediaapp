package com.icarasia.social.socialmediaapp.Posts

import android.annotation.SuppressLint
import com.icarasia.social.socialmediaapp.Login.User

interface PostViewContract {
    var logedinFlag : Boolean
    fun showProgress()
    fun hideProgress()
    fun deletionConfirmingMessage()
    fun addPostsToAddapter(posts: ArrayList<Post>)
    fun addSinglePostToAddapter(post: Post)
    fun AddapterItemCount(): Int
    fun getCurrentUser(): User?
    fun toCommentsActivity(post: Post)
    fun trigerDeletionMode()
    fun showNewPostAlertDialoge()
    fun showLoginAlertDialoge()
}