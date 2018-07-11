package com.icarasia.social.socialmediaapp.Posts

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.Login.User
import com.icarasia.social.socialmediaapp.extensions.onObservData

class PostsPresenter(val view: PostViewContract,val repo : DataSourece)  : PostAdapterOB.Criteria{

    override fun isOK(data: Post): Boolean {
        (view.getCurrentUser())?.let {
            return it.id == data.userId
        }
        return false
    }

    val totalCount = 500
    var page = 1
    private var itemsPerPage = 20

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