package com.example.jetpackweatherforecastapp.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackweatherforecastapp.data.DataOrException
import com.example.jetpackweatherforecastapp.models.location.Location
import com.example.jetpackweatherforecastapp.models.location.LocationItem
import com.example.jetpackweatherforecastapp.models.weather.Weather
import com.example.jetpackweatherforecastapp.repository.LocationRepository
import com.example.jetpackweatherforecastapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository,
                                        private val locationRepository: LocationRepository): ViewModel() {

    suspend fun getLocationData(city: String): DataOrException<Location, Boolean, Exception> {
        return locationRepository.getLocation(cityQuery = city)
    }
    suspend fun getWeatherData(location: Location): DataOrException<Weather, Boolean, Exception> {
        return weatherRepository.getWeather(locationQuery = location)
    }
}