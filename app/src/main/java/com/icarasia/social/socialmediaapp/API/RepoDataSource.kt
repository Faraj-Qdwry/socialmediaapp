package com.icarasia.social.socialmediaapp.API

import android.util.Log
import com.icarasia.social.socialmediaapp.DataModels.*
import com.icarasia.social.socialmediaapp.Login.User
import com.icarasia.social.socialmediaapp.extensions.onObservData
import io.reactivex.Observable

object RepoDataSource : DataSourece {

    private val map : HashMap<String,ArrayList<Any>> = HashMap()

    private val RDS: RemoteDataSource = RemoteDataSource()

    override fun deletePosts(postsId: Int): Observable<Post> {
        //map.clear()
        return RDS.deletePosts(postsId)
    }

    override fun createPost(post: Post): Observable<Post> {
        //map.clear()
        return RDS.createPost(post)//.onObservData { whenPostAdded(it) }
    }

    override fun getCommetsForPost(postId: Int): Observable<ArrayList<Comment>> {
        val key = "$postId"

//        //from local
//        map.get(key)?.let {
//            whenCommentsReceved(it as ArrayList<Comment>)
//        }
//
//        //from API
//        if(map[key] ==null) {
          return  RDS.getCommetsForPost(postId) //.onObservData {
//                map.put(key,whenCommentsReceved(it) as ArrayList<Any>)
//            }
//        }
    }

    override fun getPosts(page: Int, pageCount: Int): Observable<ArrayList<Post>> {
        val key = "$page$pageCount"
//
//        //from local
//        map.get(key)?.let {
//            return it
//            Log.d("$key","Locaaal")
//        }

        //from API
        //if(map[key]==null) {
            return RDS.getPosts(page, pageCount)

//                    .onObservData {
//                map.put(key,whenPostesReceved(it) as ArrayList<Any>)
//                Log.d("$key","API")
//            }

    }

    override fun getUser(username: String): Observable<List<User>> {
        return RDS.getUser(username)
    }

    override fun getAlbums(id: Int): Observable<ArrayList<Any>> {
        return RDS.getAlbums(id)
    }

    override fun getTodos(id: Int): Observable<ArrayList<Any>> {
        return RDS.getTodos(id)
    }
//
//
//
//
//    fun getPostsREPO(page: Int,pageCount: Int,whenPostesReceved :(ArrayList<Post>)->ArrayList<Post>){
//
//    }
//
//    fun getCommentREPO(postId: Int, whenCommentsReceved : (ArrayList<Comment>)->ArrayList<Comment>){
//
//    }
//
//    fun deletePostREPO(postsId: Int, whenDeleted: (post : Post) -> Unit){
//
//    }
//
//    fun addPostREPO(post: Post, whenPostAdded: (post: Post) -> Unit){
//
//    }
//
//    fun getUserREPO(username: String,whenUserReceived : (users : List<User>)->Unit){
//        getUser(username).onObservData { whenUserReceived(it) }
//    }
//
//    fun getAlbumsREPO(id : Int, whenAlbumsReceived :(albums : ArrayList<Any>) ->Unit){
//        getAlbums(id).onObservData { whenAlbumsReceived(it) }
//    }
//
//    fun getToDosREPO(id: Int,whenTodosReceived: (ArrayList<Any>) -> Unit){
//        getTodos(id).onObservData { whenTodosReceived(it) }
//    }

}

