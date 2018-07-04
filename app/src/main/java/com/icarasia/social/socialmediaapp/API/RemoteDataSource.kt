package com.icarasia.social.socialmediaapp.API

import com.icarasia.social.socialmediaapp.DataModels.Comment
import com.icarasia.social.socialmediaapp.DataModels.Post
import com.icarasia.social.socialmediaapp.DataModels.User
import io.reactivex.Observable


class RemoteDataSource :  DataSourece{

    private val retrofit = RetrofitAPI.create()

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
        return retrofit.getPosts(page,pageCount)
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