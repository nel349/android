package com.norman.weatherapp.data.repository

import android.util.Log
import com.norman.weatherapp.BuildConfig
import com.norman.weatherapp.data.api.RetrofitInstance
import com.norman.weatherapp.data.model.WeatherData
import com.norman.weatherapp.data.model.toWeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository pattern - Single source of truth for weather data
 * Separates data layer from UI layer
 */
class WeatherRepository {

    private val api = RetrofitInstance.api

    companion object {
        private const val TAG = "WeatherRepository"
    }

    /**
     * Fetch weather data for a city
     *
     * suspend function - can be paused/resumed
     * withContext(Dispatchers.IO) - runs on background thread for I/O
     *
     * @param city City name
     * @return Result<WeatherData> - Success with data or Error with message
     */
    suspend fun getWeather(city: String): Result<WeatherData> {
        return withContext(Dispatchers.IO) {  // Switch to IO thread
            try {
                Log.d(TAG, "Fetching weather for: $city")

                // Network call - suspend function
                // This is where the coroutine "pauses" and waits
                val response = api.getWeather(
                    city = city,
                    apiKey = BuildConfig.WEATHER_API_KEY
                )

                Log.d(TAG, "Weather fetched successfully: ${response.cityName}")

                // Convert API model to domain model
                val weatherData = response.toWeatherData()

                // Return success
                Result.Success(weatherData)

            } catch (e: Exception) {
                // Handle any errors (network, parsing, etc.)
                Log.e(TAG, "Error fetching weather: ${e.message}", e)
                Result.Error(e.message ?: "Unknown error occurred")
            }
        }  // Automatically switches back to caller's dispatcher when done
    }
}
