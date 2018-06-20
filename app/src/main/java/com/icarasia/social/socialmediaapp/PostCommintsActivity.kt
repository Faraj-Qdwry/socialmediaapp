package com.icarasia.social.socialmediaapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_post_commints.*

class PostCommintsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_commints)

        with(intent){
            postTitleComment.text = getStringExtra("title")
            postBodyComment.text = getStringExtra("body")
        }

        //TODO receive all post details and lunch a call for coments

    }
}
