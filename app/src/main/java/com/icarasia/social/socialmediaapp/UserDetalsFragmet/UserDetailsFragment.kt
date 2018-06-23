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
import com.google.gson.Gson
import com.icarasia.social.socialmediaapp.DataModels.User
import com.icarasia.social.socialmediaapp.DataModels.UserDetails
import com.icarasia.social.socialmediaapp.LoginLogout.MainActivity

import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.navigationActivity

class UserDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        with(inflater.inflate(R.layout.fragment_user_details, container, false)){

            with(context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE)){
                with(this.getString("User","")){
                    if (this.isNullOrEmpty()){
                        findViewById<ConstraintLayout>(R.id.containerOfAllViews).visibility = View.GONE
                        findViewById<LinearLayout>(R.id.logedoutView).visibility = View.VISIBLE
                    }else {
                        //TODO setUp Login
                        var listview = findViewById<ListView>(R.id.userListView)
                        SetUpListView(listview , Gson().fromJson(this,User::class.java))
                    }
                }
                setUPLogin(findViewById<Button>(R.id.loginButtonFragment))
                findViewById<Button>(R.id.logout).setUp(this)
            }
            return this
        }

    }

    private fun SetUpListView(listview: ListView?,user: User) {
        var arr = ArrayList<UserDetails>()

        arr.add(UserDetails("Id",user.id.toString()))
        arr.add(UserDetails("User Name",user.username))
        arr.add(UserDetails("Name",user.name))
        arr.add(UserDetails("Email",user.email))
        arr.add(UserDetails("Phone",user.phone))
        arr.add(UserDetails("Website",user.website))
        arr.add(UserDetails("Albums",user.albumsNumber.toString()))
        arr.add(UserDetails("Todos",user.todosNumber.toString()))
        arr.add(UserDetails("Company",user.company.toString()))
        arr.add(UserDetails("Address",user.address.toString()))


        var adapt = UserListAdapter(activity!!, arr)

        listview!!.adapter = adapt
    }

    private fun Button.setUp(pref: SharedPreferences) {
        setOnClickListener {
            pref.edit().putString("User","").apply()
            navigationActivity.StartActivity(this.context)
        }
    }


    private fun setUPLogin(bt : Button) {
        bt.setOnClickListener {
            MainActivity.startMainActivity(this.context!!)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance()= UserDetailsFragment()
    }
}






