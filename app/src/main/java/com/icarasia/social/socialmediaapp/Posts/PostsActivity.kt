package com.icarasia.social.socialmediaapp.Posts

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.icarasia.social.socialmediaapp.API.RetrofitSectviceAPI
import com.icarasia.social.socialmediaapp.API.kickApiCall
import com.icarasia.social.socialmediaapp.DataModels.Post
import com.icarasia.social.socialmediaapp.R
import kotlinx.android.synthetic.main.activity_posts.*
import retrofit2.Call
import android.view.View



class PostsActivity : AppCompatActivity() {

    private val postsAdapter: PostsRecyclerViewAdapter by lazy { PostsRecyclerViewAdapter(lunchNewCallForPosts,click) }
    private val recyclerView : RecyclerView by lazy { findViewById<RecyclerView>(R.id.postsRecyclerView) }
    private lateinit var postCall: Call<ArrayList<Post>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show() }

        recyclerView.setUp()

        lunchNewCallForPosts()

    }

    private val lunchNewCallForPosts = {
        progressBar.visibility = View.VISIBLE
        postCall = RetrofitSectviceAPI.create().getPosts()
        kickApiCall(postCall) {
            //Toast.makeText(this@PostsActivity,"Loading new Posts",Toast.LENGTH_SHORT).show()
                postsAdapter.addData(it)
                postsAdapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }
        }

    private fun RecyclerView.setUp() {
        layoutManager = LinearLayoutManager(this@PostsActivity, LinearLayoutManager.VERTICAL, false)
        this.adapter = postsAdapter
    }

    private val click: (Post, Int) -> Unit =
            { user , _ -> with(user){
        //SecondActivity.startActivity(this@MainActivity, login, avatar_url)}
    } }

}
