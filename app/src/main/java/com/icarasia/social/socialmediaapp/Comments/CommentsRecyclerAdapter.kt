package com.icarasia.social.socialmediaapp.Comments

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.icarasia.social.socialmediaapp.R


fun ViewGroup.inflate(@LayoutRes id: Int): View = LayoutInflater.from(context).inflate(id, this, false)


class CommentsRecyclerViewAdapter : RecyclerView.Adapter<CommentsRecyclerViewAdapter.RecyclerViewHolder>() {

    private val commentsList: ArrayList<Comment> = ArrayList()

    //region Override Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecyclerViewHolder(parent.inflate(viewType))

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindData(commentsList[position])
    }

    override fun getItemViewType(position: Int) = R.layout.recycler_item_view

    override fun getItemCount() = commentsList.size
    //endregion

    //region Public Methods
    fun addData(data: ArrayList<Comment>) = commentsList.addAll(data)
    //endregion


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val commentName: TextView = itemView.findViewById(R.id.itemTitle)
        private val commentBody: TextView = itemView.findViewById(R.id.itemBody)

        fun bindData(commnet: Comment) {
            commentName.text = commnet.name
            commentBody.text = commnet.body
        }
    }
}