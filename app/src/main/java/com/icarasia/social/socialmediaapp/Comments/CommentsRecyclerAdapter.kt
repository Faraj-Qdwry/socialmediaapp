package com.icarasia.social.socialmediaapp.Comments

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.icarasia.social.socialmediaapp.DataModels.Comment
import com.icarasia.social.socialmediaapp.R


fun ViewGroup.inflate(@LayoutRes id: Int): View = LayoutInflater.from(context).inflate(id, this, false)


class CommentsRecyclerViewAdapter(private val click: (Comment, Int) -> Unit) : RecyclerView.Adapter<CommentsRecyclerViewAdapter.RecyclerViewHolder>() {

    private val commentsList: ArrayList<Comment> = ArrayList()

    //region Override Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecyclerViewHolder(parent.inflate(viewType))

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindData(commentsList[position]) { commnet -> click(commnet, position) }
    }

    override fun getItemViewType(position: Int) = R.layout.comment_item_view

    override fun getItemCount() = commentsList.size
    //endregion

    //region Public Methods
    fun addData(data: ArrayList<Comment>) = commentsList.addAll(data)
    //endregion


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val commentName: TextView = itemView.findViewById(R.id.commentName)
        private val commentBody: TextView = itemView.findViewById(R.id.commentBody)

        fun bindData(commnet: Comment, click: (Comment) -> Unit): Unit = with(commnet) {
            itemView.setOnClickListener {
                click(commnet)
            }
            commentName.text = name
            commentBody.text = body
        }
    }
}