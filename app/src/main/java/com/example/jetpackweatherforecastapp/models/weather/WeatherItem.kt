package com.example.jetpackweatherforecastapp.models.weather

data class WeatherItem(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Double,
    val weather: List<WeatherObject>,
    val wind: Wind
)