package com.norman.weatherapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.norman.weatherapp.data.preferences.PreferencesRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Application class for WeatherApp
 *
 * @HiltAndroidApp:
 * - REQUIRED for every Hilt app
 * - Triggers Hilt's code generation
 * - Creates the dependency graph (how all objects are created)
 * - Must be in AndroidManifest.xml
 *
 * GLOBAL THEME APPLICATION:
 * - Observes user's dark mode preference
 * - Applies theme to entire app (XML + Compose)
 * - AppCompatDelegate.setDefaultNightMode() affects ALL activities
 *
 * This is the STARTING POINT for Hilt:
 * 1. App starts → WeatherApplication created
 * 2. Hilt initializes → Scans for @Module classes
 * 3. Hilt builds dependency graph → Knows how to create Database, Retrofit, etc.
 * 4. Activities/Fragments request dependencies → Hilt provides them
 * 5. (New) Apply global theme based on user preference
 */
@HiltAndroidApp
class WeatherApplication : Application() {

    /**
     * Field injection for PreferencesRepository
     *
     * WHY FIELD INJECTION HERE:
     * - Application doesn't have constructor injection support with Hilt
     * - Must use @Inject on field (lateinit var)
     * - Hilt injects this after Application is created
     */
    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreate() {
        super.onCreate()

        // Apply global theme based on user preference
        applyGlobalTheme()
    }

    /**
     * Apply theme globally to all activities (XML + Compose)
     *
     * CONCEPT:
     * - ProcessLifecycleOwner = lifecycle for the entire app process
     * - lifecycleScope = coroutine scope tied to app lifecycle
     * - AppCompatDelegate.setDefaultNightMode() = changes theme for ALL activities
     *
     * HOW IT WORKS:
     * 1. User toggles dark mode in AboutScreen
     * 2. PreferencesRepository saves to DataStore
     * 3. DataStore emits new value through Flow
     * 4. This collect{} receives new value
     * 5. AppCompatDelegate applies theme
     * 6. All activities automatically recreate with new theme
     */
    private fun applyGlobalTheme() {
        ProcessLifecycleOwner.get().lifecycleScope.launch {
            preferencesRepository.userPreferences.collect { preferences ->
                val mode = if (preferences.isDarkMode) {
                    AppCompatDelegate.MODE_NIGHT_YES  // Dark mode
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO   // Light mode
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }
}
