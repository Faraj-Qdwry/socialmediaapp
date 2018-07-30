package com.icarasia.social.socialmediaapp.userDetailsFragmet

import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.icarasia.social.socialmediaapp.BR
import com.icarasia.social.socialmediaapp.loginActivity.UserDetails
import com.icarasia.social.socialmediaapp.R

class UserDetailsAdapter: RecyclerView.Adapter<ViewHolder>() {

    private var userDetailsList = ArrayList<UserDetails>()

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ViewHolder{
        return ViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.user_list_item_view,
                parent,
                false))
    }

    override fun getItemCount(): Int = userDetailsList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(userDetailsList[position])
    }


    fun addData(arr : ArrayList<UserDetails>){
        this.userDetailsList.addAll(arr)
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
        (adapter as UserDetailsAdapter).addData(it)
    }
}
