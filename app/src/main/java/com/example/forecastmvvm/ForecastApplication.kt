package com.example.forecastmvvm

import android.app.Application
import androidx.preference.PreferenceManager
import com.example.forecastmvvm.data.db.ForecastDatabase
import com.example.forecastmvvm.data.network.*
import com.example.forecastmvvm.data.provider.LocationProvider
import com.example.forecastmvvm.data.provider.LocationProviderImpl
import com.example.forecastmvvm.data.provider.UnitProvider
import com.example.forecastmvvm.data.provider.UnitProviderImpl
import com.example.forecastmvvm.data.repository.ForecastRepository
import com.example.forecastmvvm.data.repository.ForecastRepositoryImpl
import com.example.forecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { (instance<ForecastDatabase>().currentWeatherDao()) }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance())}
        //be extremely careful with kodein
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton {WeatherNetworkDataSourceImpl(instance())}

        //add location provider and weather location
        bind<LocationProvider>() with singleton { LocationProviderImpl() }
        bind() from singleton { (instance<ForecastDatabase>().weatherLocationDao()) }
        bind<ForecastRepository>() with singleton {ForecastRepositoryImpl(instance(), instance(), instance(), instance())}
        //adding unit provider
        bind<UnitProvider>() with singleton {UnitProviderImpl(instance())}
        bind() from provider { CurrentWeatherViewModelFactory(instance(),instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        //default values for the preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)

    }
}