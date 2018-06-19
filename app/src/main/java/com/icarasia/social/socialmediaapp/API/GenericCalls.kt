package com.icarasia.social.socialmediaapp.API

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> kickApiCall(call: Call<T>, operation: (T) -> Unit) {
    val callback: Callback<T> = object : Callback<T> {
        override fun onFailure(call: Call<T>?, t: Throwable) {
            failure(t)
        }

        override fun onResponse(call: Call<T>?, response: Response<T>) {
            response.body()?.let {
                operation(it)
            }
        }
    }
    call.enqueue(callback)
}

fun failure(t: Throwable) {
    Log.d("Failure", t.toString());
}