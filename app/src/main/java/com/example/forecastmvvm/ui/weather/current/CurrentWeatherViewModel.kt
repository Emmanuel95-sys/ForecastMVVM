package com.example.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.internal.UnitSystem

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC//get from setting later
    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazy {
        forecastRepository.getCurrentWeather(isMetric)
    }
    
}