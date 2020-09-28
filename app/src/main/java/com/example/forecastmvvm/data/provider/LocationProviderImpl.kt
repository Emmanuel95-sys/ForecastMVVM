package com.example.forecastmvvm.data.provider

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.example.forecastmvvm.data.db.entity.WeatherLocation
import com.example.forecastmvvm.internal.LocationPermissionNotGrantedException
import com.example.forecastmvvm.internal.asDeferred
import com.example.forecastmvvm.internal.asDeferredAsync
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    context: Context
) : PreferenceProvider(context), LocationProvider {

    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        //hasDeviceLocationChanged is awaiting on getLastDeviceLocation()
        //and get last device location can throw a custom exception
        //we need to wrap it in a try catch block

        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        }catch (e : LocationPermissionNotGrantedException){
            false
        }
        
        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }


    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        //device location can not change if we are not using it
        if(!isUsingDeviceLocation())
            return false
        //if the last device location is null then the location haven't changed
        val deviceLocation = getLastDeviceLocationAsync().await()
        ?: return false
        //comparing doubles can not be done using "=="
        val comparisonThresholds = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat.toDouble()) > comparisonThresholds &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon.toDouble()) > comparisonThresholds
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation): Boolean {
        val customLocationName = getCustomLocationName()
        return customLocationName != lastWeatherLocation.name
    }

    private fun getCustomLocationName(): String? {
        return preferences.getString(CUSTOM_LOCATION, null)
    }

    override suspend fun getPreferredLocationString(): String {
        return "Mexico%20City"
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocationAsync() : Deferred<Location?>{
        return if (hasLocationPermission())
            //we are already checking if the permission is granted
            //so we can suppress the exception
            fusedLocationProviderClient.lastLocation.asDeferredAsync()
        else
            throw LocationPermissionNotGrantedException()
    }

    private fun hasLocationPermission() : Boolean{
        return ContextCompat.checkSelfPermission(appContext,
        android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}