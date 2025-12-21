package com.norman.weatherapp.data.repository

import android.util.Log
import com.norman.weatherapp.BuildConfig
import com.norman.weatherapp.data.api.RetrofitInstance
import com.norman.weatherapp.data.local.WeatherDao
import com.norman.weatherapp.data.local.toWeatherData
import com.norman.weatherapp.data.local.toWeatherEntity
import com.norman.weatherapp.data.model.WeatherData
import com.norman.weatherapp.data.model.toWeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

/**
 * Repository pattern with offline-first caching
 *
 * OFFLINE-FIRST PATTERN:
 * 1. Try to fetch from API (network)
 * 2. On success: Cache it in Room + return data
 * 3. On failure: Try to return cached data (fallback)
 *
 * BENEFITS:
 * - Fresh data when online
 * - Cached data when offline
 * - Single source of truth (Repository)
 */
class WeatherRepository(
    private val weatherDao: WeatherDao  // Room DAO for caching
) {

    private val api = RetrofitInstance.api

    companion object {
        private const val TAG = "WeatherRepository"
    }

    /**
     * Fetch weather data with offline-first caching
     *
     * FLOW:
     * 1. Try API call (fresh data)
     * 2. Success? Cache it + return
     * 3. Failure? Check cache (offline fallback)
     *
     * @param city City name
     * @return Result<WeatherData> - Success with data or Error with message
     */
    suspend fun getWeather(city: String): Result<WeatherData> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Fetching weather from API for: $city")

                // 1. Try to fetch from API (fresh data)
                val response = api.getWeather(
                    city = city,
                    apiKey = BuildConfig.WEATHER_API_KEY
                )

                // 2. Convert API response to domain model
                val weatherData = response.toWeatherData()

                // 3. Cache the fresh data in Room
                weatherDao.insertWeather(weatherData.toWeatherEntity())
                Log.d(TAG, "Weather cached successfully for: $city")

                // 4. Return fresh data
                Result.Success(weatherData)

            } catch (e: Exception) {
                Log.e(TAG, "API call failed: ${e.message}", e)

                // 5. API failed - try to get cached data (offline fallback)
                try {
                    // Get cached weather from Room
                    // Flow.first() gets the current value (doesn't observe forever)
                    val cachedEntity = weatherDao.getWeatherForCity(city).first()

                    if (cachedEntity != null) {
                        Log.d(TAG, "Returning cached weather for: $city")
                        Result.Success(cachedEntity.toWeatherData())
                    } else {
                        Log.e(TAG, "No cached data available for: $city")
                        Result.Error("No internet and no cached data available")
                    }

                } catch (cacheError: Exception) {
                    Log.e(TAG, "Error reading cache: ${cacheError.message}", cacheError)
                    Result.Error(e.message ?: "Unknown error occurred")
                }
            }
        }
    }
}
