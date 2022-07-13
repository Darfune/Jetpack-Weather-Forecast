package com.example.jetpackweatherforecastapp.repository

import android.util.Log
import com.example.jetpackweatherforecastapp.data.DataOrException
import com.example.jetpackweatherforecastapp.models.location.Location
import com.example.jetpackweatherforecastapp.models.weather.Weather
import com.example.jetpackweatherforecastapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi){

    suspend fun getWeather(locationQuery: Location):DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(latitude = locationQuery[0].lat, longitude = locationQuery[0].lon)

        } catch (exc: Exception) {
            Log.d("Weather Repository", "getWeather: $exc")
            return DataOrException(e = exc)
        }
        return DataOrException(data = response)
    }
}