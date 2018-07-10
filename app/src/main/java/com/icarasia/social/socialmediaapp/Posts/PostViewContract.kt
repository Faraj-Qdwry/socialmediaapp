package com.icarasia.social.socialmediaapp.Posts

interface PostViewContract {
    fun showProgress()
    fun hideProgress()
    fun confirmDeletionMessage()
    fun addPostsToAddapter(posts: ArrayList<Post>)
    fun addSinglePostToAddapter(post: Post)
}