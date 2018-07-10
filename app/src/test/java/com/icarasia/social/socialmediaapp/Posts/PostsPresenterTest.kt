package com.icarasia.social.socialmediaapp.Posts

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.RxUnitTestingSetup
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

class PostsPresenterTest {

    lateinit var view : PostViewContract
    lateinit var repo : DataSourece
    lateinit var postsPresenter: PostsPresenter
    lateinit var listofPosts :ArrayList<Post>

    @Before
    fun setUp() {
        RxUnitTestingSetup.run()
        listofPosts = ArrayList()

        for (i in 1..10){
            this.listofPosts.add(Post())
        }

        view = mock(PostViewContract::class.java)
        repo = mock(DataSourece::class.java)
        postsPresenter = PostsPresenter(view,repo)
    }

    @Test
    fun callpost() {
        `when`(repo.getPosts(ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(listofPosts))

        postsPresenter.callpost(1,20)

        verify(view).showProgress()
    }


    @Test
    fun whePostsReceived() {

        var ret = postsPresenter.whePostsReceived(listofPosts)

        assert(ret == listofPosts)

        verify(view).addPostsToAddapter(listofPosts)
        verify(view).hideProgress()
    }

    @Test
    fun postsToremove() {
        `when`(repo.deletePosts(ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(listofPosts.get(0)))

        postsPresenter.deletePost(listofPosts.get(0))
        verify(view).confirmDeletionMessage()
    }

    @Test
    fun deletePost() {

    }

    @Test
    fun sendApost() {

        with(listofPosts.get(0)) {
            postsPresenter.whenPostAdded(this)

            verify(view).addSinglePostToAddapter(this)
        }

    }

}