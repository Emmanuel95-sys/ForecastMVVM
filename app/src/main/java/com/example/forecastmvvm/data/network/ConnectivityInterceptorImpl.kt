package com.example.forecastmvvm.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.forecastmvvm.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response


class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline()){
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }

    private fun isOnline() : Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
        //https://stackoverflow.com/questions/32547006/connectivitymanager-getnetworkinfoint-deprecated
//        val activeNetwork = context.getSystemService(Context.CONNECTIVITY_SERVICE).activeNetworkInfo
//        if (activeNetwork != null) {
//            // connected to the internet
//            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
//                // connected to wifi
//            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
//                // connected to mobile data
//            }
//        } else {
//            // not connected to the internet
//        }
    }
}