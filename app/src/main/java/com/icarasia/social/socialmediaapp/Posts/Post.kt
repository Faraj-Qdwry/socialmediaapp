package com.icarasia.social.socialmediaapp.Posts


data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)