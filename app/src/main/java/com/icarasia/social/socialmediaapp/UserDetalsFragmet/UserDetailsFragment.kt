package com.icarasia.social.socialmediaapp.UserDetalsFragmet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.Home.HomeActivity
import com.icarasia.social.socialmediaapp.Login.*

class UserDetailsFragment : Fragment() {

    lateinit var logout : Button
    lateinit var login : Button
    lateinit var ListView : ListView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        with(inflater.inflate(R.layout.fragment_user_details, container, false)){

            with(getUser()){
                this?.let {
                    SetUpListView(findViewById(R.id.userListView) , this)
                    logout = findViewById(R.id.logout)
                    logoutSetUp(this@UserDetailsFragment.activity!!.baseContext.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE))
                }
                if (this==null){
                    findViewById<ConstraintLayout>(R.id.containerOfAllViews).visibility = View.GONE
                    findViewById<LinearLayout>(R.id.logedoutView).visibility = View.VISIBLE
                    setUPLogin(findViewById(R.id.loginButtonFragment))
                }
            }

            return this
        }

    }

    private fun getUser(): User? {
       return LoginActivity.getUserlogedIn(this@UserDetailsFragment.activity!!.baseContext)
    }

    //TODO ,,, joking , nothing to do ,,, just look at my piece of art bellow

    private fun SetUpListView(listview: ListView?,user: User) {
        with(ArrayList<UserDetails>()){
            with(this){
                with(user){
                    add(UserDetails("Id", id.toString()))
                    add(UserDetails("Id", id.toString()))
                    add(UserDetails("User Name", username))
                    add(UserDetails("Name", name))
                    add(UserDetails("Email", email))
                    add(UserDetails("Phone", phone))
                    add(UserDetails("Website", website))
                    add(UserDetails("Albums", albumsNumber.toString()))
                    add(UserDetails("Todos", todosNumber.toString()))
                    add(UserDetails("Company", company.toString()))
                    add(UserDetails("Address", address.toString()))
                }
                listview?.adapter = UserListAdapter(activity!!, this)
            }
        }
    }

    private fun logoutSetUp(pref: SharedPreferences) {
        logout.setOnClickListener {
            erraseUserDetails(pref)

            context?.startActivity(
                    Intent(context,HomeActivity::class.java))
            //HomeActivity.StartActivity(this@UserDetailsFragment.activity!!.baseContext)
        }
    }




    private fun setUPLogin(bt : Button) {
        bt.setOnClickListener {
            this.context?.let { it1 -> LoginActivity.start(it1) }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance()= UserDetailsFragment()
    }
}

fun erraseUserDetails(pref: SharedPreferences) {
    pref.edit().putString("User","").apply()
}






