package com.example.jetpackweatherforecastapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun formatData(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM, d")
    val date = Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

