package com.example.forecastmvvm.internal

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {
    private val gson = Gson()

    //list to String
    @TypeConverter
    fun listToObj(list: List<String>): String{
        return gson.toJson(list)
    }

    //string to list
    @TypeConverter
    fun objectsToList(value: String?): List<String>{
        if (value == null)
            return Collections.emptyList()

        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
}