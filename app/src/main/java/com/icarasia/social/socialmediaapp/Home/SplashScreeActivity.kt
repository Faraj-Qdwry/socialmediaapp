package com.icarasia.social.socialmediaapp.Home

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.ValusesInjector
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaActivity

class SplashScreeActivity : SocialMediaActivity() {

    lateinit var arrayOfTextView: ArrayList<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_scree)

        this.supportActionBar?.hide()

        ValusesInjector.inject(this)

        ToMainActivity(arrayOfTextView,0)

    }

    private fun ToMainActivity(arr: ArrayList<TextView>, j: Int) {

        object : CountDownTimer(((arr.size)*1500).toLong(), 1500)
        {
            var i = j
            override fun onTick(millisUntilFinished: Long) {
                    arr[i++].visibility = View.VISIBLE
            }

            override fun onFinish() {
                toMainScreen()
            }
        }.start()

    }

    fun toMainScreen() {
        LoginActivity.start(this@SplashScreeActivity)
        this@SplashScreeActivity.finish()
    }

}
