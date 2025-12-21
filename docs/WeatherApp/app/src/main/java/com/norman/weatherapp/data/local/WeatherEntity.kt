package com.norman.weatherapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.norman.weatherapp.data.model.WeatherData

/**
 * Room Entity - Represents a table in the database
 *
 * KEY ANNOTATIONS:
 * @Entity(tableName = "weather") - Creates a table named "weather"
 * @PrimaryKey - Marks the primary key (unique identifier for each row)
 *
 * Room automatically creates columns for each property:
 * - cityName → TEXT column
 * - temperatureCelsius → REAL column
 * - description → TEXT column
 * - etc.
 */
@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    val cityName: String,               // Primary key (each city has one entry)
    val temperatureCelsius: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double,
    val timestamp: Long = System.currentTimeMillis()  // When was this cached?
)

/**
 * Extension function: Convert WeatherEntity (DB model) to WeatherData (domain model)
 */
fun WeatherEntity.toWeatherData(): WeatherData {
    return WeatherData(
        cityName = cityName,
        temperatureCelsius = temperatureCelsius,
        description = description,
        humidity = humidity,
        windSpeed = windSpeed
    )
}

/**
 * Extension function: Convert WeatherData (domain model) to WeatherEntity (DB model)
 */
fun WeatherData.toWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        cityName = cityName,
        temperatureCelsius = temperatureCelsius,
        description = description,
        humidity = humidity,
        windSpeed = windSpeed
    )
}
