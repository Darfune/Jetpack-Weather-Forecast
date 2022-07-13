package com.example.jetpackweatherforecastapp.screens.main

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetpackweatherforecastapp.data.DataOrException
import com.example.jetpackweatherforecastapp.models.location.Location
import com.example.jetpackweatherforecastapp.models.weather.Weather
import com.example.jetpackweatherforecastapp.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel()) {

    val locationData = produceState<DataOrException<Location, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getLocationData("London")
    }.value


    if (locationData.loading == true) {
        CircularProgressIndicator()
    } else if (locationData.data != null) {
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)) {
            value = mainViewModel.getWeatherData(locationData.data!!)
        }.value
        if (weatherData.data != null)
            MainScaffold(weather = weatherData.data!!, navController)
    }


}

@Composable
fun MainScaffold(weather: Weather, navigationController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(title = weather.city.name + " ,${weather.city.country}",
            navController = navigationController,
            elevation = 5.dp)
    }) {
        MainContent(data = weather)
    }
}

@Composable
fun MainContent(data: Weather) {
    Text(text = data.city.name)
}
