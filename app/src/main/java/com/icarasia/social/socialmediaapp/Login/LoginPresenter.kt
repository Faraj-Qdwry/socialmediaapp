package com.icarasia.social.socialmediaapp.Login

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.HomeActivity
import com.icarasia.social.socialmediaapp.extensions.onObservData

class LoginPresenter(private val viewInstance: viewContract, private val repo : DataSourece){

    private lateinit var user : User
    private var todoFinishFlage = false
    private var albumsFinishFlage = false

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

    private val whenUserReceived : (users : List<User>) -> Unit = {
        if (it.isNotEmpty()) {
            this.user = it[0]
            Log.d("User Received ", "  ***********      ${user.email}")
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
        viewInstance.getContext().getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE)
                .edit().putString("User", Gson().toJson(user)).apply()

        viewInstance.toPostsActivity()
    }

    fun checkUserLogedIn() {
        getUserlogedIn(viewInstance.getContext())?.let { viewInstance.toPostsActivity() }
        loginProcess()
    }



}

fun getUserlogedIn(context: Context): User? =
        Gson().fromJson<User>(context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE).getString("User",""), User::class.java)
