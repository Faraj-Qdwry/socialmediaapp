package com.icarasia.social.socialmediaapp.LoginLogout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import com.icarasia.social.socialmediaapp.R
import kotlinx.android.synthetic.main.activity_splash_scree.*

class SplashScreeActivity : AppCompatActivity() {

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
                MainActivity.startMainActivity(this@SplashScreeActivity)
                this@SplashScreeActivity.finish()
            }
        }.start()

    }
}
