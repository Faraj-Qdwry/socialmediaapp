package com.icarasia.social.socialmediaapp.API

import android.util.Log
import com.google.gson.Gson
import com.icarasia.social.socialmediaapp.DataModels.*
import com.icarasia.social.socialmediaapp.extensions.onObservData

object RepoDataSource : DataSourece by RemoteDataSource(){

    private val map : HashMap<String,ArrayList<Any>> = HashMap()

    fun getPostsREPO(page: Int,pageCount: Int,whenPostesReceved :(ArrayList<Post>)->ArrayList<Post>){
        val key = "$page$pageCount"

        //from local
        map.get(key)?.let {
            whenPostesReceved(it as ArrayList<Post>)
            Log.d("$key","Locaaal")
        }

        //from API
        if(map[key]==null) {
            getPosts(page, pageCount).onObservData {
                map.put(key,whenPostesReceved(it) as ArrayList<Any>)
                Log.d("$key","API")
            }
        }
    }

    fun getCommentREPO(postId: Int, whenCommentsReceved : (ArrayList<Comment>)->ArrayList<Comment>){
        val key = "$postId"

        //from local
        map.get(key)?.let {
            whenCommentsReceved(it as ArrayList<Comment>)
        }

        //from API
        if(map[key] ==null) {
            getCommetsForPost(postId).onObservData {
                map.put(key,whenCommentsReceved(it) as ArrayList<Any>)
            }
        }
    }

    fun deletePostREPO(postsId: Int, whenDeleted: (post : Post) -> Unit){
        deletePosts(postsId).onObservData { whenDeleted(it) }
    }

    fun addPostREPO(post: Post, whenPostAdded: (post: Post) -> Unit){
        createPost(post).onObservData { whenPostAdded(it) }
    }

    fun getUserREPO(username: String,whenUserReceived : (users : List<User>)->Unit){
        getUser(username).onObservData { whenUserReceived(it) }
    }

    fun getAlbumsREPO(id : Int, whenAlbumsReceived :(albums : ArrayList<Any>) ->Unit){
        getAlbums(id).onObservData { whenAlbumsReceived(it) }
    }

    fun getToDosREPO(id: Int,whenTodosReceived: (ArrayList<Any>) -> Unit){
        getTodos(id).onObservData { whenTodosReceived(it) }
    }

}

