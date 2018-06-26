package com.icarasia.social.socialmediaapp.UserDetalsFragmet

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.icarasia.social.socialmediaapp.DataModels.UserDetails
import com.icarasia.social.socialmediaapp.R

class UserListAdapter(private var activity: FragmentActivity, private var items: ArrayList<UserDetails>): BaseAdapter() {

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var txtComment: TextView? = null

        init {
            this.txtName = row?.findViewById(R.id.attribute)
            this.txtComment = row?.findViewById(R.id.value)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.user_list_item_view, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var userDto = items[position]
        viewHolder.txtName?.text = userDto.attribute
        viewHolder.txtComment?.text = userDto.value

        return view as View
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