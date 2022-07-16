package com.example.jetpackweatherforecastapp.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.jetpackweatherforecastapp.R
import com.example.jetpackweatherforecastapp.data.DataOrException
import com.example.jetpackweatherforecastapp.models.location.Location
import com.example.jetpackweatherforecastapp.models.weather.Weather
import com.example.jetpackweatherforecastapp.models.weather.WeatherItem
import com.example.jetpackweatherforecastapp.utils.formatData
import com.example.jetpackweatherforecastapp.utils.formatDateTime
import com.example.jetpackweatherforecastapp.utils.formatDecimals
import com.example.jetpackweatherforecastapp.widgets.WeatherAppBar
import java.text.SimpleDateFormat

@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel()) {

    val locationData = produceState<DataOrException<Location, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getLocationData("Piraeus")
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

    val currentWeather = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${currentWeather.weather[0].icon}.png"

    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = formatData(currentWeather.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp))

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(text = formatDecimals(currentWeather.main.temp) + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold)
                Text(text = currentWeather.weather[0].description,
                    fontStyle = FontStyle.Italic)
            }
        }

        HumidityWindPressureRow(currentWeather)
        Divider()
        SunriseAndSunsetRow(data)
    }
}

@Composable
fun SunriseAndSunsetRow(weather: Weather) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp))
            Text(text = formatDateTime(weather.city.sunrise),
                style = MaterialTheme.typography.caption)
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunset),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp))
            Text(text = formatDateTime(weather.city.sunset),
                style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon", 
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.main.humidity}%",
                style = MaterialTheme.typography.caption)
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.main.pressure} psi",
                style = MaterialTheme.typography.caption)
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp))
            Text(text = "${weather.wind.speed} mph",
                style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "icon image",
        modifier = Modifier.size(80.dp))
}
