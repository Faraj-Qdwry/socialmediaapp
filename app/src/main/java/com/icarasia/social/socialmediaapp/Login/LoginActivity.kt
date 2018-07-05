package com.icarasia.social.socialmediaapp.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.HomeActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkActivity
import kotlinx.android.synthetic.main.activity_main.*

const val sharedPreferencesName : String = "UserDetails"


class LoginActivity : SocialMediaNetworkActivity(R.id.mainLoginActivity) , viewContract {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(LoginPresenter(this, RepoDataSource)){
            skipText.setOnClickListener { toPostsActivity() }
            checkUserLogedIn()
        }
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

    companion object {
        fun start( context : Context){
            context.startActivity(Intent(context,LoginActivity::class.java))
        }
    }


}