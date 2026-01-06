package com.norman.weatherapp.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.norman.weatherapp.ui.compose.screens.AboutScreen
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
 */
@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // KEY DIFFERENCE from XML:
        // Instead of: setContentView(R.layout.activity_compose)
        // We use: setContent { @Composable content }
        setContent {
            // MaterialTheme provides colors, typography, shapes
            MaterialTheme {
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
