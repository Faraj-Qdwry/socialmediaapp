package com.icarasia.social.socialmediaapp.Dagger2.daggerUserDetailsFragment

import com.icarasia.social.socialmediaapp.UserDetalsFragmet.UserDetailsFragment
import dagger.Component

@Component(modules = [UserDetailsMoule::class])
interface UserDetailsFragmentComponent {
    fun inject(userDetailsFragment: UserDetailsFragment)
}