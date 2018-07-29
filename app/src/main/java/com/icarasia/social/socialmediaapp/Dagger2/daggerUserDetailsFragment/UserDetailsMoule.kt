package com.icarasia.social.socialmediaapp.Dagger2.daggerUserDetailsFragment

import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsFragment
import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsViewModel
import dagger.Module
import dagger.Provides

@Module
class UserDetailsMoule(private val userDetailsFragment: UserDetailsFragment) {
    @Provides
    fun getUserDetailsViewModel(): UserDetailsViewModel {
        return UserDetailsViewModel(userDetailsFragment)
    }
}
