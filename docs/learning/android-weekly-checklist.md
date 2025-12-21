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
- [ ] NavHost and NavController
- [ ] Navigation graph (XML)
- [ ] SafeArgs (type-safe arguments)
- [ ] Deep linking basics

**Fragments:**
- [ ] Fragment lifecycle
- [ ] FragmentManager and transactions
- [ ] Fragment-Activity communication
- [ ] Fragment arguments (Bundle)

**Project:**
- [x] Weather app: Add MVVM architecture ✅ ViewModel implemented, rotation tested
- [x] Weather app: Add Room caching (offline-first) ✅ Offline functionality working
- [ ] Weather app: Multi-screen (list + detail) ⏳ Next up
- [ ] Weather app: Navigation Component ⏳ Next up

**LeetCode:**
- [ ] 7 easy-medium mix (HashMaps, Two Pointers) ⏳ Week 2 target

---

### Week 3: DI + Testing (15 hours)

#### Kotlin Language Topics

**Testing:**
- [ ] JUnit basics (`@Test`, assertions)
- [ ] MockK (mocking dependencies)
- [ ] Turbine (testing Flow)
- [ ] Coroutine testing (`runTest`, `TestDispatcher`)

#### Android Framework Topics

**Hilt Dependency Injection:**
- [ ] `@HiltAndroidApp` (Application class)
- [ ] `@AndroidEntryPoint` (Activity, Fragment, ViewModel)
- [ ] `@Module` (provide dependencies)
- [ ] `@Provides` (instances you construct)
- [ ] `@Binds` (interface to implementation)
- [ ] `@Inject` (inject dependencies)
- [ ] Scopes: `@Singleton`, `@ActivityScoped`, `@ViewModelScoped`

**Android Testing:**
- [ ] ViewModel testing (fake repository)
- [ ] `InstantTaskExecutorRule` for LiveData
- [ ] AndroidX Test library
- [ ] Testing Room DAOs

**Project:**
- [ ] Weather app: Add Hilt
- [ ] Weather app: ViewModel tests (80%+ coverage)
- [ ] Weather app: Repository tests

**ElevenLabs SDK:**
- [ ] Start research (ElevenLabs API docs)
- [ ] Read native SDK docs (if exists)

**LeetCode:**
- [ ] 7 medium (Linked Lists, Stacks)

---

### Week 4: Compose + SDK Start (18 hours)

#### Kotlin Language Topics

**Compose State (Kotlin Integration):**
- [ ] `remember`, `mutableStateOf`
- [ ] `State<T>` vs `MutableState<T>`
- [ ] Delegates (`by` keyword for state)

#### Android Framework Topics

**Jetpack Compose Fundamentals:**
- [ ] Composable functions (`@Composable`)
- [ ] State and recomposition
- [ ] State hoisting (lift state up)
- [ ] Unidirectional data flow

**Compose Layouts:**
- [ ] `Row`, `Column`, `Box`
- [ ] `LazyColumn`, `LazyRow`
- [ ] `Modifier` (padding, size, click, etc.)
- [ ] Spacer, Divider

**Compose Side Effects:**
- [ ] `LaunchedEffect` (for suspend functions)
- [ ] `DisposableEffect` (cleanup on leave)
- [ ] `SideEffect` (publish to non-Compose code)
- [ ] `rememberCoroutineScope`

**Compose State Management:**
- [ ] `collectAsState()` (Flow → State)
- [ ] `collectAsStateWithLifecycle()` (lifecycle-aware)
- [ ] `rememberSaveable` (survive process death)

**Compose Theming:**
- [ ] Material Design 3 basics
- [ ] Color scheme
- [ ] Typography

**Project:**
- [ ] Weather app: Migrate UI to Compose
- [ ] Weather app: State hoisting practice
- [ ] ElevenLabs SDK: Project structure setup
- [ ] ElevenLabs SDK: Core API design

**LeetCode:**
- [ ] 7 medium (Trees, Binary Search)

---

## Month 2: Clean Architecture + Midnight Wallet

### Week 5: Clean Arch + Multi-Module (15 hours)

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
- [ ] Complete ElevenLabs SDK (publish to GitHub)
- [ ] Midnight Wallet: Architecture planning
- [ ] Midnight Wallet: Module structure setup

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

### Week 8: Testing + CI/CD (20 hours)

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

**LeetCode:**
- [ ] 7 medium-hard (Graphs)

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
- **Month 1:**
  - Kotlin: Scope functions, null safety, coroutines basics, Flow basics
  - Android: Activity lifecycle, ViewBinding, Room, Navigation, Compose fundamentals
  - Project: ElevenLabs SDK in progress
- **Month 2:**
  - Kotlin: Advanced coroutines, structured concurrency, advanced Flow
  - Android: Clean Architecture, multi-module, Hilt, testing, security
  - Project: Midnight Wallet complete
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
