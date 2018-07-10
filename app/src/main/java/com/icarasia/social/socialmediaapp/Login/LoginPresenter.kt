package com.icarasia.social.socialmediaapp.Login

import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.extensions.onObservData

class LoginPresenter(private val viewInstance: viewContract, private val repo : DataSourece){

    private lateinit var user : User
    var todoFinishFlage : Boolean = false
    var albumsFinishFlage : Boolean = false


    fun CallForUser(userName : String) {
        if (!userName.isEmpty()){
            if (viewInstance.internetStatuse)
                repo.getUser(userName).onObservData { whenUserReceived(it) }
            else
                viewInstance.showErrorMessage()
        }
    }

    fun whenUserReceived(users : List<User>){
        if (users.isNotEmpty()) {
            this.user = users[0]
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
                if (todoFinishFlage) {
                    saveUser(user)
                }

            }
        }
    }

    fun saveUser(user: User?) {
        user?.let {
            viewInstance.saveUser(user)
            viewInstance.toPostsActivity()
        }
    }

    fun checkUserLogedIn() {
        if (!viewInstance.userLogedIn())
            viewInstance.loginButtunSetUp()
        else
            viewInstance.toPostsActivity()
    }

}