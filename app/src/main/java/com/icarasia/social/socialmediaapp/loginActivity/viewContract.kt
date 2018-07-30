package com.icarasia.social.socialmediaapp.loginActivity

interface viewContract {
    var internetStatuse : Boolean
    fun getUserNameFromEditText(): String
    fun showLoadingDialoge()
    fun hidLoadingDialoge()
    fun hidActionBar()
    fun toPostsActivity()
    fun saveUser(user: User)
    fun userLogedIn(): Boolean
    fun showErrorMessage()
}