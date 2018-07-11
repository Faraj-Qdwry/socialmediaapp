package com.icarasia.social.socialmediaapp.Home

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.ValusesInjector
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaActivity
import kotlinx.android.synthetic.main.activity_splash_scree.*

class SplashScreeActivity : SocialMediaActivity() {

    lateinit var arrayOfTextView: ArrayList<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_scree)

        this.supportActionBar?.hide()

        with(this){
            this.arrayOfTextView = ArrayList()
            with(arrayOfTextView) {
                    add(textView2)
                    add(textView3)
                    add(textView4)
                add(textView5)
            }
        }

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
