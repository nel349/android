package com.norman.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.norman.weatherapp.data.local.entities.WeatherHistoryEntity

/**
 * Room Database - Main database holder
 *
 * KEY ANNOTATIONS:
 * @Database(entities = [...], version = 2)
 *   - entities: List of tables (Entity classes)
 *   - version: Database schema version (increment when changing tables)
 *   - exportSchema: Whether to export schema JSON (useful for migrations)
 *
 * ABSTRACT CLASS:
 * - You define abstract methods to get DAOs
 * - Room generates the implementation
 *
 * SINGLETON PATTERN:
 * - Only create one instance for the entire app
 * - Use synchronized block to ensure thread-safety
 *
 * VERSION HISTORY:
 * - v1: Initial version with WeatherEntity
 * - v2: Added WeatherHistoryEntity for search history tracking
 */
@Database(
    entities = [
        WeatherEntity::class,           // Cached weather data for cities
        WeatherHistoryEntity::class     // Search history log
    ],
    version = 2,                        // Incremented: added WeatherHistoryEntity
    exportSchema = false                // Don't export schema (set true for production)
)
abstract class WeatherDatabase : RoomDatabase() {

    /**
     * Get DAO for weather operations
     * Room generates the implementation
     */
    abstract fun weatherDao(): WeatherDao

    companion object {
        /**
         * Singleton instance
         * @Volatile ensures visibility across threads
         */
        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        /**
         * Get database instance (creates if doesn't exist)
         *
         * WHY SINGLETON?
         * - Database creation is expensive
         * - Multiple instances can cause conflicts
         * - One instance shared across entire app
         *
         * @param context Application context
         * @return WeatherDatabase instance
         */
        fun getDatabase(context: Context): WeatherDatabase {
            // If instance exists, return it
            return INSTANCE ?: synchronized(this) {
                // Double-check inside synchronized block
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDatabase::class.java,
                    "weather_database"  // Database file name
                )
                    // Fallback to destructive migration (for development)
                    // In production, write proper migrations
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
