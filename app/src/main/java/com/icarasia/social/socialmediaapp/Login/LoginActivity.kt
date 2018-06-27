package com.icarasia.social.socialmediaapp.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.google.gson.Gson
import com.icarasia.social.socialmediaapp.API.RetrofitSectviceAPI
import com.icarasia.social.socialmediaapp.API.observData
import com.icarasia.social.socialmediaapp.DataModels.User
import com.icarasia.social.socialmediaapp.HomeActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

val sharedPreferencesName : String = "UserDetails"

class LoginActivity : SocialMediaNetworkActivity(){

    override fun onInternetDisconnected() {
        Snakbar.show()
    }

    override fun onInternetConnected() {
        Snakbar.dismiss()
    }

    private lateinit var retrofitService : RetrofitSectviceAPI

    private val Snakbar : Snackbar by lazy {
        Snackbar.make(findViewById(R.id.mainLoginActivity),"Not Connected",Snackbar.LENGTH_INDEFINITE)}

    private lateinit var user : User
    private var todoFinishFlage = false
    private var albumsFinishFlage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.supportActionBar!!.hide()

        skipText.setOnClickListener { toPostsActivity() }


        with(getUserlogedIn(this)){
            if (this==null)
                login()
            else
                toPostsActivity()
        }

    }



    companion object {
        fun startMainActivity(context: Context){
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    fun login() {

        retrofitService = RetrofitSectviceAPI.create()
        compositeDisposable = CompositeDisposable()


        loginButton.setOnClickListener {
            var name = nameEditText.text
            if (name.isNullOrEmpty()) {
                nameEditText.error = getString(R.string.nameError)
            } else {
                showDialog()


                compositeDisposable.add(observData(retrofitService.getUser(name.toString())) {
                    if (it.isNotEmpty()) {
                        this.user = it[0]
                        callTodos(this.user.id)
                        callAlbums(this.user.id)
                        toPostsActivity()
                    } else {
                        hideDialog()
                        nameEditText.error = getString(R.string.nameError)
                    }
                })



            }
        }

    }

    private fun callTodos(userId: Int) {
        compositeDisposable.add(observData(retrofitService.getTodos(userId)) {
            user.todosNumber = it.size
            todoFinishFlage = true
            if (albumsFinishFlage) {
                saveUser(user)
            }
        })

    }

    private fun callAlbums(userId: Int) {
        compositeDisposable.add(observData(retrofitService.getAlbums(userId)) {
            user.albumsNumber = it.size
            albumsFinishFlage = true
            if (albumsFinishFlage) {
                saveUser(user)
            }
        })


    }

    private fun saveUser(user: User) {
        getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE)
                .edit()
                .putString("User", Gson()
                        .toJson(user))
                .apply()
        compositeDisposable.clear()
        toPostsActivity()
    }

    private fun toPostsActivity() {
        HomeActivity.StartActivity(this@LoginActivity)
        this.finish()
    }

}

fun getUserlogedIn(context: Context): User? {
    var pref = context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE)
    var userJs = pref.getString("User","")
    var user = Gson().fromJson<User>(userJs,User::class.java)

    return if (user!=null){
        user
    }else{
        //Toast.makeText(context, "you are LogedOut", Toast.LENGTH_LONG).show()
        null
    }
}


