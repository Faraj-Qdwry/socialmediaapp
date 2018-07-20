package com.icarasia.social.socialmediaapp.UserDetalsFragmet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.os.Binder
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.icarasia.social.socialmediaapp.BR

import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.Home.HomeActivity
import com.icarasia.social.socialmediaapp.Login.*
import com.icarasia.social.socialmediaapp.databinding.FragmentUserDetailsBinding
import com.icarasia.social.socialmediaapp.extensions.ValusesInjector

class UserDetailsFragment : Fragment() , UserDetailsContract {

    lateinit var logout : Button
    lateinit var login : Button
    lateinit var userDetailsViewModel: UserDetailsViewModel
    lateinit var mBinder : FragmentUserDetailsBinding
    lateinit var addapter : UserListAdapter
    private val data: ObservableArrayList<UserDetails> = ObservableArrayList()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinder = DataBindingUtil.inflate(inflater,R.layout.fragment_user_details,container,false)
        with(mBinder.root){

            ValusesInjector.inject(this@UserDetailsFragment)

            with(getUser()){
                this?.let {
                    SetUpListView(findViewById(R.id.userListView) , this)
                    mBinder.userList = data
                }
                if (this==null){
                    findViewById<ConstraintLayout>(R.id.containerOfAllViews).visibility = View.GONE
                    findViewById<LinearLayout>(R.id.logedoutView).visibility = View.VISIBLE
                }
            }
            return this
        }

    }

    private fun getUser(): User? {
       return LoginActivity.getUserlogedIn(this@UserDetailsFragment.activity!!.baseContext)
    }

    private fun SetUpListView(listview: ListView,user: User) {

        var userList = userDetailsViewModel.getListFromUser(user)

        addapter = UserListAdapter(activity!!)

        //addapter.addData(userList)

        data.addAll(userList)

        listview.adapter = addapter
    }



    override fun logout() {
        LoginActivity.erraseUserDetails(getSharedPrefrences())
        context?.startActivity(
                Intent(context,HomeActivity::class.java))
    }

    private fun getSharedPrefrences(): SharedPreferences {
        return this@UserDetailsFragment.activity!!.
                baseContext.getSharedPreferences(sharedPreferencesName,Context.MODE_PRIVATE)
    }

    override fun login() {
        this.context?.let { it -> LoginActivity.start(it) }
    }

    companion object {
        @JvmStatic
        fun newInstance()= UserDetailsFragment()
    }

}







