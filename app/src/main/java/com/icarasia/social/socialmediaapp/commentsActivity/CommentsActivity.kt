package com.icarasia.social.socialmediaapp.commentsActivity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkActivity
import com.icarasia.social.socialmediaapp.databinding.ActivityPostCommintsBinding
import com.icarasia.social.socialmediaapp.commentsActivity.DI.CommentsActivityModule
import com.icarasia.social.socialmediaapp.commentsActivity.DI.DaggerCommentsActivityComponent
import com.icarasia.social.socialmediaapp.extensions.App
import kotlinx.android.synthetic.main.activity_post_commints.*
import javax.inject.Inject

class CommentsActivity : SocialMediaNetworkActivity(R.id.commentsActivity), CommentsViewCotract {

    @Inject
    lateinit var commentadapter : CommentsRecyclerViewAdapter
    @Inject
    lateinit var commentsPresenter: CommentsViewModel


    lateinit var postId : String
    lateinit var mBiner : ActivityPostCommintsBinding
    var data = ObservableArrayList<Comment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBiner = DataBindingUtil.setContentView(this,R.layout.activity_post_commints)
        mBiner.comments = data

        this.supportActionBar?.title = "Comments"
        setUpPostView(intent)

        DaggerCommentsActivityComponent
                .builder()
                .appComponent(this.application.App.appComponent)
                .commentsActivityModule(CommentsActivityModule(this))
                .build()
                .inject(this)

        commentsRecyclerView.setup()

        commentsPresenter.callComments(Integer.parseInt(postId))
    }

    private fun setUpPostView(intent: Intent) {
        with(intent){
            postTitleComment.text = getStringExtra("title")
            postBodyComment.text = getStringExtra("body")
            postId = getStringExtra("id")
        }
    }

    override fun hidProgressBar() {
        commentProgressBar.visibility = View.GONE
    }

    override fun showProgressBar() {
        commentProgressBar.visibility = View.VISIBLE
    }

    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(this@CommentsActivity,LinearLayoutManager.VERTICAL,false)
        this.adapter = commentadapter
    }

    override fun addDataToAddapter(it: ArrayList<Comment>) {
        data.addAll(it)
        //commentadapter.addData(it)
        //commentadapter.notifyDataSetChanged()
    }

    override fun onInternetConnected() {
        snakBar.dismiss()
    }

    override fun onInternetDisconnected() {
        snakBar.show()
    }

}

