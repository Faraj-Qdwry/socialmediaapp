package com.icarasia.social.socialmediaapp.userDetailsFragmet

import com.icarasia.social.socialmediaapp.loginActivity.User
import com.icarasia.social.socialmediaapp.loginActivity.UserDetails

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