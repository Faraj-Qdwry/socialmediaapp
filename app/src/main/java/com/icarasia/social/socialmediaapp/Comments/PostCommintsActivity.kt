package com.icarasia.social.socialmediaapp.Comments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.DataModels.Comment
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkActivity
import com.icarasia.social.socialmediaapp.extensions.onObservData
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

    private val dataSource = RepoDataSource()


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

        callComments(Integer.parseInt(postId))
    }

    private fun callComments(postId: Int) {
        commentProgressBar.visibility = View.VISIBLE

//        compositeDisposable.add(retrofitService.getCommetsForPost(postId).onObservData {
//            commentadapter.addData(it)
//            commentadapter.notifyDataSetChanged()
//            commentProgressBar.visibility = View.GONE
//        })

        dataSource.getCommentREPO(postId,whenCommentsReceved)

    }

    private val whenCommentsReceved : (arrayList: ArrayList<Comment>) -> Unit = {
        commentadapter.addData(it)
        commentadapter.notifyDataSetChanged()
        commentProgressBar.visibility = View.GONE
    }


    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(this@PostCommintsActivity,LinearLayoutManager.VERTICAL,false)
        this.adapter = commentadapter
    }
}

