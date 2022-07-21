package com.example.jetpackweatherforecastapp.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetpackweatherforecastapp.widgets.WeatherAppBar

@Composable
fun SettingsScreen(navController: NavHostController, settingsViewModel: SettingsViewModel = hiltViewModel()) {

    var unitToggleState by remember { mutableStateOf(false) }

    val measurementUnits = listOf("Imperial (F)", "Metric (C)")

    var choiceState by remember { mutableStateOf("") }

    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            icon = Icons.Default.ArrowBack,
            isMainScreen = false,
            navController = navController){
            navController.popBackStack()
        }
    }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        choiceState = if (unitToggleState) {
                            "Imperial (F)"
                        } else {
                            "Metric (C)"
                        }
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .clip(shape = RectangleShape)
                    .padding(5.dp)
                    .background(Color.Magenta.copy(alpha = 0.4f))
                ) {
                    Text(text = if (unitToggleState) "Fahrenheit °F" else "Celsius °C")
                }
            }
}
    }
}