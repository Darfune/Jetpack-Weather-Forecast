package com.example.jetpackweatherforecastapp.network

import com.example.jetpackweatherforecastapp.models.weather.Weather
import com.example.jetpackweatherforecastapp.models.weather.WeatherObject
import com.example.jetpackweatherforecastapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    @GET(value = "/data/2.5/forecast")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("cnt") cnt: Int = 7,
        @Query("appid") appid: String = Constants.API_KEY
    ) : Weather


}