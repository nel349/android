package com.norman.weatherapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for WeatherApp
 *
 * @HiltAndroidApp:
 * - REQUIRED for every Hilt app
 * - Triggers Hilt's code generation
 * - Creates the dependency graph (how all objects are created)
 * - Must be in AndroidManifest.xml
 *
 * This is the STARTING POINT for Hilt:
 * 1. App starts → WeatherApplication created
 * 2. Hilt initializes → Scans for @Module classes
 * 3. Hilt builds dependency graph → Knows how to create Database, Retrofit, etc.
 * 4. Activities/Fragments request dependencies → Hilt provides them
 */
@HiltAndroidApp
class WeatherApplication : Application() {
    // That's it! Just this annotation does all the magic.
    // Hilt will:
    // - Find all @Module classes
    // - Build dependency graph
    // - Inject dependencies when needed
}
