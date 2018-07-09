package com.icarasia.social.socialmediaapp.API

import com.icarasia.social.socialmediaapp.Comments.Comment
import com.icarasia.social.socialmediaapp.Posts.Post
import com.icarasia.social.socialmediaapp.Login.User
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RemoteDataSource : DataSourece {

    private val retrofit = Retrofit.Builder().apply {
        baseUrl("https://jsonplaceholder.typicode.com")
        addConverterFactory(GsonConverterFactory.create())
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }.build().create(DataSourece::class.java)

    override fun deletePosts(postsId: Int): Observable<Post> {
        return retrofit.deletePosts(postsId)
    }

    override fun createPost(post: Post): Observable<Post> {
        return retrofit.createPost(post)
    }

    override fun getCommetsForPost(postId: Int): Observable<ArrayList<Comment>> {
        return retrofit.getCommetsForPost(postId)
    }

    override fun getPosts(page: Int, pageCount: Int): Observable<ArrayList<Post>> {
        return retrofit.getPosts(page, pageCount)
    }

    override fun getUser(username: String): Observable<List<User>> {
        return retrofit.getUser(username)
    }

    override fun getAlbums(id: Int): Observable<ArrayList<Any>> {
        return retrofit.getAlbums(id)
    }

    override fun getTodos(id: Int): Observable<ArrayList<Any>> {
        return retrofit.getTodos(id)
    }

}