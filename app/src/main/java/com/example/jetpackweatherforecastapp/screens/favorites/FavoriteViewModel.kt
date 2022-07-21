package com.example.jetpackweatherforecastapp.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackweatherforecastapp.models.favorite.Favorite
import com.example.jetpackweatherforecastapp.repository.WeatherDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDatabaseRepository): ViewModel(){
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect { listOfFav ->
                    if (listOfFav.isEmpty())
                        Log.d("listOfFav", ": Empty Fav")
                    else {
                        _favList.value = listOfFav
                        Log.d("listOfFav", ": ${favList.value}")
                    }
                }
        }
    }

    fun insterFavorite(favorite: Favorite) = viewModelScope.launch { repository.insertFavorite(favorite) }

    fun updateFavorite(favorite: Favorite) = viewModelScope.launch { repository.updateFavorite(favorite) }

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch { repository.deleteFavorite(favorite) }

    fun deleleAllFavorite() = viewModelScope.launch { repository.deleteAllFavorites() }

    fun getFavById(city: String) = viewModelScope.launch { repository.getFavById(city) }

}