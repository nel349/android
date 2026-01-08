package com.norman.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.norman.weatherapp.data.model.WeatherData
import com.norman.weatherapp.data.repository.Result
import com.norman.weatherapp.databinding.FragmentWeatherDetailBinding
import com.norman.weatherapp.ui.viewmodel.SettingsViewModel
import com.norman.weatherapp.ui.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * WeatherDetailFragment - Shows weather details for a selected city
 *
 * FRAGMENT ARGUMENTS:
 * - Receives cityName as argument from navigation
 * - Uses SafeArgs for type-safe argument passing
 * - navArgs() delegate automatically parses arguments
 *
 * FRAGMENT vs ACTIVITY:
 * - Fragment has more complex lifecycle (onAttach, onCreate, onCreateView, onViewCreated, etc.)
 * - Fragment can be reused in different activities
 * - Fragment communicates via shared ViewModel (not direct references)
 *
 * WITH HILT:
 * - @AndroidEntryPoint enables Hilt in this Fragment
 * - activityViewModels() now gets Hilt-injected ViewModel
 */
@AndroidEntryPoint
class WeatherDetailFragment : Fragment() {

    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!

    // Shared ViewModel (shared with Activity and CityListFragment)
    private val viewModel: WeatherViewModel by activityViewModels()

    // Settings ViewModel for temperature unit preference
    private val settingsViewModel: SettingsViewModel by viewModels()

    // SafeArgs - automatically generated from nav_graph.xml
    private val args: WeatherDetailFragmentArgs by navArgs()

    // Store current temperature unit preference
    private var isCelsius: Boolean = true

    // Store current weather data to re-display when preference changes
    private var currentWeatherData: WeatherData? = null

    companion object {
        private const val TAG = "WeatherDetailFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
        observeUserPreferences()
        observeWeatherState()

        // If cityName was passed, fetch weather
        val cityName = args.cityName
        if (cityName.isNotEmpty()) {
            Log.d(TAG, "Fetching weather for: $cityName")
            viewModel.fetchWeather(cityName)
        }
    }

    /**
     * Observe user preferences for temperature unit
     *
     * TEMPERATURE UNIT INTEGRATION:
     * - Observes user's Celsius/Fahrenheit preference
     * - Updates local variable when preference changes
     * - Re-displays current weather with new unit (if weather is showing)
     *
     * KEY DIFFERENCE FROM COMPOSE:
     * - XML views don't auto-update when variables change
     * - Must manually refresh the UI by calling showWeather() again
     * - Compose would automatically recompose when state changes
     */
    private fun observeUserPreferences() {
        viewLifecycleOwner.lifecycleScope.launch {
            settingsViewModel.userPreferences.collect { preferences ->
                isCelsius = preferences.isCelsius

                // Re-display current weather with new temperature unit
                // This makes the UI update immediately when user changes preference
                currentWeatherData?.let { data ->
                    showWeather(data)
                }
            }
        }
    }

    /**
     * Setup click listeners
     */
    private fun setupClickListeners() {
        binding.fetchWeatherButton.setOnClickListener {
            val cityName = binding.cityInput.text.toString().trim()
            viewModel.fetchWeather(cityName)
        }
    }

    /**
     * Observe weather state from ViewModel
     * Same logic as old MainActivity
     */
    private fun observeWeatherState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.weatherState.collect { result ->
                Log.d(TAG, "Weather state changed: $result")

                when (result) {
                    is Result.Idle -> showIdle()
                    is Result.Loading -> showLoading()
                    is Result.Success -> showWeather(result.data)
                    is Result.Error -> showError(result.message)
                }
            }
        }
    }

    // ========== UI UPDATE METHODS (same as old MainActivity) ==========

    private fun showIdle() {
        currentWeatherData = null  // Clear stored data
        binding.loadingProgressBar.visibility = View.GONE
        binding.weatherCard.visibility = View.GONE
        binding.errorText.visibility = View.GONE
    }

    private fun showLoading() {
        // Don't clear currentWeatherData - keep showing previous data while loading
        binding.loadingProgressBar.visibility = View.VISIBLE
        binding.weatherCard.visibility = View.GONE
        binding.errorText.visibility = View.GONE
    }

    private fun showWeather(data: WeatherData) {
        // Store current weather data for re-display when preference changes
        currentWeatherData = data

        // Hide loading and error
        binding.loadingProgressBar.visibility = View.GONE
        binding.errorText.visibility = View.GONE

        // Show weather card
        binding.weatherCard.visibility = View.VISIBLE

        // Update UI with data
        // Use user's temperature unit preference
        binding.cityNameText.text = data.cityName
        binding.temperatureText.text = data.getFormattedTemperature(isCelsius)
        binding.descriptionText.text = data.description.replaceFirstChar { it.uppercase() }
        binding.humidityText.text = data.getFormattedHumidity()
        binding.windSpeedText.text = data.getFormattedWindSpeed()
    }

    private fun showError(message: String) {
        currentWeatherData = null  // Clear stored data on error

        // Hide loading and weather card
        binding.loadingProgressBar.visibility = View.GONE
        binding.weatherCard.visibility = View.GONE

        // Show error
        binding.errorText.visibility = View.VISIBLE
        binding.errorText.text = message
    }

    /**
     * Clean up binding when view is destroyed
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
