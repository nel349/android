package com.norman.weatherapp.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.norman.weatherapp.ui.compose.screens.AboutScreen
import com.norman.weatherapp.ui.compose.screens.WeatherHistoryScreen
import com.norman.weatherapp.ui.navigation.Screen
import com.norman.weatherapp.ui.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * ComposeActivity - Host for Compose screens with Navigation
 *
 * HOW IT WORKS:
 * - ComponentActivity (not AppCompatActivity) for Compose
 * - setContent { } replaces setContentView(R.layout.xxx)
 * - MaterialTheme provides Material Design 3 theming
 * - NavHost manages navigation between Compose screens
 *
 * NAVIGATION:
 * - XML Fragment → Intent → ComposeActivity (this file) → NavHost → Compose screens
 * - NavHost manages: About ↔ History ↔ (future screens)
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
                // COMPOSE NAVIGATION:
                // Create NavController to manage navigation state
                val navController = rememberNavController()

                // NavHost defines the navigation graph
                ComposeNavGraph(
                    navController = navController,
                    onClose = { finish() }
                )
            }
        }
    }
}

/**
 * Compose Navigation Graph
 *
 * NAVIGATION CONCEPTS:
 * - NavHost = Container that swaps composables based on route
 * - NavController = Manages navigation state and actions
 * - composable("route") = Defines a destination in the graph
 * - startDestination = First screen to show
 *
 * HOW IT WORKS:
 * 1. NavHost starts at "about" route
 * 2. Shows AboutScreen
 * 3. User clicks "View Weather History" → navController.navigate("history")
 * 4. NavHost swaps to WeatherHistoryScreen
 * 5. User presses back button → navController.popBackStack() → returns to About
 *
 * NAVIGATION ACTIONS:
 * - navController.navigate(route) = Go to a screen
 * - navController.popBackStack() = Go back to previous screen
 * - onClose() = finish() activity (exit app)
 *
 * COMPARISON TO XML NAVIGATION:
 * - XML: <fragment> tags in nav_graph.xml
 * - Compose: composable {} blocks in code
 * - Both create a navigation graph, Compose is more flexible
 */
@Composable
fun ComposeNavGraph(
    navController: NavHostController,
    onClose: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.About.route
    ) {
        // About Screen destination
        composable(route = Screen.About.route) {
            AboutScreen(
                onNavigateToHistory = {
                    // Navigate to History screen
                    navController.navigate(Screen.History.route)
                },
                onClose = onClose
            )
        }

        // Weather History Screen destination
        composable(route = Screen.History.route) {
            WeatherHistoryScreen(
                onNavigateBack = {
                    // Navigate back to About screen
                    navController.popBackStack()
                }
            )
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
