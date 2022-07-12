package com.example.jetpackweatherforecastapp.models.weather

data class Weather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherItem>,
    val message: Int
)