package com.icarasia.social.socialmediaapp.UserDetalsFragmet

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.icarasia.social.socialmediaapp.BR
import com.icarasia.social.socialmediaapp.Login.UserDetails
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.databinding.UserListItemViewBinding

class UserCustomAdapter: RecyclerView.Adapter<ViewHolder>() {

    private var items = ArrayList<UserDetails>()

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ViewHolder{
        return ViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.user_list_item_view,
                parent,
                false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }


    fun addData(arr : ArrayList<UserDetails>){
        this.items.addAll(arr)
        notifyDataSetChanged()
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }
}

class ViewHolder(private val mBinder: ViewDataBinding) : RecyclerView.ViewHolder(mBinder.root) {
    fun bind(userDetails: UserDetails){
        mBinder.setVariable(BR.userD,userDetails)
        mBinder.executePendingBindings()
    }
}

@BindingAdapter("dataUser")
fun RecyclerView.bindItem(data: ArrayList<UserDetails>?){
    data?.let {
        (adapter as UserCustomAdapter).addData(it)
    }
}
