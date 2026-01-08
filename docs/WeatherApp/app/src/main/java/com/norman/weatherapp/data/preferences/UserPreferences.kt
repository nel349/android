package com.norman.weatherapp.data.preferences

/**
 * UserPreferences - Data class for user settings
 *
 * CONCEPT:
 * - Data class holds user preferences (dark mode, temperature unit, etc.)
 * - Immutable (val) - follows Kotlin best practices
 * - Used with DataStore for persistence
 *
 * WHY DATA CLASS:
 * - Auto-generates equals(), hashCode(), toString(), copy()
 * - Perfect for holding simple data
 * - Easy to update with .copy(darkMode = true)
 */
data class UserPreferences(
    val isDarkMode: Boolean = false,
    val isCelsius: Boolean = true
)
