package com.icarasia.social.socialmediaapp.Login

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.extensions.onObservData
import com.icarasia.social.socialmediaapp.BR

class LoginViewModel(private val viewInstance: viewContract, private val repo : DataSourece) : BaseObservable(){

    var userName : String = String()
        @Bindable
        get() = field
        set(value){
            field = value
            Log.d("value : ",field)
            notifyPropertyChanged(BR.loginViewModel)
        }

    lateinit var user : User
    var todoFinishFlage : Boolean = false
    var albumsFinishFlage : Boolean = false

    fun checkUserLogedIn() {
        if (viewInstance.userLogedIn())
            moveToPostActivity()
        else{
            viewInstance.hidActionBar()
        }
    }

    fun moveToPostActivity(){
        viewInstance.toPostsActivity()
    }

    fun CallForUser(userName : String) {
        with(viewInstance){
            with(userName){
                if (isEmpty()) {
                    showErrorMessage()
                } else {
                    showLoadingDialoge()
                    if (internetStatuse)
                        repo.getUser(userName).onObservData { whenUserReceived(it) }
                    else
                        showErrorMessage()
                }
            }
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

    fun callTodosAndAlbums(id: Int) {
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
            moveToPostActivity()
        }
    }
}