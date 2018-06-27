package com.icarasia.social.socialmediaapp.abstracts

import android.content.Context
import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable

interface NetworkInformer {
    fun onConnected()
    fun onDisconnected()
}

abstract class SocialMediaNetworkFragment : SocialMediaFragment(), NetworkInformer {


    protected lateinit var compositeDisposable: CompositeDisposable

    private lateinit var networkActivity: SocialMediaNetworkActivity
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        networkActivity = context as SocialMediaNetworkActivity

        android.widget.ImageButton
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkActivity.register(this)
        compositeDisposable = CompositeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        networkActivity.unregister(this)
        compositeDisposable.clear()
    }

    abstract fun onInternetConnected()

    abstract fun onInternetDisconnected()

    override fun onConnected() {
        onInternetConnected()
    }

    override fun onDisconnected() {
        onInternetDisconnected()
    }

}