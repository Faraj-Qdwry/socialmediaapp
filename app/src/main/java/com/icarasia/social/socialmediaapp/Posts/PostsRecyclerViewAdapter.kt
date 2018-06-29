package com.icarasia.social.socialmediaapp.Posts

import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.icarasia.social.socialmediaapp.DataModels.Post
import com.icarasia.social.socialmediaapp.R
import com.icarasia.social.socialmediaapp.extensions.inflate
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


const val shortClik = 1
const val longClick = 2

class PostAdapterOB : RecyclerView . Adapter <PostAdapterOB.PostViewHolder>() {

    interface Criteria {
        fun isOK(data: Post): Boolean
    }


    private var criteria: Criteria = object : Criteria {
        override fun isOK(data: Post) = false
    }

    private val data = ArrayList<Post>()
    val selections = SparseBooleanArray()
    var isEnableSelectionMode = false


    private val paginationSubject = PublishSubject.create<Int>()
    private val clickSubject = PublishSubject.create<Pair<Post, Int>>()

    private val counterSubject = PublishSubject.create<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = PostViewHolder(parent.inflate(viewType))

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int = R.layout.recycler_item_view

    override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int) {
        if (position == itemCount - 1)

            paginationSubject.onNext(position)

        viewHolder.bind(data[position], position, selections, isEnableSelectionMode, clickSubject, criteria,counterSubject)
    }


    fun getCounterObservable(): Observable<Int> = counterSubject as Observable<Int>
    fun getPaginationObservable(): Observable<Int> = paginationSubject as Observable<Int>
    fun getClickObservable(): Observable<Pair<Post, Int>> = clickSubject as Observable<Pair<Post, Int>>


    fun enableSelectionMode(criteria: Criteria) {
        isEnableSelectionMode = true
        this.criteria = criteria
        notifyDataSetChanged()
    }

    fun disableSelectionMode() {
        isEnableSelectionMode = false
        selections.clear()
        this.criteria = object : Criteria {
            override fun isOK(data: Post) = false
        }
        notifyDataSetChanged()
    }


    fun add(data: ArrayList<Post>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun insert(data: Post, position: Int) {
        this.data.add(position, data)
        notifyItemInserted(position)
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

    fun remove(data: Post) {
        this.data.remove(data)
    }

    fun remove(data: ArrayList<Post>) {
        this.data.removeAll(data)
    }

    fun getSelectedData(): ArrayList<Post> {
        val list = ArrayList<Post>(selections.size())
        for (i in 0 until selections.size()) {
            val key = selections.keyAt(i)
            if (selections[key])
                list.add(data[key])
        }

        return list
    }

    fun sortAsc() {
            data.sortBy { it.title }
            notifyDataSetChanged()
    }

    fun sortDec() {
            data.sortByDescending { it.title }
            notifyDataSetChanged()
    }



    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val postTitle: TextView = itemView.findViewById(R.id.itemTitle)
        private val postBody: TextView = itemView.findViewById(R.id.itemBody)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkboxSelection)

        fun bind(post: Post,
                 position: Int,
                 selections: SparseBooleanArray,
                 enableSelectionMode: Boolean,
                 clickSubject: PublishSubject<Pair<Post, Int>>?,
                 criteria: Criteria,
                 counterSubject: PublishSubject<Int>
        ) {

            postTitle.text = "${post.userId}  ${post.title}"
            postBody.text = post.body

            if (enableSelectionMode)
                showOrhidCheckBox(criteria,post)
            else
                checkBox.visibility = View.GONE

            itemView.setOnClickListener{
                if (enableSelectionMode)
                    handelSelection(selections,position,criteria,post,counterSubject)
                else {
                    clickSubject!!.onNext(Pair(post,shortClik))
                }
            }

            itemView.setOnLongClickListener {
                clickSubject!!.onNext(Pair(post, longClick))
                true
            }

        }

        private fun handelSelection(selections: SparseBooleanArray,
                                    position: Int,
                                    criteria: Criteria,
                                    post: Post,
                                    counterSubject: PublishSubject<Int>) {

            if (criteria.isOK(post)){
                if (selections.get(position) != null){
                    checkBox.isChecked = true
                    selections.put(position,true)
                 }else if(selections.get(position)){
                    selections.delete(position)
                    checkBox.isChecked = false
               }
                counterSubject.onNext(selections.size())
            }
        }

        fun showOrhidCheckBox(criteria: Criteria,
                              post: Post){

            if (criteria.isOK(post))
                    checkBox.visibility = View.VISIBLE
            else
                checkBox.visibility = View.GONE
        }
    }


}