package com.icarasia.social.socialmediaapp.commentsActivity

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.icarasia.social.socialmediaapp.BR
import com.icarasia.social.socialmediaapp.R

class CommentsRecyclerViewAdapter : RecyclerView.Adapter<CommentsRecyclerViewAdapter.RecyclerViewHolder>() {

    private val commentsList: ArrayList<Comment> = ArrayList()

    //region Override Methods
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerViewHolder {
        return RecyclerViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                        R.layout.recycler_item_view,
                        parent,
                        false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindData(commentsList[position])
    }

    override fun getItemViewType(position: Int) = R.layout.recycler_item_view

    override fun getItemCount() = commentsList.size

    fun addData(data: ArrayList<Comment>) {
        commentsList.addAll(data)
        notifyDataSetChanged()
    }

    class RecyclerViewHolder(private val mBiner : ViewDataBinding) : RecyclerView.ViewHolder(mBiner.root) {
        fun bindData(commnet: Comment) {
            mBiner.setVariable(BR.itemView,commnet)
            mBiner.executePendingBindings()
        }
    }

}

@BindingAdapter("dataComment")
fun RecyclerView.bindItem(commets : ArrayList<Comment>){
    (adapter as CommentsRecyclerViewAdapter).addData(commets)
}