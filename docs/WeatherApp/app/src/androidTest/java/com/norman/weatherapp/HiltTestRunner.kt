package com.norman.weatherapp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Custom Test Runner for Hilt Instrumented Tests
 *
 * WHY WE NEED THIS:
 * - Hilt instrumented tests need to use HiltTestApplication instead of the production Application
 * - This test runner swaps out WeatherApplication with HiltTestApplication at runtime
 * - This allows @HiltAndroidTest to work correctly with test modules
 *
 * HOW IT WORKS:
 * 1. AndroidJUnitRunner is the standard Android test runner
 * 2. We override newApplication() to return HiltTestApplication
 * 3. This happens BEFORE any tests run
 * 4. All tests then use HiltTestApplication with our test modules
 *
 * CONFIGURATION:
 * - Set this as testInstrumentationRunner in build.gradle.kts:
 *   testInstrumentationRunner = "com.norman.weatherapp.HiltTestRunner"
 */
class HiltTestRunner : AndroidJUnitRunner() {

    /**
     * Replace the production Application with HiltTestApplication
     *
     * This method is called by the Android framework to create the Application instance
     * for the test. By returning HiltTestApplication, we enable Hilt's test infrastructure.
     */
    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        // Replace WeatherApplication with HiltTestApplication
        return super.newApplication(
            classLoader,
            HiltTestApplication::class.java.name,  // Use Hilt's test application
            context
        )
    }
}
