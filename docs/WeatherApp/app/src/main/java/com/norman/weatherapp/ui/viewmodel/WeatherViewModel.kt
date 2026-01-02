package com.norman.weatherapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norman.weatherapp.data.model.WeatherData
import com.norman.weatherapp.data.repository.Result
import com.norman.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Weather screen
 *
 * WITH HILT:
 * - @HiltViewModel tells Hilt this is a ViewModel
 * - @Inject constructor tells Hilt to provide dependencies
 * - No more AndroidViewModel (don't need Application context!)
 * - No more manual dependency creation
 *
 * BEFORE (without Hilt):
 * class WeatherViewModel(application: Application) : AndroidViewModel(application) {
 *     private val database = WeatherDatabase.getDatabase(application)
 *     private val weatherDao = database.weatherDao()
 *     private val repository = WeatherRepository(weatherDao)
 * }
 *
 * AFTER (with Hilt):
 * @HiltViewModel
 * class WeatherViewModel @Inject constructor(
 *     private val repository: WeatherRepository
 * ) : ViewModel() {
 *     // Hilt provides repository automatically!
 *     // Repository exposes cachedCities (no need to inject DAO directly)
 * }
 *
 * WHY VIEWMODEL?
 * - Survives configuration changes (screen rotation)
 * - Separates UI logic from business logic
 * - Testable (can inject fake repository!)
 * - Has its own lifecycle (longer than Activity)
 *
 * KEY CONCEPTS:
 * - viewModelScope: CoroutineScope tied to ViewModel lifecycle
 * - StateFlow: Hot flow for UI state (like LiveData but better)
 * - ViewModel is NOT destroyed on rotation!
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository  // Hilt provides this (single dependency!)
) : ViewModel() {

    // StateFlow - UI state
    // Private mutable version (only ViewModel can modify)
    private val _weatherState = MutableStateFlow<Result<WeatherData>>(Result.Idle)

    // Public read-only version (Activity/Fragment observes this)
    val weatherState: StateFlow<Result<WeatherData>> = _weatherState

    // Flow of all cached cities (for CityListFragment)
    // Exposed from repository - automatically updates when data changes
    val cachedCities = repository.cachedCities

    companion object {
        private const val TAG = "WeatherViewModel"
    }

    /**
     * Fetch weather for a city
     * Called from UI layer (Activity)
     */
    fun fetchWeather(city: String) {
        // Validate input
        if (city.isBlank()) {
            _weatherState.value = Result.Error("City name cannot be empty")
            return
        }

        // Launch coroutine in viewModelScope
        // viewModelScope is automatically cancelled when ViewModel is cleared
        viewModelScope.launch {
            Log.d(TAG, "Fetching weather for: $city")

            // Set loading state
            _weatherState.value = Result.Loading

            // Call repository (suspend function)
            val result = repository.getWeather(city)

            // Update state with result
            _weatherState.value = result

            Log.d(TAG, "Weather result: $result")
        }
    }

    /**
     * Called when ViewModel is about to be destroyed
     * Good place for cleanup (not needed here, viewModelScope handles it)
     */
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared (Activity permanently destroyed)")
        // viewModelScope is automatically cancelled here
    }
}
