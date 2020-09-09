package com.example.forecastmvvm.data.network

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse
import retrofit2.http.Query

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String ,
        languageCode: String
    )
}