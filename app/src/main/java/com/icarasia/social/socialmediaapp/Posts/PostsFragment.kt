package com.icarasia.social.socialmediaapp.Posts

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.icarasia.social.socialmediaapp.API.RepoDataSource
import com.icarasia.social.socialmediaapp.Comments.PostCommintsActivity
import com.icarasia.social.socialmediaapp.DataModels.Post
import com.icarasia.social.socialmediaapp.Login.User
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkFragment
import com.icarasia.social.socialmediaapp.extensions.onObservData
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class  PostsFragment : SocialMediaNetworkFragment() {

    override fun onInternetConnected() {
        callpost(1, 20)
    }

    override fun onInternetDisconnected() {}

    val postsAdapter :PostAdapterOB by lazy {  PostAdapterOB()}
    var curruntAdapterPosition = 0
    private var logedinFlag = false

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
    private val totalCount = 500
    var page = 1
    private var itemsPerPage = 20

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        with(LoginActivity.getUserlogedIn(this.activity!!.baseContext)){
            if (this!=null){
                user = this
                logedinFlag = true
            }
        }

        with(inflater.inflate(R.layout.fragment_posts, container, false)) {
            addNewPostb = findViewById(R.id.addNewPost)
            cancleDelete = findViewById(R.id.deleteCancelation)
            confirmDelete = findViewById(R.id.deleteConfirmation)
            progressFragment = findViewById(R.id.progressBarFragment)
            deletionGroupRelativeLayout = findViewById(R.id.deletionGroup)
            selectionCounterTextView = findViewById(R.id.selectionCounter)
            recyclerView = findViewById(R.id.postsFragmentRecyclerView)
            recyclerView.setUp()
            addNewPostb.setAddNewPost()
            return this
        }

    }

    private fun setUpdeletCancelation() {
        cancleDelete.setOnClickListener {
            postsAdapter.disableSelectionMode()
            dismissDeletionGroup()
        }
    }
    private fun setUpdeletConfirmation(){
        confirmDelete.setOnClickListener {
            postsAdapter.remove(postsToremove(postsAdapter.getSelectedData()))
            postsAdapter.disableSelectionMode()
            dismissDeletionGroup()
        }
    }

    fun setUpDeletionGroup(){
        hidActionbar()
        setUpdeletConfirmation()
        setUpdeletCancelation()
        deletionGroupRelativeLayout.visibility = View.VISIBLE
        postsAdapter.getCounterObservable().subscribe {selectionCounterTextView.text = it.toString()}
    }

    fun dismissDeletionGroup(){
        showActionbar()
        deletionGroupRelativeLayout.visibility = View.INVISIBLE
        selectionCounterTextView.text = "0"
    }


    fun setShowHidActionBar(show : ()-> Unit,hid:()->Unit){
        showActionbar = show
        hidActionbar = hid
    }

    private fun RecyclerView.setUp() {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this@PostsFragment.context, LinearLayoutManager.VERTICAL, false)
        this.adapter = postsAdapter

        if (postsAdapter.isEnableSelectionMode){
            setUpDeletionGroup()
        }else{
            dismissDeletionGroup()
        }

        progressFragment.visibility = View.GONE

        postsAdapter.getPaginationObservable().subscribe(object : Observer<Int>{
                override fun onNext(position: Int) {

                    if (postsAdapter.itemCount < totalCount){
                        Toast.makeText(networkActivity.baseContext, page.toString(), Toast.LENGTH_SHORT).show()
                        page++
                        callpost(page, itemsPerPage)
                    }
                }
                override fun onError(e: Throwable) {}
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
            })

        postsAdapter.getPositionObservable().subscribe { curruntAdapterPosition = it }

        postsAdapter.getClickObservable().subscribe(object : Observer<Pair<Post,Int>>{
            override fun onNext(t: Pair<Post, Int>) {
                click(t.first,t.second)
            }
            override fun onComplete() {}
            override fun onSubscribe(d: Disposable) {}
            override fun onError(e: Throwable) {}
        })

    }

    val callpost: (Int, Int) -> Unit = { page, pageCount ->
        progressFragment.visibility = View.VISIBLE
        RepoDataSource.getPosts(page,pageCount).onObservData { whePostsReceived(it) }
    }


    private val whePostsReceived : ( posts : ArrayList<Post>) -> ArrayList<Post> = {
        postsAdapter.add(it)
        progressFragment.visibility = View.GONE
        it
    }

    private val postsToremove: (ArrayList<Post>) -> ArrayList<Post> = { listOfPosts ->
        listOfPosts.forEach { deletePost(it) }
        listOfPosts
    }

    fun deletePost(post : Post){
        RepoDataSource.deletePosts(post.id).onObservData{
            Toast.makeText(this.context,"Posts #${it.id} was deleted",Toast.LENGTH_LONG).show()
        }

    }

    private val click: (Post, Int) -> Unit = { post, clickType ->

                if (clickType== shortClik) {
                        with(post) {
                            startActivity(
                                    with(Intent(this@PostsFragment.context, PostCommintsActivity::class.java)) {
                                        putExtra("id", post.id.toString())
                                        putExtra("userId", post.userId.toString())
                                        putExtra("title", post.title)
                                        putExtra("body", post.body)
                                    })
                        }
                }else if (clickType == longClick){
                    with(LoginActivity.getUserlogedIn(this@PostsFragment.networkActivity.baseContext)){
                        this?.let {
                            postsAdapter.enableSelectionMode(object : PostAdapterOB.Criteria{
                                override fun isOK(data: Post): Boolean {
                                    return id == data.userId
                                }
                            })
                        }
                    }
                    setUpDeletionGroup()
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
                        .setPositiveButton("Post") { _, _ ->
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
                        .setPositiveButton("Login") { _ , _ ->
                            this@PostsFragment.context?.let { LoginActivity.start(it) }
                        }
                        .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                        .show()
            }
        }
    }

    private fun sendApost(title: String, body: String, view: View) {
        var post = Post(user.id, 0, title, body)
        RepoDataSource.createPost(post).onObservData{whenPostAdded(it)}
    }

    private val whenPostAdded : (post : Post)->Unit = {
        postsAdapter.insert(it,0)
        snakeMessage(it.toString(), this.view!!)
        snakeMessage("Your post was added Successfully with the ID : ${it.id}", this.view!!)
    }

    fun snakeMessage(msg: String, view: View) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }

}