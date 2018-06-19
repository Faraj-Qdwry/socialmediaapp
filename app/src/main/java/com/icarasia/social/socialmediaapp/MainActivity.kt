package com.icarasia.social.socialmediaapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private lateinit var userCall: Call<ArrayList<User>>
    private lateinit var albumsCall: Call<ArrayList<albums>>
    private lateinit var todosCall: Call<ArrayList<todos>>
    lateinit var user : User
    lateinit var todos: todos
    lateinit var albums: albums

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loginButton.setOnClickListener {
            var name = nameEditText.text

            if (name.isNullOrEmpty()){
                nameEditText.error = getString(R.string.nameError)
            }else{

                userCall = RetrofitSectviceAPI.create().getUserDetails(name.toString())
                kickApiCall(userCall) { user = it[0]
                    nameTextView.text = user.username}

                todosCall = RetrofitSectviceAPI.create().getUserTodos(user.id)
                kickApiCall(todosCall) {todos = it[0]}


//                userCall.enqueue(object : Callback<User> {
//                    override fun onFailure(call: Call<User>?, t: Throwable?) {
//                        Log.d("Failure", "Failure")
//                    }
//                    override fun onResponse(call: Call<User>?, response: Response<User>?) {
//                        user = response?.body()!!
//                    }
//                })

                //




            }
        }


        //todosCall = RetrofitSectviceAPI.create().getUserAlbums(user.id)


        //albumsCall = RetrofitSectviceAPI.create().getUserTodos(user.id)


    }



    fun <T> kickApiCall(call: Call<T>, operation: (T)->Unit )
    {
        val callback: Callback<T> = object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable?) {
                failure(t)
            }
            override fun onResponse(call: Call<T>?, response: Response<T>){
                operation(response?.body()!!)
            }
        }
        call.enqueue(callback)
    }

    private fun failure(t: Throwable?) {
        Log.d("Failure",t.toString());
    }

}
