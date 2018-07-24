package com.icarasia.social.socialmediaapp.Comments

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.RxUnitTestingSetup
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

import org.mockito.Mockito

class CommentsViewModelTest {

    lateinit var view : CommentsViewCotract
    lateinit var repo : DataSourece
    lateinit var commentsViewModel: CommentsViewModel
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
        commentsViewModel = CommentsViewModel(view,repo)
    }

    @Test
    fun whenUserReceivedFull(){
        commentsViewModel.whenCommentsReceved(listofCommetns)
        verify(view).addDataToAddapter(listofCommetns)
        verify(view).hidProgressBar()
    }

    @Test
    fun whenUserReceivedEmpty(){
        listofCommetns.clear()
        commentsViewModel.whenCommentsReceved(listofCommetns)
        verify(view, never()).addDataToAddapter(listofCommetns)
        verify(view, never()).hidProgressBar()
    }

    @Test
    fun callComments() {
        Mockito.`when`(repo.getCommetsForPost(1)).thenReturn(Observable.fromArray(listofCommetns))
        commentsViewModel.callComments(1)
        verify(view).showProgressBar()
    }



    @Test
    fun `call comments test Observable`(){
        `when`(repo.getCommetsForPost(1)).thenReturn(Observable.fromArray(listofCommetns))

        var test  = repo.getCommetsForPost(1).test()

        commentsViewModel.callComments(1)

        test.assertComplete()

    }

    @Test
    fun `call comments test Observable Empty`(){
        `when`(repo.getCommetsForPost(1)).thenReturn(Observable.empty())

        var test  = repo.getCommetsForPost(1).test()

        commentsViewModel.callComments(1)

        test.errors()

    }

}