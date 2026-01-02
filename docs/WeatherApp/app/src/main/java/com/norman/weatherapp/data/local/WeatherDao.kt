package com.norman.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) - Defines database operations
 *
 * KEY CONCEPTS:
 * @Dao - Marks this as a Room DAO
 * @Query - Custom SQL query (Room validates at compile time!)
 * @Insert(onConflict = REPLACE) - If city exists, replace it
 * suspend - Runs on background thread (coroutines)
 * Flow<T> - Reactive stream (updates UI automatically when data changes)
 *
 * Room generates all implementation code via KSP!
 */
@Dao
interface WeatherDao {

    /**
     * Get weather for a specific city
     * Returns Flow - emits new value whenever data changes in DB
     *
     * @param cityName City to fetch
     * @return Flow that emits WeatherEntity (or null if not cached)
     */
    @Query("SELECT * FROM weather WHERE cityName = :cityName LIMIT 1")
    fun getWeatherForCity(cityName: String): Flow<WeatherEntity?>

    /**
     * Insert or update weather data
     * If city already exists, replace it (REPLACE strategy)
     *
     * suspend = must be called from coroutine (runs on background thread)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)

    /**
     * Get all cached cities (for future multi-city feature)
     * Returns Flow - automatically updates when table changes
     */
    @Query("SELECT * FROM weather ORDER BY timestamp DESC")
    fun getAllWeather(): Flow<List<WeatherEntity>>

    /**
     * Get all cached cities as a one-shot query (for testing)
     * Returns List directly instead of Flow
     * Useful for verifying database state in tests
     */
    @Query("SELECT * FROM weather ORDER BY timestamp DESC")
    suspend fun getAllWeatherOneShot(): List<WeatherEntity>

    /**
     * Delete old cached data (optional cleanup)
     * Example: Delete entries older than 1 hour
     */
    @Query("DELETE FROM weather WHERE timestamp < :timestamp")
    suspend fun deleteOldWeather(timestamp: Long)
}
