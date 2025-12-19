package com.norman.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.norman.weatherapp.data.model.WeatherData
import com.norman.weatherapp.data.repository.Result
import com.norman.weatherapp.data.repository.WeatherRepository
import com.norman.weatherapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // ViewBinding - lateinit means we'll initialize it later (before use)
    private lateinit var binding: ActivityMainBinding

    // Repository instance
    private val repository = WeatherRepository()

    // StateFlow - Hot flow that always has a value
    // MutableStateFlow = can change value (private, only we can modify)
    // StateFlow = read-only (public, others can observe)
    private val _weatherState = MutableStateFlow<Result<WeatherData>>(Result.Loading)
    val weatherState: StateFlow<Result<WeatherData>> = _weatherState

    companion object {
        private const val TAG = "MainActivity"  // Tag for Logcat filtering
    }

    // ========== ACTIVITY LIFECYCLE METHODS ==========

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Sets the root view

        // Setup UI listeners
        setupClickListeners()

        // Observe StateFlow for UI updates
        observeWeatherState()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called - Activity becoming visible")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called - Activity in foreground, user can interact")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called - Activity losing focus (e.g., dialog appears)")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called - Activity no longer visible")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called - Activity being destroyed")
    }

    // ========== UI SETUP ==========

    private fun setupClickListeners() {
        binding.fetchWeatherButton.setOnClickListener {
            Log.d(TAG, "Fetch Weather button clicked")

            val cityName = binding.cityInput.text.toString().trim()

            if (cityName.isEmpty()) {
                // Show error immediately
                _weatherState.value = Result.Error(getString(R.string.empty_city_error))
            } else {
                // Fetch weather using coroutines
                fetchWeather(cityName)
            }
        }
    }

    // ========== COROUTINES & FLOW ==========

    /**
     * Observe StateFlow and update UI based on state changes
     * Uses lifecycleScope.launch - automatically cancelled when Activity dies
     */
    private fun observeWeatherState() {
        lifecycleScope.launch {
            // collect = listens to StateFlow emissions
            // This suspends and collects values forever (until scope is cancelled)
            weatherState.collect { result ->
                Log.d(TAG, "Weather state changed: $result")

                when (result) {
                    is Result.Loading -> showLoading()
                    is Result.Success -> showWeather(result.data)
                    is Result.Error -> showError(result.message)
                }
            }
        }
    }

    /**
     * Fetch weather data using coroutines
     * lifecycleScope.launch - tied to Activity lifecycle
     */
    private fun fetchWeather(city: String) {
        lifecycleScope.launch {  // Launch coroutine on Main thread
            Log.d(TAG, "Fetching weather for: $city")

            // Update state to Loading
            _weatherState.value = Result.Loading

            // Call repository (suspend function)
            // Repository switches to Dispatchers.IO internally
            val result = repository.getWeather(city)

            // Back on Main thread automatically
            _weatherState.value = result
        }
    }

    // ========== UI UPDATE METHODS ==========

    private fun showLoading() {
        Log.d(TAG, "Showing loading state")
        binding.loadingProgressBar.visibility = View.VISIBLE
        binding.weatherCard.visibility = View.GONE
        binding.errorText.visibility = View.GONE
    }

    private fun showWeather(data: WeatherData) {
        Log.d(TAG, "Showing weather: $data")

        // Hide loading and error
        binding.loadingProgressBar.visibility = View.GONE
        binding.errorText.visibility = View.GONE

        // Show weather card
        binding.weatherCard.visibility = View.VISIBLE

        // Update UI with data
        binding.cityNameText.text = data.cityName
        binding.temperatureText.text = data.getFormattedTemperature()
        binding.descriptionText.text = data.description.replaceFirstChar { it.uppercase() }
        binding.humidityText.text = data.getFormattedHumidity()
        binding.windSpeedText.text = data.getFormattedWindSpeed()
    }

    private fun showError(message: String) {
        Log.e(TAG, "Showing error: $message")

        // Hide loading and weather card
        binding.loadingProgressBar.visibility = View.GONE
        binding.weatherCard.visibility = View.GONE

        // Show error
        binding.errorText.visibility = View.VISIBLE
        binding.errorText.text = message
    }
}