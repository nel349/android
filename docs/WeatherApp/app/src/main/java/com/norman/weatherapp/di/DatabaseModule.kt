package com.norman.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.norman.weatherapp.data.local.WeatherDao
import com.norman.weatherapp.data.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DatabaseModule - Tells Hilt how to provide Room database dependencies
 *
 * KEY CONCEPTS:
 *
 * @Module:
 * - Marks this as a Hilt module
 * - Hilt scans this during build time
 *
 * @InstallIn(SingletonComponent::class):
 * - Defines the LIFETIME of these dependencies
 * - SingletonComponent = Lives for entire app lifetime
 * - Database should be singleton (one instance shared across app)
 *
 * @Provides:
 * - Tells Hilt "this function creates a dependency"
 * - Hilt calls this function when someone needs WeatherDatabase
 *
 * @Singleton:
 * - Ensures only ONE instance is created
 * - Same database instance used everywhere (memory efficient)
 */
@Module
@InstallIn(SingletonComponent::class)  // Lives for entire app
object DatabaseModule {

    /**
     * Provides WeatherDatabase instance
     *
     * @ApplicationContext:
     * - Hilt automatically provides Application context
     * - No need to manually get it!
     *
     * @Singleton:
     * - Creates database once, reuses it
     * - Thread-safe (Room handles locking)
     *
     * How Hilt uses this:
     * 1. ViewModel asks for WeatherDao
     * 2. Hilt sees WeatherDao needs WeatherDatabase
     * 3. Hilt calls this function to create WeatherDatabase
     * 4. Hilt caches it (singleton)
     * 5. Next request gets the same instance
     */
    @Provides
    @Singleton
    fun provideWeatherDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database"
        ).build()
    }

    /**
     * Provides WeatherDao
     *
     * Hilt knows:
     * - This function returns WeatherDao
     * - It needs WeatherDatabase (parameter)
     * - It should call provideWeatherDatabase() first
     *
     * Dependency chain:
     * ViewModel needs WeatherDao
     *   → WeatherDao needs WeatherDatabase
     *     → Hilt calls provideWeatherDatabase()
     *       → Hilt calls database.weatherDao()
     *         → Returns WeatherDao to ViewModel
     */
    @Provides
    fun provideWeatherDao(database: WeatherDatabase): WeatherDao {
        return database.weatherDao()
    }
}
