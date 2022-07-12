package com.example.jetpackweatherforecastapp.models.weather

data class WeatherObject(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)