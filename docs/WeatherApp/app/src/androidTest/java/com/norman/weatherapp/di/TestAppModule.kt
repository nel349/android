package com.norman.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.norman.weatherapp.data.api.WeatherApiService
import com.norman.weatherapp.data.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Test Dependency Injection Modules
 *
 * WHAT THIS DOES:
 * - Replaces production modules (DatabaseModule, NetworkModule) during instrumented tests
 * - Provides test-specific dependencies (in-memory database, fake API service)
 *
 * HOW IT WORKS:
 * - Tests annotated with @UninstallModules(DatabaseModule::class, NetworkModule::class)
 * - These test modules are used instead
 * - Each test gets fresh, isolated dependencies
 *
 * BENEFITS:
 * - Fast: In-memory database (no disk I/O)
 * - Isolated: Each test gets clean state
 * - Safe: No real network calls
 * - Controllable: Can inject fake data for specific test scenarios
 */

/**
 * Test Database Module
 *
 * REPLACES: DatabaseModule (production)
 *
 * IMPROVEMENTS OVER PRODUCTION:
 * - Uses in-memory database instead of on-disk
 * - Database is automatically cleared when closed
 * - No data persists between test runs
 * - Much faster (no disk I/O)
 * - Allows main thread queries for testing simplicity
 */
@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule {

    /**
     * Provide in-memory database for testing
     *
     * KEY DIFFERENCES FROM PRODUCTION:
     * - inMemoryDatabaseBuilder() instead of databaseBuilder()
     * - allowMainThreadQueries() for testing (NEVER do this in production!)
     * - Database exists only in RAM
     * - Automatically cleared when closed
     *
     * @param context Application context (provided by Hilt with @ApplicationContext qualifier)
     * @return In-memory WeatherDatabase instance
     */
    @Provides
    @Singleton
    fun provideInMemoryDatabase(
        @dagger.hilt.android.qualifiers.ApplicationContext context: Context
    ): WeatherDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            WeatherDatabase::class.java
        )
            .allowMainThreadQueries()  // For testing only! Simplifies test code
            .build()
    }

    /**
     * Provide WeatherDao from test database
     *
     * Same signature as production, but uses in-memory database
     */
    @Provides
    fun provideWeatherDao(database: WeatherDatabase) = database.weatherDao()
}

/**
 * Test Network Module
 *
 * REPLACES: NetworkModule (production)
 *
 * IMPROVEMENTS OVER PRODUCTION:
 * - Provides fake API service (no real network calls)
 * - Prevents flaky tests due to network issues
 * - Prevents accidental API usage during tests
 * - Can be extended to return fake data for specific test scenarios
 */
@Module
@InstallIn(SingletonComponent::class)
object TestNetworkModule {

    /**
     * Provide fake API service for testing
     *
     * CURRENT IMPLEMENTATION:
     * - Simple fake that throws error if called
     * - Safe for UI tests that don't trigger API calls
     *
     * FUTURE ENHANCEMENTS:
     * - Create FakeWeatherApiService class with configurable responses
     * - Allow tests to configure expected responses
     * - Simulate network errors, delays, etc.
     *
     * EXAMPLE ENHANCEMENT:
     * ```kotlin
     * class FakeWeatherApiService : WeatherApiService {
     *     var responseToReturn: WeatherResponse? = null
     *     var errorToThrow: Exception? = null
     *
     *     override suspend fun getWeather(city: String, apiKey: String): WeatherResponse {
     *         errorToThrow?.let { throw it }
     *         return responseToReturn ?: throw IllegalStateException("No response configured")
     *     }
     * }
     * ```
     *
     * @return Fake WeatherApiService for testing
     */
    @Provides
    @Singleton
    fun provideFakeWeatherApiService(): WeatherApiService {
        // Simple fake implementation
        // Throws error if called - safe for UI-only tests
        return object : WeatherApiService {
            override suspend fun getWeather(cityName: String, apiKey: String) =
                throw UnsupportedOperationException(
                    "Fake API service - should not be called in UI tests. " +
                    "If you need to test API interactions, create a configurable FakeWeatherApiService."
                )
        }
    }

    // NOTE: We don't provide Retrofit in tests
    // We directly provide the API service (simpler, faster)
}

/**
 * USAGE IN TESTS:
 *
 * ```kotlin
 * @HiltAndroidTest
 * @UninstallModules(DatabaseModule::class, NetworkModule::class)
 * class MyInstrumentedTest {
 *     @get:Rule
 *     var hiltRule = HiltAndroidRule(this)
 *
 *     @Inject
 *     lateinit var database: WeatherDatabase  // This will be in-memory!
 *
 *     @Before
 *     fun setup() {
 *         hiltRule.inject()
 *         // database is now injected and ready to use
 *     }
 *
 *     @After
 *     fun tearDown() {
 *         database.close()  // Clears in-memory database
 *     }
 * }
 * ```
 *
 * SUMMARY OF BENEFITS:
 * ✅ Fast - In-memory database (no disk I/O)
 * ✅ Isolated - Each test gets fresh database
 * ✅ Safe - No real network calls
 * ✅ Simple - Easy to set up test data
 * ✅ Reliable - No flakiness from external dependencies
 * ✅ Maintainable - Test modules mirror production structure
 */
