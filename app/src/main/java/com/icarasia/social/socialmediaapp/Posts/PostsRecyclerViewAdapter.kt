package com.icarasia.social.socialmediaapp.Posts

import android.content.Context
import android.graphics.Color
import android.support.annotation.LayoutRes
import android.support.design.circularreveal.cardview.CircularRevealCardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.icarasia.social.socialmediaapp.DataModels.Post
import com.icarasia.social.socialmediaapp.DataModels.PostContainer
import com.icarasia.social.socialmediaapp.DataModels.User
import com.icarasia.social.socialmediaapp.Login.getUserlogedIn
import com.icarasia.social.socialmediaapp.R


fun ViewGroup.inflate(@LayoutRes id: Int): View =
     LayoutInflater.from(context).inflate(id, this, false)

    var deletionMod: Boolean = false
    lateinit var thisUser : User

    class PostsRecyclerViewAdapter(conext: Context,
                                   private val removeAll: (ArrayList<PostContainer>) -> Unit,
                                   private val loadMoreData: (Int, Int) -> Unit,
                                   private val click: (Post, Int) -> Unit,
                                   private val deletionActions: () -> Unit,
                                   hidActionbar: () -> Unit,
                                   showActionbar: () -> Unit)

        : RecyclerView.Adapter<PostsRecyclerViewAdapter.RecyclerViewHolder>() {

        private val postList: ArrayList<PostContainer> = ArrayList()
        private val totalCount = 500
        private var page = 1
        private var itemsPerPage = 20
        private val cont = conext
        private val deletionAction = deletionActions
        private val hidActBar = hidActionbar

        //region Override Methods
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                RecyclerViewHolder(parent.inflate(viewType))

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            if (position == getItemCount() - 1 && getItemCount() < totalCount) {
                loadMoreData(++page, itemsPerPage)
            }
            holder.bindData(
                    hidActBar,
                    deletionAction,
                    notify,
                    cont,
                    postList[position], {user -> click(user, position)}
            )
        }

        private val notify : ()-> Unit = {
            notifyDataSetChanged()
        }

        override fun getItemViewType(position: Int) = R.layout.recycler_item_view

        override fun getItemCount() = postList.size

        fun sortAsc() {
            postList.sortBy { it.post.title}
            notifyDataSetChanged()
        }

        fun sortDec() {
            postList.sortByDescending { it.post.title }
            notifyDataSetChanged()
        }
        //endregion

        //region Public Methods
        fun addData(data: ArrayList<PostContainer>) = postList.addAll(data)

        fun clearSelected() {
            var dataToremove = postList.filter { it.checked } as ArrayList<PostContainer>
            var filteredList = postList.filter { !it.checked } as ArrayList<PostContainer>
            postList.clear()
            postList.addAll(filteredList)
            notifyDataSetChanged()
            deletionMod = false
            removeAll(dataToremove)
        }
        //endregion


        class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val postTitle: TextView = itemView.findViewById(R.id.itemTitle)
            private val postBody: TextView = itemView.findViewById(R.id.itemBody)
            private val cardView: CircularRevealCardView = itemView.findViewById(R.id.cardContainer)

            fun bindData(
                    hidact : ()->Unit,
                    setDeletioModeActions : ()->Unit,
                    notify: () -> Unit,
                    conext: Context,
                    postContainer: PostContainer,
                    click: (Post) -> Unit)
            {
                with(postContainer) {

                    itemView.setOnClickListener {
                        if (deletionMod)
                            ifcanBeCheckedCheckit(conext,postContainer,notify)
                        else
                            click(post)
                    }

                    itemView.setOnLongClickListener {
                        longClikeed(hidact,conext,postContainer,notify, setDeletioModeActions)
                    }

                    postTitle.text = "${post.userId}  ${post.title}"
                    postBody.text = post.body

                    colorCard(cardView, postContainer)
                }


            }

            private fun longClikeed(
                    hidact: () -> Unit,
                    conext: Context,
                    postContainer: PostContainer,
                    notify: () -> Unit,
                    setDeletioModeActions: () -> Unit): Boolean {
                with (getUserlogedIn(conext)){
                    if (this!=null) {
                        hidact()
                        setDeletioModeActions()
                        deletionMod = true
                        thisUser = this
                        ifcanBeCheckedCheckit(conext,postContainer, notify)
                    }
                }
                return true
            }

            private fun ifcanBeCheckedCheckit(conext: Context,postContainer: PostContainer, notify: () -> Unit){
                if (postContainer.post.userId == thisUser.id){
                    postContainer.checked = true
                }
                colorCard(cardView, postContainer)
                notify()
            }

            private fun colorCard(cardView: CircularRevealCardView, container: PostContainer) {
                if (deletionMod){
                    if (container.checked)
                        cardView.setBackgroundColor(Color.CYAN)
                    else{
                        if (container.post.userId == thisUser.id){
                            cardView.setBackgroundColor(Color.WHITE)
                        }else
                            cardView.setBackgroundColor(Color.GRAY)
                    }
                }else{
                    cardView.setBackgroundColor(Color.WHITE)
                }
            }


        }
    }