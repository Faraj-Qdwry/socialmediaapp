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

class UserListAdapter(private var activity: FragmentActivity): BaseAdapter() {

    private var items = ArrayList<UserDetails>()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View, parent: ViewGroup): ViewHolder {
//        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        view = inflater.inflate(R.layout.user_list_item_view, null)
        var mBider = DataBindingUtil.inflate<UserListItemViewBinding>(
                LayoutInflater.from(parent.context),
                R.layout.user_list_item_view,
                parent,
                false)

        val viewHolder = ViewHolder(mBider,parent.context)

        viewHolder.bind(items[position])

        return viewHolder
    }



    fun addData(arr : ArrayList<UserDetails>){
        this.items.addAll(arr)
        notifyDataSetChanged()
    }

    override fun getItem(i: Int): UserDetails {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}

class ViewHolder(private val mBinder: ViewDataBinding,context: Context) : View(context) {
    fun bind(userDetails: UserDetails){
        mBinder.setVariable(BR.userD,userDetails)
        mBinder.executePendingBindings()
    }
}

@BindingAdapter("dataUser")
fun ListView.bindItem(data: ArrayList<UserDetails>){
    (adapter as UserListAdapter).addData(data)
}


//    var txtName: TextView? = null
//    var txtComment: TextView? = null
//
//    init {
//        this.txtName = row?.findViewById(R.id.attribute)
//        this.txtComment = row?.findViewById(R.id.value)
//    }
//}