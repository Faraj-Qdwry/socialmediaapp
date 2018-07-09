package com.icarasia.social.socialmediaapp.Comments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkActivity
import com.icarasia.social.socialmediaapp.ValusesInjector
import kotlinx.android.synthetic.main.activity_post_commints.*

class CommintsActivityView : SocialMediaNetworkActivity(R.id.commentsActivity), CommentsViewCotract {


    lateinit var commentRecyclerView : RecyclerView
    lateinit var commentadapter : CommentsRecyclerViewAdapter
    lateinit var postId : String
    lateinit var commentsPresenter: CommentsPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_commints)
        this.supportActionBar?.title = "Comments"

        ValusesInjector.inject(this)
        commentRecyclerView.setup()

        commentsPresenter.callComments(Integer.parseInt(postId))
    }

    override fun hidProgressBar() {
        commentProgressBar.visibility = View.GONE
    }

    override fun showProgressBar() {
        commentProgressBar.visibility = View.VISIBLE
    }

    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(this@CommintsActivityView,LinearLayoutManager.VERTICAL,false)
        this.adapter = commentadapter
    }

    override fun addDataToAddapter(it: ArrayList<Comment>) {
        commentadapter.addData(it)
        commentadapter.notifyDataSetChanged()
    }

    override fun onInternetConnected() {
        snakBar.dismiss()
    }

    override fun onInternetDisconnected() {
        snakBar.show()
    }

}

