package com.icarasia.social.socialmediaapp.Posts

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.icarasia.social.socialmediaapp.DataModels.Post
import com.icarasia.social.socialmediaapp.R


fun ViewGroup.inflate(@LayoutRes id: Int): View = LayoutInflater.from(context).inflate(id, this, false)


class PostsRecyclerViewAdapter(private val loadMoreData : (Int, Int) -> Unit ,private val click: (Post, Int) -> Unit) : RecyclerView.Adapter<PostsRecyclerViewAdapter.RecyclerViewHolder>() {

    private val postList: ArrayList<Post> = ArrayList()
    private val totalCount = 500
    private var page = 1
    private var itemsPerPage = 20

    //region Override Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecyclerViewHolder(parent.inflate(viewType))

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        if (position == getItemCount() - 1 && getItemCount() < totalCount){
            loadMoreData(++page,itemsPerPage)
        }
        holder.bindData(postList[position]) { user -> click(user, position)}
    }

    override fun getItemViewType(position: Int) = R.layout.post_item_view

    override fun getItemCount() = postList.size
    //endregion

    //region Public Methods
    fun addData(data: ArrayList<Post>) = postList.addAll(data)
    //endregion


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val postTitle: TextView = itemView.findViewById(R.id.postTitle)
        private val postBody: TextView = itemView.findViewById(R.id.postBody)

        fun bindData(post: Post, click: (Post) -> Unit): Unit = with(post) {
            itemView.setOnClickListener {
                click(post)
            }
            postTitle.text = title
            postBody.text = body
        }
    }
}