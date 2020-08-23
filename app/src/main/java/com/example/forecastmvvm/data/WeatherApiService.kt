package com.example.forecastmvvm.data

import android.util.Log
import com.example.forecastmvvm.data.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "690abb47475a2b29c2c8af386452237b"
const val BASE_URL = "http://api.weatherstack.com/"
/**
 * query URL: http://api.weatherstack.com/ current? access_key=690abb47475a2b29c2c8af386452237b & query=Mexico %20 City
 * **/

interface WeatherApiService {
    //because we are using deferred as areturn type we need to specify
    //a factory call adapter
    @GET("current")
    fun getCurrentWeather(
        @Query("query") location: String //,
        //@Query("language") languageCode: String
    ): Deferred<CurrentWeatherResponse>

    companion object{
        operator fun invoke(): WeatherApiService{
            //put api key in every single call
            val requestInterceptor = Interceptor{ chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()
                Log.i("WeatherApiService", url.toString())
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }

}