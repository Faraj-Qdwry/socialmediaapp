package com.icarasia.social.socialmediaapp.Posts


data class Post(
    val userId: Int = 0,
    val id: Int = 0,
    val title: String = "",
    val body: String = ""
) {
    fun isFull(): Boolean {
        return userId != 0 &&title.isNotBlank()&&body.isNotBlank()
    }
}