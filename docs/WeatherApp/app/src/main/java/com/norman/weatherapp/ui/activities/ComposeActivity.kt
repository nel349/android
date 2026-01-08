package com.norman.weatherapp.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.norman.weatherapp.ui.compose.screens.AboutScreen
import com.norman.weatherapp.ui.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * ComposeActivity - Host for Compose screens
 *
 * HOW IT WORKS:
 * - ComponentActivity (not AppCompatActivity) for Compose
 * - setContent { } replaces setContentView(R.layout.xxx)
 * - MaterialTheme provides Material Design 3 theming
 *
 * NAVIGATION:
 * - XML Fragment → Intent → ComposeActivity (this file) → AboutScreen
 *
 * THEMING:
 * - Observes user preference for dark/light mode
 * - Applies color scheme dynamically
 * - Changes apply immediately when user toggles switch
 */
@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // KEY DIFFERENCE from XML:
        // Instead of: setContentView(R.layout.activity_compose)
        // We use: setContent { @Composable content }
        setContent {
            // Get SettingsViewModel to observe dark mode preference
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val userPreferences by settingsViewModel.userPreferences.collectAsStateWithLifecycle()

            // MATERIAL3 THEMING:
            // - darkColorScheme() = Material3 dark colors
            // - lightColorScheme() = Material3 light colors
            // - Switch based on user preference
            val colorScheme = if (userPreferences.isDarkMode) {
                darkColorScheme()
            } else {
                lightColorScheme()
            }

            // MaterialTheme provides colors, typography, shapes
            // Now responds to dark mode toggle!
            MaterialTheme(colorScheme = colorScheme) {
                // AboutScreen is our @Composable function
                AboutScreen(
                    onClose = {
                        // Close button → finish activity → return to CityList
                        finish()
                    }
                )
            }
        }
    }
}

/**
 * KEY LEARNINGS:
 *
 * 1. ComponentActivity vs AppCompatActivity:
 *    - ComponentActivity = lightweight, Compose-first
 *    - AppCompatActivity = legacy support, XML views
 *
 * 2. setContent { } vs setContentView():
 *    - setContent { } = Compose (declarative)
 *    - setContentView() = XML (imperative)
 *
 * 3. MaterialTheme:
 *    - Provides design system (colors, typography, shapes)
 *    - Customizable (we're using default for now)
 *
 * 4. Lambda for onClose:
 *    - AboutScreen(onClose = { finish() })
 *    - Compose → Activity communication via callbacks
 */
