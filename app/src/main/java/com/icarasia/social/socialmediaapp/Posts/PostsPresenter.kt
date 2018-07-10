package com.icarasia.social.socialmediaapp.Posts

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.Login.User
import com.icarasia.social.socialmediaapp.extensions.onObservData

class PostsPresenter(val view: PostViewContract,val repo : DataSourece) {

    fun callpost(page : Int, pageCount:Int) {
        view.showProgress()
        repo.getPosts(page,pageCount).onObservData { whePostsReceived(it) }
    }

    fun whePostsReceived ( posts : ArrayList<Post>) : ArrayList<Post>  {
        view.addPostsToAddapter(posts)
        view.hideProgress()
        return  posts
    }

    fun postsToremove (listOfPosts : ArrayList<Post>): ArrayList<Post> {
        listOfPosts.forEach { deletePost(it) }
        return listOfPosts
    }

    fun deletePost(post : Post){
        repo.deletePosts(post.id).onObservData{
            view.confirmDeletionMessage()
        }
    }

    fun sendApost(title: String, body: String , user: User) {
        repo.createPost(Post(user.id, 0, title, body))
                .onObservData{whenPostAdded(it)}
    }

    fun whenPostAdded (post : Post) {
       view.addSinglePostToAddapter(post)
    }


}