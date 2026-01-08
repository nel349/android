package com.norman.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.norman.weatherapp.data.local.entities.WeatherHistoryEntity
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

    // ========== WEATHER HISTORY METHODS ==========

    /**
     * Insert weather search into history
     *
     * LEARNING: History vs Cached Weather
     * - WeatherEntity = User's saved cities (one per city, REPLACE on conflict)
     * - WeatherHistoryEntity = Search log (new entry every search, IGNORE duplicates)
     *
     * OnConflictStrategy.IGNORE:
     * - If exact same entry exists, don't insert again
     * - Prevents duplicate entries when user searches same city multiple times quickly
     * - Alternative: REPLACE would update timestamp of existing entry
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history: WeatherHistoryEntity)

    /**
     * Get all search history
     *
     * LEARNING: Flow for reactive UI
     * - Returns Flow<List<WeatherHistoryEntity>>
     * - Compose UI will automatically update when history changes
     * - ORDER BY timestamp DESC = most recent first
     *
     * LazyColumn will display this list:
     * - User searches "Tokyo" → inserted → Flow emits updated list → UI updates
     * - No manual refresh needed!
     */
    @Query("SELECT * FROM weather_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<WeatherHistoryEntity>>

    /**
     * Get recent history (last N entries)
     * Useful for showing "Recent Searches" section
     *
     * LEARNING: LIMIT in SQL
     * - LIMIT :limit = Return only first N rows
     * - Combined with ORDER BY timestamp DESC = most recent N searches
     */
    @Query("SELECT * FROM weather_history ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentHistory(limit: Int): Flow<List<WeatherHistoryEntity>>

    /**
     * Delete all search history
     * For "Clear History" button in settings
     *
     * LEARNING: Confirmation dialogs
     * - Destructive actions should show confirmation dialog
     * - We'll implement this in Compose with AlertDialog
     */
    @Query("DELETE FROM weather_history")
    suspend fun deleteAllHistory()

    /**
     * Delete old history entries
     * For automatic cleanup (e.g., delete entries older than 30 days)
     *
     * LEARNING: Maintenance queries
     * - Keep database size manageable
     * - Can run periodically with WorkManager (not covered yet)
     */
    @Query("DELETE FROM weather_history WHERE timestamp < :timestamp")
    suspend fun deleteOldHistory(timestamp: Long)
}
