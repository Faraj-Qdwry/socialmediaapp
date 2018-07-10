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
import com.icarasia.social.socialmediaapp.Comments.CommintsActivityView
import com.icarasia.social.socialmediaapp.Login.User
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.ValusesInjector
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkFragment
import com.icarasia.social.socialmediaapp.extensions.onObservData
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class  PostsFragment : SocialMediaNetworkFragment(R.id.drawer_layout) , PostViewContract {



    var curruntAdapterPosition = 0
    var logedinFlag = false
    lateinit var user: User
    lateinit var progressFragment: ProgressBar
    lateinit var showActionbar : ()-> Unit
    lateinit var hidActionbar: () -> Unit
    lateinit var confirmDelete : FloatingActionButton
    lateinit var cancleDelete : FloatingActionButton
    lateinit var addNewPostb : FloatingActionButton
    lateinit var deletionGroupRelativeLayout: RelativeLayout
    lateinit var selectionCounterTextView: TextView
    lateinit var recyclerView: RecyclerView
    private val totalCount = 500
    var page = 1
    private var itemsPerPage = 20
    lateinit var postsPresenter : PostsPresenter
    val postsAdapter = PostAdapterOB()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        ValusesInjector.inject(this@PostsFragment)

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

    override fun addSinglePostToAddapter(post: Post) {
        postsAdapter.insert(post,0)
        snakeMessage(post.toString(), this.view)
        snakeMessage("Your post was added Successfully with the ID : ${post.id}", this.view!!)
    }

    override fun addPostsToAddapter(posts: ArrayList<Post>) {
        postsAdapter.add(posts)
    }

    override fun confirmDeletionMessage() {
        Toast.makeText(this.context,"Post(s) deleted", Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        progressFragment.visibility = View.VISIBLE
    }

    override fun hideProgress(){
        progressFragment.visibility = View.GONE
    }

    override fun onInternetConnected() {
        snakBar.dismiss()
        postsPresenter.callpost(1, 20)
    }

    override fun onInternetDisconnected() {
        snakBar.show()
    }

    private fun setUpdeletCancelation() {
        cancleDelete.setOnClickListener {
            postsAdapter.disableSelectionMode()
            dismissDeletionGroup()
        }
    }
    private fun setUpdeletConfirmation(){
        confirmDelete.setOnClickListener {
            postsAdapter.remove(postsPresenter.postsToremove(postsAdapter.getSelectedData()))
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

        hideProgress()

        postsAdapter.getPaginationObservable().subscribe(object : Observer<Int>{
                override fun onNext(position: Int) {

                    if (postsAdapter.itemCount < totalCount){
                        Toast.makeText(networkActivity.baseContext, page.toString(), Toast.LENGTH_SHORT).show()
                        page++
                        postsPresenter.callpost(page, itemsPerPage)
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

    private val click: (Post, Int) -> Unit = { post, clickType ->

        if (clickType== shortClik) {
            with(post) {
                startActivity(
                        with(Intent(this@PostsFragment.context, CommintsActivityView::class.java)) {
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
                setUpDeletionGroup()
            }
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
                                postsPresenter.sendApost(title.text.toString(), body.text.toString(),user)
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

    fun snakeMessage(msg: String, view: View?) {
        view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_LONG).show() }
    }

}