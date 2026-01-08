package com.norman.weatherapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * WeatherHistory Entity - Stores weather search history
 *
 * LEARNING: Room Database Entity
 * - @Entity marks this as a Room table
 * - @PrimaryKey(autoGenerate = true) creates auto-incrementing ID
 * - Each property becomes a column in the database
 *
 * PURPOSE:
 * - Track all weather searches made by the user
 * - Show history in Weather History screen
 * - Different from WeatherEntity (saved cities) vs WeatherHistoryEntity (search log)
 *
 * ARCHITECTURE:
 * - WeatherEntity = User's saved/favorite cities (CityListFragment)
 * - WeatherHistoryEntity = Search history log (WeatherHistoryScreen in Compose)
 */
@Entity(tableName = "weather_history")
data class WeatherHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    /**
     * City name searched
     * Example: "London", "Tokyo"
     */
    val cityName: String,

    /**
     * Temperature in Celsius
     * Store in Celsius, convert to Fahrenheit on display if needed
     */
    val temperatureCelsius: Double,

    /**
     * Weather description
     * Example: "clear sky", "light rain"
     */
    val description: String,

    /**
     * Timestamp when this search was made
     * Using System.currentTimeMillis()
     * For sorting (most recent first) and displaying "2 hours ago"
     */
    val timestamp: Long = System.currentTimeMillis()
) {
    /**
     * Format temperature based on user preference
     * Same pattern as WeatherData
     */
    fun getFormattedTemperature(isCelsius: Boolean = true): String {
        return if (isCelsius) {
            "${temperatureCelsius.toInt()}°C"
        } else {
            val fahrenheit = (temperatureCelsius * 9 / 5) + 32
            "${fahrenheit.toInt()}°F"
        }
    }

    /**
     * Format description with capitalized first letter
     */
    fun getFormattedDescription(): String {
        return description.replaceFirstChar { it.uppercase() }
    }

    /**
     * Format timestamp as relative time
     * Example: "2 hours ago", "Yesterday", "3 days ago"
     *
     * LEARNING: This is a simple version
     * Production apps use libraries like:
     * - java.time (API 26+) with Duration
     * - androidx.compose.material3:material3 has built-in formatters
     * - Third-party: Joda-Time, ThreeTenABP
     */
    fun getRelativeTimeString(): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            minutes < 1 -> "Just now"
            minutes < 60 -> "$minutes minute${if (minutes != 1L) "s" else ""} ago"
            hours < 24 -> "$hours hour${if (hours != 1L) "s" else ""} ago"
            days < 7 -> "$days day${if (days != 1L) "s" else ""} ago"
            else -> {
                val weeks = days / 7
                "$weeks week${if (weeks != 1L) "s" else ""} ago"
            }
        }
    }
}

/**
 * KEY LEARNINGS:
 *
 * 1. @Entity vs data class:
 *    - @Entity makes this a Room table
 *    - data class gives us equals(), hashCode(), copy(), toString()
 *    - They work great together!
 *
 * 2. @PrimaryKey(autoGenerate = true):
 *    - Room generates unique ID for each entry
 *    - We can set id = 0 when inserting, Room replaces it
 *
 * 3. Default parameter values:
 *    - id = 0 (Room will replace)
 *    - timestamp = System.currentTimeMillis() (auto-set to now)
 *
 * 4. Helper methods in Entity:
 *    - getFormattedTemperature() - formatting logic
 *    - getFormattedDescription() - text transformation
 *    - getRelativeTimeString() - time formatting
 *    - These make the UI code cleaner
 *
 * 5. WeatherEntity vs WeatherHistoryEntity:
 *    - WeatherEntity = Persistent saved cities (user favorites)
 *    - WeatherHistoryEntity = Search log (every search)
 *    - Different purposes, different tables
 */
