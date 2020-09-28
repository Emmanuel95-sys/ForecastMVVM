package com.example.forecastmvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.forecastmvvm.data.db.entity.CurrentWeatherEntry
import com.example.forecastmvvm.data.db.entity.WeatherLocation

interface ForecastRepository {
    suspend fun getCurrentWeatherFromLocalDb() : LiveData<CurrentWeatherEntry>
    suspend fun getWeatherLocationFromLocalDb() : LiveData<WeatherLocation>
}