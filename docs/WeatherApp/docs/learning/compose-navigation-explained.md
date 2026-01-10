# Compose Navigation - How It Works

This document explains how navigation works in the Weather App, specifically the flow between AboutScreen and WeatherHistoryScreen.

## 📋 Table of Contents
1. [Navigation Components](#navigation-components)
2. [Step-by-Step Flow](#step-by-step-flow)
3. [Code Walkthrough](#code-walkthrough)
4. [Comparison to XML Navigation](#comparison-to-xml-navigation)
5. [Key Concepts](#key-concepts)

---

## Navigation Components

### 1. Routes (Screen.kt)
```kotlin
sealed class Screen(val route: String) {
    object About : Screen("about")
    object History : Screen("history")
}
```

**What are routes?**
- Routes are like **addresses** for your screens
- `"about"` = address for AboutScreen
- `"history"` = address for WeatherHistoryScreen
- Similar to URLs in a web browser (`/about`, `/history`)

**Why sealed class?**
- **Type-safe**: Can't make typos like `"histori"` (compiler catches errors)
- **Finite set**: All routes known at compile time
- **Better than raw strings**: `Screen.History.route` vs `"history"`

---

### 2. NavController
```kotlin
val navController = rememberNavController()
```

**What is NavController?**
- The **GPS system** for your app
- Knows where you are (current screen)
- Knows how to get places (navigate to screens)
- Remembers where you've been (back stack)

**Key NavController methods:**
```kotlin
// Go to a screen
navController.navigate("history")
navController.navigate(Screen.History.route)  // Type-safe version

// Go back to previous screen
navController.popBackStack()

// Go back to specific screen
navController.popBackStack(Screen.About.route, inclusive = false)
```

---

### 3. NavHost
```kotlin
NavHost(
    navController = navController,
    startDestination = Screen.About.route
) {
    composable(route = Screen.About.route) { /* Screen A */ }
    composable(route = Screen.History.route) { /* Screen B */ }
}
```

**What is NavHost?**
- A **container** that swaps screens based on the current route
- Think of it like a **picture frame** that changes the picture inside
- Only shows ONE screen at a time (the active route)

**How it works:**
1. NavHost looks at `navController.currentRoute`
2. Finds matching `composable(route = ...)` block
3. Shows that composable content
4. When route changes → swaps to new composable

---

## Step-by-Step Flow

### User Journey: About → History → Back

```
┌─────────────────────────────────────────────────────────────┐
│ 1. App Starts                                                │
├─────────────────────────────────────────────────────────────┤
│ ComposeActivity.onCreate()                                   │
│   ↓                                                          │
│ setContent { }  (Compose UI starts here)                    │
│   ↓                                                          │
│ navController = rememberNavController()                      │
│   ↓                                                          │
│ NavHost(startDestination = "about")                         │
│   ↓                                                          │
│ Shows AboutScreen (default route)                           │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ 2. User Clicks "View Weather History" Button                │
├─────────────────────────────────────────────────────────────┤
│ AboutScreenContent (UI Layer)                                │
│   OutlinedButton(onClick = onNavigateToHistory)             │
│                              ↓                               │
│ AboutScreen (Container)                                      │
│   AboutScreen(                                               │
│     onNavigateToHistory = { navController.navigate(...) }   │
│   )                         ↓                               │
│ ComposeNavGraph (Navigation Setup)                          │
│   AboutScreen(                                               │
│     onNavigateToHistory = {                                 │
│       navController.navigate(Screen.History.route) ✓        │
│     }                                                        │
│   )                                                          │
│                              ↓                               │
│ NavController                                                │
│   - Current route: "about"                                  │
│   - Navigate to: "history"                                  │
│   - Back stack: ["about", "history"] ← added history       │
│                              ↓                               │
│ NavHost                                                      │
│   - Detects route change                                    │
│   - Swaps from AboutScreen to WeatherHistoryScreen          │
│   - AboutScreen is NOT destroyed (kept in back stack)       │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ 3. User Sees WeatherHistoryScreen                           │
├─────────────────────────────────────────────────────────────┤
│ WeatherHistoryScreen shows:                                  │
│   - Top bar with back button                                │
│   - LazyColumn with search history                          │
│   - Clear history button (if not empty)                     │
│                                                              │
│ Back stack: ["about", "history"] ← we are here             │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ 4. User Clicks Back Button (Arrow Icon)                     │
├─────────────────────────────────────────────────────────────┤
│ WeatherHistoryContent (UI Layer)                             │
│   IconButton(onClick = onNavigateBack)                       │
│                          ↓                                   │
│ WeatherHistoryScreen (Container)                             │
│   WeatherHistoryScreen(                                      │
│     onNavigateBack = { navController.popBackStack() }       │
│   )                     ↓                                   │
│ ComposeNavGraph (Navigation Setup)                          │
│   WeatherHistoryScreen(                                      │
│     onNavigateBack = { navController.popBackStack() } ✓     │
│   )                                                          │
│                          ↓                                   │
│ NavController                                                │
│   - Current route: "history"                                │
│   - Pop back stack                                          │
│   - Back stack: ["about", "history"] → ["about"]           │
│   - New current route: "about"                              │
│                          ↓                                   │
│ NavHost                                                      │
│   - Detects route change                                    │
│   - Swaps from WeatherHistoryScreen to AboutScreen          │
│   - WeatherHistoryScreen is destroyed                       │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ 5. User Clicks "Close" Button on AboutScreen                │
├─────────────────────────────────────────────────────────────┤
│ AboutScreenContent (UI Layer)                                │
│   Button(onClick = onClose)                                  │
│                      ↓                                       │
│ ComposeNavGraph (Navigation Setup)                          │
│   AboutScreen(                                               │
│     onClose = { finish() } ✓  (from ComposeActivity)       │
│   )                                                          │
│                      ↓                                       │
│ ComposeActivity.finish()                                     │
│   - Activity destroyed                                       │
│   - App closes (returns to home screen)                     │
└─────────────────────────────────────────────────────────────┘
```

---

## Code Walkthrough

### File: `ComposeActivity.kt`

```kotlin
class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // 1. Create NavController (navigation manager)
            val navController = rememberNavController()

            // 2. Set up navigation graph
            ComposeNavGraph(
                navController = navController,
                onClose = { finish() }  // Exit app callback
            )
        }
    }
}

@Composable
fun ComposeNavGraph(
    navController: NavHostController,
    onClose: () -> Unit
) {
    // 3. NavHost defines all possible screens (navigation graph)
    NavHost(
        navController = navController,
        startDestination = Screen.About.route  // "about" = first screen
    ) {
        // 4. Define AboutScreen destination
        composable(route = Screen.About.route) {
            AboutScreen(
                // When AboutScreen wants to navigate to History...
                onNavigateToHistory = {
                    navController.navigate(Screen.History.route)
                    // ↑ This changes the route to "history"
                    // NavHost sees the change and swaps screens
                },
                // When AboutScreen wants to close app...
                onClose = onClose  // Calls finish() from activity
            )
        }

        // 5. Define History destination
        composable(route = Screen.History.route) {
            WeatherHistoryScreen(
                // When History wants to go back...
                onNavigateBack = {
                    navController.popBackStack()
                    // ↑ Removes "history" from back stack
                    // Returns to "about" screen
                }
            )
        }
    }
}
```

### File: `AboutScreen.kt`

```kotlin
@Composable
fun AboutScreen(
    onNavigateToHistory: () -> Unit = {},  // Callback for navigation
    onClose: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val userPreferences by viewModel.userPreferences.collectAsStateWithLifecycle()

    // Pass callbacks down to stateless UI
    AboutScreenContent(
        userPreferences = userPreferences,
        onDarkModeChange = viewModel::updateDarkMode,
        onTemperatureUnitChange = viewModel::updateTemperatureUnit,
        onNavigateToHistory = onNavigateToHistory,  // ← Passed down
        onClose = onClose                           // ← Passed down
    )
}

@Composable
private fun AboutScreenContent(
    userPreferences: UserPreferences,
    onDarkModeChange: (Boolean) -> Unit,
    onTemperatureUnitChange: (Boolean) -> Unit,
    onNavigateToHistory: () -> Unit,  // ← Received from parent
    onClose: () -> Unit
) {
    // ... UI code ...

    // Navigation button
    OutlinedButton(
        onClick = onNavigateToHistory,  // ← When clicked, calls callback
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("View Weather History")
    }
}
```

**Callback Chain:**
```
User clicks button
    ↓
onClick = onNavigateToHistory (AboutScreenContent)
    ↓
onNavigateToHistory callback flows UP to AboutScreen
    ↓
onNavigateToHistory callback flows UP to ComposeNavGraph
    ↓
{ navController.navigate(Screen.History.route) } executes
    ↓
NavController changes route from "about" to "history"
    ↓
NavHost detects change and swaps screens
```

---

## Comparison to XML Navigation

### XML Fragment Navigation (Old Way)

```kotlin
// In XML: nav_graph.xml
<navigation>
    <fragment id="@+id/aboutFragment" />
    <fragment id="@+id/historyFragment" />
    <action
        id="@+id/action_about_to_history"
        destination="@+id/historyFragment" />
</navigation>

// In Code: AboutFragment.kt
val action = AboutFragmentDirections.actionAboutToHistory()
findNavController().navigate(action)
```

**XML Navigation requires:**
- XML file (`nav_graph.xml`)
- Navigation component library
- SafeArgs plugin for type safety
- Fragment transaction management

### Compose Navigation (New Way)

```kotlin
// All in code - no XML!
sealed class Screen(val route: String) {
    object About : Screen("about")
    object History : Screen("history")
}

NavHost(navController, startDestination = Screen.About.route) {
    composable(Screen.About.route) { AboutScreen(...) }
    composable(Screen.History.route) { HistoryScreen(...) }
}

// Navigate
navController.navigate(Screen.History.route)
```

**Compose Navigation benefits:**
- All in Kotlin (no XML)
- Type-safe with sealed classes
- Simpler API
- Better integration with Compose

---

## Key Concepts

### 1. Back Stack

The back stack is like a **stack of plates**:

```
Initial state:
┌─────────┐
│  About  │ ← Top (current screen)
└─────────┘

After navigate("history"):
┌─────────┐
│ History │ ← Top (current screen)
├─────────┤
│  About  │
└─────────┘

After popBackStack():
┌─────────┐
│  About  │ ← Top (current screen)
└─────────┘
```

**Rules:**
- Only the **top** screen is visible
- `navigate()` **pushes** a new screen on top
- `popBackStack()` **pops** the top screen off
- Press back button → automatically calls `popBackStack()`

### 2. Unidirectional Data Flow

```
Events flow UP (callbacks):
Screen → Container → NavGraph → NavController

Data flows DOWN (parameters):
NavController → NavHost → Screen
```

**Example:**
```kotlin
// Data flows DOWN ↓
NavHost {
    composable(...) {
        WeatherHistoryScreen(
            onNavigateBack = { ... }  // Callback flows UP ↑
        )
    }
}
```

### 3. State Hoisting

Navigation state is **hoisted** to NavController:
- Screens don't know about NavController (better encapsulation)
- Screens receive callbacks (`onNavigateBack`, `onNavigateToHistory`)
- NavGraph connects screens to NavController
- Screens can be previewed without navigation

**Bad (tight coupling):**
```kotlin
@Composable
fun AboutScreen(navController: NavController) {
    Button(onClick = { navController.navigate("history") })
}
// ❌ Can't preview - needs NavController
// ❌ Tightly coupled to navigation
```

**Good (loose coupling):**
```kotlin
@Composable
fun AboutScreen(onNavigateToHistory: () -> Unit = {}) {
    Button(onClick = onNavigateToHistory)
}
// ✅ Easy to preview - just pass empty lambda
// ✅ Loosely coupled - doesn't know about navigation
```

### 4. rememberNavController()

```kotlin
val navController = rememberNavController()
```

**What does `remember` do?**
- **Survives recomposition**: NavController persists across UI updates
- Without `remember`: NavController recreated every recomposition (loses state!)
- Same as `remember { NavController() }` but optimized

**Why important?**
- Recomposition happens frequently (state changes, theme changes, etc.)
- NavController must survive recomposition to maintain back stack
- `remember` caches the instance across recompositions

### 5. Composable Destinations

```kotlin
composable(route = Screen.History.route) {
    WeatherHistoryScreen(...)
}
```

**How it works:**
- `composable { }` is a **builder function** for NavHost
- Each `composable` defines one destination in the navigation graph
- When route matches, the lambda is executed
- Only the matching destination is shown (others are not composed)

**With arguments (future enhancement):**
```kotlin
composable(
    route = "weather/{cityId}",
    arguments = listOf(navArgument("cityId") { type = NavType.IntType })
) { backStackEntry ->
    val cityId = backStackEntry.arguments?.getInt("cityId")
    WeatherDetailScreen(cityId = cityId)
}

// Navigate with argument:
navController.navigate("weather/123")
```

---

## Summary

### The Complete Flow:

1. **Setup** (ComposeActivity):
   - Create NavController with `rememberNavController()`
   - Define NavHost with start destination
   - Define all composable destinations

2. **Navigation** (User action):
   - User clicks button → callback fires
   - Callback executes `navController.navigate(route)`
   - NavController updates current route
   - NavController adds route to back stack

3. **Screen Swap** (NavHost):
   - NavHost observes NavController's current route
   - Finds matching `composable(route = ...)` block
   - Composes the new screen
   - Previous screen goes to back stack (not destroyed)

4. **Back Navigation**:
   - User clicks back → callback fires
   - Callback executes `navController.popBackStack()`
   - NavController removes current route from back stack
   - NavController sets previous route as current
   - NavHost swaps to previous screen

### Key Differences from XML:

| Aspect | XML Navigation | Compose Navigation |
|--------|---------------|-------------------|
| **Configuration** | XML file + Kotlin | Pure Kotlin |
| **Type Safety** | SafeArgs plugin | Sealed classes |
| **API** | Fragment-based | Composable-based |
| **Complexity** | High | Low |
| **Learning Curve** | Steep | Gentle |
| **Integration** | Good | Excellent (native) |

### Why Compose Navigation is Better:

1. **All in code**: No XML context switching
2. **Type-safe**: Sealed classes prevent typos
3. **Simpler**: Fewer concepts to learn
4. **Composable-first**: Designed for Compose (not retrofitted)
5. **Less boilerplate**: No actions, no SafeArgs setup
6. **Better previews**: Screens are easily previewable

---

## What's Next?

You've learned:
- ✅ Compose Navigation basics
- ✅ NavHost, NavController, Routes
- ✅ Back stack management
- ✅ Callback-based navigation
- ✅ State hoisting in navigation

**Future enhancements:**
- Navigation with arguments (`/weather/{cityId}`)
- Deep links (opening specific screen from notification)
- Bottom navigation bar (multiple top-level destinations)
- Nested navigation graphs (complex multi-module apps)
- Shared element transitions (animate between screens)
