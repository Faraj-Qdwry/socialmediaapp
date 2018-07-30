package com.icarasia.social.socialmediaapp.userDetailsFragmet.DI

import com.icarasia.social.socialmediaapp.userDetailsFragmet.UserDetailsFragment
import dagger.Component

@Component(modules = [UserDetailsMoule::class])
interface UserDetailsFragmentComponent {
    fun inject(userDetailsFragment: UserDetailsFragment)
}