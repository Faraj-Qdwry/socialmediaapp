package com.icarasia.social.socialmediaapp.Login

import android.content.Context

interface viewContract {
    fun getUserNameFromEditText(): String
    fun showLoadingDialoge()
    fun hidLoadingDialoge()
    fun hidActionBar()
    fun loginButtunSetUp(process: (name: String) -> Unit)
    fun showErrorMessage()
    fun getContext(): Context
    fun toPostsActivity()
    fun saveUser(user: User)
    fun userLogedIn(): Boolean
}