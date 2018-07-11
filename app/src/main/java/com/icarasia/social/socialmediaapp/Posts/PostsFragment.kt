package com.icarasia.social.socialmediaapp.Posts

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.icarasia.social.socialmediaapp.Comments.CommentsActivityView
import com.icarasia.social.socialmediaapp.Login.User
import com.icarasia.social.socialmediaapp.Login.LoginActivity
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.ValusesInjector
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkFragment
import io.reactivex.Observable

class  PostsFragment : SocialMediaNetworkFragment(R.id.drawer_layout) , PostViewContract {

    override fun AddapterItemCount(): Int {
        return postsAdapter.itemCount
    }

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
    lateinit var postsPresenter : PostsPresenter
    val postsAdapter = PostAdapterOB()
    var curruntAdapterPosition = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        LoginActivity.getUserlogedIn(this.activity.baseContext)?.let {
            user = it
            logedinFlag = true
        }

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

    private fun RecyclerView.setUp() {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this@PostsFragment.context, LinearLayoutManager.VERTICAL, false)
        this.adapter = postsAdapter

        if (postsAdapter.isEnableSelectionMode){
            shouwUpDeletionGroup()
        }else{
            dismissDeletionGroup()
        }

        hideProgress()

        postsAdapter.getPaginationObservable().subscribe {
            if (AddapterItemCount() < postsPresenter.totalCount){
            postsPresenter.page++
            postsPresenter.callpost(postsPresenter.getCurrentPage(), postsPresenter.getItemsPerPageCount())
        } }

        postsAdapter.getClickObservable().subscribe { postsPresenter.clickHandle(it.first,it.second) }

        postsAdapter.getPositionObservable().subscribe { curruntAdapterPosition = it }

    }

    override fun addSinglePostToAddapter(post: Post) {
        postsAdapter.insert(post,0)
        snakeMessage(post.toString(), this.view)
        snakeMessage("Your post was added Successfully with the ID : ${post.id}", this.view!!)
    }

    override fun addPostsToAddapter(posts: ArrayList<Post>) {
        postsAdapter.add(posts)
    }

    override fun deletionConfirmingMessage() {
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
        Log.d("PostsFragment", "TRUE******************************************************")
        postsPresenter.callpost(postsPresenter.getCurrentPage(), postsPresenter.getItemsPerPageCount())
    }

    override fun onInternetDisconnected() {
        Log.d("PostsFragment", "FALSE******************************************************")
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

    fun shouwUpDeletionGroup(){
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

    override fun trigerDeletionMode() {
        with(getCurrentUser()){
            this?.let {
                postsAdapter.enableSelectionMode(object : PostAdapterOB.Criteria by postsPresenter{})
            }
            shouwUpDeletionGroup()
        }
    }

    override fun toCommentsActivity(post: Post) {
        startActivity(
                with(Intent(this@PostsFragment.context, CommentsActivityView::class.java)) {
                    putExtra("id", post.id.toString())
                    putExtra("userId", post.userId.toString())
                    putExtra("title", post.title)
                    putExtra("body", post.body)
                })
    }

    override fun getCurrentUser() : User?{
       return LoginActivity.getUserlogedIn(this@PostsFragment.networkActivity.baseContext)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PostsFragment()
    }

    private fun FloatingActionButton.setAddNewPost() {
        setOnClickListener {
            if (logedinFlag) {
                showNewPostAlertDialoge()
            } else {
                showLoginAlertDialoge()
            }
        }
    }

    private fun showLoginAlertDialoge() {
        AlertDialog.Builder(this@PostsFragment.context)
                .setTitle("you are not Loged In")
                .setPositiveButton("Login") { _ , _ ->
                    this@PostsFragment.context?.let { LoginActivity.start(it) }
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .show()
    }

    @SuppressLint("InflateParams")
    private fun showNewPostAlertDialoge() {
        val poster = layoutInflater.inflate(R.layout.post_lyout, null)
        val title = poster.findViewById<TextView>(R.id.postTitle)
        val body = poster.findViewById<TextView>(R.id.postBody)
        AlertDialog.Builder(this@PostsFragment.context)
                .setView(poster)
                .setPositiveButton("Post") { _, _ ->
                    if (!title.text.isNullOrBlank() && !body.text.isNullOrEmpty()) {
                        postsPresenter.sendApost(Post(userId = user.id,title = title.text.toString(),
                                body = body.text.toString()))
                    } else {
                        snakeMessage("Empty Post", view)
                    }
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .show()
    }

    fun snakeMessage(msg: String, view: View?) {
        view?.let { Snackbar.make(it, msg, Snackbar.LENGTH_LONG).show() }
    }

}