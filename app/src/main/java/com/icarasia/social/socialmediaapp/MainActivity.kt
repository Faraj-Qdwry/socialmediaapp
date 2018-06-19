package com.icarasia.social.socialmediaapp

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


class MainActivity : AppCompatActivity() {


    private lateinit var userCall: Call<ArrayList<User>>
    private lateinit var albumsCall: Call<ArrayList<album>>
    private lateinit var todosCall: Call<ArrayList<todo>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton.setOnClickListener {
            var name = nameEditText.text
            if (name.isNullOrEmpty()) {
                nameEditText.error = getString(R.string.nameError)
            } else {
                userCall = RetrofitSectviceAPI.create().getUserDetails(name.toString())
                kickApiCall(userCall) {
                    if (it.size >= 1) callTodos(it[0]) else {
                        nameEditText.error = getString(R.string.nameError)
                    }
                }
            }
        }

        skipText.setOnClickListener { toPostsActivity() }
    }

    private fun callTodos(user: User) {
        todosCall = RetrofitSectviceAPI.create().getUserTodos(user.id)
        kickApiCall(todosCall) {
            user.todosNumber = it.size
            callAlbums(user)
        }
    }

    private fun callAlbums(user: User) {
        albumsCall = RetrofitSectviceAPI.create().getUserAlbums(user.id)
        kickApiCall(albumsCall) {
            user.albumsNumber = it.size
            saveUser(user)
        }
    }

    private fun saveUser(user: User) {
        getPreferences(Context.MODE_PRIVATE)
                .edit()
                .putString("User", Gson()
                        .toJson(user))
                .commit()
        toPostsActivity()
    }

    private fun toPostsActivity() = startActivity(Intent(this@MainActivity, PostsActivity::class.java))
}
