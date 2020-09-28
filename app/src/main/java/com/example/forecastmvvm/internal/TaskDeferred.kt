package com.example.forecastmvvm.internal

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

fun <T> Task<T>.asDeferredAsync() : Deferred<T>{
    //completable deferred is up to us to tell this completable deferred when is completed
    //when is completed exceptionally
    val deferred = CompletableDeferred<T>()
    // because we are extending the task class we can access this which will be of type task
    this.addOnSuccessListener { result ->
        deferred.complete(result)
    }
    this.addOnFailureListener {exception ->
        deferred.completeExceptionally(exception)
    }
    return deferred
}