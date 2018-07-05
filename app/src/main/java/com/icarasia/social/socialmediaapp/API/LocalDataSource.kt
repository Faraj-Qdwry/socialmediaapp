package com.icarasia.social.socialmediaapp.API

import android.widget.Switch
import com.icarasia.social.socialmediaapp.DataModels.Comment
import com.icarasia.social.socialmediaapp.DataModels.Post
import com.icarasia.social.socialmediaapp.Login.User
import io.reactivex.Observable
import io.reactivex.Observable.create
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class LocalDataSource : DataSourece {

    private val map: HashMap<String, Any> = HashMap()


    val postsPublishSubject = BehaviorSubject.create<ArrayList<Post>>()
    val commentsPublishSubject = BehaviorSubject.create<ArrayList<Comment>>()

    fun contains(key: String): Boolean = map.containsKey(key)

    fun cashThis(key: String, arr: Any, type: Int) {
        map.put(key, arr)
        if (type == 1)
            postsPublishSubject.onNext(arr as ArrayList<Post>?)
        else
            commentsPublishSubject.onNext(arr as ArrayList<Comment>?)
    }

    fun clearCash() = map.clear()


    override fun deletePosts(postsId: Int): Observable<Post> {
        return create<Post> { }
    }

    override fun createPost(post: Post): Observable<Post> {
        return create<Post> { }
    }

    override fun getCommetsForPost(postId: Int): Observable<ArrayList<Comment>> {
        commentsPublishSubject.onNext(map.get("$postId") as ArrayList<Comment>)
        return commentsPublishSubject as Observable<ArrayList<Comment>>
    }

    override fun getPosts(page: Int, pageCount: Int): Observable<ArrayList<Post>> {
        postsPublishSubject.onNext(map.get("$page$pageCount") as ArrayList<Post>)
        return postsPublishSubject as Observable<ArrayList<Post>>
    }

    override fun getUser(username: String): Observable<List<User>> {
        return create<List<User>> { }
    }

    override fun getAlbums(id: Int): Observable<ArrayList<Any>> {
        return create<ArrayList<Any>> { }
    }

    override fun getTodos(id: Int): Observable<ArrayList<Any>> {
        return create<ArrayList<Any>> { }
    }
}
