package com.icarasia.social.socialmediaapp.API

import android.util.Log
import com.icarasia.social.socialmediaapp.DataModels.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.icarasia.social.socialmediaapp.DataModels.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.POST




interface RetrofitSectviceAPI {

//    @DELETE("posts/{postsId}")
//    fun deletePosts(@Path("postsId") postsId : Int) : Call<Post>

    @DELETE("posts/{postsId}")
    fun deletePosts(@Path("postsId") postsId : Int) : Observable<Post>


//    @POST("posts")
//    fun createPost(@Body post: Post): Call<Post>

    @POST("posts")
    fun createPost(@Body post: Post): Observable<Post>

//    @GET("posts/{postId}/comments")
//    fun getCommetsForPost(@Path("postId") postId: String): Call<ArrayList<Comment>>

    @GET("posts/{postId}/comments")
    fun getCommetsForPost(@Path("postId") postId: String): Observable<ArrayList<Comment>>

//    @GET("posts")
//    fun getPosts(@Query("page_number") page: Int, @Query("page_size") pageCount: Int): Call<ArrayList<Post>>

    @GET("posts")
    fun getPosts(@Query("page_number") page: Int, @Query("page_size") pageCount: Int): Observable<ArrayList<Post>>



//    @GET("users")
//    fun getUserDetails(@Query("username") Name: String): Call<ArrayList<User>>

    @GET("users")
    fun getUser(@Query("username")username: String) : Observable<List<User>>

//    @GET("albums")
//    fun getUserAlbums(@Query("userId") id: Int): Call<ArrayList<Any>>

    @GET("albums")
    fun getAlbums(@Query("userId")id : Int): Observable<ArrayList<Any>>

//    @GET("todos")
//    fun getUserTodos(@Query("userId") id: Int): Call<ArrayList<Any>>

    @GET("todos")
    fun getTodos(@Query("userId")id: Int): Observable<ArrayList<Any>>


    companion object Factory {
        fun create(): RetrofitSectviceAPI = Retrofit.Builder().apply {
            baseUrl("https://jsonplaceholder.typicode.com")
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build().create(RetrofitSectviceAPI::class.java)
    }
}

