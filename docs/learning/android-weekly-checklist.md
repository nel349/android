# Android Weekly Learning Checklist

Detailed topic checklist for each week of your Android learning journey. Check off topics as you master them.

---

## Month 1: Foundation

### Week 1: Kotlin Refresh (15 hours)

**Scope Functions:**
- [ ] Understand `let` (null safety + transformation, returns lambda result)
- [ ] Understand `run` (scoped operations, returns lambda result)
- [ ] Understand `with` (grouping function calls, returns lambda result)
- [ ] Understand `apply` (object configuration, returns receiver)
- [ ] Understand `also` (side effects, returns receiver)
- [ ] Know when to use each (decision tree)

**Null Safety:**
- [ ] Safe call operator `?.`
- [ ] Elvis operator `?:`
- [ ] Avoid `!!` (null assertion operator)
- [ ] Nullable types (`String?` vs `String`)
- [ ] Safe casts (`as?`)

**Initialization:**
- [ ] `lateinit` (mutable, initialized before use)
- [ ] `lazy` (immutable, initialized on first access)
- [ ] When to use each

**Coroutines Basics:**
- [ ] `launch` (fire-and-forget, returns Job)
- [ ] `async` (returns Deferred with result)
- [ ] Dispatchers: `Main`, `IO`, `Default`
- [ ] `suspend` functions
- [ ] `CoroutineScope` vs `GlobalScope`

**Flow Basics:**
- [ ] What is Flow (cold asynchronous stream)
- [ ] StateFlow (hot, always has value)
- [ ] SharedFlow (hot, configurable replay)
- [ ] `collect` vs `collectLatest`

**Project:**
- [ ] Weather app mini-project started
- [ ] OpenWeatherMap API integrated
- [ ] Retrofit setup
- [ ] Flow for data streaming

**LeetCode:**
- [ ] 7 easy problems (Arrays & Strings)

---

### Week 2: MVVM + Components (15 hours)

**ViewModel:**
- [ ] What is ViewModel (survives config changes)
- [ ] `viewModelScope` for coroutines
- [ ] ViewModel lifecycle
- [ ] ViewModel factories (if needed)

**LiveData vs Flow:**
- [ ] LiveData (lifecycle-aware, UI only)
- [ ] Flow (more flexible, Kotlin-first)
- [ ] StateFlow for UI state
- [ ] When to use each

**Room Database:**
- [ ] Entities (`@Entity`, `@PrimaryKey`)
- [ ] DAOs (`@Dao`, `@Query`, `@Insert`, `@Update`, `@Delete`)
- [ ] Database class (`@Database`)
- [ ] Migrations (basic understanding)
- [ ] Type converters

**Navigation Component:**
- [ ] NavHost and NavController
- [ ] Navigation graph (XML)
- [ ] SafeArgs (type-safe arguments)
- [ ] Deep linking basics

**Project:**
- [ ] Weather app: Add MVVM architecture
- [ ] Weather app: Add Room caching (offline-first)
- [ ] Weather app: Multi-screen (list + detail)
- [ ] Weather app: Navigation Component

**LeetCode:**
- [ ] 7 easy-medium mix (HashMaps, Two Pointers)

---

### Week 3: DI + Testing (15 hours)

**Hilt Dependency Injection:**
- [ ] `@HiltAndroidApp` (Application class)
- [ ] `@AndroidEntryPoint` (Activity, Fragment, ViewModel)
- [ ] `@Module` (provide dependencies)
- [ ] `@Provides` (instances you construct)
- [ ] `@Binds` (interface to implementation)
- [ ] `@Inject` (inject dependencies)
- [ ] Scopes: `@Singleton`, `@ActivityScoped`, `@ViewModelScoped`

**Testing:**
- [ ] JUnit basics (`@Test`, assertions)
- [ ] MockK (mocking dependencies)
- [ ] Turbine (testing Flow)
- [ ] ViewModel testing (fake repository)
- [ ] `InstantTaskExecutorRule` for LiveData

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

**Jetpack Compose Fundamentals:**
- [ ] Composable functions (`@Composable`)
- [ ] State and recomposition (`remember`, `mutableStateOf`)
- [ ] `State<T>` vs `MutableState<T>`
- [ ] State hoisting (lift state up)
- [ ] Unidirectional data flow

**Compose Layouts:**
- [ ] `Row`, `Column`, `Box`
- [ ] `LazyColumn`, `LazyRow`
- [ ] `Modifier` (padding, size, click, etc.)
- [ ] Spacer, Divider

**Side Effects:**
- [ ] `LaunchedEffect` (for suspend functions)
- [ ] `DisposableEffect` (cleanup on leave)
- [ ] `SideEffect` (publish to non-Compose code)
- [ ] `rememberCoroutineScope`

**Compose State:**
- [ ] `collectAsState()` (Flow → State)
- [ ] `collectAsStateWithLifecycle()` (lifecycle-aware)
- [ ] `rememberSaveable` (survive process death)

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

**Convention Plugins:**
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
- [ ] Flow operators: `combine`, `flatMapLatest`, `debounce`

**Repository Pattern:**
- [ ] Single source of truth (local DB)
- [ ] Network + cache strategy
- [ ] Result wrapper (Success/Error)

**Project:**
- [ ] Midnight Wallet: Crypto layer (key generation, signing)
- [ ] Midnight Wallet: Room database (encrypted)
- [ ] Midnight Wallet: Repository implementation

**LeetCode:**
- [ ] 7 medium (DP intro)

---

### Week 7: Advanced Compose + Wallet UI (18 hours)

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

**MVI Pattern:**
- [ ] Single immutable state
- [ ] Intent sealed class
- [ ] Unidirectional data flow

**Project:**
- [ ] Midnight Wallet: Full Compose UI
- [ ] Midnight Wallet: Send/receive screens
- [ ] Midnight Wallet: Backup/restore UI

**LeetCode:**
- [ ] 7 medium (More DP)

---

### Week 8: Testing + CI/CD (20 hours)

**Comprehensive Testing:**
- [ ] Unit tests (ViewModel, UseCase, Repository)
- [ ] Integration tests (Repository + DAO + API)
- [ ] Compose UI tests (user flows)
- [ ] Test coverage (80%+ goal)

**Security:**
- [ ] EncryptedSharedPreferences
- [ ] Android Keystore
- [ ] ProGuard/R8 rules
- [ ] SSL pinning (if needed)

**CI/CD:**
- [ ] GitHub Actions workflow
- [ ] Build on push
- [ ] Run tests
- [ ] Lint checks (detekt)

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

**Android Profiler:**
- [ ] CPU profiler (identify slow methods)
- [ ] Memory profiler (heap dumps, allocations)
- [ ] Network profiler (request tracking)
- [ ] Energy profiler (battery usage)

**Memory Leaks:**
- [ ] LeakCanary setup
- [ ] Common leak patterns (Activity refs, listeners)
- [ ] Weak references

**Performance Optimization:**
- [ ] LazyColumn best practices
- [ ] Overdraw detection
- [ ] Layout Inspector
- [ ] Baseline profiles (advanced)

**Project:**
- [ ] Profile Midnight Wallet
- [ ] Optimize identified bottlenecks
- [ ] AI Chat: Architecture planning

**LeetCode:**
- [ ] 7 medium-hard (Advanced DP)

---

### Week 10: Advanced Android Topics (18 hours)

**Custom Views:**
- [ ] `onMeasure` (determine size)
- [ ] `onLayout` (position children)
- [ ] `onDraw` (render on canvas)
- [ ] Touch events (`onTouchEvent`)

**WorkManager:**
- [ ] OneTime vs Periodic work
- [ ] Constraints (network, charging, battery)
- [ ] Chaining work
- [ ] Observing work status

**App Optimization:**
- [ ] R8 minification
- [ ] App bundle (AAB)
- [ ] Dynamic feature modules (advanced)

**Project:**
- [ ] AI Chat: Multi-module setup
- [ ] AI Chat: LLM abstraction layer

**LeetCode:**
- [ ] 7 hard (Graphs, advanced)

---

### Week 11: System Design Practice (20 hours)

**Practice These Designs:**
- [ ] Instagram feed (pagination, caching, offline)
- [ ] WhatsApp messaging (real-time, encryption)
- [ ] Uber driver app (location, real-time)
- [ ] Google Maps offline (tile storage, search)

**Document:**
- [ ] AI Chat architecture decisions
- [ ] System design doc started

**Project:**
- [ ] AI Chat: Core LLM integration (OpenAI, Anthropic, Gemini)
- [ ] AI Chat: Streaming implementation

**LeetCode:**
- [ ] 7 hard (Backtracking)

**Prep:**
- [ ] STAR stories documented (Nike, eBay, blockchain)

---

### Week 12: Mock Interviews Begin (18 hours)

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

**Project:**
- [ ] AI Chat: Voice integration (STT + ElevenLabs TTS)
- [ ] AI Chat: Streaming responses working

**LeetCode:**
- [ ] 7 hard (timed practice, 45 min each)

---

## Month 4: Polish + Interview Ready

### Week 13: Advanced Testing + CI (15 hours)

**Testing:**
- [ ] Screenshot tests (Paparazzi/Roborazzi)
- [ ] Espresso integration tests
- [ ] Test double strategies (fake vs mock)
- [ ] Turbine for Flow testing

**CI/CD:**
- [ ] GitHub Actions matrix builds
- [ ] Automated tests on PR
- [ ] Lint + detekt checks
- [ ] Code coverage reports

**Project:**
- [ ] AI Chat: 80%+ test coverage
- [ ] AI Chat: GitHub Actions running

**LeetCode:**
- [ ] 7 timed medium (20 min target each)

---

### Week 14: Security + Best Practices (18 hours)

**Security:**
- [ ] API key security (BuildConfig, not hardcoded)
- [ ] Authentication flows (OAuth, JWT)
- [ ] SSL pinning
- [ ] Encrypted storage (Room, SharedPrefs)

**Best Practices:**
- [ ] ProGuard rules
- [ ] Code obfuscation
- [ ] Remove debug logs in release
- [ ] Security audit checklist

**Project:**
- [ ] AI Chat: Security hardening
- [ ] AI Chat: Performance optimization

**LeetCode:**
- [ ] 7 hard (30 min target each)

---

### Week 15: System Design Mastery (20 hours)

**Practice More Designs:**
- [ ] Netflix video player (streaming, offline)
- [ ] Twitter timeline (pagination, real-time)
- [ ] Medium reading app (offline, sync)
- [ ] Spotify music player (audio, background)

**Documentation:**
- [ ] AI Chat: System design doc complete
- [ ] Architecture diagrams (draw.io)

**LeetCode:**
- [ ] 7 company-tagged (Google, Meta, Amazon)

---

### Week 16: Interview Bootcamp (20 hours)

**Mock Interviews:**
- [ ] Mock interview 3: Behavioral
- [ ] Mock interview 4: Leetcode + System design
- [ ] Mock interview 5: Full loop simulation
- [ ] Mock interview 6: Android Q&A

**Android Flashcards:**
- [ ] Review 100+ Android flashcards
- [ ] Practice explaining out loud

**Project:**
- [ ] AI Chat: Complete and publish to GitHub
- [ ] AI Chat: Demo video recorded
- [ ] All 3 projects polished

**LeetCode:**
- [ ] 7 hard (weakness focus)

---

## Month 5-6: Active Interviewing

### Week 17-20: Application + Interviews

**Resume:**
- [ ] Resume updated with 3 projects
- [ ] LinkedIn profile optimized
- [ ] Portfolio links added

**Applications:**
- [ ] 15 applications (Week 17)
- [ ] 30-40 total applications
- [ ] Track in spreadsheet

**Interview Prep:**
- [ ] Daily LeetCode (1 problem maintenance)
- [ ] Weekly system design review
- [ ] STAR stories rehearsal
- [ ] Mock interviews 2x/week

**LeetCode:**
- [ ] Maintain 180-200 problems

---

## Completion Tracking

**By end of each month, you should have checked off:**
- **Month 1:** All Kotlin, MVVM, Compose basics + ElevenLabs SDK
- **Month 2:** Clean Arch, multi-module, Midnight Wallet complete
- **Month 3:** Advanced topics, AI Chat in progress, mock interviews started
- **Month 4:** AI Chat complete, 150+ LeetCode, interview-ready
- **Month 5-6:** Active interviews, 200 LeetCode complete

---

**Use this checklist weekly to ensure you're covering all critical Android topics. Check off items as you master them, not just as you read about them.**
