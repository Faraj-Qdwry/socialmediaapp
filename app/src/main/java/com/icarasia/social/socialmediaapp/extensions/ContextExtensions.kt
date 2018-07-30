package com.icarasia.social.socialmediaapp.extensions

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.support.annotation.LayoutRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.icarasia.social.socialmediaapp.application.App
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun Context.isNetworkAvailable(): Boolean =
        with((getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo) {
    this?.let {
        return isConnected
    }

    return@with false
}

fun ViewGroup.inflate(@LayoutRes id: Int): View =
        LayoutInflater.from(context).inflate(id, this, false)

fun <T> Observable<T>.onObservData(onSuccessOperation: (T) -> Unit) {


        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<T>{

                    private var compositDisposable : Disposable? = null

                    override fun onComplete() {
                        compositDisposable?.dispose()
                    }

                    override fun onSubscribe(d: Disposable) {
                        compositDisposable = d
                    }

                    override fun onNext(t: T) {
                        onSuccessOperation(t)
                    }

                    override fun onError(e: Throwable) {
                        failure(e)
                        compositDisposable?.dispose()
                    }
                })
}

private fun failure(error: Throwable?) {
    Log.d("Observable_Error",error.toString())
}

val Application.App : App
    get() =  this as App