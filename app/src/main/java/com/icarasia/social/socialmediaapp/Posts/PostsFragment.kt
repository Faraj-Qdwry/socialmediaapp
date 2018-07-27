package com.icarasia.social.socialmediaapp.Posts

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.databinding.DataBindingUtil
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
import com.icarasia.social.socialmediaapp.Dagger2.ValusesInjector
import com.icarasia.social.socialmediaapp.abstracts.SocialMediaNetworkFragment
import com.icarasia.social.socialmediaapp.databinding.FragmentPostsBinding

class  PostsFragment : SocialMediaNetworkFragment(R.id.drawer_layout) , PostViewContract {

    override fun AddapterItemCount(): Int {
        return postsAdapter.itemCount
    }

    override var logedinFlag = false
    lateinit var user: User
    lateinit var progressFragment: ProgressBar
    lateinit var confirmDelete : FloatingActionButton
    lateinit var cancleDelete : FloatingActionButton
    lateinit var deletionGroupRelativeLayout: RelativeLayout
    lateinit var selectionCounterTextView: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var postsViewModel : PostsViewModel
    val postsAdapter = PostsRecyclerViewAdapter()
    var curruntAdapterPosition = 0


    lateinit var mBinder : FragmentPostsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        LoginActivity.getUserlogedIn(this.activity.baseContext)?.let {
            user = it
            logedinFlag = true
        }

        ValusesInjector.inject(this@PostsFragment)

        mBinder = DataBindingUtil.inflate(inflater,R.layout.fragment_posts,container,false)

        mBinder.postViewModel = this.postsViewModel
        //var view = inflater.inflate(R.layout.fragment_posts, container, false)

        with(mBinder.root) {
            progressFragment = findViewById(R.id.progressBarFragment)
            recyclerView = findViewById(R.id.postsFragmentRecyclerView)
            recyclerView.setUp()
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
            if (AddapterItemCount() < postsViewModel.totalCount){
            postsViewModel.page++
            postsViewModel.callpost(postsViewModel.getCurrentPage(), postsViewModel.getItemsPerPageCount())
        } }

        postsAdapter.getClickObservable().subscribe { postsViewModel.clickHandle(it.first,it.second) }

        postsAdapter.getPositionObservable().subscribe { curruntAdapterPosition = it }

    }

    override fun addSinglePostToAddapter(post: Post) {
        postsViewModel.posts.add(post)
        //dataOBlist.add(post)
        //postsAdapter.insert(post,0)
        snakeMessage("Your post was added Successfully with the ID : ${post.id}", this.view!!)
    }

    override fun addPostsToAddapter(posts: ArrayList<Post>) {
        postsViewModel.posts.addAll(posts)
        //postsAdapter.addData(posts)
        //dataOBlist.addAll(posts)
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
        postsViewModel.callpost(postsViewModel.getCurrentPage(), postsViewModel.getItemsPerPageCount())
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
            postsAdapter.remove(postsViewModel.postsToremove(postsAdapter.getSelectedData()))
            postsAdapter.disableSelectionMode()
            dismissDeletionGroup()
        }
    }

    fun shouwUpDeletionGroup(){
        setUpdeletConfirmation()
        setUpdeletCancelation()
        deletionGroupRelativeLayout.visibility = View.VISIBLE
        postsAdapter.getCounterObservable().subscribe {selectionCounterTextView.text = it.toString()}
    }

    fun dismissDeletionGroup(){
        deletionGroupRelativeLayout.visibility = View.INVISIBLE
        selectionCounterTextView.text = "0"
    }

    override fun trigerDeletionMode() {
        with(getCurrentUser()){
            this?.let {
                postsAdapter.enableSelectionMode(object : PostsRecyclerViewAdapter.Criteria by postsViewModel{})
                shouwUpDeletionGroup()
            }
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

    override fun showLoginAlertDialoge() {
        AlertDialog.Builder(this@PostsFragment.context)
                .setTitle("you are not Loged In")
                .setPositiveButton("Login") { _ , _ ->
                    this@PostsFragment.context?.let { LoginActivity.start(it) }
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .show()
    }

    @SuppressLint("InflateParams")
    override fun showNewPostAlertDialoge() {
        val poster = layoutInflater.inflate(R.layout.post_lyout, null)
        val title = poster.findViewById<TextView>(R.id.postTitle)
        val body = poster.findViewById<TextView>(R.id.postBody)
        AlertDialog.Builder(this@PostsFragment.context)
                .setView(poster)
                .setPositiveButton("Post") { _, _ ->
                    if (!title.text.isNullOrBlank() && !body.text.isNullOrEmpty()) {
                        postsViewModel.sendApost(Post(userId = user.id,title = title.text.toString(),
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

    fun injectDeletionGroup(deletionGroup: RelativeLayout, deleteCancelation: FloatingActionButton,
                            selectionCounter: TextView, deleteConfirmation: FloatingActionButton) {
        deletionGroupRelativeLayout = deletionGroup
        cancleDelete = deleteCancelation
        selectionCounterTextView = selectionCounter
        confirmDelete = deleteConfirmation
    }

}