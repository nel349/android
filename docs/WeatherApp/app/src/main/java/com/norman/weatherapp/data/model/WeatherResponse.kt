package com.norman.weatherapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data class representing the weather API response
 * Data classes automatically generate: equals(), hashCode(), toString(), copy()
 */
data class WeatherResponse(
    @SerializedName("name")
    val cityName: String,

    @SerializedName("main")
    val main: Main,

    @SerializedName("weather")
    val weather: List<Weather>,

    @SerializedName("wind")
    val wind: Wind
)

/**
 * Main weather data (temperature, humidity, etc.)
 */
data class Main(
    @SerializedName("temp")
    val temperature: Double,  // Temperature in Kelvin

    @SerializedName("humidity")
    val humidity: Int  // Humidity percentage
)

/**
 * Weather description
 */
data class Weather(
    @SerializedName("description")
    val description: String  // e.g., "clear sky", "light rain"
)

/**
 * Wind data
 */
data class Wind(
    @SerializedName("speed")
    val speed: Double  // Wind speed in meter/sec
)
