package com.icarasia.social.socialmediaapp.Posts

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.Login.User
import com.icarasia.social.socialmediaapp.RxUnitTestingSetup
import io.reactivex.Observable
import io.reactivex.Observer
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.lang.Exception

class PostsPresenterTest {

    private lateinit var view : PostViewContract
    private lateinit var repo : DataSourece
    private lateinit var postsViewModel: PostsViewModel
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
        postsViewModel = PostsViewModel(view,repo)
    }


    @Test
    fun isOKwhenUserSingedIN_DO_NOT_Own_ThePost(){
        `when`(view.getCurrentUser()).thenReturn(User(2))
        assert(!postsViewModel.isOK(post))
    }

    @Test
    fun isOKwhenUserSingedIN_Own_ThePost(){
        `when`(view.getCurrentUser()).thenReturn(User())
        assert(postsViewModel.isOK(post))
    }

    @Test
    fun isOKwhenUserSingedIN(){
        `when`(view.getCurrentUser()).thenReturn(User())

        assert(postsViewModel.isOK(post))
    }

    @Test
    fun isOKwhenUserSingedOUT(){
        `when`(view.getCurrentUser()).thenReturn(null)
        assert(!postsViewModel.isOK(Post()))
    }

    @Test
    fun callpostWithPositivePageNumber() {
        `when`(repo.getPosts(ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(listofPosts))

        postsViewModel.callpost(1,20)

        verify(view).showProgress()
    }

    @Test
    fun callpostWithNegativePageNumber() {
        postsViewModel.callpost(-1,20)

        verify(view, never()).showProgress()
    }

    @Test
    fun whePostsReceivedFull() {
        assert(postsViewModel.whePostsReceived(listofPosts) == listofPosts)

        verify(view).addPostsToAddapter(listofPosts)
        verify(view).hideProgress()
    }

    @Test
    fun whePostsReceivedEmpty() {
        listofPosts.clear()

        assert(postsViewModel.whePostsReceived(listofPosts) == listofPosts)

        verify(view, never()).addPostsToAddapter(listofPosts)
        verify(view).hideProgress()
    }

    @Test
    fun postsToremoveWhenFull() {
        `when`(repo.deletePosts(ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(Post()))

        postsViewModel.postsToremove(listofPosts)

        verify(view, times(listofPosts.size)).deletionConfirmingMessage()
    }

    @Test
    fun `postsToremoveWhenFull Test Observable`() {
        `when`(repo.deletePosts(ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(Post()))

        var test = repo.deletePosts(ArgumentMatchers.anyInt()).test()

        postsViewModel.postsToremove(listofPosts)

        test.assertComplete()

        test.assertNoErrors()
    }

    @Test
    fun `postsToremoveWhen Empty Test Observable`() {
        `when`(repo.deletePosts(ArgumentMatchers.anyInt())).thenReturn(Observable.empty())

        var test = repo.deletePosts(ArgumentMatchers.anyInt()).test()

        postsViewModel.postsToremove(listofPosts)

        test.errors()
    }


    @Test
    fun postsToremoveWhenEmpty() {
        listofPosts.clear()

        postsViewModel.postsToremove(listofPosts)

        verify(view, never()).deletionConfirmingMessage()
    }



    @Test
    fun `delete Post Healthy User`() {
        `when`(repo.deletePosts(ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(listofPosts.get(0)))
        postsViewModel.deletePost(listofPosts.get(0))
        verify(view).deletionConfirmingMessage()
    }


    @Test
    fun `delete Post Healthy User Observer`() {
        `when`(repo.deletePosts(ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(listofPosts.get(0)))

        var test = repo.deletePosts(ArgumentMatchers.anyInt()).test()

        postsViewModel.deletePost(listofPosts.get(0))

        test.assertComplete()

        test.assertNoErrors()

    }

    @Test
    fun deletePostWithEmptyPost() {
        postsViewModel.deletePost(Post())
        verify(view, never()).deletionConfirmingMessage()
    }

    @Test
    fun `deletePostWithPost Observer`() {
        `when`(repo.deletePosts(ArgumentMatchers.anyInt())).thenReturn(Observable.empty())

        postsViewModel.deletePost(Post())

        var test = repo.deletePosts(ArgumentMatchers.anyInt()).test()

        test.assertComplete()
    }


    @Test
    fun `deletePostWithEmptyPost Observer`() {


        `when`(repo.deletePosts(ArgumentMatchers.anyInt())).thenReturn(object :Observable<Post>(){
            override fun subscribeActual(observer: Observer<in Post>?) {
            }
        })

        postsViewModel.deletePost(Post())

        var test = repo.deletePosts(ArgumentMatchers.anyInt()).test()

        test.assertNotComplete()

        test.assertNotSubscribed()

    }


    @Test
    fun sendPostWhenIsFull() {
        with(Post(userId = 1,title = "title",body = "body")) {

            `when`(repo.createPost(this)).thenReturn(Observable.fromArray(this))

            postsViewModel.sendApost(this)

            verify(view).addSinglePostToAddapter(this)
        }
    }

    @Test
    fun sendPostWhenIsEmpty() {

        with(Post()) {
            postsViewModel.sendApost(this)

            verify(view, never()).addSinglePostToAddapter(this)
        }

    }

    @Test
    fun whenPostAddedWhenIsFull(){
        postsViewModel.whenPostAdded(listofPosts.get(0))
        verify(view).addSinglePostToAddapter(listofPosts.get(0))
    }


    @Test
    fun whenPostAddedWhenIsNOTFull(){
        postsViewModel.whenPostAdded(Post())
        verify(view, never()).addSinglePostToAddapter(listofPosts.get(0))
    }

    @Test
    fun clickHandleSingleClickedWithFullPost(){
        postsViewModel.clickHandle(listofPosts.get(0),1)
        verify(view).toCommentsActivity(listofPosts.get(0))
    }

    @Test
    fun clickHandleSingleClickedWithEmptyPost(){
        postsViewModel.clickHandle(Post(),2)
        verify(view, never()).toCommentsActivity(post)
    }

    @Test
    fun clickHandleLongClicked(){
        postsViewModel.clickHandle(Post(),2)
        verify(view).trigerDeletionMode()
    }

    @Test
    fun getCurrentPage(){
        assert(postsViewModel.getCurrentPage()>0)
    }

    @Test
    fun getItemsPerPageCount(){
        assert(postsViewModel.getItemsPerPageCount()>0)
    }

}