package com.icarasia.social.socialmediaapp.API

import com.icarasia.social.socialmediaapp.Comments.Comment
import com.icarasia.social.socialmediaapp.Posts.Post
import com.icarasia.social.socialmediaapp.Login.User
import io.reactivex.Observable
import io.reactivex.Observable.create
import io.reactivex.subjects.BehaviorSubject

class LocalDataSource : DataSourece {

    private val map: HashMap<String, Any> = HashMap()

    val postsSubject = BehaviorSubject.create<ArrayList<Post>>()
    val commentsSubject = BehaviorSubject.create<ArrayList<Comment>>()

    fun contains(key: String): Boolean = map.containsKey(key)

    fun cashThis(key: String, arr: Any) {
        map.put(key, arr)
    }

    fun clearCash() = map.clear()


    override fun deletePosts(postsId: Int): Observable<Post> {
        return create<Post> { }
    }

    override fun createPost(post: Post): Observable<Post> {
        return create<Post> { }
    }

    override fun getCommetsForPost(postId: Int): Observable<ArrayList<Comment>> {
        commentsSubject.onNext(map.get("$postId") as ArrayList<Comment>)
        return commentsSubject as Observable<ArrayList<Comment>>
    }

    override fun getPosts(page: Int, pageCount: Int): Observable<ArrayList<Post>> {
        postsSubject.onNext(map.get("$page$pageCount") as ArrayList<Post>)
        return postsSubject as Observable<ArrayList<Post>>
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
