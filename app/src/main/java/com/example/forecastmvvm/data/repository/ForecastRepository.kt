package com.example.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.example.forecastmvvm.data.network.response.CurrentWeatherResponse

interface ForecastRepository {
    suspend fun getCurrentWeather() : LiveData<CurrentWeatherEntry>
}