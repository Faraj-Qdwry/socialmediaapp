package com.icarasia.social.socialmediaapp.Posts

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.Login.User
import com.icarasia.social.socialmediaapp.RxUnitTestingSetup
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

class PostsPresenterTest {

    private lateinit var view : PostViewContract
    private lateinit var repo : DataSourece
    private lateinit var postsPresenter: PostsPresenter
    private lateinit var listofPosts :ArrayList<Post>
    private lateinit var post : Post

    @Before
    fun setUp() {
        RxUnitTestingSetup.run()
        listofPosts = ArrayList()
        post = Post()

        for (i in 1..10){
            this.listofPosts.add(Post(1,2,"title of the post","this post hase body Waw"))
        }

        view = mock(PostViewContract::class.java)
        repo = mock(DataSourece::class.java)
        postsPresenter = PostsPresenter(view,repo)
    }


    @Test
    fun isOKwhenUserSingedIN_DO_NOT_Own_ThePost(){
        `when`(view.getCurrentUser()).thenReturn(User(2))
        assert(!postsPresenter.isOK(post))
    }

    @Test
    fun isOKwhenUserSingedIN_Own_ThePost(){
        `when`(view.getCurrentUser()).thenReturn(User())
        assert(postsPresenter.isOK(post))
    }

    @Test
    fun isOKwhenUserSingedIN(){
        `when`(view.getCurrentUser()).thenReturn(User())

        assert(postsPresenter.isOK(post))
    }

    @Test
    fun isOKwhenUserSingedOUT(){
        `when`(view.getCurrentUser()).thenReturn(null)
        assert(!postsPresenter.isOK(Post()))
    }

    @Test
    fun callpostWithPositivePageNumber() {
        `when`(repo.getPosts(ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(listofPosts))

        postsPresenter.callpost(1,20)

        verify(view).showProgress()
    }

    @Test
    fun callpostWithNegativePageNumber() {
        postsPresenter.callpost(-1,20)

        verify(view, never()).showProgress()
    }

    @Test
    fun whePostsReceivedFull() {
        assert(postsPresenter.whePostsReceived(listofPosts) == listofPosts)

        verify(view).addPostsToAddapter(listofPosts)
        verify(view).hideProgress()
    }

    @Test
    fun whePostsReceivedEmpty() {
        listofPosts.clear()

        assert(postsPresenter.whePostsReceived(listofPosts) == listofPosts)

        verify(view, never()).addPostsToAddapter(listofPosts)
        verify(view).hideProgress()
    }

    @Test
    fun postsToremoveWhenFull() {
        `when`(repo.deletePosts(ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(Post()))

        postsPresenter.postsToremove(listofPosts)

        verify(view, times(listofPosts.size)).deletionConfirmingMessage()
    }

    @Test
    fun postsToremoveWhenEmpty() {
        listofPosts.clear()

        postsPresenter.postsToremove(listofPosts)

        verify(view, never()).deletionConfirmingMessage()
    }


    @Test
    fun deletePostWitthHealthy() {
        `when`(repo.deletePosts(ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(listofPosts.get(0)))
        postsPresenter.deletePost(listofPosts.get(0))
        verify(view).deletionConfirmingMessage()
    }

    @Test
    fun deletePostWithEmptyPost() {
        postsPresenter.deletePost(Post())
        verify(view, never()).deletionConfirmingMessage()
    }

    @Test
    fun sendPostWhenIsFull() {
        with(Post(userId = 1,title = "title",body = "body")) {

            `when`(repo.createPost(this)).thenReturn(Observable.fromArray(this))

            postsPresenter.sendApost(this)

            verify(view).addSinglePostToAddapter(this)
        }
    }

    @Test
    fun sendPostWhenIsEmpty() {

        with(Post()) {
            postsPresenter.sendApost(this)

            verify(view, never()).addSinglePostToAddapter(this)
        }
    }

    @Test
    fun whenPostAddedWhenIsFull(){
        postsPresenter.whenPostAdded(listofPosts.get(0))
        verify(view).addSinglePostToAddapter(listofPosts.get(0))
    }


    @Test
    fun whenPostAddedWhenIsNOTFull(){
        postsPresenter.whenPostAdded(Post())
        verify(view, never()).addSinglePostToAddapter(listofPosts.get(0))
    }

    @Test
    fun clickHandleSingleClickedWithFullPost(){
        postsPresenter.clickHandle(listofPosts.get(0),1)
        verify(view).toCommentsActivity(listofPosts.get(0))
    }

    @Test
    fun clickHandleSingleClickedWithEmptyPost(){
        postsPresenter.clickHandle(Post(),2)
        verify(view, never()).toCommentsActivity(post)
    }

    @Test
    fun clickHandleLongClicked(){
        postsPresenter.clickHandle(Post(),2)
        verify(view).trigerDeletionMode()
    }

    @Test
    fun getCurrentPage(){
        assert(postsPresenter.getCurrentPage()>0)
    }

    @Test
    fun getItemsPerPageCount(){
        assert(postsPresenter.getItemsPerPageCount()>0)
    }

}