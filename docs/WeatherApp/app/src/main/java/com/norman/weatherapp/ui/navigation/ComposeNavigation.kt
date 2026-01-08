package com.norman.weatherapp.ui.navigation

/**
 * Compose Navigation Routes
 *
 * NAVIGATION CONCEPTS:
 * - Route = String identifier for a destination (like "about", "history")
 * - Similar to XML navigation's destination IDs, but type-safe with sealed class
 *
 * SEALED CLASS PATTERN:
 * - Sealed class = restricted hierarchy (all subclasses known at compile time)
 * - Perfect for navigation routes (finite set of destinations)
 * - Type-safe compared to raw strings
 *
 * WHY NOT JUST STRINGS?
 * - Raw strings: navController.navigate("histori") // Typo! Runtime crash
 * - Sealed class: navController.navigate(Screen.History.route) // Compile-time safety
 */
sealed class Screen(val route: String) {
    /**
     * About Screen - Settings and app info
     */
    object About : Screen("about")

    /**
     * Weather History Screen - List of past weather searches
     */
    object History : Screen("history")
}

/**
 * LEARNING NOTES:
 *
 * Routes are like URLs for your app:
 * - "about" → AboutScreen
 * - "history" → WeatherHistoryScreen
 *
 * Later, you can add arguments:
 * - "detail/{cityName}" → WeatherDetailScreen with city parameter
 *
 * Compose Navigation vs XML Navigation:
 * - XML: nav_graph.xml with <fragment> and <action>
 * - Compose: NavHost {} with composable("route") {}
 * - Both achieve same goal, Compose is more programmatic
 */
