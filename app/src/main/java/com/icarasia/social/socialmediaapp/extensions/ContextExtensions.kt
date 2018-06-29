package com.icarasia.social.socialmediaapp.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Context.isNetworkAvailable(): Boolean =
        with((getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo) {
    this?.let {
        return isConnected
    }

    return@with false
}

fun ViewGroup.inflate(@LayoutRes id: Int): View =
        LayoutInflater.from(context).inflate(id, this, false)