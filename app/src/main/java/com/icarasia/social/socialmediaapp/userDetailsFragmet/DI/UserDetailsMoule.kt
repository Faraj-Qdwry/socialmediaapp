package com.icarasia.social.socialmediaapp.userDetailsFragmet.DI

import com.icarasia.social.socialmediaapp.userDetailsFragmet.UserDetailsAdapter
import com.icarasia.social.socialmediaapp.userDetailsFragmet.UserDetailsFragment
import com.icarasia.social.socialmediaapp.userDetailsFragmet.UserDetailsViewModel
import dagger.Module
import dagger.Provides

@Module
class UserDetailsMoule(private val userDetailsFragment: UserDetailsFragment) {
    @Provides
    fun getUserDetailsViewModel(): UserDetailsViewModel {
        return UserDetailsViewModel(userDetailsFragment)
    }
    @Provides
    fun getUserDetailsAdapter(): UserDetailsAdapter {
        return UserDetailsAdapter()
    }
}
