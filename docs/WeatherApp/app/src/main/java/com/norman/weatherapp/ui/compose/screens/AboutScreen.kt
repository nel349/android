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
import com.norman.weatherapp.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)

/**
 * About Screen - First Compose Screen!
 *
 * LEARNING GOALS:
 * ✅ @Composable functions
 * ✅ Column layout
 * ✅ State management (remember, mutableStateOf)
 * ✅ Event handling (onClick)
 * ✅ Material3 components (Switch, Button, AlertDialog)
 * ✅ STATE HOISTING (ViewModel owns state)
 * ✅ collectAsStateWithLifecycle() (StateFlow -> Compose State)
 * ✅ Unidirectional data flow (events up, state down)
 */

@Composable
fun AboutScreen(
    onClose: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    // STATE HOISTING IN ACTION:
    // BEFORE: var isDarkMode by remember { mutableStateOf(false) }
    // AFTER: Read state from ViewModel
    //
    // collectAsStateWithLifecycle():
    // - Converts StateFlow<UserPreferences> -> State<UserPreferences>
    // - Lifecycle-aware (stops collecting when screen not visible)
    // - Automatic recomposition when StateFlow emits new value
    //
    // WHY .value:
    // - viewModel.userPreferences is StateFlow<UserPreferences>
    // - collectAsStateWithLifecycle() returns State<UserPreferences>
    // - .value gives us UserPreferences object
    val userPreferences by viewModel.userPreferences.collectAsStateWithLifecycle()

    // STATE: Dialog visibility
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
            // 1. Display: checked = userPreferences.isDarkMode (state DOWN from ViewModel)
            // 2. Event: onCheckedChange calls viewModel function (event UP to ViewModel)
            SettingRow(
                label = "Dark Mode",
                description = "Switch between light and dark theme",
                checked = userPreferences.isDarkMode,
                onCheckedChange = { viewModel.updateDarkMode(it) }
            )

            // Temperature Unit Toggle
            SettingRow(
                label = if (userPreferences.isCelsius) "Celsius" else "Fahrenheit",
                description = "Temperature unit preference",
                checked = userPreferences.isCelsius,
                onCheckedChange = { viewModel.updateTemperatureUnit(it) }
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
 * LEARNING: @Preview lets you see Composables without running the app
 *
 * NOTE: Preview won't work after adding ViewModel with Hilt
 * - Previews don't have access to Hilt dependency injection
 * - To preview, you'd need to create a fake ViewModel
 * - For now, test by running the app
 * - This is a common tradeoff with state hoisting + DI
 */
// @Preview(showBackground = true)
// @Composable
// private fun AboutScreenPreview() {
//     MaterialTheme {
//         AboutScreen()
//     }
// }

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