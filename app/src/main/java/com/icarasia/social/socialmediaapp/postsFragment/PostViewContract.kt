package com.icarasia.social.socialmediaapp.postsFragment

import com.icarasia.social.socialmediaapp.loginActivity.User

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