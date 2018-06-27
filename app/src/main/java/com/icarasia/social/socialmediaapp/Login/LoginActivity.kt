package com.icarasia.social.socialmediaapp.Login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
import com.icarasia.social.socialmediaapp.API.*
import com.icarasia.social.socialmediaapp.DataModels.User
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.HomeActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call

val sharedPreferencesName : String = "UserDetails"

class LoginActivity : AppCompatActivity() {


   // private lateinit var userCall: Call<ArrayList<User>>
   // private lateinit var albumsCall: Call<ArrayList<Any>>
   // private lateinit var todosCall: Call<ArrayList<Any>>

    private lateinit var compositeDisposable : CompositeDisposable
    private lateinit var retrofitService : RetrofitSectviceAPI
    private var networkChangeReceiver : NetworkChangeReceiver? = null


    private lateinit var user : User
    private var todoFinishFlage = false
    private var albumsFinishFlage = false
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.supportActionBar!!.hide()

        skipText.setOnClickListener { toPostsActivity() }

        networkChangeReceiver = NetworkChangeReceiver(findViewById(R.id.mainLoginActivity))
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


        with(getUserlogedIn(this)){
            if (this==null)
                login()
            else
                toPostsActivity()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (networkChangeReceiver!=null)
            unregisterReceiver(networkChangeReceiver)

    }

    companion object {
        fun startMainActivity(context: Context){
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    fun login() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Validating...")
        progressDialog.setCancelable(false)

        retrofitService = RetrofitSectviceAPI.create()
        compositeDisposable = CompositeDisposable()


        loginButton.setOnClickListener {
            var name = nameEditText.text
            if (name.isNullOrEmpty()) {
                nameEditText.error = getString(R.string.nameError)
            } else {
                progressDialog.show()


                compositeDisposable.add(observData(retrofitService.getUser(name.toString())) {
                    if (it.isNotEmpty()) {
                        this.user = it[0]
                        callTodos(this.user.id)
                        callAlbums(this.user.id)
                        toPostsActivity()
                    } else {
                        progressDialog.dismiss()
                        nameEditText.error = getString(R.string.nameError)
                    }
                })



//                userCall = RetrofitSectviceAPI.create().getUserDetails(name.toString())
//                kickApiCall(userCall) {
//                    if (it.size >= 1) {
//                        this.user = it[0]
//                        callTodos(this.user.id)
//                        callAlbums(this.user.id)
//                        toPostsActivity()
//                    }
//                    else {
//                        progressDialog.dismiss()
//                        nameEditText.error = getString(R.string.nameError)
//                    }
//                }
//

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

//        todosCall = RetrofitSectviceAPI.create().getUserTodos(userId)
//        kickApiCall(todosCall) {
//            user.todosNumber = it.size
//            todoFinishFlage = true
//            if (albumsFinishFlage) {
//                saveUser(user)
//                compositeDisposable.clear()
//            }
//        }
    }

    private fun callAlbums(userId: Int) {
        compositeDisposable.add(observData(retrofitService.getAlbums(userId)) {
            user.albumsNumber = it.size
            albumsFinishFlage = true
            if (albumsFinishFlage) {
                saveUser(user)
            }
        })


//        albumsCall = RetrofitSectviceAPI.create().getUserAlbums(userId)
//        kickApiCall(albumsCall) {
//            user.albumsNumber = it.size
//            albumsFinishFlage = true
//            if (albumsFinishFlage) {
//                saveUser(user)
//                compositeDisposable.clear()
//            }
//        }
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


