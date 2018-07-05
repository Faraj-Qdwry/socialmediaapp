package com.icarasia.social.socialmediaapp.Login

import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.HomeActivity
import com.icarasia.social.socialmediaapp.extensions.onObservData

class LoginPresenter(private val viewInstance: viewContract, val repo : DataSourece){

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
                    repo.getUser(this).onObservData { whenUserReceived }
                }
            }
        }
    }

    private val whenUserReceived : (users : List<User>) -> Unit = {
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
                    saveUser(user,viewInstance.getContext())
                }
            }
        }
        repo.getAlbums(id).onObservData{
            run {
                user.albumsNumber = it.size
                albumsFinishFlage = true
                if (albumsFinishFlage) {
                    saveUser(user,viewInstance.getContext())
                }
            }
        }
    }

    private fun saveUser(user: User,context: Context) {
        context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE).edit().putString("User", Gson().toJson(user)).apply()
        toPostsActivity()
    }

    fun checkUserLogedIn() {
        getUserlogedIn(viewInstance.getContext())?.let { toPostsActivity() }
        loginProcess()
    }

    fun toPostsActivity() {
        HomeActivity.StartActivity(viewInstance.getContext())
    }

    fun toLoginActivity(context: Context){
        context.startActivity(Intent(context,LoginActivity::class.java))
    }

}

fun getUserlogedIn(context: Context): User? =
        Gson().fromJson<User>(context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE).getString("User",""), User::class.java)
