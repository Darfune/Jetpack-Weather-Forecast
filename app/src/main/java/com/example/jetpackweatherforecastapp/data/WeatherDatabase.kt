package com.example.jetpackweatherforecastapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetpackweatherforecastapp.models.favorite.Favorite
import com.example.jetpackweatherforecastapp.models.unit.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}