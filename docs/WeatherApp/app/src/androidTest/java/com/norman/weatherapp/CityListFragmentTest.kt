package com.norman.weatherapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.norman.weatherapp.data.local.WeatherDatabase
import com.norman.weatherapp.data.local.WeatherEntity
import com.norman.weatherapp.ui.fragments.CityListFragment
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

/**
 * CityListFragment Instrumented Tests WITHOUT Dependency Injection
 *
 * NOTE: This test is DISABLED because ViewModel now uses Hilt (@Inject constructor).
 * See CityListFragmentTestWithHilt.kt for the working version with Hilt.
 * This file is kept for reference to show the pain points without DI.
 *
 * PAIN POINTS YOU'LL SEE:
 * 1. Uses REAL on-disk database (persists between tests)
 * 2. Tests can interfere with each other (shared database state)
 * 3. Hard to set up test data (must manually clear/populate database)
 * 4. Can't mock API calls (will make real network requests)
 * 5. Slow (database I/O, potential network calls)
 * 6. Flaky (depends on device state, network availability)
 * 7. Complex setup and teardown
 *
 * After we add Hilt, you'll see how to use in-memory database and fake repositories!
 */
@Ignore("Disabled - ViewModel now requires Hilt. See CityListFragmentTestWithHilt instead.")
@RunWith(AndroidJUnit4::class)
class CityListFragmentTest {

    // PAIN POINT #1: Must manually get database instance
    // This is the REAL on-disk database used by the app
    private lateinit var database: WeatherDatabase

    @Before
    fun setup() {
        // Get application context
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // PAIN POINT #2: Get the REAL database
        // This is the same database the app uses - persists on device!
        database = WeatherDatabase.getDatabase(context)

        // PAIN POINT #3: Must manually clear database before each test
        // Otherwise tests interfere with each other
        runBlocking {
            database.clearAllTables()
        }
    }

    @After
    fun tearDown() {
        // PAIN POINT #4: Must manually clean up after test
        // If we forget this, data persists and affects other tests!
        runBlocking {
            database.clearAllTables()
        }

        // PAIN POINT #8: Can't safely close database!
        // If we close it here, Fragment's ViewModel crashes (still using it)
        // If we DON'T close it, we leak resources
        // Race condition: test vs Fragment lifecycle
        // database.close()  // ❌ Commented out - causes race condition
    }

    /**
     * Test 1: Empty database shows empty state
     *
     * This test works but uses REAL database
     */
    @Test
    fun emptyDatabaseShowsEmptyState() {
        // Arrange: Database is already empty (cleared in setup)

        // Act: Launch fragment
        launchFragmentInContainer<CityListFragment>(
            themeResId = R.style.Theme_WeatherApp
        )

        // Assert: Empty state should be visible
        onView(withId(R.id.emptyStateText))
            .check(matches(isDisplayed()))

        onView(withId(R.id.cityRecyclerView))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    /**
     * Test 2: Database with cities shows list
     *
     * PAIN POINT #5: Must manually populate REAL database
     * - Requires coroutines
     * - Database I/O is slow
     * - Data persists on device if tearDown fails
     */
    @Test
    fun databaseWithCitiesShowsCityList() {
        // Arrange: Insert test data into REAL database
        // PAIN POINT: This is slow (disk I/O)
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
        launchFragmentInContainer<CityListFragment>(
            themeResId = R.style.Theme_WeatherApp
        )

        // Assert: Cities should be displayed
        onView(withText("London")).check(matches(isDisplayed()))
        onView(withText("Cloudy")).check(matches(isDisplayed()))

        onView(withText("Paris")).check(matches(isDisplayed()))
        onView(withText("Sunny")).check(matches(isDisplayed()))

        // RecyclerView should be visible
        onView(withId(R.id.cityRecyclerView))
            .check(matches(isDisplayed()))

        // Empty state should be gone
        onView(withId(R.id.emptyStateText))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    /**
     * Test 3: FAB button is visible
     *
     * Simple UI test - works fine
     */
    @Test
    fun fabButtonIsVisible() {
        launchFragmentInContainer<CityListFragment>(
            themeResId = R.style.Theme_WeatherApp
        )

        onView(withId(R.id.searchFab))
            .check(matches(isDisplayed()))
    }

    /**
     * Test 4: PROBLEMATIC TEST - Click FAB shows search dialog
     *
     * PAIN POINT #6: We can test that dialog appears, but...
     * - If user enters city name and clicks "Search", it makes REAL API call
     * - We can't mock the API without DI
     * - Test becomes flaky (depends on network)
     * - We can't test "API fails" scenario
     */
    @Test
    fun clickFabShowsSearchDialog() {
        launchFragmentInContainer<CityListFragment>(
            themeResId = R.style.Theme_WeatherApp
        )

        // Click FAB
        onView(withId(R.id.searchFab)).perform(click())

        // Verify dialog appears
        onView(withText("Search City")).check(matches(isDisplayed()))
        onView(withText("Search")).check(matches(isDisplayed()))
        onView(withText("Cancel")).check(matches(isDisplayed()))

        // PROBLEM: We can't test what happens when user searches
        // because it would make a REAL API call!
        // Without DI, we can't inject a fake API service!
    }

    /**
     * Test 5: IMPOSSIBLE TEST - Can't test API scenarios
     *
     * How would we test:
     * - User searches for city → API succeeds → city added to list
     * - User searches for city → API fails → error message shown
     * - User searches for city → no internet → cached data shown
     *
     * IMPOSSIBLE without DI because:
     * - Can't mock API (RetrofitInstance is singleton)
     * - Can't control network state
     * - Making real API calls in tests is bad practice (slow, flaky, costs money)
     */
    // @Test
    // fun searchCityWithApiSuccessAddsCityToList() {
    //     // IMPOSSIBLE: Can't mock API to return specific data
    //     // IMPOSSIBLE: Would make real network call
    //     // IMPOSSIBLE: Depends on API key, network state, API availability
    // }

    /**
     * Test 6: IMPOSSIBLE TEST - Can't test error handling
     */
    // @Test
    // fun searchCityWithApiFailureShowsErrorMessage() {
    //     // IMPOSSIBLE: Can't force API to fail
    //     // IMPOSSIBLE: Can't test offline behavior
    // }

    /**
     * Test 7: DISABLED - Click city item crashes without NavController
     *
     * PAIN POINT #7: This test FAILS because launchFragmentInContainer doesn't set up Navigation
     * Error: "View does not have a NavController set"
     *
     * Without Hilt:
     * - Need complex setup to attach TestNavController
     * - Error-prone, lots of boilerplate
     *
     * With Hilt (we'll add later):
     * - Inject TestNavController easily
     * - Clean, simple test
     */
    // @Test  // COMMENTED OUT - demonstrates navigation limitation without DI
    fun clickCityItemNavigatesToDetail() {
        // Arrange: Add test data
        runBlocking {
            database.weatherDao().insertWeather(
                WeatherEntity(
                    cityName = "Tokyo",
                    temperatureCelsius = 22.0,
                    description = "Rainy",
                    humidity = 90,
                    windSpeed = 8.0
                )
            )
        }

        // Act: Launch fragment
        launchFragmentInContainer<CityListFragment>(
            themeResId = R.style.Theme_WeatherApp
        )

        // Click on city
        onView(withText("Tokyo")).perform(click())

        // PROBLEM: Hard to verify navigation happened
        // - Would need to set up TestNavController
        // - Without Hilt, hard to inject mock NavController
        // - This test is incomplete!

        // Assert: (Can't easily verify - this is a limitation!)
        // With Hilt, we could inject a TestNavController and verify
    }
}
