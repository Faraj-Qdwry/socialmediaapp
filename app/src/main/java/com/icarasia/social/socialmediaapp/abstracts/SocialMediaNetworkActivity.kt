package com.icarasia.social.socialmediaapp.abstracts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import com.icarasia.social.socialmediaapp.extensions.isNetworkAvailable
import io.reactivex.disposables.CompositeDisposable

abstract class SocialMediaNetworkActivity : SocialMediaActivity() {

    abstract fun onInternetConnected()

    abstract fun onInternetDisconnected()

    private val networkObserver = ArrayList<NetworkInformer>()

    private val networkReceiver = NetworkReceiver()

    protected lateinit var compositeDisposable: CompositeDisposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        compositeDisposable = CompositeDisposable()
    }



    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
        compositeDisposable.clear()
    }


    fun register(o: NetworkInformer) {
        networkObserver.add(o)
    }

    fun unregister(o: NetworkInformer) {
        networkObserver.remove(o)
    }


    private inner class NetworkReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                it.action?.let {
                    if (it == ConnectivityManager.CONNECTIVITY_ACTION) {
                        context?.let {
                            if (it.isNetworkAvailable()) {
                                onInternetConnected()
                                networkObserver.forEach { it.onConnected() }
                            } else {
                                onInternetDisconnected()
                                networkObserver.forEach { it.onDisconnected() }
                            }
                        }
                    }
                }
            }
        }
    }
}