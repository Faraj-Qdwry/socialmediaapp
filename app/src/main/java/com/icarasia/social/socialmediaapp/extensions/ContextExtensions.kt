package com.icarasia.social.socialmediaapp.extensions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkAvailable(): Boolean =
        with((getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo) {
    this?.let {
        return isConnected && isAvailable
    }

    return@with false
}