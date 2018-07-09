package com.icarasia.social.socialmediaapp.Comments

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.extensions.onObservData
import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableFromArray
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

import org.junit.Assert.*
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

class CommentsPresenterTest {

    lateinit var view : CommentsViewCotract
    lateinit var repo : DataSourece
    lateinit var commentsPresenter: CommentsPresenter
    lateinit var listofCommetns :ArrayList<Comment>

    @Before
    fun setUp() {

         listofCommetns = ArrayList()

        for (i in 1..10){
            this.listofCommetns.add(Comment())
        }

        view = mock(CommentsViewCotract::class.java)
        repo = mock(DataSourece::class.java)
        commentsPresenter = CommentsPresenter(view,repo)
    }

    @Test
    fun whenUserReceivedTest(){
        commentsPresenter.whenCommentsReceved(listofCommetns)
        verify(view).hidProgressBar()
    }

    @Test
    fun callComments() {
        Mockito.`when`(repo.getCommetsForPost(1)).thenReturn(Observable.fromArray(listofCommetns))
        commentsPresenter.callComments(1)
        verify(view).showProgressBar()
    }
}