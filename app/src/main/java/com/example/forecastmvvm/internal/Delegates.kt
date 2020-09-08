package com.example.forecastmvvm.internal

import kotlinx.coroutines.*
//fill this crazy code with comments

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T) : Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}