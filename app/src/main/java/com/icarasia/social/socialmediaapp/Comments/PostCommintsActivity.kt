package com.icarasia.social.socialmediaapp.Comments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.icarasia.social.socialmediaapp.API.onObservData
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkActivity
import kotlinx.android.synthetic.main.activity_post_commints.*

class PostCommintsActivity : SocialMediaNetworkActivity(R.id.commentsActivity) {
    override fun onInternetConnected() {
        snakBar.dismiss()
    }

    override fun onInternetDisconnected() {
        snakBar.show()
    }


    private val commentRecyclerView : RecyclerView by lazy { findViewById<RecyclerView>(R.id.commentsRecyclerView)}
    private val commentadapter by lazy { CommentsRecyclerViewAdapter() }
    private lateinit var postId : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_commints)

        this.supportActionBar!!.title = "Comments"

        with(intent){
            postTitleComment.text = getStringExtra("title")
            postBodyComment.text = getStringExtra("body")
            postId = getStringExtra("id")
        }

        commentRecyclerView.setup()

        callComments(postId)
    }

    private fun callComments(postId: String) {
        commentProgressBar.visibility = View.VISIBLE

        compositeDisposable.add(retrofitService.getCommetsForPost(postId).onObservData {
            commentadapter.addData(it)
            commentadapter.notifyDataSetChanged()
            commentProgressBar.visibility = View.GONE
        })


    }


    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(this@PostCommintsActivity,LinearLayoutManager.VERTICAL,false)
        this.adapter = commentadapter
    }
}

