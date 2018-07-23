package com.icarasia.social.socialmediaapp.UserDetalsFragmet

import android.databinding.BaseObservable
import com.icarasia.social.socialmediaapp.Login.User
import com.icarasia.social.socialmediaapp.Login.UserDetails

//: BaseObservable()

class UserDetailsViewModel(val view : UserDetailsContract)  {

    fun logoutUser(){
        view.logout()
    }

    fun loginUser(){
        view.login()
    }

    fun getListFromUser(user: User): ArrayList<UserDetails> {

        with(user){
            with(ArrayList<UserDetails>()){
                with(user){
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
                return this
            }
        }

    }

}