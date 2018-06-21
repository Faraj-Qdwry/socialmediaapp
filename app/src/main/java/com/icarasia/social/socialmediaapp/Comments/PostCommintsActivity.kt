package com.icarasia.social.socialmediaapp.Comments

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.icarasia.social.socialmediaapp.API.RetrofitSectviceAPI
import com.icarasia.social.socialmediaapp.API.kickApiCall
import com.icarasia.social.socialmediaapp.DataModels.Comment
import com.icarasia.social.socialmediaapp.R
import kotlinx.android.synthetic.main.activity_post_commints.*
import retrofit2.Call

class PostCommintsActivity : AppCompatActivity() {

    private lateinit var commentCall : Call<ArrayList<Comment>>
    private val commentRecyclerView : RecyclerView by lazy { findViewById<RecyclerView>(R.id.commentsRecyclerView)}
    private val commentadapter by lazy { CommentsRecyclerViewAdapter(click) }
    private lateinit var postId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_commints)

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
        commentCall = RetrofitSectviceAPI.create().getCommetsForPost(postId)
        kickApiCall(commentCall) {
            commentadapter.addData(it)
            commentadapter.notifyDataSetChanged()
            commentProgressBar.visibility = View.GONE
        }
    }


    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(this@PostCommintsActivity,LinearLayoutManager.VERTICAL,false)
        this.adapter = commentadapter
    }

    private val click: (Comment, Int) -> Unit =
            { commnet , position -> with(commnet){
//                startActivity(
//                        with(Intent(this@PostCommintsActivity, PostCommintsActivity::class.java)) {
//                            putExtra("id", post.id)
//                            putExtra("userId", post.userId)
//                            putExtra("title", post.title)
//                            putExtra("body", post.body)
//                        })
            }
            }
}

