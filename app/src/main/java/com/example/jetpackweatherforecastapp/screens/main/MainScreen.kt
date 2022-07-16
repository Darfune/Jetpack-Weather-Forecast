package com.example.jetpackweatherforecastapp.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
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

        Text(text = formatData(currentWeather.dt).split(":")[0],
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
        Text(text = "This Week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold)

        Surface(modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(size = 14.dp)) {
            LazyColumn(modifier = Modifier.padding(2.dp),
            contentPadding = PaddingValues(1.dp)){
                items(items = data.list ) { item: WeatherItem ->  
//                    Text(item.main.temp_max.toString())
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}

@Composable
fun WeatherDetailRow(weather: WeatherItem) {

    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"

    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = MaterialTheme.colors.primary) {

        Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
            Text(formatData(weather.dt)
                .split(",")[0] + formatData(weather.dt)
                .split(":")[1] + ":00",
            modifier = Modifier.padding(start = 5.dp))
            WeatherStateImage(imageUrl = imageUrl)
            Surface(modifier = Modifier.padding(0.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.secondaryVariant) {
                Text(weather.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption)
            }
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )){
                    append(formatDecimals(weather.main.temp_max) + "°")
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray
                )
                ){
                    append(formatDecimals(weather.main.temp_min) + "°")
                }
            })
        }
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
