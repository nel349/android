# Kotlin Coroutines & Flow Playground

Runnable examples for Week 1 concepts. Experiment, modify, and learn by doing!

## 🏃 How to Run

All files are runnable Kotlin programs with coroutines support via Gradle.

### Option 1: Using Gradle (Recommended)

```bash
# Navigate to playground directory
cd /Users/norman/Development/android/docs/learning/playground

# Run specific examples (fast and easy!)
./gradlew run1    # Cold Flow vs Hot Flow
./gradlew run2    # SharedFlow Example
./gradlew run3    # Collect vs CollectLatest
./gradlew run4    # Async Parallel Execution
./gradlew run5    # CoroutineScope Comparison
```

**First time setup:**
```bash
# Gradle will auto-download on first run
chmod +x gradlew  # Make gradlew executable (if needed)
./gradlew run1    # Downloads dependencies + runs example
```

### Option 2: Using IntelliJ IDEA / Android Studio

1. Open the `playground` directory as a project
2. Open any `.kt` file in `src/main/kotlin/playground/`
3. Right-click → Run '1_ColdFlowVsHotFlowKt'

### Option 3: Manual Kotlin Compilation

If you want to run without Gradle, you need to download kotlinx-coroutines library:

```bash
# Not recommended - use Gradle instead (it handles dependencies automatically)
# This is just for reference
kotlinc -cp kotlinx-coroutines-core-1.9.0.jar src/main/kotlin/playground/1_ColdFlowVsHotFlow.kt
```

---

## 📚 Examples Overview

### 1️⃣ `1_ColdFlowVsHotFlow.kt`
**Concept:** Cold Flow vs Hot Flow (StateFlow)

**What you'll learn:**
- Cold Flow: Each collector gets independent stream (like Netflix - starts when you press play)
- Hot Flow (StateFlow): Shared stream for all collectors (like live TV - join broadcast in progress)
- When to use each

**Key takeaway:**
```kotlin
// Cold Flow - new stream per collector
coldFlow().collect { }  // Each collector gets 1, 2, 3

// Hot Flow - shared stream
stateFlow.collect { }   // All collectors get same values
```

---

### 2️⃣ `2_SharedFlowExample.kt`
**Concept:** SharedFlow (events) vs StateFlow (state)

**What you'll learn:**
- SharedFlow with replay capability
- SharedFlow vs StateFlow differences
- When to use SharedFlow (events like button clicks, navigation)
- When to use StateFlow (state like weather data, user profile)

**Key takeaway:**
```kotlin
// StateFlow - always has value
val stateFlow = MutableStateFlow("Initial")
println(stateFlow.value)  // ✅ Can read value

// SharedFlow - events only
val sharedFlow = MutableSharedFlow<String>()
// sharedFlow.value  ❌ No .value property!
```

---

### 3️⃣ `3_CollectVsCollectLatest.kt`
**Concept:** `collect` vs `collectLatest`

**What you'll learn:**
- `collect`: Processes every value (waits for each to finish)
- `collectLatest`: Cancels previous, only processes latest
- Real-world search example (why collectLatest prevents wasteful API calls)

**Key takeaway:**
```kotlin
// collect - processes ALL values
flow.collect { value ->
    delay(300)  // Slow processing
    // Processes value 1, then 2, then 3, then 4
}

// collectLatest - cancels outdated work
flow.collectLatest { value ->
    delay(300)  // Slow processing
    // Only processes value 4 (others cancelled)
}
```

**Use collectLatest for:** Search, autocomplete, user typing

---

### 4️⃣ `4_AsyncParallelExecution.kt`
**Concept:** Sequential vs Parallel execution with `async`

**What you'll learn:**
- Sequential: Tasks run one after another (~3 seconds for 3x 1-second tasks)
- Parallel: Tasks run simultaneously (~1 second for 3x 1-second tasks)
- `launch` vs `async` (fire-and-forget vs get result back)
- Real-world dashboard loading example

**Key takeaway:**
```kotlin
// Sequential - SLOW (3 seconds total)
val user = fetchUser()      // 1 second
val posts = fetchPosts()    // 1 second
val comments = fetchComments()  // 1 second

// Parallel - FAST (1 second total)
val userDeferred = async { fetchUser() }
val postsDeferred = async { fetchPosts() }
val commentsDeferred = async { fetchComments() }
val user = userDeferred.await()  // All running at same time!
```

---

### 5️⃣ `5_CoroutineScopeComparison.kt`
**Concept:** CoroutineScope lifecycle and cancellation

**What you'll learn:**
- GlobalScope: Lives forever (dangerous, use sparingly!)
- Custom CoroutineScope: You control lifecycle
- Structured Concurrency: Parent-child relationship
- `coroutineScope` vs `supervisorScope`
- Android scopes: `lifecycleScope`, `viewModelScope`

**Key takeaway:**
```kotlin
// GlobalScope - lives forever ⚠️
GlobalScope.launch { }  // No automatic cleanup!

// Custom scope - you control
val scope = CoroutineScope(Dispatchers.Default)
scope.launch { /* work */ }
scope.cancel()  // Cancel ALL jobs

// Structured concurrency
coroutineScope {
    launch { /* child 1 */ }
    launch { /* child 2 */ }
    // Waits for ALL children
}

// Android - auto-cancels on destroy
lifecycleScope.launch { /* work */ }
viewModelScope.launch { /* work */ }
```

---

## 📖 `ANDROID_CONCEPTS_REFERENCE.md`

Reference guide for Android-specific concepts that require the Android framework (can't run standalone):

- **Configuration Changes & State Saving**
  - Screen rotation (Activity destroy/recreate)
  - `onSaveInstanceState` / `Bundle`
  - ViewModel (survives rotation)

- **RecyclerView Fundamentals**
  - Adapter, ViewHolder, LayoutManager
  - Why RecyclerView vs ListView
  - Complete implementation example

- **Layout Types Comparison**
  - LinearLayout (vertical/horizontal stacking)
  - ConstraintLayout (flexible, modern)
  - FrameLayout (overlays)
  - When to use each

**Use this as a quick reference when you encounter these in Week 2!**

---

## 🎯 How to Learn from These

1. **Run the example** - See the output
2. **Read the comments** - Understand what's happening
3. **Modify the code** - Break things, fix them
4. **Experiment** - Change delays, add more tasks, try different patterns

**Examples to try:**

- **1_ColdFlowVsHotFlow.kt:** Add a 3rd collector, change delays
- **2_SharedFlowExample.kt:** Change replay value to 0, 1, 5
- **3_CollectVsCollectLatest.kt:** Make search faster (100ms delay), see difference
- **4_AsyncParallelExecution.kt:** Add a 4th parallel task, measure time
- **5_CoroutineScopeComparison.kt:** Remove SupervisorJob, see cascading failure

---

## 🔗 Connection to Weather App

These concepts are used in the Weather app you built:

| Example | Weather App Usage |
|---------|-------------------|
| 1_ColdFlowVsHotFlow | `StateFlow` for `_weatherState` |
| 2_SharedFlowExample | Could use SharedFlow for one-time events (Snackbar messages) |
| 3_CollectVsCollectLatest | Could use collectLatest for search-as-you-type city input |
| 4_AsyncParallelExecution | Repository uses `suspend` functions |
| 5_CoroutineScopeComparison | `lifecycleScope` in MainActivity |

**Weather app file:** `/Users/norman/Development/android/docs/WeatherApp/app/src/main/java/com/norman/weatherapp/MainActivity.kt`

```kotlin
// StateFlow (Example 1)
private val _weatherState = MutableStateFlow<Result<WeatherData>>(Result.Loading)

// lifecycleScope (Example 5)
lifecycleScope.launch {
    weatherState.collect { result -> /* ... */ }
}

// suspend function / async (Example 4)
suspend fun getWeather(city: String): Result<WeatherData>
```

---

## ✅ Week 1 Completion Checklist

After running all examples, you should understand:

- [x] Cold Flow vs Hot Flow (StateFlow)
- [x] SharedFlow vs StateFlow (events vs state)
- [x] collect vs collectLatest (when to cancel outdated work)
- [x] async parallel execution (speed up independent operations)
- [x] CoroutineScope lifecycle (GlobalScope, custom, lifecycleScope, viewModelScope)
- [x] Structured concurrency (parent-child relationships)
- [x] When to use each pattern in real Android apps

---

## 🚀 Next Steps

**Week 2 Preview:**
- Apply these patterns in MVVM architecture
- Use ViewModel + StateFlow for configuration change survival
- Implement RecyclerView for weather forecasts
- Add Room database for offline caching
- Build multi-screen app with Navigation component

**You're ready!** You've covered the foundation - now it's time to build real features in Week 2.

---

**💡 Pro Tip:** Keep this playground directory open in a terminal. Whenever you encounter a concept in Week 2, come back here and run the example to refresh your memory!
