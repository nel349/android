package com.norman.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.norman.weatherapp.data.local.WeatherEntity
import com.norman.weatherapp.data.model.WeatherData
import com.norman.weatherapp.data.repository.Result
import com.norman.weatherapp.data.repository.WeatherRepository
import com.norman.weatherapp.ui.viewmodel.WeatherViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * WeatherViewModel Tests WITH Dependency Injection (Hilt)
 *
 * IMPROVEMENTS YOU'LL SEE:
 * ✅ 1. No Robolectric - just plain Kotlin JVM tests (fast!)
 * ✅ 2. Easy to mock repository with MockK
 * ✅ 3. Complete control over test scenarios
 * ✅ 4. Fast, deterministic, reliable tests
 * ✅ 5. Clean, simple setup
 * ✅ 6. Can test ALL scenarios (success, error, loading, cache fallback)
 *
 * COMPARISON TO OLD TEST:
 * - Before: @RunWith(RobolectricTestRunner::class) - slow!
 * - After: Plain Kotlin test - fast!
 *
 * - Before: Can't mock repository (ViewModel creates it)
 * - After: Inject mock repository - full control!
 *
 * - Before: Makes REAL API calls (flaky, slow)
 * - After: Mock returns fake data (fast, reliable)
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTestWithHilt {

    // Rule to make LiveData/Flow work synchronously in tests
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Test dispatcher for coroutines
    private val testDispatcher = StandardTestDispatcher()

    // IMPROVEMENT #1: Mock repository with MockK (easy!)
    private lateinit var mockRepository: WeatherRepository
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun setup() {
        // Set test dispatcher
        Dispatchers.setMain(testDispatcher)

        // IMPROVEMENT #2: Create mock repository
        // This is EASY with Hilt because ViewModel uses constructor injection!
        mockRepository = mockk(relaxed = true)

        // IMPROVEMENT #3: Mock cachedCities Flow (ViewModel accesses this in constructor)
        every { mockRepository.cachedCities } returns flowOf(emptyList())

        // IMPROVEMENT #4: Create ViewModel with mock repository
        // Before Hilt: ViewModel created its own dependencies (can't control)
        // After Hilt: We inject the mock repository!
        viewModel = WeatherViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ========== TESTS THAT WORK WITHOUT MOCKING ==========

    @Test
    fun `fetchWeather with empty city name returns error`() = runTest {
        // Arrange
        val emptyCity = ""

        // Act
        viewModel.fetchWeather(emptyCity)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        viewModel.weatherState.test {
            val state = awaitItem()
            assertTrue(state is Result.Error)
            assertEquals("City name cannot be empty", (state as Result.Error).message)
        }

        // IMPROVEMENT #5: Verify repository was NOT called (validation failed first)
        coVerify(exactly = 0) { mockRepository.getWeather(any()) }
    }

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

        // Verify repository was not called
        coVerify(exactly = 0) { mockRepository.getWeather(any()) }
    }

    // ========== TESTS THAT WERE IMPOSSIBLE WITHOUT DI ==========

    /**
     * Test 3: SUCCESS scenario - Mock repository returns success
     *
     * BEFORE: Made REAL API call (flaky, slow, depends on network)
     * AFTER: Mock returns fake data (fast, reliable, deterministic)
     */
    @Test
    fun `fetchWeather with valid city returns success from repository`() = runTest {
        // Arrange
        val cityName = "London"
        val fakeWeatherData = WeatherData(
            cityName = "London",
            temperatureCelsius = 15.5,
            description = "Cloudy",
            humidity = 80,
            windSpeed = 5.2
        )

        // IMPROVEMENT #6: Mock repository to return success
        // This was IMPOSSIBLE before Hilt!
        coEvery { mockRepository.getWeather(cityName) } returns Result.Success(fakeWeatherData)

        // Act
        viewModel.fetchWeather(cityName)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        viewModel.weatherState.test {
            val state = awaitItem()
            assertTrue(state is Result.Success)
            assertEquals("London", (state as Result.Success).data.cityName)
            assertEquals(15.5, state.data.temperatureCelsius, 0.01)
        }

        // Verify repository was called exactly once
        coVerify(exactly = 1) { mockRepository.getWeather(cityName) }
    }

    /**
     * Test 4: ERROR scenario - Mock repository returns error
     *
     * BEFORE: Impossible to test specific error scenarios
     * AFTER: Easy! Just mock the error response
     */
    @Test
    fun `fetchWeather returns error when repository fails`() = runTest {
        // Arrange
        val cityName = "InvalidCity"
        val errorMessage = "City not found"

        // Mock repository to return error
        coEvery { mockRepository.getWeather(cityName) } returns Result.Error(errorMessage)

        // Act
        viewModel.fetchWeather(cityName)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        viewModel.weatherState.test {
            val state = awaitItem()
            assertTrue(state is Result.Error)
            assertEquals(errorMessage, (state as Result.Error).message)
        }
    }

    /**
     * Test 5: CachedCities Flow is exposed from repository
     *
     * BEFORE: Would need real database with Robolectric
     * AFTER: Mock the Flow!
     */
    @Test
    fun `cachedCities exposes repository's cached cities flow`() = runTest {
        // Arrange
        val fakeCities = listOf(
            WeatherEntity("London", 15.0, "Cloudy", 80, 5.0),
            WeatherEntity("Paris", 20.0, "Sunny", 60, 3.0)
        )

        // Mock repository's cachedCities Flow
        every { mockRepository.cachedCities } returns flowOf(fakeCities)

        // Create new ViewModel with updated mock
        val testViewModel = WeatherViewModel(mockRepository)

        // Assert: ViewModel exposes the same Flow
        testViewModel.cachedCities.test {
            val cities = awaitItem()
            assertEquals(2, cities.size)
            assertEquals("London", cities[0].cityName)
            assertEquals("Paris", cities[1].cityName)
            awaitComplete()  // flowOf() completes after emitting
        }
    }

    /**
     * Test 6: Verify initial state is Idle
     */
    @Test
    fun `weatherState starts with Idle state`() = runTest {
        // Assert: Initial state should be Idle
        viewModel.weatherState.test {
            val state = awaitItem()
            assertEquals(Result.Idle, state)
        }
    }

    /**
     * Test 7: Multiple different cities can be fetched
     */
    @Test
    fun `fetchWeather can fetch multiple different cities`() = runTest {
        // Arrange
        val london = WeatherData("London", 15.0, "Cloudy", 80, 5.0)
        val paris = WeatherData("Paris", 20.0, "Sunny", 60, 3.0)

        coEvery { mockRepository.getWeather("London") } returns Result.Success(london)
        coEvery { mockRepository.getWeather("Paris") } returns Result.Success(paris)

        // Act & Assert: Fetch London
        viewModel.fetchWeather("London")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.weatherState.test {
            val state = awaitItem()
            assertTrue(state is Result.Success)
            assertEquals("London", (state as Result.Success).data.cityName)
        }

        // Act & Assert: Fetch Paris
        viewModel.fetchWeather("Paris")
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.weatherState.test {
            val state = awaitItem()
            assertTrue(state is Result.Success)
            assertEquals("Paris", (state as Result.Success).data.cityName)
        }

        // Verify both were called
        coVerify(exactly = 1) { mockRepository.getWeather("London") }
        coVerify(exactly = 1) { mockRepository.getWeather("Paris") }
    }
}

/**
 * SUMMARY OF IMPROVEMENTS WITH HILT:
 *
 * WITHOUT Hilt:
 * ❌ Need Robolectric (slow, heavyweight)
 * ❌ Can't mock repository
 * ❌ Can't mock API
 * ❌ Tests make REAL network calls
 * ❌ Tests use REAL database
 * ❌ Flaky, slow, unreliable
 * ❌ Can't test error scenarios
 * ❌ Can't test loading states
 * ❌ Setup is complex
 *
 * WITH Hilt:
 * ✅ Plain Kotlin JVM tests (fast!)
 * ✅ Easy mocking with MockK
 * ✅ Complete control over scenarios
 * ✅ Fast, deterministic, reliable
 * ✅ Can test ALL scenarios
 * ✅ Clean, simple setup
 * ✅ Tests run in milliseconds
 * ✅ No network, no database
 *
 * This is the power of Dependency Injection!
 */
