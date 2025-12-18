package com.norman.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.norman.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // ViewBinding - lateinit means we'll initialize it later (before use)
    private lateinit var binding: ActivityMainBinding

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
                // Show error
                showError(getString(R.string.empty_city_error))
            } else {
                // For now, just log the city name
                Log.d(TAG, "User entered city: $cityName")
                showError("API not connected yet. City: $cityName")
            }
        }
    }

    // ========== HELPER METHODS ==========

    private fun showError(message: String) {
        binding.errorText.text = message
        binding.errorText.visibility = View.VISIBLE
        binding.weatherCard.visibility = View.GONE
        binding.loadingProgressBar.visibility = View.GONE
    }
}