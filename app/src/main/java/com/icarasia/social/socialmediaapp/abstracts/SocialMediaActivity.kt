package com.icarasia.social.socialmediaapp.abstracts

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity

abstract class SocialMediaActivity : AppCompatActivity() {

    private val dialog: ProgressDialog by lazy {
        ProgressDialog(this).apply {
            setCancelable(false)
            setMessage("Loading please wait...")
            setTitle("Loading")
        }
    }

    fun showDialog() = dialog.show()

    fun hideDialog() = dialog.hide()


}

