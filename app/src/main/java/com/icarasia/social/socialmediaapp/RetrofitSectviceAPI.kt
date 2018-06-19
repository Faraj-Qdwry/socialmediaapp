package com.icarasia.social.socialmediaapp

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitSectviceAPI {

//    @GET("users")
//    fun getUserDetails(): Call<ArrayList<User>>

    @GET("users")
    fun getUserDetails(@Query("username") Name: String): Call<ArrayList<User>>

    @GET("albums?userId={id}")
    fun getUserAlbums(@Path("id") id: Int): Call<ArrayList<albums>>

    @GET("todos?userId={id}")
    fun getUserTodos(@Path("id") id: Int): Call<ArrayList<todos>>

    companion object Factory {
        fun create(): RetrofitSectviceAPI = Retrofit.Builder().apply {
            baseUrl("https://jsonplaceholder.typicode.com")
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(RetrofitSectviceAPI::class.java)

    }


}