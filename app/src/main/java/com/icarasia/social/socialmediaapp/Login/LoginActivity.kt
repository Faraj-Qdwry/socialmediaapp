package com.icarasia.social.socialmediaapp.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.icarasia.social.socialmediaapp.HomeActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkActivity
import com.icarasia.social.socialmediaapp.abstracts.ValusesInjector
import kotlinx.android.synthetic.main.activity_main.*

const val sharedPreferencesName : String = "UserDetails"


class LoginActivity : SocialMediaNetworkActivity(R.id.mainLoginActivity) , viewContract {


    lateinit var loginPresenter : LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ValusesInjector.inject(this)

        with(loginPresenter){
            skipText.setOnClickListener { toPostsActivity() }
            checkUserLogedIn()
        }
    }

    override fun userLogedIn(): Boolean {
        getUserlogedIn(this)?.let { return true }
        return false
    }
    override fun getContext(): Context {
        return this
    }

    override fun loginButtunSetUp(process : (name : String)->Unit) {
        loginButton.setOnClickListener {
            process(getUserNameFromEditText())
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
        nameEditText.error = getString(R.string.nameError)
    }

    override fun hidActionBar() {
        this.supportActionBar?.hide()
    }

    override fun onInternetDisconnected() {
        snakBar.show()
    }

    override fun onInternetConnected() {
        snakBar.dismiss()
    }

    override fun toPostsActivity() {
        HomeActivity.StartActivity(getContext())
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