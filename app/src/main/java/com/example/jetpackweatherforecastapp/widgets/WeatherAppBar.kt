package com.example.jetpackweatherforecastapp.widgets

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetpackweatherforecastapp.navigation.WeatherNavigation
import com.example.jetpackweatherforecastapp.navigation.WeatherScreens

@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}){

    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        SHowSettingDropMenu(showDialog = showDialog, navController = navController)
    }


    TopAppBar (title = {
                       Text(text = title,
                           color = MaterialTheme.colors.onBackground,
                           style = TextStyle(fontWeight = Bold,
                               fontSize = 15.sp))
    },
        actions = {
                  if (isMainScreen){
                      IconButton(onClick = {
                          onAddActionClicked.invoke()
                      }) {
                          Icon(imageVector = Icons.Default.Search, contentDescription = "Search icons")
                      }
                      IconButton(onClick = {
                          showDialog.value = true
                      }) {
                          Icon(imageVector = Icons.Rounded.MoreVert , contentDescription = "More Icons")
                      }
                  } else { }
        },
        navigationIcon = {
                         if (icon != null) {
                             Icon(imageVector = icon, contentDescription = null,
                             tint = MaterialTheme.colors.onSecondary,
                             modifier = Modifier.clickable {
                                 onButtonClicked.invoke()
                             })
                         }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation)

}

@Composable
fun SHowSettingDropMenu(showDialog: MutableState<Boolean>, navController: NavController) {

    var expanded by remember {
        mutableStateOf(true)
    }

    val options = listOf("About", "Favorites", "Settings")

    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentSize(Alignment.TopEnd)
        .absolutePadding(top = 45.dp, right = 20.dp)) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        modifier = Modifier
            .width(140.dp)
            .background(MaterialTheme.colors.background)) {
            options.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Icon(imageVector =
                    when(text) {
                        "About" -> Icons.Default.Info
                        "Favorites" -> Icons.Default.FavoriteBorder
                        else -> Icons.Default.Settings
                                                  },
                        contentDescription = null,
                        tint = Color.LightGray)
                    
                    Text(text = text,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                when(text) {
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favorites" -> WeatherScreens.FavoritesScreen.name
                                    else -> WeatherScreens.SettingsScreen.name
                                }
                            )
                    }, fontWeight = FontWeight.W300
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopAppBar() {
    val navController = rememberNavController()
    WeatherAppBar(navController = navController)
}