package com.icarasia.social.socialmediaapp.Posts

import android.app.AlertDialog
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
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.icarasia.social.socialmediaapp.API.RetrofitSectviceAPI
import com.icarasia.social.socialmediaapp.API.observData
import com.icarasia.social.socialmediaapp.Comments.PostCommintsActivity
import com.icarasia.social.socialmediaapp.DataModels.Post
import com.icarasia.social.socialmediaapp.DataModels.PostContainer
import com.icarasia.social.socialmediaapp.DataModels.User
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.Login.getUserlogedIn
import com.icarasia.social.socialmediaapp.R
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call

class PostsFragment : Fragment() {

    val postsAdapter: PostsRecyclerViewAdapter by lazy {
        PostsRecyclerViewAdapter(this@PostsFragment.activity!!.baseContext,
                postsToremove,
                callpost,
                click,
                hidActionbar,
                showActionbar,
                deletionGroupRelativeLayout,
                selectionCounterTextView)}

    private var logedinFlag = false
  //  private lateinit var postCall: Call<ArrayList<Post>>
  //  private lateinit var deletePostCall: Call<Post>
  //  private lateinit var postCreateCall: Call<Post>
    private lateinit var compositeDisposable : CompositeDisposable
    private lateinit var retrofitService : RetrofitSectviceAPI

    private lateinit var progressFragment: ProgressBar
    private lateinit var user: User
    private lateinit var showActionbar : ()-> Unit
    private lateinit var hidActionbar: () -> Unit
    private lateinit var confirmDelete : FloatingActionButton
    private lateinit var cancleDelete : FloatingActionButton
    private lateinit var addNewPostb : FloatingActionButton
    private lateinit var deletionGroupRelativeLayout: RelativeLayout
    private lateinit var selectionCounterTextView: TextView
    lateinit var recyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        with(getUserlogedIn(this.activity!!.baseContext)){
            if (this!=null){
                user = this
                logedinFlag = true
            }
        }

        retrofitService = RetrofitSectviceAPI.create()
        compositeDisposable = CompositeDisposable()


        with(inflater.inflate(R.layout.fragment_posts, container, false)) {
            addNewPostb = findViewById(R.id.addNewPost)
            cancleDelete = findViewById(R.id.deleteCancelation)
            confirmDelete = findViewById(R.id.deleteConfirmation)
            progressFragment = findViewById(R.id.progressBarFragment)
            deletionGroupRelativeLayout = findViewById(R.id.deletionGroup)
            selectionCounterTextView = findViewById(R.id.selectionCounter)

            recyclerView = findViewById(R.id.postsFragmentRecyclerView)

            recyclerView.setUp()
            callpost(1, 20)
            addNewPostb.setAddNewPost()
            return this
        }

    }

    private fun setUpdeletConfirmation(postsAdapter: PostsRecyclerViewAdapter) {
        confirmDelete.setOnClickListener {
            postsAdapter.clearSelected()
        }
    }

    private fun setUpdeletCancelation(postsAdapter: PostsRecyclerViewAdapter) {
        cancleDelete.setOnClickListener {
            postsAdapter.cancelSelection()
        }
    }


    fun setShowHidActionBar(show : ()-> Unit,hid:()->Unit){
        showActionbar = show
        hidActionbar = hid
    }

    private fun RecyclerView.setUp() {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this@PostsFragment.context, LinearLayoutManager.VERTICAL, false)
        this.adapter = postsAdapter


        if (deletionMod){
            hidActionbar()
            deletionGroupRelativeLayout.visibility = View.VISIBLE
        }

        postsAdapter.setUpDeletionGroup(deletionGroupRelativeLayout,selectionCounterTextView)

        setUpdeletConfirmation(postsAdapter)
        setUpdeletCancelation(postsAdapter)

    }


    private val postsToremove: (ArrayList<PostContainer>) -> Unit = { listOfPostContainers ->
        var listofPosts = ArrayList<Post>()
        listOfPostContainers.forEach {listofPosts.add(it.post) }
        listofPosts.forEach { deletePost(it) }
    }

    fun deletePost(post : Post){
        compositeDisposable.add(observData(retrofitService.deletePosts(post.id)) {
            Toast.makeText(this.context,"Posts #${post.id} was deleted",Toast.LENGTH_LONG).show()
        })

        //compositeDisposable.clear()
    }


    private val callpost: (Int, Int) -> Unit = { page, pageCount ->
        progressFragment.visibility = View.VISIBLE

        compositeDisposable.add(observData(retrofitService.getPosts(page,pageCount)) {
            postsAdapter.addData(it.map { PostContainer(it, false) } as ArrayList<PostContainer>)
            postsAdapter.notifyDataSetChanged()
            progressFragment.visibility = View.GONE
        })

        //compositeDisposable.clear()

    }

    private val click: (Post, Int) -> Unit =
            { post, position ->
                with(post) {
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
            if (logedinFlag) {
                val poster = layoutInflater.inflate(R.layout.post_lyout, null)
                val title = poster.findViewById<TextView>(R.id.postTitle)
                val body = poster.findViewById<TextView>(R.id.postBody)
                AlertDialog.Builder(this@PostsFragment.context)
                        .setView(poster)
                        .setPositiveButton("Post") { dialog, p1 ->
                            if (!title.text.isNullOrBlank() && !body.text.isNullOrEmpty()) {
                                sendApost(title.text.toString(), body.text.toString(), view)
                            } else {
                                snakeMessage("Empty Post", view)
                            }
                        }
                        .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                        .show()
            } else {
                AlertDialog.Builder(this@PostsFragment.context)
                        .setTitle("you are not Loged In")
                        .setPositiveButton("Login") { dialog, _ ->
                            LoginActivity.startMainActivity(this@PostsFragment.context!!)
                        }
                        .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                        .show()
            }
        }
    }

    private fun sendApost(title: String, body: String, view: View) {
        var post = Post(user.id, 0, title, body)

        compositeDisposable.add(observData(retrofitService.createPost(post)) {
            var newpost = ArrayList<PostContainer>()
            newpost.add(PostContainer(it, false))
            postsAdapter.addData(newpost)
            postsAdapter.notifyDataSetChanged()
            snakeMessage(it.toString(), view)
            snakeMessage("Your post was added Successfully with the ID : ${it.id}", view)
        })

        //compositeDisposable.clear()
    }

    fun snakeMessage(msg: String, view: View) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }
}