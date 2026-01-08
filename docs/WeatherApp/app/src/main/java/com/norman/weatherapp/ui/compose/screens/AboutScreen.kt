package com.norman.weatherapp.ui.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.norman.weatherapp.data.preferences.UserPreferences
import com.norman.weatherapp.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)

/**
 * About Screen - Stateful Container
 *
 * LEARNING GOALS:
 * ✅ @Composable functions
 * ✅ STATE HOISTING (ViewModel owns state)
 * ✅ collectAsStateWithLifecycle() (StateFlow -> Compose State)
 * ✅ Unidirectional data flow (events up, state down)
 * ✅ STATEFUL vs STATELESS composables
 *
 * ARCHITECTURE PATTERN:
 * - AboutScreen (this) = Stateful container (has ViewModel)
 * - AboutScreenContent = Stateless UI (just receives data and callbacks)
 * - Benefits: Can preview UI without ViewModel, easier to test
 */
@Composable
fun AboutScreen(
    onNavigateToHistory: () -> Unit = {},
    onClose: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    // STATE HOISTING IN ACTION:
    // Read state from ViewModel and pass down to content
    val userPreferences by viewModel.userPreferences.collectAsStateWithLifecycle()

    // Stateless content composable receives:
    // 1. State (userPreferences) - flows DOWN
    // 2. Callbacks (onDarkModeChange, etc.) - events flow UP
    AboutScreenContent(
        userPreferences = userPreferences,
        onDarkModeChange = viewModel::updateDarkMode,
        onTemperatureUnitChange = viewModel::updateTemperatureUnit,
        onNavigateToHistory = onNavigateToHistory,
        onClose = onClose
    )
}

/**
 * About Screen Content - Stateless UI
 *
 * LEARNING: STATELESS COMPOSABLES
 * - No ViewModel dependency
 * - No remember/mutableStateOf (except for local UI state like dialog visibility)
 * - Just receives data and callbacks as parameters
 * - Easy to preview, test, and reuse
 *
 * BENEFITS:
 * - Preview works without Hilt/ViewModel
 * - Easier to test (just pass fake data)
 * - Reusable (can be used in different contexts)
 * - Clear separation of concerns
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutScreenContent(
    userPreferences: UserPreferences,
    onDarkModeChange: (Boolean) -> Unit,
    onTemperatureUnitChange: (Boolean) -> Unit,
    onNavigateToHistory: () -> Unit,
    onClose: () -> Unit
) {
    // LOCAL UI STATE (doesn't need ViewModel)
    // Dialog visibility is local to this screen, not persisted
    var showLicensesDialog by remember { mutableStateOf(false) }

    // UI STRUCTURE
    Scaffold(
        modifier = Modifier.padding(all = 12.dp),
        topBar = {
            // Top app bar
            CenterAlignedTopAppBar(
                title = { Text("About") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            Text(
                text = "Weather App",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Version 1.0",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider()

            // Settings Section
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start)
            )

            // Dark Mode Toggle
            // UNIDIRECTIONAL DATA FLOW:
            // 1. Display: checked = userPreferences.isDarkMode (state DOWN)
            // 2. Event: onCheckedChange calls callback (event UP)
            SettingRow(
                label = "Dark Mode",
                description = "Switch between light and dark theme",
                checked = userPreferences.isDarkMode,
                onCheckedChange = onDarkModeChange
            )

            // Temperature Unit Toggle
            SettingRow(
                label = if (userPreferences.isCelsius) "Celsius" else "Fahrenheit",
                description = "Temperature unit preference",
                checked = userPreferences.isCelsius,
                onCheckedChange = onTemperatureUnitChange
            )

            Spacer(modifier = Modifier.weight(1f))

            // Same weight for spacing example
            Spacer(modifier = Modifier.weight(1f))

            // Licenses Button
            OutlinedButton(
                onClick = { showLicensesDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Licenses")
            }

            // Weather History Button
            // COMPOSE NAVIGATION IN ACTION:
            // Clicking this button calls onNavigateToHistory callback
            // Which triggers navController.navigate(Screen.History.route)
            OutlinedButton(
                onClick = onNavigateToHistory,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Weather History")
            }

            // Close Button
            Button(
                onClick = onClose,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Close")
            }

            // Credits
            Text(
                text = "Made with ❤️ using Jetpack Compose",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Licenses Dialog
        if (showLicensesDialog) {
            LicensesDialog(onDismiss = { showLicensesDialog = false })
        }
    }
}

/**
 * Licenses Dialog Component
 *
 * LEARNING: Dialogs can be extracted into reusable composables
 * Makes the component easier to preview and test
 */
@Composable
private fun LicensesDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Open Source Licenses") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LicenseItem("Retrofit", "Square, Inc.", "Apache 2.0")
                LicenseItem("Room", "Google", "Apache 2.0")
                LicenseItem("Hilt", "Google", "Apache 2.0")
                LicenseItem("Compose", "Google", "Apache 2.0")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

/**
 * Reusable Setting Row Component
 *
 * LEARNING: Composable functions can be extracted into reusable components
 */
@Composable
private fun SettingRow(
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

/**
 * License Item Component
 */
@Composable
private fun LicenseItem(name: String, author: String, license: String) {
    Column {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "$author • $license",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Preview - See in Android Studio Design tab!
 *
 * LEARNING: Previewing stateless composables
 * - AboutScreenContent is stateless (no ViewModel)
 * - We can preview it by passing fake data
 * - This is why separating stateful/stateless is powerful!
 *
 * PATTERN:
 * - Production: AboutScreen (with ViewModel) → AboutScreenContent
 * - Preview: AboutScreenContent with fake data
 */
@Preview(showBackground = true, name = "Light Mode")
@Composable
private fun AboutScreenPreviewLight() {
    MaterialTheme {
        AboutScreenContent(
            userPreferences = UserPreferences(
                isDarkMode = false,
                isCelsius = true
            ),
            onDarkModeChange = {},
            onTemperatureUnitChange = {},
            onNavigateToHistory = {},
            onClose = {}
        )
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
private fun AboutScreenPreviewDark() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        AboutScreenContent(
            userPreferences = UserPreferences(
                isDarkMode = true,
                isCelsius = false
            ),
            onDarkModeChange = {},
            onTemperatureUnitChange = {},
            onNavigateToHistory = {},
            onClose = {}
        )
    }
}

/**
 * Preview for Licenses Dialog
 *
 * LEARNING: You can preview individual components like dialogs
 * This makes it easy to see how they look without running the app
 */
@Preview(showBackground = true)
@Composable
private fun LicensesDialogPreview() {
    MaterialTheme {
        LicensesDialog(onDismiss = { })
    }
}