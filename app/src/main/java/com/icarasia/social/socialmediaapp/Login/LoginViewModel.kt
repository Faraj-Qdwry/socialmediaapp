package com.icarasia.social.socialmediaapp.Login

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.extensions.onObservData
import com.icarasia.social.socialmediaapp.BR
import com.icarasia.social.socialmediaapp.Posts.Post
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function4
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class LoginViewModel(private val viewInstance: viewContract, private val repo: DataSourece) : BaseObservable() {


    @get:Bindable
    var userName: String by Delegates.observable("") { _, _, _ -> notifyPropertyChanged(BR.loginViewModel) }


    lateinit var user: User


    fun checkUserLogedIn() {
        if (viewInstance.userLogedIn())
            moveToPostActivity()
        else {
            viewInstance.hidActionBar()
        }
    }

    fun moveToPostActivity() {
        viewInstance.toPostsActivity()
    }

    fun CallForUser(userName: String) {
        with(viewInstance) {
            with(userName) {
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

    fun whenUserReceived(users: List<User>) {
        if (users.isNotEmpty()) {
            this.user = users[0]
            callTodosAndAlbums(user.id)
        } else {
            viewInstance.hidLoadingDialoge()
            viewInstance.showErrorMessage()
        }
    }

    fun callTodosAndAlbums(id: Int) {
        Observable.zip(repo.getTodos(id), repo.getAlbums(id),
                BiFunction< ArrayList<Any>, ArrayList<Any>, Pair< ArrayList<Any>, ArrayList<Any> > > () {
                    t1, t2 -> Pair(t1, t2)
                }
        ).onObservData {
                    with(user) {
                        todosNumber = it.first.size
                        albumsNumber = it.second.size
                        saveUser(this)
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