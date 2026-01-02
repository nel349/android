package com.norman.weatherapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.norman.weatherapp.data.repository.Result
import com.norman.weatherapp.ui.viewmodel.WeatherViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * WeatherViewModel Tests WITHOUT Dependency Injection
 *
 * PAIN POINTS YOU'LL SEE:
 * 1. Need Robolectric to get Application context (slow, heavyweight)
 * 2. Can't mock database - uses REAL Room database
 * 3. Can't mock API - makes REAL network calls (or fails)
 * 4. Hard to test error scenarios
 * 5. Tests are flaky (depend on network, database state)
 * 6. Setup is complicated and verbose
 *
 * After we add Hilt, you'll see how much simpler this becomes!
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)  // PAIN POINT #1: Need Robolectric for Application context
@Config(manifest = Config.NONE)  // Don't need manifest for ViewModel tests
class WeatherViewModelTest {

    // Rule to make LiveData/Flow work synchronously in tests
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Test dispatcher for coroutines
    private val testDispatcher = StandardTestDispatcher()

    // PAIN POINT #2: Must use real Application (can't inject fake dependencies)
    private lateinit var application: Application
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        // Set test dispatcher for coroutines
        Dispatchers.setMain(testDispatcher)

        // PAIN POINT #3: Robolectric setup - heavyweight and slow
        application = RuntimeEnvironment.getApplication()

        // PAIN POINT #4: ViewModel creates its own dependencies
        // We have NO control over database, API, repository
        // ViewModel will use REAL database and REAL network calls
        viewModel = WeatherViewModel(application)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Test 1: Fetch weather with empty city name
     *
     * This test works because it doesn't touch database/network
     */
    @Test
    fun `fetchWeather with empty city name returns error`() = runTest {
        // Arrange: empty city name
        val emptyCity = ""

        // Act: fetch weather
        viewModel.fetchWeather(emptyCity)

        // Advance time to let coroutines finish
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert: should be error state
        viewModel.weatherState.test {
            val state = awaitItem()
            assertTrue(state is Result.Error)
            assertEquals("City name cannot be empty", (state as Result.Error).message)




        }
    }

    /**
     * Test 2: Fetch weather with blank city name
     */
    @Test
    fun `fetchWeather with blank city name returns error`() = runTest {
        // Arrange
        val blankCity = "   "

        // Act
        viewModel.fetchWeather(blankCity)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        viewModel.weatherState.test {
            val state = awaitItem()
            assertTrue(state is Result.Error)
        }
    }

    /**
     * Test 3: Fetch weather with valid city
     *
     * PAIN POINT #5: This test is PROBLEMATIC:
     * - Makes REAL API call (requires internet)
     * - Uses REAL database (Room in-memory or on-disk)
     * - Depends on API key being set
     * - Flaky (fails if network is down)
     * - Slow (network latency)
     *
     * We CAN'T mock the repository because ViewModel creates it internally!
     * We CAN'T mock the API because RetrofitInstance is a singleton!
     *
     * This is why we need Dependency Injection!
     */
    @Test
    fun `fetchWeather with valid city makes API call`() = runTest {
        // PROBLEM: This test will make a REAL API call
        // If network is down, test fails
        // If API key is missing, test fails
        // We have NO way to mock the API!

        // Arrange
        val validCity = "London"

        // Act
        viewModel.fetchWeather(validCity)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        // PROBLEM: We can't guarantee what state we'll get
        // - Success if API works
        // - Error if network fails
        // - Error if API key missing
        // This makes the test unreliable!

        viewModel.weatherState.test {
            val state = awaitItem()
            // We can only check that we got SOME result
            // Can't test specific behavior without mocking
            assertTrue(state is Result.Success || state is Result.Error || state is Result.Loading)
        }
    }

    /**
     * Test 4: IMPOSSIBLE TEST - Can't test error handling
     *
     * How would we test "API fails but cache exists"?
     * - We can't mock the API to force failure
     * - We can't mock the database to provide cached data
     * - We can't control network conditions
     *
     * This scenario is IMPOSSIBLE to test without DI!
     */
    // @Test
    // fun `fetchWeather returns cached data when API fails`() = runTest {
    //     // IMPOSSIBLE: Can't mock API to fail
    //     // IMPOSSIBLE: Can't mock database to have cached data
    //     // IMPOSSIBLE: Can't test this scenario!
    // }

    /**
     * Test 5: IMPOSSIBLE TEST - Can't test loading state
     *
     * How would we test that Loading state appears before Success?
     * - API call happens too fast (or too slow)
     * - Can't control timing without mocking
     * - Flaky test that sometimes passes, sometimes fails
     */
    // @Test
    // fun `fetchWeather emits Loading then Success states`() = runTest {
    //     // IMPOSSIBLE: Can't control API timing
    //     // IMPOSSIBLE: Race condition between test and API call
    // }
}
