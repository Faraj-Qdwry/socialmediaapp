package com.icarasia.social.socialmediaapp.Comments

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.RxUnitTestingSetup
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

import org.mockito.Mockito

class CommentsPresenterTest {

    lateinit var view : CommentsViewCotract
    lateinit var repo : DataSourece
    lateinit var commentsPresenter: CommentsViewModel
    lateinit var listofCommetns :ArrayList<Comment>

    @Before
    fun setUp() {

        RxUnitTestingSetup.run()

        listofCommetns = ArrayList()

        for (i in 1..10){
            this.listofCommetns.add(Comment())
        }

        view = mock(CommentsViewCotract::class.java)
        repo = mock(DataSourece::class.java)
        commentsPresenter = CommentsViewModel(view,repo)
    }

    @Test
    fun whenUserReceivedFull(){
        commentsPresenter.whenCommentsReceved(listofCommetns)
        verify(view).addDataToAddapter(listofCommetns)
        verify(view).hidProgressBar()
    }

    @Test
    fun whenUserReceivedEmpty(){
        listofCommetns.clear()
        commentsPresenter.whenCommentsReceved(listofCommetns)
        verify(view, never()).addDataToAddapter(listofCommetns)
        verify(view, never()).hidProgressBar()
    }

    @Test
    fun callComments() {
        Mockito.`when`(repo.getCommetsForPost(1)).thenReturn(Observable.fromArray(listofCommetns))
        commentsPresenter.callComments(1)
        verify(view).showProgressBar()
    }
}