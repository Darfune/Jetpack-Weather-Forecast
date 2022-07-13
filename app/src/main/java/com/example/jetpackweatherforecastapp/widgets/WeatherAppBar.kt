package com.example.jetpackweatherforecastapp.widgets

import android.graphics.drawable.Icon
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//@Preview
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}){

    TopAppBar (title = {
                       Text(text = title)
    },
        actions = {
                  if (isMainScreen){
                      IconButton(onClick = { /*TODO*/ }) {
                          Icon(imageVector = Icons.Default.Search, contentDescription = "Search icons")
                      }
                      IconButton(onClick = { /*TODO*/ }) {
                          Icon(imageVector = Icons.Rounded.MoreVert , contentDescription = "More Icons")
                      }
                  } else { }
        },
        navigationIcon = {},
        backgroundColor = Color.Transparent,
        elevation = elevation)

}