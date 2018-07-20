package com.icarasia.social.socialmediaapp.Posts

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.icarasia.social.socialmediaapp.R
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
    private val positionSubject = PublishSubject.create<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view =  layoutInflater.inflate(viewType, parent, false)
        return PostViewHolder(view)

//        var mbinder = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater,viewType,parent,false)
//        return    PostViewHolder(mbinder)
    }


    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int = R.layout.recycler_item_view

    override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int) {
        //reached Bottom
        if (position == itemCount - 1) {
            paginationSubject.onNext(position)
        }

        viewHolder.bind(data[position], position,
                selections, isEnableSelectionMode, clickSubject, criteria,
                counterSubject,notify)

        positionSubject.onNext(position)
    }


    fun getCounterObservable(): Observable<Int> = counterSubject as Observable<Int>
    fun getPaginationObservable(): Observable<Int> = paginationSubject as Observable<Int>
    fun getClickObservable(): Observable<Pair<Post, Int>> = clickSubject as Observable<Pair<Post, Int>>
    fun getPositionObservable(): Observable<Int> = positionSubject as Observable<Int>


    fun enableSelectionMode(criteria: Criteria) {
        isEnableSelectionMode = true
        selections.clear()
        this.criteria = criteria
        notifyDataSetChanged()
    }

    private val notify : ()-> Unit ={
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

    fun remove(data: ArrayList<Post>) {
        if (data.size>1)
            this.data.removeAll(data)
        else
            this.data.remove(data[0])
    }

    fun getSelectedData(): ArrayList<Post> {
        val list = ArrayList<Post>(selections.size())
        for (i in 0 until selections.size()) {
            val key = selections.keyAt(i)
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


 //   class PostViewHolder(val mbinder: ViewDataBinding) : RecyclerView.ViewHolder(mbinder.root) {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val postTitle: TextView = itemView.findViewById(R.id.itemTitle)
        private val postBody: TextView = itemView.findViewById(R.id.itemBody)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkboxSelection)

        @SuppressLint("SetTextI18n")
        fun bind(post: Post,
                 position: Int,
                 selections: SparseBooleanArray,
                 enableSelectionMode: Boolean,
                 clickSubject: PublishSubject<Pair<Post, Int>>?,
                 criteria: Criteria,
                 counterSubject: PublishSubject<Int>,
                 notify: () -> Unit
        ) {

            //mbinder.setVariable(BR.postView,post)

            postTitle.text = "${post.userId}  ${post.title}"
            postBody.text = post.body

            if (enableSelectionMode) {
                showOrhidCheckBox(position, selections, criteria, post)
            }
            else
                checkBox.visibility = View.GONE

            itemView.setOnClickListener{
                if (enableSelectionMode)
                    handelSelection(selections,position,criteria,post,counterSubject)
                else {
                    clickSubject?.onNext(Pair(post,shortClik))  // comment : understand !! and ?
                }
            }

            itemView.setOnLongClickListener {
                clickSubject?.onNext(Pair(post, longClick))
                notify()
                true
            }

        }

        private fun handelSelection(selections: SparseBooleanArray,
                                    position: Int,
                                    criteria: Criteria,
                                    post: Post,
                                    counterSubject: PublishSubject<Int>) {

            if (criteria.isOK(post)){
                if (!selections.get(position)){
                    checkBox.isChecked = true
                    selections.put(position,true)
                 }
                else {
                    selections.delete(position)
                    checkBox.isChecked = false
                }
                counterSubject.onNext(selections.size())
            }
        }

        fun showOrhidCheckBox(position: Int,
                              selections: SparseBooleanArray,
                              criteria: Criteria,
                              post: Post){

            if (criteria.isOK(post)) {
                checkBox.visibility = View.VISIBLE
                (selections.get(position)).let {
                    checkBox.isChecked = it
                }
            }
            else
                checkBox.visibility = View.GONE
        }
    }


}