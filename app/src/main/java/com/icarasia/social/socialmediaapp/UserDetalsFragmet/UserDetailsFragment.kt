package com.icarasia.social.socialmediaapp.UserDetalsFragmet

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.HomeActivity
import com.icarasia.social.socialmediaapp.Login.*

class UserDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        with(inflater.inflate(R.layout.fragment_user_details, container, false)){

            with(LoginActivity.getUserlogedIn(this@UserDetailsFragment.activity!!.baseContext)){
                if (this==null){
                    findViewById<ConstraintLayout>(R.id.containerOfAllViews).visibility = View.GONE
                    findViewById<LinearLayout>(R.id.logedoutView).visibility = View.VISIBLE
                    setUPLogin(findViewById(R.id.loginButtonFragment))
                }else{
                    var listview = findViewById<ListView>(R.id.userListView)
                    SetUpListView(listview , this)
                    findViewById<Button>(R.id.logout).setUp(this@UserDetailsFragment.activity
                    !!.baseContext.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE))
                }
            }
            return this
        }

    }

    private fun SetUpListView(listview: ListView?,user: User) {
        var arr = ArrayList<UserDetails>()

        with(arr){
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
        }

        listview?.adapter = UserListAdapter(activity!!, arr)
    }

    private fun Button.setUp(pref: SharedPreferences) {
        setOnClickListener {
            pref.edit().putString("User","").apply()
            HomeActivity.StartActivity(this.context)
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






