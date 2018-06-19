package com.icarasia.social.socialmediaapp.API

import com.icarasia.social.socialmediaapp.DataModels.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitSectviceAPI {

//    @GET("users")
//    fun getUserDetails(): Call<ArrayList<User>>

    @GET("users")
    fun getUserDetails(@Query("username") Name: String): Call<ArrayList<User>>

    @GET("albums")
    fun getUserAlbums(@Query("userId") id: Int): Call<ArrayList<album>>

    @GET("todos")
    fun getUserTodos(@Query("userId") id: Int): Call<ArrayList<todo>>

    companion object Factory {
        fun create(): RetrofitSectviceAPI = Retrofit.Builder().apply {
            baseUrl("https://jsonplaceholder.typicode.com")
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(RetrofitSectviceAPI::class.java)

    }


}