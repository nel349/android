package com.norman.weatherapp.ui.compose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.norman.weatherapp.data.local.entities.WeatherHistoryEntity
import com.norman.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Weather History Screen - Stateful Container
 *
 * LEARNING GOALS:
 * ✅ LazyColumn (Compose's RecyclerView replacement)
 * ✅ Box layout for empty states
 * ✅ LaunchedEffect for side effects
 * ✅ State hoisting with ViewModel
 * ✅ collectAsStateWithLifecycle()
 *
 * NEW CONCEPTS:
 * - LazyColumn: Efficient list rendering (only renders visible items)
 * - items() with key: Efficiently updates list when data changes
 * - Box: Overlay/stack layout (perfect for empty states)
 * - AlertDialog: Confirmation dialogs for destructive actions
 */
@HiltViewModel
class WeatherHistoryViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    /**
     * Flow of search history from repository
     * Automatically updates when history changes
     *
     * LEARNING: StateFlow vs Flow
     * - Flow = Cold stream (starts emitting when collected)
     * - StateFlow = Hot stream (always active, has current value)
     * - stateIn() converts Flow to StateFlow for Compose
     */
    val searchHistory: StateFlow<List<WeatherHistoryEntity>> =
        repository.searchHistory.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * LEARNING: Loading state for visual feedback
     * - MutableStateFlow for internal updates
     * - StateFlow for external observation
     * - Used with LaunchedEffect and rememberCoroutineScope
     */
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    /**
     * Refresh history data
     *
     * LEARNING: Simulated refresh for demonstration
     * - In real app: would fetch fresh data from API
     * - Here: simulates network delay with delay()
     * - Shows visual loading indicator
     * - Returns success/failure message for Snackbar
     */
    suspend fun refreshHistory(): String {
        _isRefreshing.value = true

        return try {
            // Simulate network delay (2 seconds)
            delay(2000)

            // In a real app, you'd fetch fresh data here:
            // val freshData = repository.fetchLatestHistory()

            _isRefreshing.value = false
            "History refreshed successfully!"
        } catch (e: Exception) {
            _isRefreshing.value = false
            "Failed to refresh: ${e.message}"
        }
    }

    /**
     * Delete all search history
     * Called when user confirms deletion in dialog
     */
    fun clearHistory() {
        viewModelScope.launch {
            repository.deleteAllHistory()
        }
    }
}

/**
 * Weather History Screen - Stateful Container
 *
 * ARCHITECTURE:
 * - WeatherHistoryScreen (this) = Stateful (has ViewModel)
 * - WeatherHistoryContent = Stateless (receives data + callbacks)
 *
 * NEW LEARNING: LaunchedEffect + rememberCoroutineScope
 * - LaunchedEffect = Auto-run when screen appears
 * - rememberCoroutineScope = Manual async from button clicks
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHistoryScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: WeatherHistoryViewModel = hiltViewModel()
) {
    // Observe history from ViewModel
    // collectAsStateWithLifecycle() is lifecycle-aware
    // Stops collecting when screen is not visible (saves resources)
    val history by viewModel.searchHistory.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    // Snackbar host state for showing messages
    val snackbarHostState = remember { SnackbarHostState() }

    // LEARNING: LaunchedEffect - Runs when screen first appears
    // Think of this like React's useEffect(() => { ... }, [])
    // Key = Unit means "run once when composable enters composition"
    LaunchedEffect(Unit) {
        // This code runs automatically when screen opens!
        // Perfect for: loading data, analytics, showing welcome messages
        val message = viewModel.refreshHistory()
        snackbarHostState.showSnackbar(
            message = "Screen opened! $message",
            duration = SnackbarDuration.Short
        )
    }

    // Pass state and callbacks to stateless content
    WeatherHistoryContent(
        history = history,
        isRefreshing = isRefreshing,
        snackbarHostState = snackbarHostState,
        onNavigateBack = onNavigateBack,
        onClearHistory = viewModel::clearHistory,
        onRefresh = {
            // This will be called from the refresh button
            // Using rememberCoroutineScope in the content
            viewModel.refreshHistory()
        }
    )
}

/**
 * Weather History Content - Stateless UI
 *
 * BENEFITS OF STATELESS:
 * - Easy to preview with fake data
 * - Easy to test
 * - Reusable in different contexts
 * - Clear data flow
 *
 * NEW LEARNING: rememberCoroutineScope
 * - For manual async operations (button clicks)
 * - Different from LaunchedEffect (which is automatic)
 * - Tied to composable lifecycle (auto-cancelled when leaving)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeatherHistoryContent(
    history: List<WeatherHistoryEntity>,
    isRefreshing: Boolean,
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit,
    onClearHistory: () -> Unit,
    onRefresh: suspend () -> String  // Returns message for Snackbar
) {
    // LOCAL UI STATE (doesn't need ViewModel)
    // Dialog visibility is transient UI state
    var showClearDialog by remember { mutableStateOf(false) }

    // LEARNING: rememberCoroutineScope
    // Get a CoroutineScope tied to this composable's lifecycle
    // Use this for manual async operations (button clicks, swipe actions, etc.)
    // Automatically cancelled when composable leaves composition
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather History") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // LEARNING: Refresh button with rememberCoroutineScope
                    // This demonstrates manual async operations from UI events
                    IconButton(
                        onClick = {
                            // LEARNING: Launch coroutine from button click
                            // This is where rememberCoroutineScope shines!
                            // scope.launch = manual async operation
                            scope.launch {
                                val message = onRefresh()  // suspend function
                                snackbarHostState.showSnackbar(
                                    message = message,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        },
                        enabled = !isRefreshing  // Disable while refreshing
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }

                    // Clear history button (only show if history is not empty)
                    if (history.isNotEmpty()) {
                        IconButton(onClick = { showClearDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Clear History"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = {
            // LEARNING: Snackbar host for showing messages
            // Messages shown by LaunchedEffect and rememberCoroutineScope
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        // BOX LAYOUT: Perfect for showing empty state or content
        // Box stacks children on top of each other (Z-axis)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (history.isEmpty()) {
                // EMPTY STATE
                // When no history, show friendly message
                EmptyHistoryState(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                // HISTORY LIST
                // LazyColumn renders large lists efficiently
                HistoryList(
                    history = history,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // LEARNING: Loading indicator overlay
            // Shows on top of content when refreshing
            // Demonstrates visual feedback from LaunchedEffect and rememberCoroutineScope
            if (isRefreshing) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp)
                            )
                            Text("Refreshing history...")
                        }
                    }
                }
            }
        }

        // CONFIRMATION DIALOG
        // Show before destructive action (clearing history)
        if (showClearDialog) {
            ClearHistoryDialog(
                onConfirm = {
                    onClearHistory()
                    showClearDialog = false
                },
                onDismiss = {
                    showClearDialog = false
                }
            )
        }
    }
}

/**
 * History List - LazyColumn component
 *
 * LEARNING: LazyColumn
 * - Like RecyclerView in XML (efficient list rendering)
 * - Only renders visible items (lazy loading)
 * - Automatically recycles items as you scroll
 * - Much simpler than RecyclerView (no adapter, no ViewHolder!)
 *
 * LEARNING: items() with key
 * - items(list) { item -> } = for loop for LazyColumn
 * - key = { item.id } = unique identifier for each item
 * - Key helps Compose track items efficiently during updates
 * - Without key: Compose might re-render all items on change
 * - With key: Compose only updates changed items
 */
@Composable
private fun HistoryList(
    history: List<WeatherHistoryEntity>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // items() = for loop for lazy lists
        // key = unique identifier (improves performance)
        items(
            items = history,
            key = { it.id }  // Use entity ID as key
        ) { historyItem ->
            HistoryItem(historyItem = historyItem)
        }
    }
}

/**
 * History Item - Individual item in the list
 *
 * LEARNING: Card component
 * - Material3 Card provides elevation and rounded corners
 * - clickable modifier makes entire card clickable
 * - Can navigate to detail screen on click (future enhancement)
 */
@Composable
private fun HistoryItem(
    historyItem: WeatherHistoryEntity,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                // TODO: Navigate to detail screen
                // Could show full weather details when clicked
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // City name (header)
            Text(
                text = historyItem.cityName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Temperature
            Text(
                text = historyItem.getFormattedTemperature(),
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Description
            Text(
                text = historyItem.getFormattedDescription(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Timestamp (relative time)
            Text(
                text = historyItem.getRelativeTimeString(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Empty History State - Shown when no history
 *
 * LEARNING: Empty states
 * - Always show friendly message instead of blank screen
 * - Explains why nothing is showing
 * - Suggests action user can take
 */
@Composable
private fun EmptyHistoryState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No search history",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your weather searches will appear here",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Clear History Dialog - Confirmation before deletion
 *
 * LEARNING: AlertDialog
 * - Used for important decisions or confirmations
 * - Destructive actions (delete, clear) should always confirm
 * - dismissButton = "Cancel" (safe action)
 * - confirmButton = "Clear" (destructive action, uses error color)
 */
@Composable
private fun ClearHistoryDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Clear search history?")
        },
        text = {
            Text("This will permanently delete all your search history. This action cannot be undone.")
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Clear")
            }
        }
    )
}

// ========== PREVIEWS ==========

/**
 * Preview with sample data
 *
 * LEARNING: Previewing lists
 * - Create sample data for previews
 * - Test different states (empty, few items, many items)
 * - Preview stateless composables
 */
@Preview(showBackground = true, name = "History with items")
@Composable
private fun WeatherHistoryContentPreview() {
    MaterialTheme {
        WeatherHistoryContent(
            history = listOf(
                WeatherHistoryEntity(
                    id = 1,
                    cityName = "London",
                    temperatureCelsius = 15.0,
                    description = "partly cloudy",
                    timestamp = System.currentTimeMillis() - 3600000 // 1 hour ago
                ),
                WeatherHistoryEntity(
                    id = 2,
                    cityName = "Tokyo",
                    temperatureCelsius = 22.0,
                    description = "clear sky",
                    timestamp = System.currentTimeMillis() - 7200000 // 2 hours ago
                ),
                WeatherHistoryEntity(
                    id = 3,
                    cityName = "New York",
                    temperatureCelsius = 18.0,
                    description = "light rain",
                    timestamp = System.currentTimeMillis() - 86400000 // 1 day ago
                )
            ),
            isRefreshing = false,
            snackbarHostState = remember { SnackbarHostState() },
            onNavigateBack = {},
            onClearHistory = {},
            onRefresh = { "Refreshed!" }
        )
    }
}

@Preview(showBackground = true, name = "Empty history")
@Composable
private fun EmptyHistoryPreview() {
    MaterialTheme {
        WeatherHistoryContent(
            history = emptyList(),
            isRefreshing = false,
            snackbarHostState = remember { SnackbarHostState() },
            onNavigateBack = {},
            onClearHistory = {},
            onRefresh = { "Refreshed!" }
        )
    }
}

@Preview(showBackground = true, name = "Refreshing state")
@Composable
private fun RefreshingPreview() {
    MaterialTheme {
        WeatherHistoryContent(
            history = listOf(
                WeatherHistoryEntity(
                    id = 1,
                    cityName = "Paris",
                    temperatureCelsius = 20.0,
                    description = "sunny",
                    timestamp = System.currentTimeMillis()
                )
            ),
            isRefreshing = true,  // Show loading indicator
            snackbarHostState = remember { SnackbarHostState() },
            onNavigateBack = {},
            onClearHistory = {},
            onRefresh = { "Refreshed!" }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryItemPreview() {
    MaterialTheme {
        HistoryItem(
            historyItem = WeatherHistoryEntity(
                id = 1,
                cityName = "San Francisco",
                temperatureCelsius = 20.0,
                description = "foggy",
                timestamp = System.currentTimeMillis() - 1800000 // 30 minutes ago
            )
        )
    }
}

/**
 * KEY LEARNINGS SUMMARY:
 *
 * 1. LazyColumn vs RecyclerView:
 *    - LazyColumn = Compose (simpler, no adapter)
 *    - RecyclerView = XML (complex, needs adapter + ViewHolder)
 *    - Both are efficient (lazy loading)
 *
 * 2. Box layout:
 *    - Stacks children on Z-axis
 *    - Perfect for empty states, overlays, loading indicators
 *    - align(Alignment.Center) centers content
 *
 * 3. items() with key:
 *    - Efficient list updates
 *    - Key should be unique and stable
 *    - Without key: poor performance on updates
 *
 * 4. Local UI state vs ViewModel state:
 *    - Dialog visibility = local state (remember)
 *    - History data = ViewModel state (StateFlow)
 *    - Rule: Transient UI → local, Persistent/Business → ViewModel
 *
 * 5. Empty states:
 *    - Never show blank screen
 *    - Explain why empty
 *    - Suggest what to do
 *
 * 6. Confirmation dialogs:
 *    - Always confirm destructive actions
 *    - Use error color for destructive button
 *    - Provide cancel option
 *
 * 7. Previews:
 *    - Test multiple states (empty, populated)
 *    - Use fake data
 *    - Preview individual components
 *
 * 8. ⭐ LaunchedEffect (NEW LEARNING):
 *    - Runs AUTOMATICALLY when composable enters composition
 *    - Like React's useEffect with dependencies
 *    - Key changes → effect restarts
 *    - Use for: auto-loading data, analytics, showing welcome messages
 *    - Example in this file: Auto-refresh when screen opens
 *    - Automatically CANCELLED when composable leaves or key changes
 *
 * 9. ⭐ rememberCoroutineScope (NEW LEARNING):
 *    - For MANUAL async operations (button clicks)
 *    - Returns a CoroutineScope tied to composable lifecycle
 *    - Use for: button clicks, swipe actions, manual triggers
 *    - Example in this file: Refresh button in TopAppBar
 *    - Automatically CANCELLED when composable leaves composition
 *
 * 10. LaunchedEffect vs rememberCoroutineScope:
 *     ┌─────────────────────────────────────────────────────────┐
 *     │ LaunchedEffect                                          │
 *     ├─────────────────────────────────────────────────────────┤
 *     │ • AUTOMATIC - runs when composable appears              │
 *     │ • Tied to key (restarts when key changes)              │
 *     │ • Use for: data loading, initialization                │
 *     │ • Example: LaunchedEffect(Unit) { loadData() }         │
 *     └─────────────────────────────────────────────────────────┘
 *
 *     ┌─────────────────────────────────────────────────────────┐
 *     │ rememberCoroutineScope                                  │
 *     ├─────────────────────────────────────────────────────────┤
 *     │ • MANUAL - runs when YOU trigger it                    │
 *     │ • User interaction (clicks, swipes, etc.)              │
 *     │ • Use for: button actions, user-triggered async        │
 *     │ • Example: Button { scope.launch { action() } }        │
 *     └─────────────────────────────────────────────────────────┘
 *
 * VISUAL DEMONSTRATION IN THIS FILE:
 * 1. Open History screen → LaunchedEffect auto-refreshes → Snackbar shows
 * 2. Click refresh button → rememberCoroutineScope launches → Loading indicator
 * 3. Both show visual feedback so you can SEE the difference!
 *
 * REACT DEVELOPERS:
 * - LaunchedEffect ≈ useEffect(() => { ... }, [deps])
 * - rememberCoroutineScope ≈ async in onClick handlers
 * - Key difference: Kotlin coroutines are cleaner than Promises!
 */
