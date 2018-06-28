package com.icarasia.social.socialmediaapp.API

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.onObservData(onSuccessOperation: (T) -> Unit): Disposable =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> onSuccessOperation(result); },
                        { error -> failure(error); }
                )


private fun failure(error: Throwable?) {
    Log.d("Observable_Error",error.toString())
}