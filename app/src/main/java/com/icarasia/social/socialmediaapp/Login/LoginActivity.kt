package com.icarasia.social.socialmediaapp.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.icarasia.social.socialmediaapp.Home.HomeActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkActivity
import com.icarasia.social.socialmediaapp.ValusesInjector
import kotlinx.android.synthetic.main.activity_main.*

const val sharedPreferencesName : String = "UserDetails"


class LoginActivity : SocialMediaNetworkActivity(R.id.mainLoginActivity) , viewContract {

    override var internetStatuse: Boolean = false


    lateinit var loginPresenter : LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ValusesInjector.inject(this)

        skipText.setOnClickListener { toPostsActivity() }

        loginPresenter.checkUserLogedIn()
    }

    override fun userLogedIn(): Boolean {
        getUserlogedIn(this)?.let { return true }
        return false
    }

    override fun loginButtunSetUp() {
        loginButton.setOnClickListener {
            hidActionBar()
            with(getUserNameFromEditText()){
                if (this@with.isEmpty()) {
                    showErrorMessage()
                } else {
                    showLoadingDialoge()
                    loginPresenter.CallForUser(this)
                }
            }
        }
    }

    override fun showLoadingDialoge() {
        showDialog()
    }

    override fun hidLoadingDialoge() {
        hideDialog()
    }

    override fun getUserNameFromEditText() : String {
        return nameEditText.text.toString()
    }

    override fun showErrorMessage() {
        if(internetStatuse){
            nameEditText.error = getString(R.string.nameError)
        }else{
            nameEditText.error = "No Internet Connection"
        }
    }

    override fun hidActionBar() {
        this.supportActionBar?.hide()
    }

    override fun onInternetDisconnected() {
        snakBar.show()
        internetStatuse = false
    }

    override fun onInternetConnected() {
        snakBar.dismiss()
        internetStatuse = true
    }

    override fun toPostsActivity() {
        HomeActivity.StartActivity(this)
    }

    override fun saveUser(user: User){
        this.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE)
                .edit().putString("User", Gson().toJson(user)).apply()
    }


    companion object {
        fun start( context : Context){
            context.startActivity(Intent(context,LoginActivity::class.java))
        }

        fun getUserlogedIn(context: Context): User? =
                Gson().fromJson<User>(context.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE).getString("User",""), User::class.java)
    }


}