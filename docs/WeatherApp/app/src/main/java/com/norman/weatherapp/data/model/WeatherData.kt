package com.norman.weatherapp.data.model

/**
 * Domain model - UI-friendly weather data
 * Separating from API response allows us to transform/format data for UI
 */
data class WeatherData(
    val cityName: String,
    val temperatureCelsius: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double
) {
    /**
     * Extension function to format temperature for display
     */
    fun getFormattedTemperature(): String = "${temperatureCelsius.toInt()}°C"

    /**
     * Extension function to format humidity for display
     */
    fun getFormattedHumidity(): String = "$humidity%"

    /**
     * Extension function to format wind speed for display
     */
    fun getFormattedWindSpeed(): String = "${windSpeed.toInt()} km/h"
}

/**
 * Extension function to convert API response to domain model
 * This demonstrates extension functions - adding methods to existing classes
 */
fun WeatherResponse.toWeatherData(): WeatherData {
    // Convert Kelvin to Celsius
    val tempCelsius = main.temperature - 273.15

    // Convert m/s to km/h
    val windKmh = wind.speed * 3.6

    return WeatherData(
        cityName = cityName,
        temperatureCelsius = tempCelsius,
        description = weather.firstOrNull()?.description ?: "Unknown",
        humidity = main.humidity,
        windSpeed = windKmh
    )
}
