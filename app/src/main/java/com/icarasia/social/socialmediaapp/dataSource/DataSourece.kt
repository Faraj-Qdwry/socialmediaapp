package com.icarasia.social.socialmediaapp.dataSource

import com.icarasia.social.socialmediaapp.commentsActivity.Comment
import retrofit2.http.*
import com.icarasia.social.socialmediaapp.loginActivity.User
import com.icarasia.social.socialmediaapp.postsFragment.Post
import io.reactivex.Observable
import retrofit2.http.POST


interface DataSourece {

    @DELETE("posts/{postsId}")
    fun deletePosts(@Path("postsId") postsId : Int) : Observable<Post>

    @POST("posts")
    fun createPost(@Body post: Post): Observable<Post>

    @GET("posts/{postId}/comments")
    fun getCommetsForPost(@Path("postId") postId: Int): Observable<ArrayList<Comment>>

    @GET("posts")
    fun getPosts(@Query("page_number") page: Int, @Query("page_size") pageCount: Int)
            : Observable<ArrayList<Post>>

    @GET("users")
    fun getUser(@Query("username")username: String) : Observable<List<User>>


    @GET("albums")
    fun getAlbums(@Query("userId")id : Int): Observable<ArrayList<Any>>

    @GET("todos")
    fun getTodos(@Query("userId")id: Int): Observable<ArrayList<Any>>

}

