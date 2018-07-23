package com.icarasia.social.socialmediaapp.UserDetalsFragmet

import com.icarasia.social.socialmediaapp.Login.Address
import com.icarasia.social.socialmediaapp.Login.Company
import com.icarasia.social.socialmediaapp.Login.User
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.*

class UserDetailsViewModelTest {

    lateinit var view : UserDetailsContract
    lateinit var viewModel: UserDetailsViewModel

    @Before
    fun setUp() {
        view = mock(UserDetailsContract::class.java)
        viewModel = UserDetailsViewModel(view)
    }

    @Test
    fun loginUser() {
        viewModel.loginUser()
        verify(view).login()
    }

    @Test
    fun logoutUser() {
        viewModel.logoutUser()
        verify(view).logout()
    }

    @Test
    fun getListOfUserDetailsEmptyUser(){
        var arr = viewModel.getListFromUser(User())
        assert(arr[0].attribute == "Id" && arr[0].value == "0")
        assert(arr[2].attribute == "Name" && arr[2].value == "")
    }

    @Test
    fun getListOfUserDetailsFullUser(){
        var arr = viewModel.getListFromUser(
                User(20,"Bret","bratian","bret.com", Address(),
                        "7793746573","www.com", Company(),
                        20,30))

        assert(arr[0].attribute == "Id" && arr[0].value == "20")
        assert(arr[2].attribute == "Name" && arr[2].value == "Bret")
    }

}