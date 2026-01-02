package com.norman.weatherapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.norman.weatherapp.data.api.WeatherApiService
import com.norman.weatherapp.data.local.WeatherDatabase
import com.norman.weatherapp.data.local.WeatherEntity
import com.norman.weatherapp.di.DatabaseModule
import com.norman.weatherapp.di.NetworkModule
import com.norman.weatherapp.ui.fragments.CityListFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton

/**
 * CityListFragment Instrumented Tests WITH Hilt
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
 *
 * COMPARISON TO OLD TEST:
 * - Before: Real on-disk database → After: In-memory database (fast!)
 * - Before: Tests interfere with each other → After: Isolated tests
 * - Before: Manual setup/teardown → After: Hilt handles it
 * - Before: Can't mock API → After: Easy fake API
 * - Before: Can't test navigation → After: Easy with test modules
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest  // IMPROVEMENT #1: Enable Hilt for testing
@UninstallModules(DatabaseModule::class, NetworkModule::class)  // IMPROVEMENT #2: Replace production modules
class CityListFragmentTestWithHilt {

    // IMPROVEMENT #3: HiltAndroidRule manages Hilt in tests
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    // IMPROVEMENT #4: Hilt injects test database
    // This is the IN-MEMORY database from our test module!
    @Inject
    lateinit var database: WeatherDatabase

    @Before
    fun setup() {
        // IMPROVEMENT #6: Inject dependencies
        hiltRule.inject()

        // IMPROVEMENT #7: Database is already clean (new instance per test)
        // No need to manually clear like before!
    }

    @After
    fun tearDown() {
        // IMPROVEMENT #8: Clean shutdown (no race conditions!)
        database.close()
        // In-memory database is automatically cleared when closed
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
 * Helper function to launch Fragment in Hilt-enabled container
 *
 * This is necessary because Hilt Fragments must be attached to @AndroidEntryPoint Activities.
 * Standard launchFragmentInContainer() uses EmptyFragmentActivity which doesn't have @AndroidEntryPoint.
 */
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.Theme_WeatherApp,
    crossinline action: Fragment.() -> Unit = {}
) {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra(
        "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
        themeResId
    )

    ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )
        fragment.arguments = fragmentArgs
        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()

        fragment.action()
    }
}

/**
 * TEST MODULE: Replaces production modules with test versions
 *
 * IMPROVEMENT #9: Provide test dependencies
 * - In-memory database (fast, isolated)
 * - Fake API service (no network calls)
 */
@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule {

    /**
     * IMPROVEMENT #10: Provide in-memory database
     *
     * BEFORE: Tests used real on-disk database
     * AFTER: Each test gets a fresh in-memory database
     *
     * Benefits:
     * - Fast (no disk I/O)
     * - Isolated (no test interference)
     * - Automatically cleared when closed
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
            .allowMainThreadQueries()  // For testing only!
            .build()
    }

    @Provides
    fun provideWeatherDao(database: WeatherDatabase) = database.weatherDao()
}

/**
 * TEST MODULE: Provides fake API service
 */
@Module
@InstallIn(SingletonComponent::class)
object TestNetworkModule {

    /**
     * IMPROVEMENT #11: Provide fake API service
     *
     * BEFORE: Tests would make real API calls
     * AFTER: Fake API service (returns error - safe for testing UI without network)
     *
     * This fake service prevents real network calls during UI tests.
     * For tests that need to verify API responses, you could create
     * a FakeWeatherApiService class that returns predefined data.
     */
    @Provides
    @Singleton
    fun provideFakeWeatherApiService(): WeatherApiService {
        // Simple fake implementation that throws error if called
        // This is safe because our UI tests don't trigger API calls
        return object : WeatherApiService {
            override suspend fun getWeather(cityName: String, apiKey: String) =
                throw UnsupportedOperationException("Fake API service - should not be called in UI tests")
        }
    }

    // Note: We don't provide Retrofit in tests (not needed)
    // We directly provide the API service mock
}

/**
 * SUMMARY OF IMPROVEMENTS WITH HILT:
 *
 * WITHOUT Hilt:
 * ❌ Real on-disk database (slow, persists)
 * ❌ Tests interfere with each other
 * ❌ Manual database setup/teardown
 * ❌ Can't mock API (makes real calls)
 * ❌ Flaky tests (network, state)
 * ❌ Race conditions with database lifecycle
 * ❌ Complex boilerplate
 * ❌ Can't test navigation easily
 *
 * WITH Hilt:
 * ✅ In-memory database (fast, isolated)
 * ✅ Each test gets fresh dependencies
 * ✅ Automatic dependency injection
 * ✅ Easy to mock API
 * ✅ Fast, reliable tests
 * ✅ No race conditions
 * ✅ Clean, simple test code
 * ✅ Navigation testing supported
 *
 * The difference is night and day!
 */
