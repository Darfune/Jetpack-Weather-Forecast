package com.example.jetpackweatherforecastapp.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackweatherforecastapp.models.unit.Unit
import com.example.jetpackweatherforecastapp.repository.WeatherDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDatabaseRepository): ViewModel() {
    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect { listOfUnits ->
                    if (listOfUnits.isEmpty())
                        Log.d("listOfUnits", ": Empty Units")
                    else {
                        _unitList.value = listOfUnits
                        Log.d("listOfFav", ": ${unitList.value}")
                    }
                }
        }
    }

    fun insterUnit(unit: Unit) = viewModelScope.launch { repository.insertUnit(unit) }

    fun updateUnit(unit: Unit) = viewModelScope.launch { repository.updateUnit(unit) }

    fun deleteUnit(unit: Unit) = viewModelScope.launch { repository.deleteUnit(unit) }

    fun deleleAllUnits() = viewModelScope.launch { repository.deleteAllUnits() }


}