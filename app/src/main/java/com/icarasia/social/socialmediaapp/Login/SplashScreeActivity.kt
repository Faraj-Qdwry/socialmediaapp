package com.icarasia.social.socialmediaapp.Login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaActivity
import kotlinx.android.synthetic.main.activity_splash_scree.*

class SplashScreeActivity : SocialMediaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_scree)

        this.supportActionBar!!.hide()

        var arr = ArrayList<TextView>()
        arr.add(textView2)
        arr.add(textView3)
        arr.add(textView4)
        arr.add(textView5)
        ToMainActivity(arr,0)
    }

    private fun ToMainActivity(arr: ArrayList<TextView>, j: Int) {

        object : CountDownTimer(((arr.size)*1500).toLong(), 1500)
        {
            var i = j
            override fun onTick(millisUntilFinished: Long) {
                    arr[i++].visibility = View.VISIBLE
            }

            override fun onFinish() {
                LoginActivity.startMainActivity(this@SplashScreeActivity)
                this@SplashScreeActivity.finish()
            }
        }.start()

    }
}
