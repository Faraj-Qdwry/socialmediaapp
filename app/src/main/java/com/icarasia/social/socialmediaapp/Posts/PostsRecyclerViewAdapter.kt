package com.icarasia.social.socialmediaapp.Posts

import android.graphics.Color
import android.support.annotation.LayoutRes
import android.support.design.circularreveal.cardview.CircularRevealCardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.icarasia.social.socialmediaapp.DataModels.Post
import com.icarasia.social.socialmediaapp.DataModels.PostContainer
import com.icarasia.social.socialmediaapp.R


fun ViewGroup.inflate(@LayoutRes id: Int): View = LayoutInflater.from(context).inflate(id, this, false)

var deletionMod: Boolean = false

class PostsRecyclerViewAdapter(private val removeAll: (ArrayList<PostContainer>) -> Unit,private val loadMoreData: (Int, Int) -> Unit, private val click: (Post, Int) -> Unit)
    : RecyclerView.Adapter<PostsRecyclerViewAdapter.RecyclerViewHolder>() {

    private val postList: ArrayList<PostContainer> = ArrayList()
    private val totalCount = 500
    private var page = 1
    private var itemsPerPage = 20
    //var selectionMode: Boolean = false

    //region Override Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecyclerViewHolder(parent.inflate(viewType))

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        if (position == getItemCount() - 1 && getItemCount() < totalCount) {
            loadMoreData(++page, itemsPerPage)
        }
        holder.bindData(postList[position]) { user -> click(user, position) }
    }

    override fun getItemViewType(position: Int) = R.layout.recycler_item_view

    override fun getItemCount() = postList.size

    fun sortAsc() {
        postList.sortBy { it.post.title}
        notifyDataSetChanged()
    }

    fun sortDec() {
        postList.sortByDescending { it.post.title }
        notifyDataSetChanged()
    }
    //endregion

    //region Public Methods
    fun addData(data: ArrayList<PostContainer>) = postList.addAll(data)

    fun clearSelected() {
        var dataToremove = postList.filter { it.checked } as ArrayList<PostContainer>
        var filteredList = postList.filter { !it.checked } as ArrayList<PostContainer>
        postList.clear()
        postList.addAll(filteredList)
        notifyDataSetChanged()
        deletionMod = false
        removeAll(dataToremove)
    }
    //endregion


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val postTitle: TextView = itemView.findViewById(R.id.itemTitle)
        private val postBody: TextView = itemView.findViewById(R.id.itemBody)
        private val cardView: CircularRevealCardView = itemView.findViewById(R.id.cardContainer)

        fun bindData(postContainer: PostContainer, click: (Post) -> Unit): Unit =
                with(postContainer) {
                    itemView.setOnClickListener {
                        if (deletionMod) {
                            postContainer.checked = true
                        } else {
                            click(post)
                        }
                        colorCard(cardView, postContainer.checked)
                    }
                    itemView.setOnLongClickListener {
                        postContainer.checked = true
                        deletionMod = true
                        colorCard(cardView, true)
                        true
                    }
                    postTitle.text = post.title
                    postBody.text = post.body
                    colorCard(cardView, postContainer.checked)
                }

        fun allowDelete(){
            //TODO delete only those with users ID
        }

        private fun colorCard(cardView: CircularRevealCardView, checked: Boolean) {
            if (checked)
                cardView.setBackgroundColor(Color.CYAN)
            else
                cardView.setBackgroundColor(Color.WHITE)
        }
    }
}