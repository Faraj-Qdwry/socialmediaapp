package com.icarasia.social.socialmediaapp.API

import com.icarasia.social.socialmediaapp.DataModels.*
import com.icarasia.social.socialmediaapp.extensions.onObservData
import io.reactivex.Observable

class RepoDataSource : DataSourece{

    val RemoteDS = RemoteDataSource()

    //TODO here you chse either from cash or Network

    fun deletePostREPO(postsId: Int, whenDeleted: (post : Post) -> Unit){
        deletePosts(postsId).onObservData {
            whenDeleted(it) }
    }

    fun addPostREPO(post: Post, whenPostAdded: (post: Post) -> Unit){
        createPost(post).onObservData { whenPostAdded(it) }
    }

    fun getPostsREPO(page: Int,pageCount: Int,whenPostesReceved :(ArrayList<Post>)->Unit){
        getPosts(page,pageCount).onObservData { whenPostesReceved(it) }
    }

    fun getCommentREPO(postId: Int, whenCommentsReceved : (ArrayList<Comment>)->Unit){
        getCommetsForPost(postId).onObservData { whenCommentsReceved(it) }
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



    //**********************************************************

    override fun deletePosts(postsId: Int): Observable<Post> {
        return RemoteDS.deletePosts(postsId)
    }

    override fun createPost(post: Post): Observable<Post> {
        return RemoteDS.createPost(post)
    }

    override fun getCommetsForPost(postId: Int): Observable<ArrayList<Comment>> {
        return RemoteDS.getCommetsForPost(postId)
    }

    override fun getPosts(page: Int, pageCount: Int): Observable<ArrayList<Post>> {
        return RemoteDS.getPosts(page,pageCount)
    }

    override fun getUser(username: String): Observable<List<User>> {
        return RemoteDS.getUser(username)
    }

    override fun getAlbums(id: Int): Observable<ArrayList<Any>> {
        return RemoteDS.getAlbums(id)
    }

    override fun getTodos(id: Int): Observable<ArrayList<Any>> {
        return RemoteDS.getTodos(id)
    }

}