package com.example.jetpackweatherforecastapp.network


import android.util.Log
import com.example.jetpackweatherforecastapp.models.location.Location
import com.example.jetpackweatherforecastapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface LocationApi {

    @GET(value = "/geo/1.0/direct")
    suspend fun getLocation(
        @Query("q") location: String,
        @Query("appid") appid: String = Constants.API_KEY
    ): Location
}