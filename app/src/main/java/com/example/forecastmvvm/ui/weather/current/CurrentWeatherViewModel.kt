package com.example.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecastmvvm.data.provider.UnitProvider
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.internal.UnitSystem
import com.example.forecastmvvm.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()//get from setting later
    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeatherFromLocalDb()
    }

    val location by lazyDeferred {
        forecastRepository.getWeatherLocationFromLocalDb()
    }
}