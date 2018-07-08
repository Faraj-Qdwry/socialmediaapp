package com.icarasia.social.socialmediaapp.Login

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.abstracts.ValusesInjector
import com.icarasia.social.socialmediaapp.extensions.onObservData

class LoginPresenter(private val viewInstance: viewContract, private val repo : DataSourece){

    private lateinit var user : User
    var todoFinishFlage : Boolean = false
    var albumsFinishFlage : Boolean = false

    init {
        ValusesInjector.inject(this)
    }

    fun loginProcess() {
        viewInstance.hidActionBar()
        viewInstance.loginButtunSetUp {name ->
            with(name){
                if (this@with.isEmpty()) {
                    viewInstance.showErrorMessage()
                } else {
                    viewInstance.showLoadingDialoge()
                    repo.getUser(this).onObservData { whenUserReceived(it) }
                }
            }
        }
    }

    val whenUserReceived : (users : List<User>) -> Unit = {
        if (it.isNotEmpty()) {
            this.user = it[0]
            callTodosAndAlbums(user.id)
        } else {
            viewInstance.hidLoadingDialoge()
            viewInstance.showErrorMessage()
        }
    }

    private fun callTodosAndAlbums(id: Int) {
        repo.getTodos(id).onObservData{
            run {
                user.todosNumber = it.size
                todoFinishFlage = true
                if (albumsFinishFlage) {
                    saveUser(user)
                }
            }
        }
        repo.getAlbums(id).onObservData{
            run {
                user.albumsNumber = it.size
                albumsFinishFlage = true
                if (albumsFinishFlage) {
                    saveUser(user)
                }
            }
        }
    }

    private fun saveUser(user: User) {
        viewInstance.saveUser(user)
        viewInstance.toPostsActivity()
    }

    fun checkUserLogedIn() {
        if (!viewInstance.userLogedIn())
            loginProcess()
        else
            viewInstance.toPostsActivity()
    }

}