package com.icarasia.social.socialmediaapp.Comments

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.icarasia.social.socialmediaapp.API.RetrofitSectviceAPI
import com.icarasia.social.socialmediaapp.API.observData
import com.icarasia.social.socialmediaapp.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_post_commints.*

class PostCommintsActivity : AppCompatActivity() {

    private val commentRecyclerView : RecyclerView by lazy { findViewById<RecyclerView>(R.id.commentsRecyclerView)}
    private val commentadapter by lazy { CommentsRecyclerViewAdapter() }
    private lateinit var postId : String

    private lateinit var compositeDisposable : CompositeDisposable
    private lateinit var retrofitService : RetrofitSectviceAPI

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

        retrofitService = RetrofitSectviceAPI.create()
        compositeDisposable = CompositeDisposable()

        callComments(postId)
    }

    private fun callComments(postId: String) {
        commentProgressBar.visibility = View.VISIBLE

        compositeDisposable.add(observData(retrofitService.getCommetsForPost(postId)) {
            commentadapter.addData(it)
            commentadapter.notifyDataSetChanged()
            commentProgressBar.visibility = View.GONE
        })

        //compositeDisposable.clear()

    }


    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(this@PostCommintsActivity,LinearLayoutManager.VERTICAL,false)
        this.adapter = commentadapter
    }
}

