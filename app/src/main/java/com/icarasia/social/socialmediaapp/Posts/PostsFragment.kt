package com.icarasia.social.socialmediaapp.Posts

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.icarasia.social.socialmediaapp.API.RetrofitSectviceAPI
import com.icarasia.social.socialmediaapp.API.kickApiCall
import com.icarasia.social.socialmediaapp.Comments.PostCommintsActivity
import com.icarasia.social.socialmediaapp.DataModels.Post

import com.icarasia.social.socialmediaapp.R
import retrofit2.Call

class PostsFragment : Fragment() {

    private val postsAdapter: PostsRecyclerViewAdapter by lazy { PostsRecyclerViewAdapter(callpost,click) }
    private lateinit var postCall: Call<ArrayList<Post>>
    private lateinit var progressFragment : ProgressBar


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        with(inflater.inflate(R.layout.fragment_posts, container, false)){
            findViewById<RecyclerView>(R.id.postsFragmentRecyclerView).setUp()
            findViewById<FloatingActionButton>(R.id.addNewPost).setAddNewPost()
            progressFragment = findViewById(R.id.progressBarFragment)
            callpost(1,20)
            return this
        }
    }




    private fun RecyclerView.setUp() {
        layoutManager = LinearLayoutManager(this@PostsFragment.context, LinearLayoutManager.VERTICAL, false)
        this.adapter = postsAdapter
    }

    private val callpost : (Int,Int)->Unit = { page,pageCount ->
        progressFragment.visibility = View.VISIBLE
        postCall = RetrofitSectviceAPI.create().getPosts(page,pageCount)
        kickApiCall(postCall) {
            postsAdapter.addData(it)
            postsAdapter.notifyDataSetChanged()
            progressFragment.visibility = View.GONE
        }
    }

    private val click: (Post, Int) -> Unit =
            { post , position ->
                with(post){
                startActivity(
                        with(Intent(this@PostsFragment.context, PostCommintsActivity::class.java)) {
                            putExtra("id", post.id.toString())
                            putExtra("userId", post.userId.toString())
                            putExtra("title", post.title)
                            putExtra("body", post.body)
                        })
                }
            }

    companion object {
        @JvmStatic
        fun newInstance() = PostsFragment()
    }

    private fun FloatingActionButton.setAddNewPost() {
        setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show() }

    }

}




