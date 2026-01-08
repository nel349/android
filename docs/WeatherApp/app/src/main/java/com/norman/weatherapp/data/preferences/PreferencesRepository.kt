package com.norman.weatherapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * PreferencesRepository - Manages user preferences with DataStore
 *
 * DATASTORE CONCEPTS:
 * - Modern replacement for SharedPreferences
 * - Type-safe (using keys)
 * - Asynchronous (uses Flow and suspend functions)
 * - Safe from runtime exceptions
 *
 * EXTENSION PROPERTY PATTERN:
 * - Context.dataStore creates single instance per app
 * - by preferencesDataStore delegates creation
 * - "user_preferences" is the file name
 *
 * WHY FLOW:
 * - Preferences can change while app is running
 * - Flow emits new values automatically when preferences update
 * - Perfect for reactive UI (Compose)
 *
 * STATE HOISTING PREP:
 * - This repository will be injected into ViewModel
 * - ViewModel exposes preferences as StateFlow
 * - Compose observes StateFlow with collectAsStateWithLifecycle()
 */

// Extension property to create DataStore instance
// This pattern ensures only one DataStore instance exists
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

@Singleton
class PreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // Preference keys (type-safe)
    private object PreferencesKeys {
        val DARK_MODE = booleanPreferencesKey("dark_mode")
        val IS_CELSIUS = booleanPreferencesKey("is_celsius")
    }

    /**
     * Observe user preferences as Flow
     *
     * FLOW PATTERN:
     * - dataStore.data is Flow<Preferences> (emits on every change)
     * - map{} transforms Preferences -> UserPreferences
     * - UI can collect this Flow to stay updated
     */
    val userPreferences: Flow<UserPreferences> = context.dataStore.data
        .map { preferences ->
            UserPreferences(
                isDarkMode = preferences[PreferencesKeys.DARK_MODE] ?: false,
                isCelsius = preferences[PreferencesKeys.IS_CELSIUS] ?: true
            )
        }

    /**
     * Update dark mode preference
     *
     * SUSPEND FUNCTION:
     * - DataStore operations are async (suspend)
     * - edit{} atomically updates preferences
     * - Changes automatically emit through userPreferences Flow
     */
    suspend fun updateDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE] = isDarkMode
        }
    }

    /**
     * Update temperature unit preference
     */
    suspend fun updateTemperatureUnit(isCelsius: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_CELSIUS] = isCelsius
        }
    }
}
