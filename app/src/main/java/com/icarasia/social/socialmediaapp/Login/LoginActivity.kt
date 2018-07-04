package com.icarasia.social.socialmediaapp.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.icarasia.social.socialmediaapp.API.DataSourece
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.DataModels.User
import com.icarasia.social.socialmediaapp.HomeActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

const val sharedPreferencesName : String = "UserDetails"

class LoginActivity : SocialMediaNetworkActivity(R.id.mainLoginActivity) {

    override fun onInternetDisconnected() {
        snakBar.show()
    }

    override fun onInternetConnected() {
        snakBar.dismiss()
    }

    private lateinit var user : User
    private var todoFinishFlage = false
    private var albumsFinishFlage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.supportActionBar?.hide()
        skipText.setOnClickListener { toPostsActivity() }
        getUserlogedIn(this)?.let { toPostsActivity() }
        login()
    }

    companion object {
        fun startMainActivity(context: Context){
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    fun login() {
        loginButton.setOnClickListener {
            with(nameEditText.text){
                if (this.isNullOrEmpty()) {
                    nameEditText.error = getString(R.string.nameError)
                } else {
                    showDialog()
                    RepoDataSource.getUserREPO(this.toString(),whenUserReceived)
                }
            }
        }
    }

    private val whenUserReceived : (users : List<User>) -> Unit = {
        if (it.isNotEmpty()) {
            this.user = it[0]
            callTodosAndAlbums(user.id)
        } else {
            hideDialog()
            nameEditText.error = getString(R.string.nameError)
        }
    }

    private fun callTodosAndAlbums(id: Int) {
        RepoDataSource.getToDosREPO(id){
            run {
                user.todosNumber = it.size
                todoFinishFlage = true
                if (albumsFinishFlage) {
                    saveUser(user)
                }
            }
        }
        RepoDataSource.getAlbumsREPO(id){
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
        getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE).edit().putString("User", Gson().toJson(user)).apply()
        toPostsActivity()
    }

    private fun toPostsActivity() {
        HomeActivity.StartActivity(this@LoginActivity)
        this.finish()
    }
}

fun getUserlogedIn(context: Context): User? = Gson().fromJson<User>(context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE).getString("User",""),User::class.java)

