package com.example.forecastmvvm.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

//abstract can not be instanciated by itself it needs a super class

abstract class PreferenceProvider(context: Context){
    private val appContext = context.applicationContext

    protected val preferences : SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

}