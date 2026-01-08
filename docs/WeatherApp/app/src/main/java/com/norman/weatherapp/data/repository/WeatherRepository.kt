package com.norman.weatherapp.data.repository

import android.util.Log
import com.norman.weatherapp.BuildConfig
import com.norman.weatherapp.data.api.WeatherApiService
import com.norman.weatherapp.data.local.WeatherDao
import com.norman.weatherapp.data.local.entities.WeatherHistoryEntity
import com.norman.weatherapp.data.local.toWeatherData
import com.norman.weatherapp.data.local.toWeatherEntity
import com.norman.weatherapp.data.model.WeatherData
import com.norman.weatherapp.data.model.toWeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

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
 *
 * WITH HILT:
 * - @Inject constructor tells Hilt to provide dependencies
 * - No need to create RetrofitInstance.api manually
 * - Hilt provides weatherDao and api automatically
 */
class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,  // Hilt provides this (from DatabaseModule)
    private val api: WeatherApiService    // Hilt provides this (from NetworkModule)
) {

    companion object {
        private const val TAG = "WeatherRepository"
    }

    /**
     * Flow of all cached cities from Room database
     * Exposed for ViewModel to observe
     */
    val cachedCities = weatherDao.getAllWeather()

    /**
     * Flow of all search history from Room database
     * Exposed for WeatherHistoryScreen to observe
     *
     * LEARNING: Multiple Flows from same Repository
     * - cachedCities = User's saved cities (CityListFragment)
     * - searchHistory = All searches made (WeatherHistoryScreen)
     * - Both update automatically when data changes
     */
    val searchHistory = weatherDao.getAllHistory()

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

    /**
     * Insert weather search into history
     *
     * LEARNING: Why separate method?
     * - ViewModel calls this after successful weather fetch
     * - Keeps history tracking separate from data fetching
     * - Easy to add/remove history tracking without changing getWeather()
     *
     * WHEN CALLED:
     * - ViewModel.fetchWeather() gets Result.Success
     * - ViewModel calls insertHistory() to log the search
     * - History appears in WeatherHistoryScreen
     *
     * @param weatherData The weather data to log in history
     */
    suspend fun insertHistory(weatherData: WeatherData) {
        withContext(Dispatchers.IO) {
            try {
                val historyEntity = WeatherHistoryEntity(
                    cityName = weatherData.cityName,
                    temperatureCelsius = weatherData.temperatureCelsius,
                    description = weatherData.description,
                    timestamp = System.currentTimeMillis()
                )
                weatherDao.insertHistory(historyEntity)
                Log.d(TAG, "History logged for: ${weatherData.cityName}")
            } catch (e: Exception) {
                Log.e(TAG, "Error inserting history: ${e.message}", e)
                // Don't propagate error - history is not critical
                // Main weather fetch should succeed even if history fails
            }
        }
    }

    /**
     * Delete all search history
     * Called from Settings or History screen "Clear History" button
     *
     * LEARNING: Destructive operations
     * - Should show confirmation dialog before calling
     * - Repository doesn't know about UI (no dialog here)
     * - ViewModel/Screen shows dialog, then calls this if confirmed
     */
    suspend fun deleteAllHistory() {
        withContext(Dispatchers.IO) {
            try {
                weatherDao.deleteAllHistory()
                Log.d(TAG, "History cleared")
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting history: ${e.message}", e)
                throw e  // Propagate error so UI can show error message
            }
        }
    }
}
