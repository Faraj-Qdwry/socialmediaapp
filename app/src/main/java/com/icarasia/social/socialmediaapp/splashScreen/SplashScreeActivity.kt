package com.icarasia.social.socialmediaapp.splashScreen

import android.os.Bundle
import android.os.CountDownTimer
import android.support.test.espresso.idling.CountingIdlingResource
import android.view.View
import com.icarasia.social.socialmediaapp.loginActivity.LoginActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaActivity
import kotlinx.android.synthetic.main.activity_splash_scree.*

class SplashScreeActivity : SocialMediaActivity() {

    lateinit var arrayOfTextView: ArrayList<View>

    var countIdling = CountingIdlingResource("IDOLER")

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
                    add(imageView2)
                add(talkiText)
            }
        }

        ToMainActivity(arrayOfTextView,0)

    }

    private fun ToMainActivity(arr: ArrayList<View>, j: Int) {

        countIdling.increment()
        object : CountDownTimer(((arr.size)*1500).toLong(), 1500)
        {
            var i = j
            override fun onTick(millisUntilFinished: Long) {
                    arr[i++].visibility = View.VISIBLE
            }

            override fun onFinish() {
                countIdling.decrement()
                toMainScreen()
            }
        }.start()

    }

    fun toMainScreen() {
        LoginActivity.start(this@SplashScreeActivity)
        this@SplashScreeActivity.finish()
    }

}
