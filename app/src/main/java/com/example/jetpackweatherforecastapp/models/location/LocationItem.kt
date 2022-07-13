package com.example.jetpackweatherforecastapp.models.location

data class LocationItem(
    val country: String,
    var lat: Double,
    val lon: Double,
    val name: String,
    val state: String
)