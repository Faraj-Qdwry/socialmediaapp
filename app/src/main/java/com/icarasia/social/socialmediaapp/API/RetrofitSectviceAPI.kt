package com.icarasia.social.socialmediaapp.API

import com.icarasia.social.socialmediaapp.DataModels.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.icarasia.social.socialmediaapp.DataModels.User
import retrofit2.http.POST




interface RetrofitSectviceAPI {

    @POST("posts")
    fun createPost(@Body post: Post): Call<Post>

    @GET("posts/{postId}/comments")
    fun getCommetsForPost(@Path("postId") postId: String): Call<ArrayList<Comment>>

    @GET("posts")
    fun getPosts(@Query("page_number") page: Int, @Query("page_size") pageCount: Int): Call<ArrayList<Post>>

    @GET("users")
    fun getUserDetails(@Query("username") Name: String): Call<ArrayList<User>>

    @GET("albums")
    fun getUserAlbums(@Query("userId") id: Int): Call<ArrayList<Album>>

    @GET("todos")
    fun getUserTodos(@Query("userId") id: Int): Call<ArrayList<Todo>>

    companion object Factory {
        fun create(): RetrofitSectviceAPI = Retrofit.Builder().apply {
            baseUrl("https://jsonplaceholder.typicode.com")
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(RetrofitSectviceAPI::class.java)

    }


}