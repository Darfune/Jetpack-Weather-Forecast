package com.example.jetpackweatherforecastapp.repository

import com.example.jetpackweatherforecastapp.data.WeatherDao
import com.example.jetpackweatherforecastapp.models.favorite.Favorite
import com.example.jetpackweatherforecastapp.models.unit.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDatabaseRepository @Inject constructor(private val weatherDao: WeatherDao){

    //Favorites
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()

    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)

    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)

    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()

    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)

    suspend fun getFavById(city: String): Favorite = weatherDao.getFavById(city)

    //Units
    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()

    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)

    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)

    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()

    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)

}