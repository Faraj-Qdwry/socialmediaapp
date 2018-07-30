package com.icarasia.social.socialmediaapp.postsFragment

import android.databinding.ObservableArrayList
import android.util.Log
import com.icarasia.social.socialmediaapp.dataSource.DataSourece
import com.icarasia.social.socialmediaapp.extensions.onObservData

class PostsViewModel(val view: PostViewContract, val repo : DataSourece)  : PostsRecyclerViewAdapter.Criteria{



    val totalCount = 500
    var page = 1
    private var itemsPerPage = 20
    var posts = ObservableArrayList<Post>()


    override fun isOK(data: Post): Boolean {
        (view.getCurrentUser())?.let {
            return it.id == data.userId
        }
        return false
    }

    fun callpost(page : Int, pageCount:Int) {
        if(page>0){
            view.showProgress()
            repo.getPosts(page,pageCount).onObservData { whePostsReceived(it) }
        }
    }

    fun whePostsReceived ( posts : ArrayList<Post>) : ArrayList<Post>  {
        posts.let {
            if (it.isNotEmpty()){
                view.addPostsToAddapter(it)
            }
            view.hideProgress()
        }
        return  posts
    }

    fun postsToremove (listOfPosts : ArrayList<Post>): ArrayList<Post> {
        if (listOfPosts.isNotEmpty()){
            listOfPosts.forEach { deletePost(it) }
        }
        return listOfPosts
    }


    fun deletePost(post : Post){
        if (post.isFull()){
            repo.deletePosts(post.id).onObservData{
                view.deletionConfirmingMessage()
            }
        }
    }

    fun addPost(){
        Log.d("addPost####","called ")
        with(view){
            if (logedinFlag) {
                showNewPostAlertDialoge()
            } else {
                showLoginAlertDialoge()
            }
        }
    }

    fun sendApost(post: Post) {
        if (post.isFull())
            repo.createPost(post).onObservData{whenPostAdded(it)}
    }

    fun whenPostAdded (post : Post) {
        if (post.isFull())
            view.addSinglePostToAddapter(post)
    }

    fun clickHandle (post : Post,clickType: Int){
        if (clickType== shortClik) {
            if (post.isFull())
                view.toCommentsActivity(post)
        }else if (clickType == longClick){
            view.trigerDeletionMode()
        }
    }

    fun getCurrentPage(): Int {
        return page
    }

    fun getItemsPerPageCount(): Int {
        return itemsPerPage
    }
}