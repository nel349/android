package com.norman.weatherapp.data.api

import com.norman.weatherapp.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API service interface
 * Retrofit generates implementation at compile time
 */
interface WeatherApiService {

    /**
     * Get weather data for a city
     *
     * @param city City name (e.g., "London", "New York")
     * @param apiKey OpenWeatherMap API key
     * @return WeatherResponse with weather data
     *
     * suspend = This is a suspending function (can be paused/resumed)
     * Only callable from coroutines or other suspend functions
     */
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): WeatherResponse

    companion object {
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        // API_KEY is now accessed via BuildConfig.WEATHER_API_KEY
    }
}
