package com.icarasia.social.socialmediaapp.API

import android.util.Log
import com.icarasia.social.socialmediaapp.DataModels.*
import com.icarasia.social.socialmediaapp.Login.User
import com.icarasia.social.socialmediaapp.extensions.onObservData
import io.reactivex.Observable


object RepoDataSource : DataSourece {

    private val remoteDS: RemoteDataSource = RemoteDataSource()
    private val localDS: LocalDataSource = LocalDataSource()

    override fun deletePosts(postsId: Int): Observable<Post> {
        localDS.clearCash()
        return remoteDS.deletePosts(postsId)
    }

    override fun createPost(post: Post): Observable<Post> {
        localDS.clearCash()
        return remoteDS.createPost(post)
    }

    override fun getCommetsForPost(postId: Int): Observable<ArrayList<Comment>> =
            if (localDS.contains("$postId")) {
                Log.d("Local", "*****************")
                localDS.getCommetsForPost(postId)
            } else {
                with(remoteDS.getCommetsForPost(postId)) {
                    onObservData { localDS.cashThis("$postId", it ) }
                    Log.d("API", "*****************")
                    this
                }
            }

    override fun getPosts(page: Int, pageCount: Int): Observable<ArrayList<Post>> =
            if (localDS.contains("$page$pageCount")) {
                Log.d("Local", "*****************")
                localDS.getPosts(page, pageCount)
            } else {
                with(remoteDS.getPosts(page, pageCount)) {
                    onObservData { localDS.cashThis("$page$pageCount", it ) }
                    Log.d("API", "*****************")
                    this
                }
            }

    override fun getUser(username: String): Observable<List<User>> {
        return remoteDS.getUser(username)
    }

    override fun getAlbums(id: Int): Observable<ArrayList<Any>> {
        return remoteDS.getAlbums(id)
    }

    override fun getTodos(id: Int): Observable<ArrayList<Any>> {
        return remoteDS.getTodos(id)
    }
}

