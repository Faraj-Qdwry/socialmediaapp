package com.icarasia.social.socialmediaapp.abstracts

import android.content.Context
import android.support.v4.app.Fragment
import com.icarasia.social.socialmediaapp.ValusesInjector

abstract class SocialMediaFragment : Fragment() {

    lateinit var activity: SocialMediaActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.activity = this.context as SocialMediaActivity
    }

    fun showDialog() {
        activity.showDialog()
    }

    fun hideDialog() {
        activity.hideDialog()
    }
}