package com.icarasia.social.socialmediaapp.Login

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.Posts.Post
import com.icarasia.social.socialmediaapp.RxUnitTestingSetup
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import kotlin.math.log

class LoginPresenterTest {


    lateinit var repo: DataSourece
    lateinit var view: viewContract
    lateinit var loginPresenter: LoginPresenter
    lateinit var listofAny: ArrayList<Any>
    lateinit var listofUsers: ArrayList<User>

    @Before
    fun setUp() {

        RxUnitTestingSetup.run()

        listofAny = ArrayList()
        listofUsers = ArrayList()

        for (i in 1..10) {
            this.listofAny.add(Any())
            this.listofUsers.add(User(1))
        }

        repo = mock(DataSourece::class.java)
        view = mock(viewContract::class.java)
        loginPresenter = LoginPresenter(view, repo)
    }



    @Test
    fun whenUserReceivedEmpty() {
        loginPresenter.whenUserReceived(ArrayList())

        verify(view).showErrorMessage()
        verify(view).hidLoadingDialoge()
    }

    @Test
    fun whenUserReceivedNotEmpty() {

        `when`(repo.getAlbums(1)).thenReturn(Observable.fromArray(listofAny))
        `when`(repo.getTodos(1)).thenReturn(Observable.fromArray(listofAny))

        loginPresenter.whenUserReceived(listofUsers)

        verify(repo).getAlbums(1)
        verify(repo).getTodos(1)

        assert(loginPresenter.todoFinishFlage)
        assert(loginPresenter.albumsFinishFlage)

        verify(view).toPostsActivity()
    }

    @Test
    fun callForAlbumsAndTodos(){
        `when`(repo.getAlbums(ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(listofAny))
        `when`(repo.getTodos(ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(listofAny))

        loginPresenter.user = User(1)
        loginPresenter.callTodosAndAlbums(1)

        assert(loginPresenter.todoFinishFlage)
        assert(loginPresenter.albumsFinishFlage)
        assert(loginPresenter.user.todosNumber != 0 )
        assert(loginPresenter.user.albumsNumber != 0 )

        verify(repo).getAlbums(ArgumentMatchers.anyInt())
        verify(repo).getTodos(ArgumentMatchers.anyInt())
        verify(view).toPostsActivity()
    }


    @Test
    fun checkUserLogedInWhenISlogedIn() {
        `when`(view.userLogedIn()).thenReturn(true)
        loginPresenter.checkUserLogedIn()

        verify(view).toPostsActivity()
    }

    @Test
    fun checkUserLogedInWhenISNOTlogedIn() {
        `when`(view.userLogedIn()).thenReturn(false)
        loginPresenter.checkUserLogedIn()

        verify(view).loginButtunSetUp()
    }



    @Test
    fun saveUserEmpty() {
        loginPresenter.saveUser(null)

        verify(view, never()).saveUser(User())
        verify(view, never()).toPostsActivity()
    }

    @Test
    fun saveUserNOTEmpty() {
        loginPresenter.saveUser(User())

        verify(view).saveUser(User())
        verify(view).toPostsActivity()
    }



    @Test
    fun CallForUserNotEmptyWithInternet() {

        `when`(repo.getAlbums(ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(listofAny))
        `when`(repo.getTodos(ArgumentMatchers.anyInt())).thenReturn(Observable.fromArray(listofAny))

        `when`(view.internetStatuse).thenReturn(true)
        `when`(repo.getUser("Bret")).thenReturn(Observable.fromArray(listofUsers))

        loginPresenter.CallForUser("Bret")

        verify(view, never()).showErrorMessage()
    }

    @Test
    fun CallForUserNotEmptyWithNoInternet() {
        `when`(view.internetStatuse).thenReturn(false)

        loginPresenter.CallForUser("Bret")

        verify(view).showErrorMessage()
    }

    @Test
    fun CallForUserEmpty() {
        loginPresenter.CallForUser("")

        verify(view, never()).showErrorMessage()
        verify(repo, never()).getUser(ArgumentMatchers.anyString())
    }

}