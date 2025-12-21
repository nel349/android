package com.norman.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.norman.weatherapp.databinding.ActivityMainBinding

/**
 * MainActivity - NavHost container for fragments
 *
 * SIMPLIFIED ROLE:
 * - Before: Contained all weather UI logic
 * - After: Just hosts NavHostFragment (container for fragments)
 *
 * FRAGMENTS PATTERN:
 * - MainActivity = Container (minimal logic)
 * - Fragments = Actual screens with UI logic
 * - Navigation Component = Manages fragment swapping
 *
 * Benefits:
 * - Reusable fragments
 * - Better separation of concerns
 * - Easier testing
 * - Modern Android architecture
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // That's it! NavHostFragment handles everything else
        // Fragment lifecycle, navigation, back stack - all automatic
        Log.d(TAG, "NavHostFragment loaded, navigation ready")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}