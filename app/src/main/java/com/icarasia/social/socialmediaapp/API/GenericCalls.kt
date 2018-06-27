package com.icarasia.social.socialmediaapp.API

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//fun <T> kickApiCall(call: Call<T>, operation: (T) -> Unit) {
//    val callback: Callback<T> = object : Callback<T> {
//        override fun onFailure(call: Call<T>?, t: Throwable) {
//            failure(t)
//        }
//
//        override fun onResponse(call: Call<T>?, response: Response<T>) {
//            response.body()?.let {
//                operation(it)
//            }
//        }
//    }
//    call.enqueue(callback)
//}


fun <T> observData (call: Observable<T>, onSuccessOperation: (T)->Unit):Disposable{
    return   call
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
}


//
//fun <T> Observable<T>.observData(onSuccessOperation: (T) -> Unit): Disposable =
//        subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        { result -> onSuccessOperation(result); },
//                        { error -> failure(error); }
//                )



private fun failure(error: Throwable?) {
    Log.d("Observable_Error",error.toString())
}