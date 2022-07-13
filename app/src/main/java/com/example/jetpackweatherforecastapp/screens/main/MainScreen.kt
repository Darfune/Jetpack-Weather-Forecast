package com.example.jetpackweatherforecastapp.screens

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetpackweatherforecastapp.data.DataOrException
import com.example.jetpackweatherforecastapp.models.location.Location
import com.example.jetpackweatherforecastapp.models.weather.Weather
import com.example.jetpackweatherforecastapp.screens.main.MainViewModel

@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel()) {
    ShowData(mainViewModel)
}

@Composable
fun ShowData(mainViewModel: MainViewModel) {

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

        if (weatherData.loading == true){
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            Text(text = "Main Screen ${weatherData.data!!}")
        }
    }


}