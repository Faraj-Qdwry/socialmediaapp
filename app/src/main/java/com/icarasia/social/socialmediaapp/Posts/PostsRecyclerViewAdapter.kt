package com.icarasia.social.socialmediaapp.Posts

import android.content.Context
import android.graphics.Color
import android.support.annotation.LayoutRes
import android.support.design.circularreveal.cardview.CircularRevealCardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.icarasia.social.socialmediaapp.DataModels.Post
import com.icarasia.social.socialmediaapp.DataModels.PostContainer
import com.icarasia.social.socialmediaapp.DataModels.User
import com.icarasia.social.socialmediaapp.Login.getUserlogedIn
import com.icarasia.social.socialmediaapp.R


fun ViewGroup.inflate(@LayoutRes id: Int): View =
        LayoutInflater.from(context).inflate(id, this, false)

var deletionMod: Boolean = false
lateinit var thisUser: User
class PostsRecyclerViewAdapter(private val conext: Context,
                               private val removeAll: (ArrayList<PostContainer>) -> Unit,
                               private val loadMoreData: (Int, Int) -> Unit,
                               private val click: (Post, Int) -> Unit,
                               private val hidActionbar: () -> Unit,
                               private val showActionbar: () -> Unit,
                               private var deletionGroupRelativeLayout: RelativeLayout,
                               private var selectionCounterTextView: TextView)
    : RecyclerView.Adapter<PostsRecyclerViewAdapter.RecyclerViewHolder>() {

    private val postList: ArrayList<PostContainer> = ArrayList()
    private val totalCount = 500
    private var page = 1
    private var itemsPerPage = 20
    private var selectionCounter = mutableListOf(0)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecyclerViewHolder(parent.inflate(viewType))


    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        if (position == itemCount - 1 && itemCount < totalCount) {
            loadMoreData(++page, itemsPerPage)
        }
        holder.bindData(
                selectionCounter,
                activateDeletionMode,
                notifyChange,
                conext,
                postList[position]
        ) { user -> click(user, position) }
    }

    private val notifyChange: () -> Unit = {
        notifyDataSetChanged()
        selectionCounterTextView.text = "${selectionCounter[0]}"
    }

    //region Override + Sorting Methods
    override fun getItemViewType(position: Int) = R.layout.recycler_item_view

    override fun getItemCount() = postList.size

    fun sortAsc() {
        postList.sortBy { it.post.title }
        notifyDataSetChanged()
    }

    fun sortDec() {
        postList.sortByDescending { it.post.title }
        notifyDataSetChanged()
    }
    //endregion

    //region Public Methods
    fun addData(data: ArrayList<PostContainer>) = postList.addAll(data)


    fun setUpDeletionGroup(relativeLayout: RelativeLayout, selectionCounterText: TextView){
        deletionGroupRelativeLayout = relativeLayout
        selectionCounterTextView = selectionCounterText
        selectionCounterTextView.text = "${selectionCounter[0]}"
    }

    fun clearSelected() {
        var dataToremove = postList.filter { it.checked } as ArrayList<PostContainer>
        var filteredList = postList.filter { !it.checked } as ArrayList<PostContainer>
        postList.clear()
        postList.addAll(filteredList)
        deActivateDeletionMode()
        removeAll(dataToremove)
    }

    fun cancelSelection() {
        postList.forEach { it.checked = false }
        deActivateDeletionMode()
    }

    private val deActivateDeletionMode: () -> Unit = {
        deletionMod = false
        showActionbar()
        notifyChange()
        deletionGroupRelativeLayout.visibility = View.GONE
        selectionCounter[0] = 0
    }

    private val activateDeletionMode: () -> Unit = {
        deletionMod = true
        hidActionbar()
        notifyChange()
        deletionGroupRelativeLayout.visibility = View.VISIBLE
        selectionCounter[0] = 0
    }

    //endregion


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val postTitle: TextView = itemView.findViewById(R.id.itemTitle)
        private val postBody: TextView = itemView.findViewById(R.id.itemBody)
        private val cardView: CircularRevealCardView = itemView.findViewById(R.id.cardContainer)

        fun bindData(
                selectionCounter : MutableList<Int>,
                activateDeletionMode: () -> Unit,
                notify: () -> Unit,
                conext: Context,
                postContainer: PostContainer,
                click: (Post) -> Unit) {
            with(postContainer) {

                itemView.setOnClickListener {
                    if (deletionMod)
                        ifPostCanBeCheckedCheckit(postContainer, notify,selectionCounter)
                    else
                        click(post)
                }

                itemView.setOnLongClickListener {
                    longClikeed(activateDeletionMode, conext, postContainer, notify,selectionCounter)
                }

                postTitle.text = "${post.userId}  ${post.title}"
                postBody.text = post.body

                colorCard(cardView, postContainer)
            }


        }

        private fun longClikeed(
                activateDeletionMode: () -> Unit,
                conext: Context,
                postContainer: PostContainer,
                notify: () -> Unit,
                selectionCounter: MutableList<Int>): Boolean {

            with(getUserlogedIn(conext)) {
                if (this != null) {
                    activateDeletionMode()
                    thisUser = this
                    ifPostCanBeCheckedCheckit(postContainer, notify, selectionCounter)
                }
            }
            return true
        }

        private fun ifPostCanBeCheckedCheckit(postContainer: PostContainer,
                                              notify: () -> Unit,
                                              selectionCounter: MutableList<Int>) {
            if (postContainer.post.userId == thisUser.id) {
                if (!postContainer.checked){
                    postContainer.checked = true
                    selectionCounter[0] += 1
                }
            }
            colorCard(cardView, postContainer)
            notify()
        }


        private fun colorCard(cardView: CircularRevealCardView, container: PostContainer) {
            if (deletionMod) {
                if (container.checked)
                    cardView.setBackgroundColor(Color.CYAN)
                else {
                    if (container.post.userId == thisUser.id) {
                        cardView.setBackgroundColor(Color.WHITE)
                    } else
                        cardView.setBackgroundColor(Color.GRAY)
                }
            } else {
                cardView.setBackgroundColor(Color.WHITE)
            }
        }


    }
}