package com.icarasia.social.socialmediaapp

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.icarasia.social.socialmediaapp.API.RetrofitSectviceAPI
import com.icarasia.social.socialmediaapp.API.kickApiCall
import com.icarasia.social.socialmediaapp.DataModels.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import com.google.gson.Gson
import android.content.Intent
import android.content.SharedPreferences
import com.icarasia.social.socialmediaapp.Posts.PostsActivity


class MainActivity : AppCompatActivity() {


    private lateinit var userCall: Call<ArrayList<User>>
    private lateinit var albumsCall: Call<ArrayList<album>>
    private lateinit var todosCall: Call<ArrayList<todo>>
    private lateinit var user : User
    private var todoFinishFlage = false
    private var albumsFinishFlage = false
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        skipText.setOnClickListener { toPostsActivity() }

        login()

        // TODO check if user already logedin

        //getSharedPreferences(Context.MODE_PRIVATE).getString("User","")

    }

    fun login(){
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Validating...")
        progressDialog.setCancelable(false)


        loginButton.setOnClickListener {
            var name = nameEditText.text
            if (name.isNullOrEmpty()) {
                nameEditText.error = getString(R.string.nameError)
            } else {
                userCall = RetrofitSectviceAPI.create().getUserDetails(name.toString())
                progressDialog.show()

                kickApiCall(userCall) {
                    if (it.size >= 1) {
                        user = it[0]
                        callTodos(user.id)
                        callAlbums(user.id)
                        toPostsActivity()
                    }
                    else {
                        progressDialog.dismiss()
                        nameEditText.error = getString(R.string.nameError)
                    }
                }
            }
        }

    }

    private fun callTodos(userId: Int) {
        todosCall = RetrofitSectviceAPI.create().getUserTodos(userId)
        kickApiCall(todosCall) {
            user.todosNumber = it.size
            todoFinishFlage = true
            if (albumsFinishFlage) saveUser(user)
        }
    }

    private fun callAlbums(userId: Int) {
        albumsCall = RetrofitSectviceAPI.create().getUserAlbums(userId)
        kickApiCall(albumsCall) {
            user.albumsNumber = it.size
            albumsFinishFlage = true
            if (albumsFinishFlage) saveUser(user)
        }
    }

    private fun saveUser(user: User) {
        getPreferences(Context.MODE_PRIVATE)
                .edit()
                .putString("User", Gson()
                        .toJson(user))
                .apply()
        toPostsActivity()
    }

    private fun toPostsActivity()
            {startActivity(Intent(this@MainActivity, PostsActivity::class.java));this@MainActivity.finish()}
}
