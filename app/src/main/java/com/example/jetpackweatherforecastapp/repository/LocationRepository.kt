package com.example.jetpackweatherforecastapp.repository

import android.util.Log
import com.example.jetpackweatherforecastapp.data.DataOrException
import com.example.jetpackweatherforecastapp.models.location.Location
import com.example.jetpackweatherforecastapp.network.LocationApi
import javax.inject.Inject

class LocationRepository @Inject constructor(private val api: LocationApi){
    suspend fun getLocation(cityQuery: String): DataOrException<Location, Boolean, Exception> {
        val response = try {
               api.getLocation(location = cityQuery)
        } catch (exc :Exception) {
            Log.d("Location Repository", "getLocation: $exc")
            return DataOrException(e = exc)
        }
        return DataOrException(data = response)
    }
}