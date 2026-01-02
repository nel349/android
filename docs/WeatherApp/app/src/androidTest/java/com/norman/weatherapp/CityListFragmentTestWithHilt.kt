package com.norman.weatherapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.norman.weatherapp.data.local.WeatherDatabase
import com.norman.weatherapp.data.local.WeatherEntity
import com.norman.weatherapp.di.DatabaseModule
import com.norman.weatherapp.di.NetworkModule
import com.norman.weatherapp.ui.fragments.CityListFragment
import com.norman.weatherapp.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * CityListFragment Instrumented Tests WITH Hilt
 *
 * ARCHITECTURE:
 * - Test class: Contains only test logic (this file)
 * - Test DI modules: TestAppModule.kt (provides in-memory DB, fake API)
 * - Test utilities: HiltFragmentTestUtils.kt (fragment launcher helper)
 * - Test infrastructure: HiltTestRunner.kt, HiltTestActivity.kt
 *
 * IMPROVEMENTS YOU'LL SEE:
 * ✅ 1. Uses IN-MEMORY database (fast, doesn't persist)
 * ✅ 2. Tests are isolated (each test gets fresh database)
 * ✅ 3. Easy test data setup (Hilt provides test database)
 * ✅ 4. Can mock API calls (Hilt provides fake API)
 * ✅ 5. Fast (no disk I/O, no network)
 * ✅ 6. Reliable (no flakiness from network/state)
 * ✅ 7. Clean setup (Hilt handles dependency injection)
 * ✅ 8. Can test navigation (Hilt makes it easy)
 * ✅ 9. Modular architecture (DI modules, utils in separate files)
 *
 * COMPARISON TO OLD TEST:
 * - Before: Real on-disk database → After: In-memory database (fast!)
 * - Before: Tests interfere with each other → After: Isolated tests
 * - Before: Manual setup/teardown → After: Hilt handles it
 * - Before: Can't mock API → After: Easy fake API (see TestAppModule)
 * - Before: Can't test navigation → After: Easy with test modules
 * - Before: Test helpers in test file → After: Reusable utils (HiltFragmentTestUtils)
 *
 * See also:
 * - di/TestAppModule.kt - Test DI modules (database, network)
 * - utils/HiltFragmentTestUtils.kt - Fragment testing utilities
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest  // Enable Hilt for testing
@UninstallModules(DatabaseModule::class, NetworkModule::class)  // Replace production modules with test modules
class CityListFragmentTestWithHilt {

    // HiltAndroidRule manages Hilt injection in tests
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // Hilt injects test database (in-memory, from TestAppModule)
    @Inject
    lateinit var database: WeatherDatabase

    @Before
    fun setup() {
        // Inject dependencies (database, etc.)
        hiltRule.inject()
        // Database is already clean (fresh in-memory instance per test)
    }

    @After
    fun tearDown() {
        // Close and clear in-memory database
        database.close()
    }

    // ========== TESTS THAT NOW WORK BETTER ==========

    /**
     * Test 1: Empty database shows empty state
     *
     * BEFORE: Used real on-disk database
     * AFTER: Uses in-memory database (fast!)
     */
    @Test
    fun emptyDatabaseShowsEmptyState() {
        // Arrange: Database is empty (fresh instance)

        // Act: Launch fragment
        launchFragmentInHiltContainer<CityListFragment>()

        // Assert: Empty state visible
        onView(withId(R.id.emptyStateText))
            .check(matches(isDisplayed()))

        onView(withId(R.id.cityRecyclerView))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    /**
     * Test 2: Database with cities shows list
     *
     * BEFORE: Slow disk I/O, manual population
     * AFTER: Fast in-memory database
     */
    @Test
    fun databaseWithCitiesShowsCityList() {
        // Arrange: Insert test data (much faster with in-memory DB!)
        runBlocking {
            database.weatherDao().insertWeather(
                WeatherEntity(
                    cityName = "London",
                    temperatureCelsius = 15.0,
                    description = "Cloudy",
                    humidity = 80,
                    windSpeed = 5.0
                )
            )
            database.weatherDao().insertWeather(
                WeatherEntity(
                    cityName = "Paris",
                    temperatureCelsius = 18.0,
                    description = "Sunny",
                    humidity = 60,
                    windSpeed = 3.0
                )
            )
        }

        // Act: Launch fragment
        launchFragmentInHiltContainer<CityListFragment>()

        // Assert: Cities displayed
        onView(withText("London")).check(matches(isDisplayed()))
        onView(withText("Cloudy")).check(matches(isDisplayed()))

        onView(withText("Paris")).check(matches(isDisplayed()))
        onView(withText("Sunny")).check(matches(isDisplayed()))

        onView(withId(R.id.cityRecyclerView))
            .check(matches(isDisplayed()))

        onView(withId(R.id.emptyStateText))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    /**
     * Test 3: FAB button is visible
     */
    @Test
    fun fabButtonIsVisible() {
        launchFragmentInHiltContainer<CityListFragment>()

        onView(withId(R.id.searchFab))
            .check(matches(isDisplayed()))
    }

    /**
     * Test 4: Click FAB shows search dialog
     *
     * BEFORE: Couldn't test search because it would make real API call
     * AFTER: Can test safely because Hilt provides fake API
     */
    @Test
    fun clickFabShowsSearchDialog() {
        launchFragmentInHiltContainer<CityListFragment>()

        // Click FAB
        onView(withId(R.id.searchFab)).perform(click())

        // Verify dialog appears
        onView(withText("Search City")).check(matches(isDisplayed()))
        onView(withText("Search")).check(matches(isDisplayed()))
        onView(withText("Cancel")).check(matches(isDisplayed()))

        // IMPROVEMENT: Now we could test the search functionality
        // by creating a FakeWeatherApiService that returns predefined data!
    }

    /**
     * Test 5: Multiple cities are displayed correctly
     *
     * BEFORE: Slow with real database
     * AFTER: Fast with in-memory database
     */
    @Test
    fun multipleCitiesAreDisplayedCorrectly() {
        // Arrange: Insert multiple cities
        val cities = listOf(
            WeatherEntity("London", 15.0, "Cloudy", 80, 5.0),
            WeatherEntity("Paris", 18.0, "Sunny", 60, 3.0),
            WeatherEntity("Tokyo", 22.0, "Rainy", 90, 8.0),
            WeatherEntity("New York", 10.0, "Snowy", 70, 12.0)
        )

        runBlocking {
            cities.forEach { database.weatherDao().insertWeather(it) }
        }

        // Act: Launch fragment
        launchFragmentInHiltContainer<CityListFragment>()

        // Assert: All cities visible
        cities.forEach { city ->
            onView(withText(city.cityName)).check(matches(isDisplayed()))
            onView(withText(city.description)).check(matches(isDisplayed()))
        }
    }

    /**
     * Test 6: Database isolation between tests
     *
     * IMPROVEMENT: This test verifies that each test gets a fresh database
     * Before: Tests could interfere with each other
     * After: Complete isolation
     */
    @Test
    fun testsAreIsolatedWithFreshDatabase() {
        // Verify database is empty at test start
        runBlocking {
            val cities = database.weatherDao().getAllWeatherOneShot()
            assert(cities.isEmpty()) { "Database should be empty at test start" }
        }

        // Add some data
        runBlocking {
            database.weatherDao().insertWeather(
                WeatherEntity("TestCity", 20.0, "Test", 50, 5.0)
            )
        }

        // Verify data exists
        runBlocking {
            val cities = database.weatherDao().getAllWeatherOneShot()
            assert(cities.size == 1) { "Should have 1 city" }
        }

        // After this test, other tests will get a fresh database!
    }
}

/**
 * SUMMARY OF IMPROVEMENTS WITH HILT:
 *
 * WITHOUT Hilt (see CityListFragmentTest.kt):
 * ❌ Real on-disk database (slow, persists)
 * ❌ Tests interfere with each other
 * ❌ Manual database setup/teardown
 * ❌ Can't mock API (makes real calls)
 * ❌ Flaky tests (network, state)
 * ❌ Race conditions with database lifecycle
 * ❌ Complex boilerplate
 * ❌ Can't test navigation easily
 * ❌ Test infrastructure mixed with test code
 *
 * WITH Hilt (this file):
 * ✅ In-memory database (fast, isolated) - see di/TestAppModule.kt
 * ✅ Each test gets fresh dependencies
 * ✅ Automatic dependency injection
 * ✅ Easy to mock API - see di/TestAppModule.kt
 * ✅ Fast, reliable tests
 * ✅ No race conditions
 * ✅ Clean, simple test code (only test logic in this file)
 * ✅ Navigation testing supported
 * ✅ Reusable test utilities - see utils/HiltFragmentTestUtils.kt
 * ✅ Modular architecture (separation of concerns)
 *
 * The difference is night and day!
 */
