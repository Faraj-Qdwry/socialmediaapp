package com.icarasia.social.socialmediaapp.abstracts

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity

abstract class SocialMediaActivity : AppCompatActivity() {

    lateinit var dialog: ProgressDialog

    init {
        ValusesInjector.inject(this)
    }

    fun showDialog() = dialog.show()

    fun hideDialog() = dialog.hide()


}

