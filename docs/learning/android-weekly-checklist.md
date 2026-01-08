# Android Weekly Learning Checklist

Detailed topic checklist for each week of your Android learning journey. Check off topics as you master them.

---

## Month 1: Foundation

### Week 1: Kotlin Refresh (15 hours)

#### Kotlin Language Topics

**Scope Functions:**
- [x] Understand `let` (null safety + transformation, returns lambda result)
- [x] Understand `run` (scoped operations, returns lambda result)
- [x] Understand `with` (grouping function calls, returns lambda result)
- [x] Understand `apply` (object configuration, returns receiver)
- [x] Understand `also` (side effects, returns receiver)
- [x] Know when to use each (decision tree)

**Null Safety:**
- [x] Safe call operator `?.`
- [x] Elvis operator `?:`
- [x] Avoid `!!` (null assertion operator)
- [x] Nullable types (`String?` vs `String`)
- [x] Safe casts (`as?`)

**Initialization:**
- [x] `lateinit` (mutable, initialized before use)
- [x] `lazy` (immutable, initialized on first access)
- [x] When to use each

**Coroutines Basics:**
- [x] `launch` (fire-and-forget, returns Job) ✅ Used in MainActivity
- [x] `async` (returns Deferred with result) ⚠️ Explained but not used
- [x] Dispatchers: `Main`, `IO`, `Default` ✅ Used Dispatchers.IO
- [x] `suspend` functions ✅ Used extensively
- [ ] `CoroutineScope` vs `GlobalScope` ⚠️ Mentioned but not deeply covered

**Flow Basics:**
- [ ] What is Flow (cold asynchronous stream) ⚠️ Not covered - only used StateFlow
- [x] StateFlow (hot, always has value) ✅ Used for UI state
- [ ] SharedFlow (hot, configurable replay) ❌ Not covered
- [ ] `collect` vs `collectLatest` ⚠️ Mentioned briefly, not demonstrated

#### Android Framework Topics

**Activity Lifecycle:**
- [x] Activity lifecycle methods (onCreate, onStart, onResume, onPause, onStop, onDestroy) ✅ Implemented with logging
- [ ] Configuration changes (screen rotation) ❌ Not tested/handled
- [ ] Saving state (onSaveInstanceState, Bundle) ❌ Not covered

**Layouts & Views (XML-based):**
- [x] ConstraintLayout (modern, flexible layout) ✅ Built full layout
- [ ] LinearLayout basics ❌ Not used
- [ ] RecyclerView fundamentals ❌ Not covered
- [x] ViewBinding (replace findViewById) ✅ Used extensively
- [x] Understanding View hierarchy ✅ Covered through layout building

**Android Manifest:**
- [x] INTERNET permission
- [x] Application configuration
- [x] Activity declarations

**Resources System:**
- [x] strings.xml (externalize strings)
- [x] colors.xml, dimens.xml
- [x] Resource qualifiers (different screen sizes)

**Context:**
- [x] Application context vs Activity context
- [x] When to use each

**Gradle Basics:**
- [x] build.gradle (app level vs project level)
- [x] Adding dependencies (Retrofit, Gson, etc.)
- [x] minSdk, targetSdk, compileSdk

**Debugging:**
- [x] Logcat basics
- [x] Breakpoints in Android Studio

**Project:**
- [x] Weather app mini-project started
- [x] OpenWeatherMap API integrated
- [x] Retrofit setup
- [x] Flow for data streaming

**LeetCode:**
- [x] 7 easy problems (Arrays & Strings) ✅ **COMPLETED**

---

### Week 2: MVVM + Components (15 hours)

#### Kotlin Language Topics

**LiveData vs Flow:**
- [x] Flow (more flexible, Kotlin-first) ✅ Already using extensively
- [x] StateFlow for UI state ✅ Used in ViewModel
- [x] When to use Flow vs LiveData ✅ Discussed: StateFlow is modern replacement

#### Android Framework Topics

**ViewModel:**
- [x] What is ViewModel (survives config changes) ✅ Implemented WeatherViewModel
- [x] `viewModelScope` for coroutines ✅ Used for fetchWeather()
- [x] ViewModel lifecycle ✅ Tested with screen rotation
- [ ] ViewModel factories (if needed) ⏳ Will cover with Hilt in Week 3

**LiveData:**
- [ ] LiveData (lifecycle-aware, UI only) ⚠️ Skipping - using StateFlow instead
- [ ] Observing LiveData in Activity/Fragment ⚠️ Skipping - using StateFlow instead
- [ ] LiveData transformations ⚠️ Skipping - using Flow operators instead

**Room Database:**
- [x] Entities (`@Entity`, `@PrimaryKey`) ✅ Created WeatherEntity
- [x] DAOs (`@Dao`, `@Query`, `@Insert`, `@Update`, `@Delete`) ✅ Created WeatherDao with queries
- [x] Database class (`@Database`) ✅ Created WeatherDatabase with singleton pattern
- [x] Migrations (basic understanding) ✅ Learned fallbackToDestructiveMigration
- [ ] Type converters ⏳ Not needed yet (will cover when needed)

**Navigation Component:**
- [x] NavHost and NavController ✅ Implemented in MainActivity
- [x] Navigation graph (XML) ✅ Created nav_graph.xml with 2 destinations
- [x] SafeArgs (type-safe arguments) ✅ Passing cityName between fragments
- [ ] Deep linking basics ⚠️ Not covered (advanced topic)

**Fragments:**
- [x] Fragment lifecycle ✅ Learned onCreateView, onViewCreated, onDestroyView
- [x] FragmentManager and transactions ✅ Handled by NavController automatically
- [x] Fragment-Activity communication ✅ Shared ViewModel pattern (activityViewModels)
- [x] Fragment arguments (Bundle) ✅ SafeArgs auto-generates argument classes

**RecyclerView:**
- [x] RecyclerView fundamentals ✅ Implemented CityAdapter
- [x] ViewHolder pattern ✅ CityViewHolder with ViewBinding
- [x] ListAdapter + DiffUtil ✅ Smart list updates with animations
- [x] Item click handling ✅ Navigate to detail on click

**Project:**
- [x] Weather app: Add MVVM architecture ✅ ViewModel implemented, rotation tested
- [x] Weather app: Add Room caching (offline-first) ✅ Offline functionality working
- [x] Weather app: Multi-screen (list + detail) ✅ CityList + WeatherDetail fragments
- [x] Weather app: Navigation Component ✅ Type-safe navigation with SafeArgs

**LeetCode:**
- [ ] 7 easy-medium mix (HashMaps, Two Pointers) ⏳ Week 2 target

---

### Week 3: DI + Testing (15 hours) ✅ **COMPLETED**

#### Kotlin Language Topics

**Testing:**
- [x] JUnit basics (`@Test`, assertions) ✅ Used in both test files
- [x] MockK (mocking dependencies) ✅ Used in unit tests for Repository mocking
- [x] Turbine (testing Flow) ✅ Used for StateFlow and Flow testing
- [x] Coroutine testing (`runTest`, `TestDispatcher`) ✅ Mastered in unit tests
- [x] `inline` & `reified` ✅ Deep understanding of type erasure and reification
- [x] Extension function types ✅ `Fragment.() -> Unit` pattern mastered
- [x] `crossinline` modifier ✅ Understand lambda restrictions in inline functions
- [x] Resource annotations (`@StyleRes`, etc.) ✅ Type-safe resource handling

#### Android Framework Topics

**Hilt Dependency Injection:**
- [x] `@HiltAndroidApp` (Application class) ✅ Created WeatherApplication
- [x] `@AndroidEntryPoint` (Activity, Fragment, ViewModel) ✅ Added to MainActivity + both Fragments
- [x] `@Module` (provide dependencies) ✅ Created DatabaseModule + NetworkModule + TestAppModule
- [x] `@Provides` (instances you construct) ✅ Used for Room, Retrofit, DAOs, API service
- [x] `@Binds` (interface to implementation) ⚠️ Not needed yet (no interfaces to bind)
- [x] `@Inject` (inject dependencies) ✅ Used in Repository + ViewModel constructors
- [x] Scopes: `@Singleton`, `@ActivityScoped`, `@ViewModelScoped` ✅ Used @Singleton for app-wide dependencies
- [x] KSP setup with Hilt (modern annotation processing) ✅ Configured with version catalog
- [x] Resolved JavaPoet dependency conflicts ✅ Added plugins to root build.gradle.kts
- [x] Test modules with `@UninstallModules` ✅ Replace production modules with test modules
- [x] Custom test runner (HiltTestRunner) ✅ Swap app to HiltTestApplication
- [x] Custom test activity (HiltTestActivity) ✅ @AndroidEntryPoint activity for fragments

**Android Testing:**
- [x] ViewModel testing without DI (demonstrate pain points) ✅ Created WeatherViewModelTest showing Robolectric necessity
- [x] Fragment instrumented tests without DI (demonstrate pain points) ✅ Created CityListFragmentTest showing 8 pain points
- [x] AndroidX Test library ✅ Added Fragment testing, Navigation testing, Espresso dependencies
- [x] ViewModel testing WITH Hilt (clean approach) ✅ WeatherViewModelTestWithHilt - 7 tests, no Robolectric!
- [x] Fragment testing WITH Hilt (clean approach) ✅ CityListFragmentTestWithHilt - 6 tests, in-memory DB!
- [x] Modular test architecture ✅ Separated DI modules, utilities, and test logic
- [x] Test DI modules (di/TestAppModule.kt) ✅ Centralized in-memory DB and fake API
- [x] Test utilities (utils/HiltFragmentTestUtils.kt) ✅ Reusable fragment launcher
- [x] In-memory database for tests ✅ Fast, isolated, no disk I/O
- [x] Fake API service for tests ✅ No real network calls
- [x] Test isolation patterns ✅ Each test gets fresh dependencies
- [ ] Testing Room DAOs ⏳ Not covered yet (will cover when needed)

**Project:**
- [x] Weather app: Add Hilt ✅ Complete integration (Application, Modules, @Inject, @AndroidEntryPoint)
- [x] Weather app: Refactor architecture for DI ✅ Repository pattern with single dependency in ViewModel
- [x] Weather app: Rewrite ViewModel tests WITH Hilt ✅ 7 passing tests, fast and clean
- [x] Weather app: Rewrite Fragment tests WITH Hilt ✅ 6 passing tests with in-memory DB
- [x] Weather app: Modular test architecture ✅ Separated into test class, DI modules, utilities
- [x] Weather app: Test infrastructure ✅ Custom runner, activity, and utilities
- [x] Weather app: Comprehensive test documentation ✅ README.md and CONCEPTS_EXPLAINED.md
- [ ] Weather app: Repository tests ⏳ Not needed yet (simple pass-through)

**ElevenLabs SDK:**
- [ ] **MOVED TO AFTER MIDNIGHT WALLET** ⏳ Will do after Week 8 (need module/React Native experience first)

**LeetCode:**
- [ ] 7 medium (Linked Lists, Stacks) ⏳ Can start anytime

**Week 3 Achievements:**
- ✅ **7 unit tests passing** (WeatherViewModelTestWithHilt)
- ✅ **6 instrumented tests passing** (CityListFragmentTestWithHilt)
- ✅ **Modular test architecture** (test class, DI modules, utilities separated)
- ✅ **No Robolectric needed** for unit tests (plain Kotlin JVM)
- ✅ **In-memory database** for instrumented tests (fast, isolated)
- ✅ **Comprehensive documentation** (architecture guide + concepts explained)
- ✅ **Production-ready patterns** (SOLID principles, best practices)

---

### Week 4: Compose Fundamentals (18 hours) ✅ **COMPLETED**

#### Kotlin Language Topics

**Compose State (Kotlin Integration):**
- [x] `remember`, `mutableStateOf` ✅ Used in AboutScreen for toggles and dialog state
- [x] `State<T>` vs `MutableState<T>` ✅ Understand immutable vs mutable state
- [x] Delegates (`by` keyword for state) ✅ Used `by remember { mutableStateOf() }` pattern
- [x] Function references (`::`) ✅ `viewModel::updateDarkMode` as callback parameter
- [x] Flow operators (`map`, `stateIn`) ✅ Transform Flow and convert to StateFlow

#### Android Framework Topics

**Jetpack Compose Fundamentals:**
- [x] Composable functions (`@Composable`) ✅ Created AboutScreen, LicensesDialog, SettingRow, LicenseItem
- [x] State and recomposition ✅ Toggles trigger recomposition
- [x] State hoisting (lift state up) ✅ Moved state from AboutScreen to SettingsViewModel
- [x] Unidirectional data flow ✅ Events up (callbacks), State down (StateFlow)

**Compose Layouts:**
- [x] `Row`, `Column` ✅ Used in AboutScreen layout structure
- [x] `Box` ✅ Used in WeatherHistoryScreen for empty states
- [x] `LazyColumn`, `LazyRow` ✅ Implemented LazyColumn in WeatherHistoryScreen
- [x] `Modifier` (padding, size, click, etc.) ✅ Used extensively (padding, fillMaxSize, clickable)
- [x] Spacer, Divider ✅ Used Spacer(weight, height), HorizontalDivider

**Compose Side Effects:**
- [x] `LaunchedEffect` (for suspend functions) ✅ Visual exercise in WeatherHistoryScreen (auto-refresh on open)
- [ ] `DisposableEffect` (cleanup on leave) ⏳ Will learn during Midnight Wallet (WebSocket connect/disconnect)
- [ ] `SideEffect` (publish to non-Compose code) ⏳ Advanced topic (rarely needed)
- [x] `rememberCoroutineScope` ✅ Visual exercise in WeatherHistoryScreen (manual refresh button)

**Visual Exercise Added:**
- ✅ LaunchedEffect: Auto-refresh when screen opens (shows Snackbar automatically)
- ✅ rememberCoroutineScope: Manual refresh button (shows loading indicator)
- ✅ Simulated 2-second delay with visual feedback
- ✅ Automatic cancellation when leaving screen
- ✅ Loading state management with StateFlow
- ✅ Documentation: launchedeffect-visual-exercise.md

**Compose State Management:**
- [x] `collectAsState()` (Flow → State) ✅ Understand Flow to State conversion
- [x] `collectAsStateWithLifecycle()` (lifecycle-aware) ✅ Used in AboutScreen to observe SettingsViewModel
- [ ] `rememberSaveable` (survive process death) ⏳ Will use in future screens

**Compose Theming:**
- [x] Material Design 3 basics ✅ Using MaterialTheme, Material3 components
- [x] Color scheme (dark/light mode) ✅ Implemented functional Dark Mode with darkColorScheme()/lightColorScheme()
- [x] Typography ✅ Using MaterialTheme.typography
- [x] Dynamic theming based on state ✅ Theme switches when user toggles preference

**Compose UI Components:**
- [x] Scaffold ✅ Used for screen structure with TopAppBar
- [x] TopAppBar, CenterAlignedTopAppBar ✅ AboutScreen and WeatherHistoryScreen toolbars
- [x] TopAppBar with navigationIcon ✅ Back button in WeatherHistoryScreen
- [x] TopAppBar with actions ✅ Clear history button in WeatherHistoryScreen
- [x] Button, OutlinedButton ✅ Close, Licenses, and History buttons
- [x] Switch ✅ Toggle switches for settings
- [x] AlertDialog ✅ Licenses dialog and clear history confirmation
- [x] AlertDialog for destructive actions ✅ Confirmation before clearing history
- [x] Text ✅ All text components with various styles
- [x] Card ✅ History items in WeatherHistoryScreen
- [x] IconButton with Icons ✅ Back arrow and delete icons
- [x] MaterialToolbar (XML → Compose bridge) ✅ Added to CityListFragment

**Compose Navigation:**
- [x] Intent-based navigation (XML → Compose) ✅ CityListFragment → ComposeActivity
- [x] Compose Navigation Component ✅ NavHost, NavController, Routes
- [x] Sealed class for routes ✅ Type-safe route definitions (Screen.About, Screen.History)
- [x] `rememberNavController()` ✅ Navigation state management
- [x] `NavHost` with `startDestination` ✅ Navigation graph setup
- [x] `composable()` destinations ✅ Define screens in navigation graph
- [x] `navigate()` for forward navigation ✅ Move to new screen
- [x] `popBackStack()` for back navigation ✅ Return to previous screen
- [x] Callback-based navigation ✅ Loose coupling (screens don't know NavController)
- [x] Back stack management ✅ Understanding navigation history
- [ ] Navigation with arguments ⏳ Future: pass data between screens
- [ ] Deep linking ⏳ Future: open specific screen from notification

**LazyColumn (Efficient Lists):**
- [x] LazyColumn basics ✅ Implemented in WeatherHistoryScreen
- [x] `items()` function ✅ Iterate over list of data
- [x] `key` parameter ✅ Unique identifier for efficient updates (used item.id)
- [x] `contentPadding` ✅ Padding around list content
- [x] `verticalArrangement` ✅ Spacing between items (spacedBy)
- [x] Efficient rendering ✅ Only visible items rendered (like RecyclerView)
- [x] Empty state handling ✅ Box layout with conditional rendering
- [x] Card items ✅ Individual list items with elevation
- [x] Clickable items ✅ `Modifier.clickable` for future navigation
- [ ] Pull-to-refresh ⏳ Future: SwipeRefresh integration
- [ ] Pagination ⏳ Future: Load more items on scroll

**DataStore (Modern Preferences):**
- [x] DataStore basics ✅ Replaced SharedPreferences with DataStore
- [x] Preferences DataStore ✅ Key-value storage with type-safe keys
- [x] Flow-based reading ✅ Reactive preferences with Flow
- [x] Async writing ✅ Suspend functions for writing preferences
- [x] DataStore + Repository pattern ✅ PreferencesRepository encapsulates DataStore
- [x] StateFlow conversion ✅ Flow → StateFlow with stateIn() operator
- [x] ProcessLifecycleOwner ✅ App-wide lifecycle for global features

**Room Database (Advanced):**
- [x] Multiple entities in one database ✅ WeatherEntity + WeatherHistoryEntity
- [x] Database versioning ✅ Incremented version from 1 to 2
- [x] Adding new tables ✅ Added weather_history table
- [x] DAO methods for multiple tables ✅ Extended WeatherDao with history methods
- [x] `OnConflictStrategy.IGNORE` ✅ Prevent duplicate history entries
- [x] Query with LIMIT ✅ `getRecentHistory(limit: Int)`
- [x] Multiple Flow streams ✅ cachedCities + searchHistory from same DAO
- [x] Helper methods in entities ✅ getFormattedTemperature(), getRelativeTimeString()
- [x] Timestamp tracking ✅ System.currentTimeMillis() for search history
- [x] Repository exposing multiple Flows ✅ cachedCities and searchHistory

**XML Views Patterns (Legacy - Why Compose Exists):**
- [x] Manual state management ✅ Caching data for re-rendering when state changes
- [x] Manual UI updates ✅ Calling update functions when variables change
- [x] StateFlow replay behavior ✅ Understanding last-value caching and re-emission
- [x] Fragment lifecycle + state ✅ Managing state across recreation

**Compose Previews:**
- [x] `@Preview` annotation ✅ AboutScreenPreview, LicensesDialogPreview
- [x] `showBackground` parameter ✅ Understand background display in previews
- [x] Previewing with fake data ✅ AboutScreenPreviewLight/Dark with UserPreferences
- [x] Multiple previews (light/dark) ✅ Two preview variants for same screen

**Component Extraction:**
- [x] Reusable composables ✅ Extracted LicensesDialog, SettingRow, LicenseItem, HistoryItem, EmptyHistoryState
- [x] Component organization ✅ Learned composable extraction pattern
- [x] Stateful vs Stateless pattern ✅ AboutScreen + WeatherHistoryScreen (stateful) vs Content (stateless)
- [x] Preview-friendly architecture ✅ Stateless components can be previewed without Hilt

**Multiple ViewModels:**
- [x] Multiple ViewModels in Compose ✅ SettingsViewModel + WeatherHistoryViewModel
- [x] `@HiltViewModel` annotation ✅ Hilt provides ViewModels automatically
- [x] ViewModel per screen pattern ✅ Each screen has its own ViewModel
- [x] Exposing repository Flows ✅ ViewModel exposes StateFlow from repository
- [x] ViewModel with business logic ✅ clearHistory() method in WeatherHistoryViewModel

**Project:**
- [x] Weather app: Add Compose to project ✅ Dependencies, plugin, AboutScreen
- [x] Weather app: First Compose screen (AboutScreen) ✅ Interactive UI with state
- [x] Weather app: DataStore setup ✅ PreferencesRepository with DataStore
- [x] Weather app: SettingsViewModel ✅ State hoisting with StateFlow
- [x] Weather app: Functional Dark Mode in Compose ✅ Theme switches based on preference
- [x] Weather app: Stateful/Stateless pattern ✅ AboutScreen split for previews
- [x] Weather app: Global theme application ✅ WeatherApplication with ProcessLifecycleOwner + AppCompatDelegate
- [x] Weather app: Temperature unit integration ✅ XML fragments (WeatherDetail, CityList) use preference
- [x] Weather app: Manual state management pattern (XML) ✅ Experienced the pain of manual UI updates
- [x] Weather app: Compose Navigation ✅ NavHost, NavController, sealed class routes, navigation between screens
- [x] Weather app: WeatherHistory entity ✅ Room table for search history
- [x] Weather app: History tracking ✅ Repository + ViewModel log searches
- [x] Weather app: Weather History with LazyColumn ✅ WeatherHistoryScreen with efficient list rendering
- [x] Weather app: Empty states ✅ Box layout with conditional rendering
- [x] Weather app: Confirmation dialogs ✅ AlertDialog for destructive actions
- [x] Weather app: Documentation ✅ compose-navigation-explained.md guide
- [x] Weather app: **COMPLETE** ✅ Ready to move to Midnight Wallet

**LeetCode:**
- [ ] 7 medium (Trees, Binary Search)

**Week 4 Achievements:**
- ✅ **Settings Feature Complete** - DataStore + SettingsViewModel + Dark Mode + Temperature Unit
- ✅ **State Hoisting Mastered** - ViewModel → StateFlow → Compose State
- ✅ **Stateful/Stateless Pattern** - Preview-friendly architecture
- ✅ **Material3 Theming** - Dynamic dark/light mode switching
- ✅ **DataStore Integration** - Modern preferences with Flow
- ✅ **Global Theme Application** - ProcessLifecycleOwner + AppCompatDelegate (entire app)
- ✅ **Temperature Conversion** - Celsius/Fahrenheit with user preference
- ✅ **XML Pain Points Experienced** - Manual state management, duplicate caching, manual UI updates
- ✅ **StateFlow Replay Behavior** - Understanding last-value emission to new collectors
- ✅ **Compose Navigation Complete** - NavHost, NavController, routes, back stack management
- ✅ **LazyColumn Mastered** - Efficient list rendering with items() and key
- ✅ **Weather History Feature** - Room entity, DAO methods, tracking, WeatherHistoryScreen
- ✅ **Box Layout for Empty States** - Conditional rendering with Box and Alignment
- ✅ **Multiple ViewModels** - SettingsViewModel + WeatherHistoryViewModel with @HiltViewModel
- ✅ **Room Database Migration** - Version 2 with WeatherHistoryEntity table
- ✅ **Confirmation Dialogs** - AlertDialog for destructive actions
- ✅ **LaunchedEffect Visual Exercise** - Auto-refresh on screen open (like React useEffect)
- ✅ **rememberCoroutineScope Visual Exercise** - Manual refresh button (async on click)
- ✅ **Side Effect Automatic Cancellation** - Both cancel when leaving screen
- ✅ **Loading State Management** - Visual feedback with CircularProgressIndicator
- ✅ **Snackbar Integration** - SnackbarHost + SnackbarHostState for messages
- ✅ **Comprehensive Documentation** - compose-navigation-explained.md + launchedeffect-visual-exercise.md
- ✅ **Navigation Callback Pattern** - Loose coupling between screens and navigation

---

## Month 2: Clean Architecture + Midnight Wallet

### Week 5: Clean Arch + Multi-Module + Midnight Wallet Start (15 hours) ⏳ **NEXT**

#### Kotlin Language Topics

**Kotlin for Architecture:**
- [ ] Sealed classes (for Result/State types)
- [ ] Data classes (for models)
- [ ] Extension functions (for mappers)

#### Android Framework Topics

**Clean Architecture:**
- [ ] Domain layer (entities, repository interfaces, use cases)
- [ ] Data layer (repository implementations, data sources)
- [ ] Presentation layer (ViewModels, UI)
- [ ] Dependency rule (inner layers don't know outer)

**Multi-Module Setup:**
- [ ] Feature modules (`:feature-wallet`, `:feature-send`)
- [ ] Core modules (`:core-network`, `:core-database`)
- [ ] Module dependencies (feature → core)
- [ ] Version catalogs (`libs.versions.toml`)

**Gradle Convention Plugins:**
- [ ] Build logic structure
- [ ] Shared build configuration
- [ ] Compose plugin, Hilt plugin

**Study Reference Apps:**
- [ ] Nowinandroid architecture
- [ ] Tivi structure
- [ ] Multi-module patterns

**Project:**
- [ ] Midnight Wallet: Architecture planning
- [ ] Midnight Wallet: Module structure setup
- [ ] Midnight Wallet: Clean Architecture layers design

**Note:** ElevenLabs SDK moved to after Midnight Wallet completion (need multi-module + React Native bridge experience first)

**LeetCode:**
- [ ] 7 medium (DFS/BFS)

---

### Week 6: Advanced Coroutines + Data Layer (18 hours)

#### Kotlin Language Topics

**Structured Concurrency:**
- [ ] `coroutineScope` (parallel tasks)
- [ ] `supervisorScope` (don't cancel siblings)
- [ ] `withContext` (switch dispatcher)
- [ ] Exception handling in coroutines
- [ ] `CoroutineExceptionHandler`

**Advanced Flow:**
- [ ] Hot vs cold flows
- [ ] `stateIn` (convert cold to hot)
- [ ] `shareIn` (share cold flow)
- [ ] Flow operators: `combine`, `flatMapLatest`, `debounce`, `map`, `filter`

#### Android Framework Topics

**Repository Pattern:**
- [ ] Single source of truth (local DB)
- [ ] Network + cache strategy
- [ ] Result wrapper (Success/Error)
- [ ] Offline-first architecture

**Data Layer Implementation:**
- [ ] Data sources (local, remote)
- [ ] DTO to Entity mapping
- [ ] Cache invalidation strategies

**Project:**
- [ ] Midnight Wallet: Crypto layer (key generation, signing)
- [ ] Midnight Wallet: Room database (encrypted)
- [ ] Midnight Wallet: Repository implementation

**LeetCode:**
- [ ] 7 medium (DP intro)

---

### Week 7: Advanced Compose + Wallet UI (18 hours)

#### Kotlin Language Topics

**MVI Pattern (Kotlin):**
- [ ] Single immutable state (data class)
- [ ] Intent sealed class
- [ ] Immutability patterns

#### Android Framework Topics

**Advanced Compose:**
- [ ] Custom layouts (`Layout` composable)
- [ ] Animations (`animateDpAsState`, `AnimatedVisibility`)
- [ ] Transitions (`updateTransition`)
- [ ] Performance: `@Stable`, `derivedStateOf`
- [ ] `key()` for stable item identity

**Compose Testing:**
- [ ] `createComposeRule()`
- [ ] `onNodeWithText`, `onNodeWithTag`
- [ ] `performClick()`, `performTextInput()`
- [ ] `assertIsDisplayed()`, `assertTextEquals()`

**Compose Navigation:**
- [ ] NavController in Compose
- [ ] Type-safe navigation
- [ ] Passing arguments

**MVI Pattern (Android Implementation):**
- [ ] Unidirectional data flow
- [ ] State hoisting to ViewModel
- [ ] Event handling

**Project:**
- [ ] Midnight Wallet: Full Compose UI
- [ ] Midnight Wallet: Send/receive screens
- [ ] Midnight Wallet: Backup/restore UI

**LeetCode:**
- [ ] 7 medium (More DP)

---

### Week 8: Testing + CI/CD + Midnight Wallet Completion (20 hours)

#### Kotlin Language Topics

**Kotlin Testing:**
- [ ] Fakes vs Mocks (when to use each)
- [ ] Test doubles
- [ ] Assertion libraries

#### Android Framework Topics

**Comprehensive Testing:**
- [ ] Unit tests (ViewModel, UseCase, Repository)
- [ ] Integration tests (Repository + DAO + API)
- [ ] Compose UI tests (user flows)
- [ ] Test coverage (80%+ goal)
- [ ] Testing best practices

**Security:**
- [ ] EncryptedSharedPreferences
- [ ] Android Keystore
- [ ] ProGuard/R8 rules
- [ ] SSL pinning (if needed)
- [ ] Certificate pinning

**CI/CD:**
- [ ] GitHub Actions workflow
- [ ] Build on push
- [ ] Run tests
- [ ] Lint checks (detekt, ktlint)
- [ ] Code quality gates

**Project:**
- [ ] Midnight Wallet: Comprehensive tests
- [ ] Midnight Wallet: CI/CD setup
- [ ] Midnight Wallet: Security audit
- [ ] Midnight Wallet: Publish to GitHub
- [ ] Midnight Wallet: **COMPLETE** ✅

**After Week 8:** Start ElevenLabs SDK (Android module + React Native bridge)

**LeetCode:**
- [ ] 7 medium-hard (Graphs)

---

## ElevenLabs SDK (Between Month 2 & 3) - 1 Week

### ElevenLabs Android SDK (7-10 hours)

**Why After Midnight Wallet:**
- Need multi-module architecture experience first
- Need to understand Clean Architecture layers
- Need React Native bridge knowledge
- Midnight Wallet provides foundation for SDK structure

#### Project Structure
- [ ] Android module structure (`:elevenlabs-sdk`)
- [ ] React Native bridge module (`:react-native-elevenlabs`)
- [ ] Multi-module dependencies
- [ ] Version catalog management

#### Core SDK Features
- [ ] ElevenLabs API client (Retrofit)
- [ ] Text-to-Speech implementation
- [ ] Audio streaming
- [ ] Voice cloning API integration
- [ ] Error handling & retry logic

#### React Native Bridge
- [ ] React Native module setup
- [ ] Native methods exposure
- [ ] Promise-based API
- [ ] Event emitters for streaming
- [ ] TypeScript definitions

#### Documentation & Publishing
- [ ] API documentation
- [ ] Usage examples
- [ ] README.md
- [ ] Publish to GitHub
- [ ] (Optional) Publish to Maven Central

**After ElevenLabs SDK:** Continue to Month 3 (AI Chat)

---

## Month 3: Advanced Topics + AI Chat Start

### Week 9: Performance + Profiling (15 hours)

#### Kotlin Language Topics

**Kotlin Performance:**
- [ ] Inline functions
- [ ] Sequence vs List (lazy evaluation)
- [ ] Avoiding unnecessary allocations

#### Android Framework Topics

**Android Profiler:**
- [ ] CPU profiler (identify slow methods)
- [ ] Memory profiler (heap dumps, allocations)
- [ ] Network profiler (request tracking)
- [ ] Energy profiler (battery usage)

**Memory Leaks:**
- [ ] LeakCanary setup
- [ ] Common leak patterns (Activity refs, listeners)
- [ ] Weak references
- [ ] Debugging memory leaks

**Performance Optimization:**
- [ ] LazyColumn best practices
- [ ] Overdraw detection
- [ ] Layout Inspector
- [ ] Baseline profiles (advanced)
- [ ] Compose performance optimization

**Project:**
- [ ] Profile Midnight Wallet
- [ ] Optimize identified bottlenecks
- [ ] AI Chat: Architecture planning

**LeetCode:**
- [ ] 7 medium-hard (Advanced DP)

---

### Week 10: Advanced Android Topics (18 hours)

#### Kotlin Language Topics

**Advanced Kotlin:**
- [ ] Generics and variance (`in`, `out`)
- [ ] Higher-order functions
- [ ] DSL creation basics

#### Android Framework Topics

**Custom Views:**
- [ ] `onMeasure` (determine size)
- [ ] `onLayout` (position children)
- [ ] `onDraw` (render on canvas)
- [ ] Touch events (`onTouchEvent`)
- [ ] Custom attributes

**WorkManager:**
- [ ] OneTime vs Periodic work
- [ ] Constraints (network, charging, battery)
- [ ] Chaining work
- [ ] Observing work status
- [ ] Data passing between workers

**App Optimization:**
- [ ] R8 minification
- [ ] App bundle (AAB)
- [ ] Dynamic feature modules (advanced)
- [ ] Build variants

**Project:**
- [ ] AI Chat: Multi-module setup
- [ ] AI Chat: LLM abstraction layer

**LeetCode:**
- [ ] 7 hard (Graphs, advanced)

---

### Week 11: System Design Practice (20 hours)

#### Android System Design Topics

**Practice These Designs:**
- [ ] Instagram feed (pagination, caching, offline)
- [ ] WhatsApp messaging (real-time, encryption)
- [ ] Uber driver app (location, real-time)
- [ ] Google Maps offline (tile storage, search)

**System Design Skills:**
- [ ] Requirements gathering
- [ ] API design
- [ ] Data modeling
- [ ] Offline-first strategies
- [ ] Real-time communication patterns
- [ ] Scalability considerations

**Documentation:**
- [ ] AI Chat architecture decisions
- [ ] System design doc started
- [ ] Architecture diagrams

**Project:**
- [ ] AI Chat: Core LLM integration (OpenAI, Anthropic, Gemini)
- [ ] AI Chat: Streaming implementation

**LeetCode:**
- [ ] 7 hard (Backtracking)

**Behavioral Prep:**
- [ ] STAR stories documented (Nike, eBay, blockchain)

---

### Week 12: Mock Interviews Begin (18 hours)

#### Interview Preparation

**STAR Stories:**
- [ ] Technical challenge story
- [ ] Leadership story
- [ ] Conflict resolution story
- [ ] Failure & learning story
- [ ] Ambiguity & initiative story

**Mock Interviews:**
- [ ] Schedule 2 mock interviews (Pramp)
- [ ] Practice explaining projects
- [ ] Practice system design whiteboarding

#### Android Framework Topics

**Project:**
- [ ] AI Chat: Voice integration (STT + ElevenLabs TTS)
- [ ] AI Chat: Streaming responses working
- [ ] AI Chat: Testing voice features

**LeetCode:**
- [ ] 7 hard (timed practice, 45 min each)

---

## Month 4: Polish + Interview Ready

### Week 13: Advanced Testing + CI (15 hours)

#### Kotlin Language Topics

**Advanced Testing Patterns:**
- [ ] Test double strategies (fake vs mock)
- [ ] Builder pattern for test data
- [ ] Test fixtures

#### Android Framework Topics

**Advanced Testing:**
- [ ] Screenshot tests (Paparazzi/Roborazzi)
- [ ] Espresso integration tests
- [ ] Turbine for Flow testing
- [ ] Testing navigation
- [ ] Testing animations

**CI/CD:**
- [ ] GitHub Actions matrix builds
- [ ] Automated tests on PR
- [ ] Lint + detekt checks
- [ ] Code coverage reports
- [ ] Automated releases

**Project:**
- [ ] AI Chat: 80%+ test coverage
- [ ] AI Chat: GitHub Actions running

**LeetCode:**
- [ ] 7 timed medium (20 min target each)

---

### Week 14: Security + Best Practices (18 hours)

#### Android Framework Topics

**Security:**
- [ ] API key security (BuildConfig, not hardcoded)
- [ ] Authentication flows (OAuth, JWT)
- [ ] SSL pinning
- [ ] Encrypted storage (Room, SharedPrefs)
- [ ] Network security config
- [ ] SafetyNet / Play Integrity API

**Best Practices:**
- [ ] ProGuard/R8 rules
- [ ] Code obfuscation
- [ ] Remove debug logs in release
- [ ] Security audit checklist
- [ ] OWASP Mobile Top 10

**Code Quality:**
- [ ] Architecture best practices
- [ ] Code review guidelines
- [ ] Documentation standards

**Project:**
- [ ] AI Chat: Security hardening
- [ ] AI Chat: Performance optimization

**LeetCode:**
- [ ] 7 hard (30 min target each)

---

### Week 15: System Design Mastery (20 hours)

#### Android System Design Topics

**Practice More Designs:**
- [ ] Netflix video player (streaming, offline)
- [ ] Twitter timeline (pagination, real-time)
- [ ] Medium reading app (offline, sync)
- [ ] Spotify music player (audio, background)

**System Design Deep Dives:**
- [ ] Caching strategies (LRU, TTL)
- [ ] Pagination patterns
- [ ] Real-time sync mechanisms
- [ ] Media streaming architecture
- [ ] Background processing strategies

**Documentation:**
- [ ] AI Chat: System design doc complete
- [ ] Architecture diagrams (draw.io)
- [ ] Trade-offs documentation

**LeetCode:**
- [ ] 7 company-tagged (Google, Meta, Amazon)

---

### Week 16: Interview Bootcamp (20 hours)

#### Interview Preparation

**Mock Interviews:**
- [ ] Mock interview 3: Behavioral
- [ ] Mock interview 4: Leetcode + System design
- [ ] Mock interview 5: Full loop simulation
- [ ] Mock interview 6: Android Q&A

**Android Flashcards:**
- [ ] Review 100+ Android flashcards
- [ ] Practice explaining out loud
- [ ] Activity lifecycle
- [ ] Architecture patterns (MVVM, MVI, Clean)
- [ ] Jetpack components
- [ ] Compose fundamentals

#### Android Framework Topics

**Interview Topics Review:**
- [ ] Common Android interview questions
- [ ] Architecture explanations
- [ ] Performance optimization techniques
- [ ] Security best practices

**Project:**
- [ ] AI Chat: Complete and publish to GitHub
- [ ] AI Chat: Demo video recorded
- [ ] All 3 projects polished

**LeetCode:**
- [ ] 7 hard (weakness focus)

---

## Month 5-6: Active Interviewing

### Week 17-20: Application + Interviews

#### Interview Preparation

**Resume:**
- [ ] Resume updated with 3 projects
- [ ] LinkedIn profile optimized
- [ ] Portfolio links added
- [ ] GitHub README polished

**Applications:**
- [ ] 15 applications (Week 17)
- [ ] 30-40 total applications
- [ ] Track in spreadsheet
- [ ] Follow up with recruiters

**Interview Prep:**
- [ ] Daily LeetCode (1 problem maintenance)
- [ ] Weekly system design review
- [ ] STAR stories rehearsal
- [ ] Mock interviews 2x/week

**Android Interview Maintenance:**
- [ ] Review Android fundamentals weekly
- [ ] Stay current with latest Android news
- [ ] Practice explaining projects clearly
- [ ] Review architecture decisions

**LeetCode:**
- [ ] Maintain 180-200 problems

---

## Completion Tracking

**By end of each month, you should have checked off:**
- **Month 1:** ✅ **COMPLETED**
  - ✅ Kotlin: Scope functions, null safety, coroutines basics, Flow basics, inline/reified, extension functions
  - ✅ Android: Activity lifecycle, ViewBinding, Room, Navigation, MVVM architecture, Jetpack Compose fundamentals
  - ✅ Hilt: Complete DI integration with @HiltAndroidApp, @Module, @Inject, @AndroidEntryPoint
  - ✅ Testing: Unit tests (7 passing), instrumented tests (6 passing), modular test architecture
  - ✅ Advanced concepts: Type erasure, reification, crossinline, resource annotations
  - ✅ Compose: State management, Navigation, LazyColumn, Material3 theming, DataStore
  - ✅ Project: Weather app **COMPLETE** with Hilt + Compose + comprehensive test suite
- **Month 2:** ⏳ **STARTING NEXT**
  - Kotlin: Advanced coroutines, structured concurrency, advanced Flow
  - Android: Clean Architecture, multi-module, advanced Compose (side effects), security
  - Compose Side Effects: LaunchedEffect, DisposableEffect, rememberCoroutineScope (learned during Midnight Wallet)
  - Project: Midnight Wallet complete (Weeks 5-8)
  - **After Month 2**: ElevenLabs SDK (1 week: multi-module + React Native bridge)
- **Month 3:**
  - Kotlin: Performance optimization, advanced patterns
  - Android: Profiling, custom views, WorkManager, system design practice
  - Project: AI Chat in progress, mock interviews started
- **Month 4:**
  - Android: Advanced testing, security best practices, CI/CD mastery
  - System Design: 8 Android app designs practiced
  - Project: AI Chat complete, 150+ LeetCode, interview-ready
- **Month 5-6:**
  - Active interviews, 200 LeetCode complete

---

**Use this checklist weekly to ensure you're covering all critical topics:**
- **Kotlin topics** = Language features, syntax, patterns
- **Android topics** = Framework, architecture, tooling, best practices

**Check off items as you master them, not just as you read about them.**
