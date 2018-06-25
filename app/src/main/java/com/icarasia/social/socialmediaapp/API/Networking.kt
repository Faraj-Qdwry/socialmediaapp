package com.icarasia.social.socialmediaapp.API

import android.net.ConnectivityManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.net.NetworkInfo
import android.support.design.widget.Snackbar
import android.view.View


object NetworkUtil {

    fun getConnectivityStatus(context: Context): NetworkInfo? {
        with(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager){
            return activeNetworkInfo
        }
    }
}

class NetworkChangeReceiver(view: View) : BroadcastReceiver() {

    val snackbar : Snackbar = Snackbar.make(view,"Network is off !-!",Snackbar.LENGTH_INDEFINITE)

    override fun onReceive(context: Context, intent: Intent) {
        if (NetworkUtil.getConnectivityStatus(context)==null)
            snackbar.show()
        else
            snackbar.dismiss()
    }
}