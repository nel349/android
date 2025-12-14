# Your Personalized Elite Android Engineer Roadmap
## 3-6 Month Aggressive Plan to FAANG-Ready

---

## YOUR SITUATION (Starting Point)

### Your Strengths 💪
- **Nike & eBay mobile experience** - You've shipped production code at scale
- **UT Austin CS degree** - Algorithm foundation exists, just needs refresh
- **Blockchain background** - Proves you can master complex technology fast (Near, Algorand, Solana, Ethereum)
- **SDET/QA expertise** - Quality mindset, testing knowledge
- **Fast learner** - Multiple domains conquered (mobile, blockchain, testing)

### Your Current Gaps (Honest Assessment) 🎯
- **Rusty on modern Android** - Kotlin, Compose, Coroutines/Flow, MVVM/MVI all need refresh
- **Leetcode skills degraded** - CS degree means foundation exists, but algorithms need practice
- **Portfolio gap** - No recent showcase of elite Android work
- **Interview prep** - Need FAANG-specific preparation (behavioral stories, system design)

### Your Timeline ⏰
- **3-6 months (aggressive)** - Can dedicate 2-3 hours daily
- **Total hours available:** 420-630 hours over 6 months
- **Target:** FAANG/Big Tech Android roles (Google, Meta, Amazon, Apple)

### Why This Plan Will Work
1. You're **rusty, not clueless** - Shaking off rust is 5x faster than learning from scratch
2. You have **CS fundamentals** - Algorithms just need refresh, not learn from zero
3. You know **production scale** - Nike/eBay experience is huge advantage
4. You learn **complex tech fast** - Blockchain proves this
5. **Clear targets** - 3 projects, 200 leetcode problems, systematic interview prep

---

# THE 6-MONTH PLAN

## MONTH 1: RUST REMOVAL - Foundation Rebuild

**Goal:** Regain fluency in Kotlin and modern Android fundamentals
**Time commitment:** 2 hrs weekday, 6 hrs weekend = ~60 hours total

### Week 1: Kotlin Deep Dive (15 hours)

**Mon-Tue:** Kotlin syntax, scope functions, null safety
- let, run, with, apply, also - when to use each
- Avoid !!, use safe calls (?.) and elvis (?:)
- lateinit vs lazy delegation

**Wed-Thu:** Coroutines fundamentals
- launch vs async (when to use each)
- Dispatchers (Main, IO, Default, Unconfined)
- Context switching (withContext)

**Fri-Sat:** Flow basics
- StateFlow vs SharedFlow (differences and use cases)
- Flow operators (map, filter, combine)
- Collecting flows lifecycle-aware

**Sun:** Build mini-app
- **Weather App** using OpenWeatherMap API
- Practice: Retrofit, Coroutines, Flow, basic error handling
- Simple UI, focus on architecture

**Resources:**
- Kotlin docs (official)
- "Kotlin Coroutines by Tutorials" book
- Exercism.io Kotlin track

**Success Metric:** ✅ Can explain every Kotlin feature and when/why to use it

---

### Week 2: MVVM + Jetpack Components (15 hours)

**Mon-Tue:** Architecture basics
- ViewModel - what it is, why it survives config changes
- LiveData vs Flow for UI state
- Lifecycle-aware components

**Wed-Thu:** Room database
- Entities, DAOs, Database setup
- Migrations
- Relationships (one-to-many, many-to-many)

**Fri:** Navigation Component
- SafeArgs for type-safe navigation
- Deep linking
- Multi-screen navigation

**Sat-Sun:** Expand Weather App
- Add: Room caching, multi-screen navigation, offline-first
- Architecture: Proper MVVM with Repository pattern
- Data flow: API → Repository → ViewModel → UI

**Success Metric:** ✅ Weather app follows clean MVVM, works offline

---

### Week 3: Dependency Injection + Testing (15 hours)

**Mon-Tue:** Hilt fundamentals
- @Module, @Provides, @Inject annotations
- Component scopes (@Singleton, @ActivityScoped, @ViewModelScoped)
- When to use constructor injection vs @Provides

**Wed:** Testing basics
- JUnit for unit tests
- MockK for mocking (Kotlin-friendly)
- Turbine for Flow testing

**Thu:** Practice
- Add Hilt to Weather app
- Write unit tests for ViewModels
- Test Room DAOs

**Fri-Sun:** **PROJECT 1 START - Hacker News Reader**
- Features: Article list, comments, save favorites, search
- Stack: Kotlin, Coroutines, Flow, MVVM, Hilt, Room, Retrofit
- Goal: Production-quality code, 80%+ test coverage
- Plan architecture and start implementation

**Success Metric:** ✅ Comfortable with Hilt setup and testing patterns

---

### Week 4: Compose Basics + Leetcode Restart (18 hours)

**Mon-Tue:** Compose fundamentals
- Composable functions
- State and recomposition
- remember vs rememberSaveable
- Layouts (Row, Column, Box, LazyColumn)

**Wed:** Side effects
- LaunchedEffect (one-time effects, key-based relaunch)
- DisposableEffect (cleanup needed)
- rememberCoroutineScope

**Thu:** Compose + ViewModel integration
- Collecting StateFlow in Compose
- State hoisting patterns
- ViewModel in Compose screens

**Fri-Sun:**
- **PROJECT 1 COMPLETE:** Build UI in Jetpack Compose
- Finish Hacker News Reader app
- Polish: Loading states, error states, empty states
- Deploy to GitHub with professional README

**LEETCODE STARTS:**
- 1 hour/day (7 hours this week)
- Focus: Arrays and Strings (easy problems)
- Goal: 7 easy problems solved
- Platform: LeetCode, use NeetCode.io for patterns

**Deliverables:**
- ✅ **Hacker News Reader app** (GitHub, README, architecture diagram)
- ✅ **7 leetcode problems** solved
- ✅ **Comfortable writing Kotlin/Coroutines/Flow**

**Month 1 Total Progress:**
- Portfolio apps: 1 completed
- Leetcode: 15 easy problems
- Hours invested: ~60 hours
- Confidence: Can build MVVM app from scratch ✅

---

## MONTH 2: DEPTH - Clean Architecture & Advanced Topics

**Goal:** Go beyond basics into production-grade architecture
**Time commitment:** 2-3 hrs daily = ~70 hours total

### Week 5: Clean Architecture + Multi-Module (15 hours)

**Mon-Tue:** Clean Architecture principles
- Domain layer (use cases, entities)
- Data layer (repositories, data sources)
- Presentation layer (ViewModels, UI)
- Dependency rule (inner layers don't know outer)

**Wed-Thu:** Multi-module setup
- Feature modules vs core modules
- Why modularize (build speed, team scalability, reusability)
- Version catalogs for dependency management
- Handling circular dependencies

**Fri:** Study reference apps
- Nowinandroid by Google (gold standard)
- Tivi by Chris Banes (Compose + MVI)
- Plaid by Nick Butcher (beautiful UI)
- Analyze their module structure

**Sat-Sun:** **PROJECT 2 PLANNING - Reddit Clone**
- Features: Feed (posts), subreddits, comments, user profiles, offline caching
- Architecture: Clean Architecture, multi-module
- Modules: app, feature-feed, feature-comments, core-data, core-network, core-database
- Plan dependencies and data flow

**Leetcode:** 1.5 hrs/day (10.5 hours)
- Arrays, HashMaps, Two Pointers (easy-medium mix)
- Goal: 7 problems (2 medium, 5 easy)

**Success Metric:** ✅ Understand Clean Architecture and multi-module benefits

---

### Week 6: Advanced Coroutines + Flow (18 hours)

**Mon-Tue:** Structured concurrency
- coroutineScope vs supervisorScope
- Exception handling in coroutines
- Job, SupervisorJob
- Cancellation and timeouts

**Wed-Thu:** Advanced Flow
- Hot vs Cold flows (when each is appropriate)
- SharedFlow configuration (replay, buffer)
- StateFlow as single source of truth
- Flow exception handling (catch, retry)

**Fri:** Flow operators deep dive
- Transformation (map, flatMapConcat, flatMapMerge)
- Combination (combine, zip, merge)
- Terminal operators (collect, first, toList)

**Sat-Sun:** **PROJECT 2** - Implement data layer
- Repository pattern with Flow
- Offline-first: Cache-first strategy
- Network layer with Retrofit
- Room for local storage
- Error handling throughout

**Leetcode:** 1.5 hrs/day (10.5 hours)
- Linked Lists, Stacks, Queues (medium problems)
- Goal: 7 problems (4 medium, 3 easy)

**Success Metric:** ✅ Can architect offline-first data layer with Flow

---

### Week 7: Advanced Compose (18 hours)

**Mon-Tue:** Custom layouts and drawing
- Custom Modifier
- Layout composable
- Canvas and drawing APIs
- Animations (animateDpAsState, animateColorAsState)

**Wed-Thu:** Performance optimization
- @Stable and @Immutable annotations
- remember with keys
- derivedStateOf (expensive calculations)
- LazyColumn optimization (keys, contentType)

**Fri:** Compose testing
- ComposeTestRule
- Testing state and interactions
- Semantics for accessibility and testing

**Sat-Sun:** **PROJECT 2** - Build presentation layer
- LazyColumn with pagination (infinite scroll)
- Pull-to-refresh
- State hoisting (hoist state up, pass events down)
- Navigation with Compose Navigation
- Beautiful UI (Material 3, custom theme)

**Leetcode:** 1.5 hrs/day (10.5 hours)
- Trees, Binary Search (medium problems)
- Goal: 7 problems (5 medium, 2 easy)

**Success Metric:** ✅ Can build complex Compose UI with good performance

---

### Week 8: System Design Introduction (20 hours)

**Mon-Wed:** Android system design patterns (9 hours)
- Offline-first architecture (when and why)
- Caching strategies (memory cache, disk cache, cache invalidation)
- Push notifications (FCM setup, handling, deep links)
- Real-time updates (WebSockets vs polling)
- Background processing (WorkManager)
- Image loading and caching (Coil, Glide)

**Thu-Fri:** **PROJECT 2 COMPLETE** (6 hours)
- Add: Image loading (Coil), pull-to-refresh, infinite scroll
- Polish: All states (loading, error, empty, success)
- Tests: Unit tests for ViewModels, integration tests for repos
- CI/CD: GitHub Actions for build and tests
- README: Architecture diagram, tech stack, screenshots/GIFs

**Sat-Sun:** Deploy and document (5 hours)
- Clean up codebase
- Comprehensive README
- Deploy to GitHub
- Write architecture doc explaining design decisions

**Leetcode:** 1.5 hrs/day (10.5 hours)
- DFS/BFS graph traversal (medium problems)
- Goal: 7 problems (5 medium, 2 hard attempt)

**Deliverables:**
- ✅ **Reddit Clone app** (multi-module, Clean Architecture, production-quality, GitHub)
- ✅ **2 portfolio apps total**
- ✅ **50 leetcode problems solved** (mix of easy/medium)
- ✅ **Can design basic Android features** end-to-end

**Month 2 Total Progress:**
- Portfolio apps: 2 completed
- Leetcode: 50 total (30 easy, 20 medium)
- Hours invested: ~130 hours cumulative
- Confidence: Understand Clean Architecture, multi-module, system design basics ✅

---

## MONTH 3: FAANG-LEVEL SKILLS - Performance & Advanced Topics

**Goal:** Build FAANG-ready technical depth
**Time commitment:** 2-3 hrs daily = ~75 hours total

### Week 9: Performance & Profiling (15 hours)

**Mon-Tue:** Android Profiler
- CPU Profiler (method tracing, call stack)
- Memory Profiler (heap dumps, memory leaks)
- Network Profiler (API calls, payload sizes)

**Wed:** Memory leak detection
- Common causes (Activity context in ViewModel, static references, listeners)
- LeakCanary integration
- Prevention strategies

**Thu:** Performance optimization
- RecyclerView/LazyColumn optimization (view recycling, DiffUtil)
- Overdraw detection and reduction
- Layout inspector for UI performance

**Fri-Sun:** Performance audit
- Profile your Reddit Clone app
- Find and fix performance issues
- Document findings in README (before/after metrics)
- Optimize critical paths

**Leetcode:** 2 hrs/day (14 hours)
- Dynamic Programming basics (Fibonacci, climbing stairs, coin change)
- Goal: 7 problems (3 medium, 4 easy DP intro)

**Success Metric:** ✅ Can profile and optimize Android app performance

---

### Week 10: Advanced Topics Deep Dive (18 hours)

**Mon:** Custom Views
- View lifecycle (onMeasure, onLayout, onDraw)
- Touch events (onTouchEvent)
- Custom attributes (attrs.xml)

**Tue:** WorkManager for background tasks
- OneTimeWorkRequest vs PeriodicWorkRequest
- Constraints (network, battery, charging)
- Chaining work

**Wed:** Android app architecture patterns
- Single-activity architecture (why and when)
- Modularization strategies (by feature vs layer)
- Shared element transitions

**Thu:** App optimization
- ProGuard/R8 (shrinking, obfuscation, optimization)
- App bundle vs APK
- App size reduction strategies

**Fri-Sun:** Research & document
- Study FAANG Android apps in the wild (Twitter, Instagram, WhatsApp)
- Reverse engineer architecture decisions
- Document learnings for future reference
- Start prepping for system design interviews

**Leetcode:** 2 hrs/day (14 hours)
- More DP, Greedy algorithms (knapsack, longest subsequence)
- Goal: 7 problems (5 medium, 2 hard)

**Success Metric:** ✅ Understand advanced Android topics, can discuss trade-offs

---

### Week 11: System Design Practice (20 hours)

**Mon:** Design Instagram feed feature
- Requirements (functional, non-functional)
- API design, data models
- Architecture (offline-first, caching, pagination)
- Discuss trade-offs

**Tue:** Design WhatsApp messaging
- Real-time messaging (WebSockets)
- Message persistence (Room)
- Encryption considerations
- Offline mode

**Wed:** Design Uber driver app
- Location tracking (FusedLocationProvider)
- Real-time updates (driver location, ride requests)
- Background processing (WorkManager, foreground service)
- Battery optimization

**Thu:** Design Google Maps offline
- Tile-based map system
- Download and storage (space constraints)
- Updates and versioning

**Fri-Sun:** **PROJECT 3 PLANNING - Social Media App**
- Your masterpiece to showcase everything
- Features: Feed (posts with images), likes, comments, user profiles, follow system
- Tech: Multi-module, Clean Arch, Compose, Hilt, Coil, Room, Retrofit, WorkManager
- Bonus: KMM for shared business logic (stretch goal)
- Plan architecture thoroughly

**Leetcode:** 2 hrs/day (14 hours)
- Graphs (DFS, BFS, shortest path, topological sort)
- Goal: 7 problems (4 medium, 3 hard)

**Success Metric:** ✅ Can design complex Android features with system constraints

---

### Week 12: Mock Interviews Begin + STAR Stories (18 hours)

**Mon-Wed:** **PROJECT 3** - Core implementation (9 hours)
- Set up multi-module structure
- Implement authentication (fake login for now)
- Start feed feature (UI + ViewModel)

**Thu-Fri:** Prepare STAR stories (6 hours)
- **Nike experience:**
  - Technical challenge overcome
  - Cross-team collaboration
  - Handling ambiguous requirements
- **eBay experience:**
  - Scalability challenge
  - Mentoring experience
  - Tight deadline project
- **Blockchain experience:**
  - Learning complex tech fast
  - Building from scratch
  - Production incident handling
- **Generic:**
  - Biggest failure and learning
  - Conflict with teammate
  - Disagreement with manager

**Sat-Sun:** First mock interviews (3 hours)
- Schedule on Pramp (free) or Interviewing.io
- 1 leetcode session (45 min)
- 1 system design session (45 min)
- Record feedback

**Leetcode:** 2 hrs/day (14 hours)
- Mixed review, timed practice
- Goal: 7 problems (4 medium, 3 hard, all timed)

**Deliverables:**
- ✅ **2 complete portfolio apps, 1 in progress**
- ✅ **100 leetcode problems solved** (40 easy, 50 medium, 10 hard)
- ✅ **First mock interviews completed**
- ✅ **STAR stories documented**

**Month 3 Total Progress:**
- Portfolio apps: 2 complete, 1 in progress
- Leetcode: 100 total (40 easy, 50 medium, 10 hard)
- Hours invested: ~205 hours cumulative
- Mock interviews: 2 sessions
- Confidence: FAANG-level technical depth building ✅

---

## MONTH 4: POLISH - Testing, CI/CD, Interview-Ready

**Goal:** Become interview-ready, complete portfolio
**Time commitment:** 2-3 hrs daily = ~75 hours total

### Week 13: Advanced Testing & CI/CD (15 hours)

**Mon-Tue:** Compose UI testing
- ComposeTestRule
- Assertions (assertExists, assertTextEquals)
- Actions (performClick, performTextInput)
- Screenshot testing (Paparazzi or similar)

**Wed:** Espresso for integration tests
- ViewMatchers, ViewActions, ViewAssertions
- Testing RecyclerView
- Idling resources for async operations

**Thu:** GitHub Actions CI/CD
- Set up workflow (build, test, lint)
- Code coverage reporting
- Automated releases

**Fri-Sun:** **PROJECT 3** - Add comprehensive tests (6 hours)
- Unit tests for all ViewModels (100% coverage)
- Integration tests for repositories
- UI tests for critical flows (login, post creation, feed scroll)
- GitHub Actions workflow

**Leetcode:** 2 hrs/day (14 hours)
- Timed medium problems (20 min target)
- Goal: 7 problems (6 medium, 1 hard)

**Success Metric:** ✅ Can write comprehensive test suites with CI/CD

---

### Week 14: Security & Best Practices (18 hours)

**Mon:** API key security
- Hiding API keys (not in version control)
- ProGuard rules for obfuscation
- NDK for key storage (advanced)

**Tue:** Authentication flows
- OAuth 2.0 flow
- JWT token handling
- Refresh token mechanism
- Secure token storage (EncryptedSharedPreferences)

**Wed:** Security best practices
- SSL pinning
- Certificate transparency
- Network security config
- Data encryption at rest

**Thu-Sun:** **PROJECT 3 COMPLETE** (11 hours)
- Add authentication (real or mocked OAuth)
- Security best practices implemented
- Performance optimization (profile and optimize)
- Accessibility (content descriptions, talkback support)
- Polish UI (animations, transitions, Material 3 theming)
- Deploy to GitHub with comprehensive README

**Leetcode:** 2 hrs/day (14 hours)
- Hard problems, timed (30 min target)
- Goal: 7 problems (3 medium, 4 hard)

**Success Metric:** ✅ Understand Android security, deployed production-quality app

---

### Week 15: System Design Mastery (20 hours)

**Mon:** Design Netflix app
- Video player (ExoPlayer)
- Offline downloads
- Adaptive streaming (quality based on network)
- Playback state persistence

**Tue:** Design Google Maps offline functionality
- Already did this in Month 3, now deeper
- Tile caching, storage limits, updates
- Location tracking, battery optimization

**Wed:** Design Twitter timeline
- Infinite scroll, pagination
- Real-time updates (new tweets appear)
- Image/video in feed
- Caching strategy

**Thu:** Design Medium reading app
- Article rendering (web content in app)
- Offline reading mode
- Bookmark syncing
- Reading progress tracking

**Fri-Sun:** Write system design docs (8 hours)
- For your Social Media app (Project 3)
- Architecture diagrams (draw.io or Excalidraw)
- Data flow documentation
- Trade-off decisions explained
- Scalability considerations
- Add to GitHub README

**Leetcode:** 2 hrs/day (14 hours)
- Mock interview problems (company-tagged)
- Goal: 7 problems (4 medium, 3 hard, Google/Meta/Amazon tags)

**Success Metric:** ✅ Can design and document complex systems thoroughly

---

### Week 16: Interview Bootcamp (20 hours)

**Mon-Tue:** Mock interviews (6 hours)
- 2 full sessions
  - 1 behavioral + Android Q&A
  - 1 leetcode (2 problems) + system design
- Record sessions if possible
- Take detailed notes

**Wed-Thu:** Review and gap filling (6 hours)
- Analyze mock interview performance
- Identify weak areas
- Study those topics deeply
- If algorithm X is weak, drill that pattern
- If architecture explanations unclear, practice more

**Fri-Sun:** Targeted practice (8 hours)
- Focus on identified gaps
- More mock interviews if needed
- Flashcards for Android theory
- Practice explaining concepts out loud (as if to interviewer)

**Leetcode:** 2 hrs/day (14 hours)
- Problem types you struggle with
- Goal: 7 problems (mix based on weaknesses)

**Deliverables:**
- ✅ **3 production-quality portfolio apps on GitHub**
- ✅ **150 leetcode problems solved** (40 easy, 90 medium, 20 hard)
- ✅ **4+ mock interviews completed**
- ✅ **Can solve medium in 20 min, hard in 30-40 min**

**Month 4 Total Progress:**
- Portfolio apps: 3 completed (Hacker News, Reddit Clone, Social Media)
- Leetcode: 150 total (40 easy, 90 medium, 20 hard)
- Hours invested: ~280 hours cumulative
- Mock interviews: 6+ sessions
- Confidence: Interview-ready ✅

---

## MONTH 5: APPLICATION WAVE 1 - Start Interviewing

**Goal:** Start applying, get interview experience, iterate
**Time commitment:** 2-3 hrs daily = ~70 hours total

### Week 17: Resume & Application Prep (12 hours)

**Mon-Tue:** Resume optimization (6 hours)
- Quantify Nike/eBay achievements
  - "Reduced app crash rate by X%"
  - "Improved performance metric by Y%"
  - "Shipped feature used by Z million users"
- Highlight portfolio projects with metrics
  - "Built Reddit clone with 100% Compose, multi-module architecture, 80% test coverage"
- Emphasize blockchain (shows fast learning)
  - "Developed dApps for 4 blockchain platforms (Near, Algorand, Solana, Ethereum)"
- Use FAANG resume templates

**Wed:** LinkedIn optimization (3 hours)
- Professional headshot
- Compelling headline: "Android Engineer | Nike, eBay | Kotlin, Compose, Clean Architecture"
- Experience section matches resume
- Add portfolio projects with links
- Skills section (endorsements for Kotlin, Android, etc.)
- Activity (share Android posts, engage with content)

**Thu:** Apply to first batch (3 hours)
- **FAANG (3-4 companies):**
  - Google (Android team)
  - Meta (various Android roles)
  - Amazon (Android)
  - Apple (if iOS experience translates)
- **Tier-2 (5-8 companies):**
  - Uber, Airbnb, Snap, Pinterest, Twitter, Stripe, Square, Robinhood
- **Startups (3-5 companies for backup):**
  - xAI, Anthropic, Whatnot, and other YC companies
- Use job boards: LinkedIn, AngelList, Built In, company career pages

**Fri-Sun:** Leetcode marathon (15+ problems)
- Contest simulation
- Timed practice (20 min per medium, 40 min per hard)

**Leetcode:** 2 hrs/day (14 hours total this week)
- Mock contest simulation
- Goal: 10 problems (6 medium, 4 hard)

**Success Metric:** ✅ Resume polished, 15 applications submitted

---

### Week 18-19: Interview Loop Begins (20 hrs/week each)

**Focus:** Active interview mode

**Daily activities:**
- **Leetcode maintenance:** 1.5 hrs/day (stay sharp)
- **System design practice:** 3x/week (30-45 min sessions)
- **Mock interviews:** 2x/week (schedule on Pramp, Interviewing.io)
- **Android flashcards:** 30 min/day (theory questions)
- **Behavioral prep:** Review STAR stories before each interview

**Application strategy:**
- Continue applying (aim for 30-40 total applications)
- Apply in batches (10-15 per week)
- Track in spreadsheet:
  - Company, role, date applied, status, contacts
  - Interview dates and types
  - Feedback notes

**Interview types:**
- **Recruiter screens:** 15-30 min, behavioral, sell yourself
- **Technical phone screens:** 45-60 min, 1-2 leetcode problems
- **Android Q&A:** 45-60 min, theory questions, architecture discussion
- **System design:** 45-60 min, design Android feature
- **Behavioral:** 30-45 min, STAR stories
- **Onsite:** 4-5 rounds (mix of above)

**Strategy:**
- Schedule strategically: Save top choices (Google, Meta) for later
- Use early interviews as practice
- Document every question asked for future reference
- Send thank-you emails after each interview

**Leetcode:** 1.5 hrs/day (21 hours each week)
- Goal: 7 problems/week (4 medium, 3 hard)

**Success Metric:** ✅ 10-15 recruiter screens, 3-5 phone screens started

---

### Week 20: Reflection & Adjustment (15 hours)

**Mon-Tue:** Analyze interview feedback (6 hours)
- What went well?
- What needs improvement?
- Common question patterns?
- Weak areas identified?

**Wed-Thu:** Address weak areas (6 hours)
- If Compose feedback: Build Compose-only feature
- If testing feedback: Write comprehensive test suite
- If system design feedback: Document 3 more designs
- If algorithm weakness: Drill that pattern

**Fri-Sun:** Continue interview prep (3 hours)
- More mock interviews
- Refine STAR stories based on feedback
- Practice weak areas

**Leetcode:** 1.5 hrs/day (10.5 hours)
- Focus on problem types that came up in interviews
- Goal: 7 problems

**Deliverables:**
- ✅ **30-40 applications submitted**
- ✅ **10-15 recruiter screens**
- ✅ **3-5 technical phone screens**
- ✅ **1-2 onsite loops** (hopefully)
- ✅ **Identified and addressed weak areas**

**Month 5 Total Progress:**
- Applications: 30-40 submitted
- Interviews: 10-15 recruiter screens, 3-5 phone screens
- Leetcode: 180 total (40 easy, 110 medium, 30 hard)
- Hours invested: ~350 hours cumulative
- Confidence: Active in interview pipeline ✅

---

## MONTH 6: FINAL PUSH - Close Offers

**Goal:** Close interviews, get offers, negotiate
**Time commitment:** Variable (interview-dependent) ~70-100 hours

### Week 21-23: Interview Peak Season (25 hrs/week)

**Primary focus:** Active interview loops

**Daily schedule (flexible based on interviews):**
- **Interviews:** 1-3 per week (phone screens, onsites)
- **Leetcode maintenance:** 1 hr/day (stay sharp, don't let skills degrade)
- **System design prep:** 3-4 hrs before each onsite
- **Behavioral prep:** Review STAR stories before each behavioral round
- **Rest properly:** Don't burn out, sleep well before interviews

**Interview strategy:**
- **Time onsites strategically:** 1-2 per week max (exhausting)
- **Learn from each:** Document questions, improve for next
- **Network:** Connect with Android engineers at target companies (LinkedIn, Twitter)
  - Ask for referrals (referrals >> cold apply)
  - Informational interviews
- **Stay organized:** Track all interview states in spreadsheet

**Company-specific prep:**
- **FAANG:** Leetcode (2 problems) + system design + behavioral
- **Startups (xAI, Anthropic):** Live coding + architecture + product sense
- Before each onsite: Research company, product, Android challenges they might have

**Leetcode:** 1 hr/day (21 hours/week)
- Maintenance mode
- Goal: 3-5 problems/week (review previously solved, try new hard)

**Success Metric:** ✅ 2-3 onsite loops, staying sharp, learning from each

---

### Week 24: Evaluation & Negotiation (Variable)

**If you have offers:**
- **Research compensation:**
  - levels.fyi for comp data (base, stock, bonus)
  - Blind app for anonymous comp discussion
  - Talk to recruiters at multiple companies
- **Negotiate:**
  - Leverage multiple offers
  - Don't accept first offer immediately
  - Ask for more (base, stock, signing bonus)
  - Timeline: "I have other onsites, can I get back to you in 2 weeks?"
- **Evaluate beyond compensation:**
  - Team (who's your manager? team size? Android focus?)
  - Tech stack (modern? legacy? greenfield projects?)
  - Growth (promotion trajectory? learning opportunities?)
  - Culture (work-life balance? remote? office?)

**If no offers yet (backup plan):**
- **Extend timeline 1-2 months:** Not the end, more practice
- **Focus on tier-2 and startups:** Get more interview reps
- **Build one more project:** Address any skill gaps identified
- **Open source contribution:** Contribute to Android, Kotlin, Compose repos
  - Shows initiative
  - Good for resume
  - Networking with maintainers
- **More mock interviews:** Get professional feedback (Interviewing.io)
- **Expand search:** Apply to 20-30 more companies

**Leetcode:** 1 hr/day (optional at this point)
- If still interviewing: maintain
- If offers in hand: you can relax

**Deliverables:**
- ✅ **Hopefully 1+ offers** (goal: 2-3 for leverage)
- ✅ **Negotiated best compensation**
- ✅ **Made informed decision**

**Month 6 Total Progress:**
- Offers: 1-3 (goal)
- Leetcode: 200 total (40 easy, 120 medium, 40 hard)
- Hours invested: ~450 hours cumulative
- Confidence: FAANG-ready, elite Android engineer ✅

---

# PORTFOLIO PROJECTS (The 3 That Get You Hired)

## Project 1: Hacker News Reader (Month 1)
**Purpose:** Prove you can build clean MVVM app with modern stack

**Features:**
- Article list (top stories, new, best, ask, show)
- Article detail with comments (nested threading)
- Save favorites locally
- Search functionality
- Pull-to-refresh

**Tech Stack:**
- Kotlin
- Jetpack Compose (100% Compose UI)
- Coroutines & Flow
- MVVM architecture
- Hilt (dependency injection)
- Room (local database for favorites)
- Retrofit (Hacker News API)
- Coil (image loading)
- JUnit + MockK + Turbine (testing)

**Complexity:** Medium
**Time:** 15-20 hours
**GitHub Must-Haves:**
- Professional README with screenshots
- Architecture diagram
- GIF showing app in action
- Setup instructions
- Highlights of technical decisions

---

## Project 2: Reddit Clone (Month 2)
**Purpose:** Showcase Clean Architecture and multi-module expertise

**Features:**
- Feed (posts from subreddits, infinite scroll, pagination)
- Subreddit browser (search, subscribe)
- Comments (nested, vote, reply)
- User profiles (posts, comments, karma)
- Offline-first (cache posts, read offline)
- Dark mode

**Tech Stack:**
- Kotlin
- Multi-module architecture (by feature + layer)
  - :app, :feature-feed, :feature-comments, :feature-profile
  - :core-data, :core-network, :core-database, :core-ui
- Clean Architecture (domain, data, presentation layers)
- Jetpack Compose
- Coroutines & Flow (advanced: SharedFlow, StateFlow, hot/cold)
- MVI architecture pattern
- Hilt with multi-module
- Room with migrations
- Retrofit + OkHttp
- WorkManager (for syncing)
- GitHub Actions (CI/CD, automated testing)

**Complexity:** High
**Time:** 30-40 hours
**GitHub Must-Haves:**
- Comprehensive README
- Module dependency diagram
- Architecture documentation
- CI/CD badges
- System design doc (why offline-first, caching strategy, etc.)

---

## Project 3: Social Media App (Month 3-4)
**Purpose:** Your masterpiece - shows you can build production-ready, feature-rich app

**Features:**
- User authentication (login, signup, OAuth)
- Feed (posts with images/videos, infinite scroll, pull-to-refresh)
- Create post (text, image upload, camera integration)
- Likes and comments (real-time updates)
- User profiles (edit profile, profile picture, followers/following)
- Follow system (follow/unfollow users)
- Notifications (likes, comments, new followers)
- Search (users, posts)
- Dark mode with custom theming
- Accessibility support (talkback, content descriptions)

**Tech Stack:**
- Kotlin
- Multi-module Clean Architecture
- Jetpack Compose (with custom components, animations)
- MVI architecture
- Coroutines & Flow (advanced patterns)
- Hilt
- Room (with relationships, migrations)
- Retrofit + OkHttp (with interceptors for auth)
- Coil (image loading and caching)
- CameraX (camera integration)
- WorkManager (background upload, sync)
- EncryptedSharedPreferences (secure token storage)
- Firebase (or mock): Auth, Firestore, Storage, Cloud Messaging
- GitHub Actions (build, test, lint, code coverage)
- Comprehensive testing:
  - Unit tests (ViewModels, use cases, repos)
  - Integration tests
  - Compose UI tests
  - Screenshot tests

**Complexity:** Very High
**Time:** 50-60 hours
**GitHub Must-Haves:**
- Production-quality README (looks like a real product)
- Architecture diagrams (module dependencies, data flow)
- System design document
- Video demo or live app link
- Detailed features list
- Tech stack highlights
- Performance metrics (if profiled)
- Code coverage badge

**Bonus (Stretch Goal):**
- Kotlin Multiplatform Mobile (KMM) for shared business logic
- Shows you're cutting-edge, not just following tutorials

---

# LEETCODE STRATEGY (200 Problems to FAANG-Ready)

## Target Distribution by Difficulty

**Total: 200 problems by Month 6**

| Difficulty | Count | Percentage |
|------------|-------|------------|
| Easy       | 40    | 20%        |
| Medium     | 120   | 60%        |
| Hard       | 40    | 20%        |

**Why this distribution:**
- **Easy:** Warm-ups, confidence builders, interview intros
- **Medium:** Majority of FAANG interviews are medium
- **Hard:** 1-2 hard per onsite, shows depth

---

## Focus Areas by Month

### Month 1-2: Foundations (50 problems)
- **Arrays:** Two pointers, sliding window, prefix sum
- **Strings:** Anagrams, palindromes, substring search
- **HashMaps:** Frequency counting, two sum variations
- **Linked Lists:** Fast/slow pointer, reversal, merging
- **Stacks & Queues:** Valid parentheses, monotonic stacks

**Goal:** 25 easy, 25 medium

---

### Month 2-3: Trees & Graphs (50 problems)
- **Binary Trees:** DFS (inorder, preorder, postorder), BFS (level order)
- **Binary Search Trees:** Search, insert, validate
- **Graphs:** DFS, BFS, connected components, cycle detection
- **Binary Search:** On arrays, search space reduction

**Goal:** 10 easy, 30 medium, 10 hard

---

### Month 3-4: Dynamic Programming (50 problems)
- **DP Intro:** Fibonacci, climbing stairs, house robber
- **1D DP:** Longest increasing subsequence, coin change
- **2D DP:** Longest common subsequence, edit distance, knapsack
- **Greedy:** Activity selection, jump game

**Goal:** 5 easy, 35 medium, 10 hard

---

### Month 4-5: Advanced Topics (50 problems)
- **Advanced DP:** State machine DP, interval DP
- **Backtracking:** Permutations, combinations, subsets, N-queens
- **Heaps:** Top K elements, merge K sorted lists
- **Tries:** Word search, autocomplete
- **Union Find:** Disjoint sets, connected components

**Goal:** 0 easy, 30 medium, 20 hard

---

## Practice Strategy

### First Attempt (Critical for Learning)
1. **Read problem carefully** (2 min)
2. **Think of approach** (3-5 min)
   - Brute force first (always)
   - Optimize (if you can)
3. **Code solution** (15-25 min)
4. **Test with examples** (3-5 min)

**Time limits:**
- Easy: 20 min max
- Medium: 30 min max (Month 1-2), 20 min max (Month 3+)
- Hard: 45 min max (Month 1-3), 30 min max (Month 4+)

**If stuck:**
- After time limit: Check solution
- Understand the pattern (don't just memorize code)
- Implement from scratch (don't copy-paste)

### Second Attempt (Spaced Repetition)
- **Redo** problems you struggled with after 1 week
- No peeking at previous solution
- Should be faster second time

### Timed Practice (From Month 3)
- **Weekly mock contests** (Saturday mornings)
- LeetCode Weekly Contest (live) or past contests
- 4 problems in 90 minutes
- Simulates interview pressure

### Company-Specific (Month 5-6)
- **LeetCode Premium:** Company-tagged problems
- Focus on:
  - Google: Medium-hard, often requires optimization
  - Meta: System design + medium leetcode
  - Amazon: Leadership principles + medium leetcode (2 problems)
  - Apple: Focus on fundamentals, medium problems

---

## Resources

### Learning Platforms
- **LeetCode Premium:** Company-tagged questions, video explanations
- **NeetCode.io:** 150 curated problems (patterns-based), video solutions
- **"Cracking the Coding Interview":** Classic patterns, interview tips

### Pattern Recognition
Learn patterns, not individual problems:
- **Sliding Window:** Max sum subarray, longest substring
- **Two Pointers:** Two sum, container with most water
- **Fast & Slow Pointers:** Cycle detection, middle of list
- **Merge Intervals:** Overlapping intervals, insert interval
- **Cyclic Sort:** Missing number, find duplicates
- **In-place Reversal of Linked List**
- **Tree BFS:** Level order traversal, zigzag
- **Tree DFS:** Path sum, diameter
- **Topological Sort:** Course schedule, alien dictionary
- **Binary Search:** Search rotated array, find peak
- **Backtracking:** Permutations, subsets, N-queens
- **Dynamic Programming:** 0/1 knapsack, LCS, LIS

### Study Groups
- Join Android dev communities (Discord, Slack)
- Find leetcode accountability partner
- Discuss approaches, not just solutions

---

## Tracking Progress

Create `leetcode-log.md`:
```
## Month 1
- [x] Week 1: 7 problems (7 easy) - Arrays & Strings
  - Two Sum, Valid Anagram, Contains Duplicate, etc.
- [x] Week 2: 7 problems (5 easy, 2 medium)
- [ ] Week 3: ...

## Stats
- Total: 50/200
- Easy: 30/40
- Medium: 20/120
- Hard: 0/40
```

---

# SYSTEM DESIGN PREPARATION

## Android System Design Patterns to Master

### 1. Offline-First Architecture
**When:** Apps that need to work without network (news, social media)
**Components:**
- Local database (Room) as single source of truth
- Repository pattern (fetch from network, cache in DB, emit from DB)
- WorkManager for background sync
- Conflict resolution (last-write-wins, CRDT)

**Trade-offs:**
- Pros: Better UX, works offline, faster (cache)
- Cons: Complexity, stale data, sync conflicts

---

### 2. Caching Strategies
**Types:**
- **Memory cache:** Fast, small (LRU cache)
- **Disk cache:** Slower, large (Room, files)
- **Network:** Always fresh, requires network

**Strategies:**
- **Cache-first:** Read from cache, fallback to network
- **Network-first:** Fetch fresh, fallback to cache (offline)
- **Stale-while-revalidate:** Show cache immediately, update in background

**Invalidation:**
- Time-based (TTL: time-to-live)
- Manual (user pull-to-refresh)
- Event-based (new data notification)

---

### 3. Pagination & Infinite Scroll
**Components:**
- Paging 3 library
- RemoteMediator (fetch from network, store in DB)
- LazyColumn/RecyclerView with scroll listener

**Challenges:**
- Smooth scrolling (no jank)
- Loading states (shimmer, skeleton)
- Error handling (retry mechanism)

---

### 4. Real-Time Updates
**Approaches:**
- **Polling:** Simple, inefficient (battery drain)
- **WebSockets:** Bi-directional, real-time, battery drain
- **Firebase Cloud Messaging (FCM):** Push notifications, efficient
- **Server-Sent Events (SSE):** One-way, simpler than WebSockets

**When to use:**
- Chat apps: WebSockets
- Social media feed: Polling + FCM
- Notifications: FCM

---

### 5. Background Processing
**Tools:**
- **WorkManager:** Deferred, guaranteed execution (upload, sync)
- **Foreground Service:** User-visible, long-running (music player, location tracking)
- **JobScheduler:** System-level scheduling (deprecated, use WorkManager)

**Constraints:**
- Network type (WiFi, cellular)
- Battery level
- Device charging
- Device idle

---

### 6. Image Loading & Caching
**Libraries:** Coil, Glide, Picasso

**Optimizations:**
- Resize images (don't load full resolution)
- Memory cache (LRU)
- Disk cache
- Placeholder and error images
- Lazy loading (load when visible)

---

### 7. Multi-Module Architecture
**Reasons:**
- **Build speed:** Parallel builds, incremental compilation
- **Team scalability:** Teams own modules
- **Reusability:** Share modules across apps
- **Separation of concerns:** Feature isolation

**Strategies:**
- **By feature:** :feature-feed, :feature-profile
- **By layer:** :data, :domain, :ui
- **Hybrid:** :feature-feed-ui, :feature-feed-data

**Challenges:**
- Circular dependencies (avoid with dependency inversion)
- Shared code (:core modules)
- Version management (version catalogs)

---

### 8. Push Notifications
**Components:**
- Firebase Cloud Messaging (FCM)
- Notification channels (Android 8+)
- Deep linking (navigate to specific screen)

**Best practices:**
- Batching (don't spam user)
- Opt-in (user permission)
- Clear action (what happens on click)

---

## 10 System Designs to Practice (30-45 min each)

### 1. Instagram Feed
**Requirements:**
- Infinite scroll of posts (images, videos, text)
- Like, comment on posts
- Offline mode (cached posts)
- Pull-to-refresh

**Components:**
- Paging 3 for pagination
- Room for local cache
- Coil for image loading
- WorkManager for syncing likes/comments
- LazyColumn with Compose

**Trade-offs:**
- Cache size (limit to last 100 posts)
- Video autoplay (battery vs UX)
- Real-time updates (polling vs FCM)

---

### 2. WhatsApp Messaging
**Requirements:**
- Real-time messaging
- Offline mode (queue messages)
- Message persistence
- Read receipts
- Encryption (E2E)

**Components:**
- WebSockets for real-time
- Room for message storage
- WorkManager for sending queued messages
- Foreground service for persistent connection
- Encryption library (if implementing)

**Trade-offs:**
- WebSocket vs polling (real-time vs battery)
- Message retention (how long to keep)
- Encryption overhead

---

### 3. Uber Driver App
**Requirements:**
- Real-time location tracking
- Ride requests (real-time)
- Navigation
- Offline mode (when no network)
- Battery optimization

**Components:**
- FusedLocationProvider API
- Foreground service (location tracking)
- WebSockets or FCM (ride requests)
- Google Maps SDK
- WorkManager (upload location periodically)

**Trade-offs:**
- Location update frequency (accuracy vs battery)
- Foreground service (user visibility)
- Network efficiency (batch location updates)

---

### 4. Netflix Video Player
**Requirements:**
- Adaptive streaming (quality based on network)
- Offline downloads
- Playback state persistence (resume from where left off)
- Subtitles

**Components:**
- ExoPlayer (or Media3)
- Adaptive streaming (DASH, HLS)
- Room for download metadata
- WorkManager for downloads
- Foreground service for downloading

**Trade-offs:**
- Download storage limits
- Streaming quality (network vs battery)
- DRM (content protection complexity)

---

### 5. Google Maps Offline
**Requirements:**
- Download map tiles for offline use
- Search locations offline
- Navigation offline
- Storage management (limited space)

**Components:**
- Tile-based system (vector tiles)
- Room for tile storage
- Custom tile provider
- Download manager

**Trade-offs:**
- Storage space (how much to allow)
- Tile resolution (detail vs size)
- Update frequency (map changes)

---

### 6. Twitter Timeline
**Requirements:**
- Infinite scroll timeline
- Real-time new tweets (pull-down to see)
- Images/videos in feed
- Offline mode (cached tweets)
- Like, retweet, reply

**Components:**
- Paging 3 for timeline
- Room for cache
- FCM or polling for new tweets
- Coil for media
- WorkManager for syncing actions

**Trade-offs:**
- Real-time vs polling (battery)
- Cache size
- Video autoplay

---

### 7. Medium Reading App
**Requirements:**
- Article list (topics, authors)
- Article rendering (formatted text, images)
- Offline reading mode (download articles)
- Reading progress tracking
- Bookmark syncing

**Components:**
- WebView or custom rendering (Markdown)
- Room for offline articles
- WorkManager for syncing
- SharedPreferences for reading progress

**Trade-offs:**
- WebView vs native rendering (simplicity vs control)
- Download size limits
- Sync frequency

---

### 8. Spotify Music Player
**Requirements:**
- Music playback (foreground service)
- Offline downloads
- Playlists
- Search
- Playback controls (notification, lock screen)

**Components:**
- MediaSession API
- Foreground service
- ExoPlayer
- Room for playlists, downloads
- WorkManager for downloads

**Trade-offs:**
- Download quality (size vs audio quality)
- Streaming vs download (storage)
- Playback notification (UX vs simplicity)

---

### 9. YouTube Video Player
**Requirements:**
- Video playback
- Adaptive streaming
- Picture-in-picture
- Offline mode (download videos)
- Comments, likes

**Components:**
- ExoPlayer (Media3)
- Picture-in-picture mode
- Room for downloads
- WorkManager for downloads
- RecyclerView/LazyColumn for comments

**Trade-offs:**
- PiP battery drain
- Download limits
- Streaming quality

---

### 10. Food Delivery App (DoorDash)
**Requirements:**
- Restaurant list (search, filter, sort)
- Menu (items, add to cart)
- Cart management
- Order tracking (real-time driver location)
- Payment integration

**Components:**
- Paging 3 for restaurant list
- Room for cart persistence
- Google Maps for tracking
- WebSockets or FCM for real-time updates
- Payment SDK (Stripe)

**Trade-offs:**
- Cart sync (local vs server)
- Real-time tracking (battery)
- Payment security

---

## Practice Approach for System Design

**Before interview (30-45 min per design):**
1. **Requirements gathering (5 min)**
   - Functional (what features)
   - Non-functional (performance, offline, battery)
   - Scale (users, data size)

2. **High-level architecture (10 min)**
   - Draw components (UI, ViewModel, Repository, Database, Network)
   - Data flow (user action → ViewModel → Repository → Network/DB → UI)
   - Identify patterns (offline-first, caching, real-time)

3. **Deep dive (15 min)**
   - Choose 1-2 components to go deep
   - Discuss implementation details
   - API design (REST endpoints or SDK calls)
   - Data models (entities, DTOs)

4. **Trade-offs & alternatives (10 min)**
   - Why this approach vs alternatives
   - Performance considerations (memory, battery, network)
   - Scalability (what if 10x users)

5. **Follow-up questions (5 min)**
   - Interviewer will ask edge cases
   - Think out loud, discuss options

**During interview:**
- **Ask clarifying questions first** (don't assume)
- **Think out loud** (interviewer wants to hear your thought process)
- **Draw diagrams** (whiteboard or virtual board)
- **Discuss trade-offs** (no perfect solution, show you understand pros/cons)
- **Handle follow-ups gracefully** (interviewer adding constraints is normal)

---

## Resources for System Design

### Android-Specific
- Your own projects (Reddit Clone, Social Media App) - explain your own architecture
- Google I/O talks (Android architecture, Jetpack libraries)
- Android Developers YouTube channel
- "Now in Android" app source code (Google's best practices)

### General Software Engineering
- "System Design Interview" book by Alex Xu (general SE, adapt to Android)
- "Designing Data-Intensive Applications" (more backend, but concepts apply)

### Mock Interviews
- Practice with peers (Android devs)
- Pramp (free system design practice)
- Interviewing.io (paid, but high quality)

---

# INTERVIEW PREPARATION CHECKLIST

## Behavioral Questions (STAR Stories)

**Prepare 8-10 stories covering these themes:**

### From Nike Experience:
- [ ] **Technical challenge overcome**
  - Example: "Reduced app crash rate by optimizing memory usage"
  - STAR format: Situation, Task, Action, Result
- [ ] **Cross-team collaboration**
  - Example: "Worked with backend team to design new API contract"
- [ ] **Handling ambiguous requirements**
  - Example: "PM gave vague feature request, I clarified with mockups and user stories"

### From eBay Experience:
- [ ] **Scalability challenge**
  - Example: "App slowed down with large data sets, implemented pagination"
- [ ] **Mentoring junior developer**
  - Example: "Onboarded new Android dev, taught MVVM and testing"
- [ ] **Project under tight deadline**
  - Example: "Shipped critical feature in 2 weeks for holiday sale"

### From Blockchain Experience:
- [ ] **Learning complex tech fast**
  - Example: "Learned Solidity and deployed smart contract in 1 month"
- [ ] **Building from scratch (0 to 1)**
  - Example: "Built dApp for NFT marketplace from ground up"
- [ ] **Handling production incident**
  - Example: "Smart contract bug found, quickly deployed fix and communicated with users"

### Generic (Applicable Anywhere):
- [ ] **Biggest failure and what you learned**
  - Example: "Shipped feature with performance issue, learned importance of profiling"
- [ ] **Conflict with teammate and resolution**
  - Example: "Disagreed on architecture, discussed trade-offs, found compromise"
- [ ] **Disagreement with manager**
  - Example: "Manager wanted quick fix, I advocated for proper solution, explained trade-offs"

**STAR Format Template:**
- **Situation:** What was the context?
- **Task:** What was your responsibility?
- **Action:** What did you do specifically?
- **Result:** What was the outcome? (Quantify if possible)

---

## Android Theory Questions (Flashcards)

Create flashcards (Anki or physical cards) for these topics:

### Kotlin
- [ ] Scope functions (let, run, with, apply, also) - when to use each
- [ ] lateinit vs lazy
- [ ] Sealed classes vs Enums
- [ ] Data classes (what gets generated, copy function, equals/hashCode)
- [ ] Coroutines: launch vs async
- [ ] Dispatchers (Main, IO, Default, Unconfined)
- [ ] Flow vs LiveData
- [ ] StateFlow vs SharedFlow
- [ ] Coroutine scopes (GlobalScope, viewModelScope, lifecycleScope)
- [ ] Structured concurrency (coroutineScope, supervisorScope)

### Android Fundamentals
- [ ] Activity lifecycle (onCreate, onStart, onResume, onPause, onStop, onDestroy)
- [ ] Fragment lifecycle (different from Activity)
- [ ] Memory leaks - common causes and prevention
- [ ] ANR (Application Not Responding) - causes and how to avoid
- [ ] Context types (Application vs Activity)
- [ ] Services (foreground, background, bound)
- [ ] Broadcast Receivers
- [ ] Content Providers
- [ ] Intents (explicit, implicit, intent filters)
- [ ] Manifest permissions (runtime vs install-time)

### Architecture
- [ ] MVVM vs MVP vs MVI (when to use each)
- [ ] ViewModel - what it is, why it survives config changes
- [ ] Repository pattern
- [ ] Clean Architecture (domain, data, presentation layers)
- [ ] Dependency Injection - why use it
- [ ] Dependency Inversion Principle

### Jetpack Compose
- [ ] State hoisting (what and why)
- [ ] Recomposition (what triggers it)
- [ ] remember vs rememberSaveable
- [ ] Side effects: LaunchedEffect, DisposableEffect, SideEffect
- [ ] @Stable and @Immutable annotations
- [ ] derivedStateOf (when to use)
- [ ] Compose phases (composition, layout, drawing)
- [ ] Performance optimization in Compose

### Dependency Injection (Hilt/Dagger)
- [ ] @Module and @Provides
- [ ] @Inject (constructor vs field)
- [ ] Scopes (@Singleton, @ActivityScoped, @ViewModelScoped)
- [ ] @Binds vs @Provides
- [ ] Hilt vs Koin (trade-offs)

### Testing
- [ ] Unit tests vs Integration tests vs E2E tests
- [ ] MockK vs Mockito
- [ ] Testing coroutines (TestCoroutineDispatcher, runTest)
- [ ] Turbine for Flow testing
- [ ] Compose UI testing (ComposeTestRule)
- [ ] Espresso for View-based UI testing

### Performance
- [ ] Memory profiling (heap dumps, memory leaks)
- [ ] CPU profiling (method tracing)
- [ ] Overdraw (how to detect and reduce)
- [ ] RecyclerView/LazyColumn optimization
- [ ] ProGuard/R8 (what they do)

### Networking
- [ ] Retrofit setup and usage
- [ ] OkHttp interceptors
- [ ] Error handling in network calls
- [ ] SSL pinning

### Persistence
- [ ] Room (entities, DAOs, database)
- [ ] Room migrations
- [ ] SharedPreferences vs DataStore
- [ ] EncryptedSharedPreferences

---

## Common Interview Questions to Practice

### "Explain X to a junior developer"
This tests your communication skills, not just knowledge.

- [ ] "Explain ViewModel to a junior developer"
- [ ] "Explain dependency injection to a junior developer"
- [ ] "Explain coroutines to a junior developer"
- [ ] "Explain state hoisting in Compose to a junior developer"

**Practice:** Explain out loud, record yourself, refine explanation

---

### "When would you use A vs B?"
This tests your understanding of trade-offs.

- [ ] "When would you use Flow vs LiveData?"
- [ ] "When would you use StateFlow vs SharedFlow?"
- [ ] "When would you use MVVM vs MVI?"
- [ ] "When would you use lateinit vs lazy?"
- [ ] "When would you use launch vs async?"

**Practice:** For each, list pros/cons, give examples

---

### "How does X work internally?"
This tests your depth of understanding.

- [ ] "How does ViewModel survive configuration changes?"
- [ ] "How does Jetpack Compose recomposition work?"
- [ ] "How does Hilt generate code at compile time?"
- [ ] "How does Room work under the hood?"

**Practice:** Read source code, documentation, explain mechanism

---

### Scenario-Based Questions
This tests your problem-solving and experience.

- [ ] "App is crashing, how do you debug?"
  - Answer: Logcat, stack trace, reproduce, isolate, fix, test
- [ ] "App is slow, how do you optimize?"
  - Answer: Profile (CPU, memory, network), identify bottleneck, optimize, measure again
- [ ] "How do you handle API errors?"
  - Answer: Retry logic, user-friendly messages, offline mode, logging
- [ ] "User reports a bug you can't reproduce, what do you do?"
  - Answer: Ask for device/OS, logs, Firebase Crashlytics, beta testing

---

## Mock Interview Practice

### Schedule Regular Mocks (2x per week from Month 3)

**Platforms:**
- **Pramp:** Free peer-to-peer interviews
- **Interviewing.io:** Paid, real engineers from FAANG
- **Blind community:** Find practice partners

**Types of mocks:**
1. **Leetcode only** (45 min)
   - 2 problems (1 easy, 1 medium) or (2 medium)
   - Practice coding while talking through approach
   - Get feedback on code quality, communication

2. **System design** (45 min)
   - Design an Android feature (Instagram feed, chat, etc.)
   - Practice whiteboarding (draw.io, Excalidraw for virtual)
   - Get feedback on depth, trade-off discussion

3. **Behavioral** (30 min)
   - STAR stories
   - Get feedback on storytelling, conciseness

4. **Full loop simulation** (2-3 hours)
   - 1 behavioral + 1 Android Q&A + 1 leetcode + 1 system design
   - Simulate onsite

**After each mock:**
- Document questions asked
- Note feedback (what to improve)
- Practice weak areas

---

# DAILY & WEEKLY SCHEDULES

## Weekday Schedule (2 hours/day)

**Typical structure:**
- **0-30 min:** Leetcode warm-up (1 easy problem, already solved before)
- **30-90 min:** Main leetcode problem (1 medium or hard, new)
- **90-120 min:** Android learning or project work
  - Month 1-2: Study Kotlin, Compose, architecture
  - Month 3-4: Build projects, system design study
  - Month 5-6: Mock interviews, interview prep

**Flexibility:**
- If interview next day: Replace leetcode with interview-specific prep
- If feeling burned out: Lighter day (just 1 easy leetcode + reading)
- If inspired: Extend project work on weekend

---

## Weekend Schedule (6 hours/day, Sat & Sun)

**Typical Saturday:**
- **0-30 min:** Leetcode warm-up
- **30-150 min:** Leetcode deep session (2-3 problems, include 1 hard)
- **150-270 min:** Project building (hands-on coding)
- **270-330 min:** System design practice (design 1 feature, document)
- **330-360 min:** Theory review, flashcards, reading

**Typical Sunday:**
- **0-120 min:** Leetcode (2-3 problems, one hard or contest)
- **120-240 min:** Project building (complete feature, test, deploy)
- **240-300 min:** Mock interview OR system design
- **300-360 min:** Weekly review
  - What did I accomplish this week?
  - Am I on track with monthly goals?
  - Adjust next week's plan if needed

**Flexibility:**
- One weekend day off per month (burnout prevention)
- Adjust based on energy (some days go deep, some days lighter)

---

## Time Allocation by Month

### Month 1-2: Learn Fundamentals + Build
- **40% Android learning** (Kotlin, Coroutines, MVVM, Compose)
- **30% Project building** (Hacker News, Reddit Clone)
- **30% Leetcode** (easy to medium, building habit)

### Month 3-4: Depth + Portfolio
- **30% Android learning** (advanced topics, performance, testing)
- **30% Project building** (Social Media app, the masterpiece)
- **40% Leetcode** (medium to hard, interview prep)

### Month 5-6: Interview Mode
- **20% Android learning** (fill gaps based on feedback)
- **20% Project polish** (if needed, address weak areas)
- **60% Interview prep** (leetcode, system design, mocks, behavioral)

---

# SUCCESS METRICS (Track Your Progress)

## After Month 2:
- [ ] **2 portfolio projects on GitHub** (Hacker News Reader, Reddit Clone)
- [ ] **Comfortable with Kotlin, Coroutines, Flow, MVVM, Hilt**
- [ ] **50 leetcode problems solved** (30 easy, 20 medium)
- [ ] **Can build Android app from scratch** without tutorials

**Self-assessment:**
- Can you explain MVVM to a junior developer? If yes, ✅
- Can you build an offline-first app? If yes, ✅
- Can you solve a medium leetcode in 30 min? If yes, ✅

---

## After Month 4:
- [ ] **3 production-quality portfolio projects** (all on GitHub, professional READMEs)
- [ ] **150 leetcode problems solved** (40 easy, 90 medium, 20 hard)
- [ ] **5+ mock interviews completed**
- [ ] **Can solve medium leetcode in 20 min**
- [ ] **Can design Android features end-to-end** (system design)
- [ ] **Resume polished and ready**

**Self-assessment:**
- Can you design Instagram feed architecture? If yes, ✅
- Can you discuss trade-offs between offline-first vs online-first? If yes, ✅
- Can you code a medium leetcode while explaining approach? If yes, ✅

---

## After Month 6:
- [ ] **200 leetcode problems solved** (40 easy, 120 medium, 40 hard)
- [ ] **10+ recruiter screens**
- [ ] **5+ technical phone screens**
- [ ] **2+ onsite loops**
- [ ] **1+ offer** (goal: 2-3 offers for leverage)
- [ ] **Active GitHub** with 3-5 quality projects

**Self-assessment:**
- Did you pass phone screens? If yes, ✅
- Can you confidently interview at FAANG? If yes, ✅
- Do you feel like an elite Android engineer? If yes, 🎉

---

# RESOURCES & COMMUNITIES

## Learning Platforms

### Kotlin
- **Official Kotlin docs:** kotlinlang.org
- **Kotlin Koans:** Interactive exercises
- **"Kotlin Coroutines by Tutorials"** book by raywenderlich.com
- **Exercism.io Kotlin track:** Practice problems

### Android
- **Android Developers site:** developer.android.com
- **Codelabs:** Hands-on tutorials (Compose, Room, WorkManager, etc.)
- **Android Developers YouTube:** Official Google channel
- **Google I/O talks:** Annual conference (watch past years)
- **"Now in Android" app:** Source code for Google's best practices

### Leetcode
- **LeetCode Premium:** Company-tagged questions, video explanations
- **NeetCode.io:** 150 curated problems, video solutions, patterns
- **"Cracking the Coding Interview"** book: Classic patterns

### System Design
- **"System Design Interview" by Alex Xu:** General SE (adapt to Android)
- **Your own projects:** Best practice is explaining your own architecture

---

## Mock Interview Platforms

- **Pramp:** Free peer-to-peer interviews (pramp.com)
- **Interviewing.io:** Paid, real engineers from FAANG (interviewing.io)
- **Blind:** Community for finding practice partners (teamblind.com)

---

## Reference Apps to Study

- **Nowinandroid** (by Google): Modern best practices, multi-module, Compose
  - GitHub: android/nowinandroid
- **Tivi** (by Chris Banes): Compose, MVI, beautiful architecture
  - GitHub: chrisbanes/tivi
- **Plaid** (by Nick Butcher): Stunning UI, design patterns
  - GitHub: nickbutcher/plaid
- **Google I/O app:** Real production Google app
  - GitHub: google/iosched

---

## Communities

### Reddit
- **r/androiddev:** Active community, job postings, discussions
- **r/cscareerquestions:** General tech careers

### Discord
- **Android Dev Discord:** Real-time chat, study groups
- **Kotlin Discord:** Language-specific help

### Twitter (X)
Follow Android experts:
- **@ChrsBanes** (Chris Banes) - Compose expert
- **@JakeWharton** (Jake Wharton) - Kotlin, Android libraries
- **@crafty** (Manuel Vivo) - Google Android DevRel
- **@objcode** - Android tips and tricks
- **@manuelvicnt** (Manuel Vicente Vivo) - Jetpack, Hilt

### LinkedIn
- Connect with Android engineers at target companies
- Engage with content (like, comment on Android posts)
- Share your projects (post about what you're building)

### Networking
- **Informational interviews:** Reach out to engineers at companies you're targeting
- **Referrals:** Ask for referrals (referrals >> cold apply)
- **Local meetups:** Android meetups, Google Developer Groups
- **Conferences:** Droidcon, Android Dev Summit (virtual options)

---

# RISK MITIGATION (When Things Don't Go as Planned)

## If Falling Behind Schedule

**Scenario:** Month 2, only completed 1 project instead of 2

**Action:**
- **Cut scope, not quality**
  - Better to have 2 excellent projects than 3 mediocre
  - Skip Project 3 or simplify features
- **Extend timeline**
  - 6 months is aggressive, 8-9 months is still great
  - FAANG jobs aren't going anywhere
- **Prioritize interview-critical skills**
  - Leetcode and system design are non-negotiable
  - Advanced topics (custom views, etc.) can wait

**Don't:**
- Rush and produce poor quality code (defeats purpose)
- Skip fundamentals to jump to advanced topics
- Burn out trying to catch up (rest is part of the plan)

---

## If Struggling with Leetcode

**Scenario:** Month 3, can't solve medium problems in 30 min

**Action:**
- **Back to basics**
  - Spend extra week on fundamentals (arrays, hashmaps)
  - Solve more easy problems to build confidence
- **Pattern recognition**
  - Use NeetCode.io to learn patterns, not individual problems
  - Focus on understanding approach, not memorizing code
- **Get help**
  - Join study group (r/leetcode, Discord)
  - Find accountability partner
  - Watch video solutions (don't just read)
- **Adjust expectations**
  - 30 min is a goal, not a requirement
  - Some problems are harder, that's okay
  - Focus on improvement over time

**Don't:**
- Give up on leetcode (it's required for FAANG)
- Only do easy problems (won't prepare you)
- Memorize solutions (you need to understand patterns)

---

## If Interviews Not Converting

**Scenario:** Month 5, did 5 phone screens but all rejected

**Action:**
- **Analyze feedback**
  - What went wrong? (ask recruiters for feedback)
  - Common patterns in rejections?
  - Specific weak areas? (algorithms, system design, communication)
- **More mock interviews**
  - 3-4 mocks per week
  - Practice with different interviewers
  - Get honest, critical feedback
- **Address weak areas**
  - If algorithms: drill leetcode patterns
  - If system design: practice 10 more designs
  - If communication: practice explaining out loud
  - If Android knowledge: review flashcards, study more
- **Expand search**
  - Apply to 50-60 companies instead of 30-40
  - Include tier-2 and startups (not just FAANG)
  - Consider remote-first companies (more opportunities)
- **Build more**
  - Another impressive project can tip the scales
  - Open source contribution (shows initiative)

**Don't:**
- Assume you're not good enough (interviews have randomness)
- Stop applying (volume matters)
- Skip feedback (critical for improvement)

---

## If Burnout Creeping In

**Scenario:** Month 4, feeling exhausted, don't want to code

**Action:**
- **Take a break**
  - 1 week off (completely off, no coding, no leetcode)
  - Do something you enjoy (not tech-related)
  - Rest is part of high performance
- **Adjust schedule**
  - Reduce to 1 hr/day for a week
  - Focus on fun projects (not interview prep)
  - Watch Android talks instead of coding
- **Vary activities**
  - Don't just grind leetcode (soul-crushing)
  - Mix in project building (more creative, satisfying)
  - System design is less draining than algorithms
- **Track wins**
  - Celebrate completed projects
  - Celebrate solved hard problems
  - Celebrate interview milestones
  - Small wins compound

**Don't:**
- Power through (leads to worse burnout)
- Feel guilty about rest (it's necessary)
- Compare to others (everyone's journey is different)

**Remember:** This is a marathon, not a sprint. You're building a career, not just passing interviews.

---

# FINAL THOUGHTS

## Why This Plan Will Work for You

### You Have Unique Advantages:

1. **Nike & eBay experience**
   - You've shipped code at scale
   - You understand production systems
   - You know how to work in a team
   - FAANG wants this (not just tutorial-followers)

2. **CS degree from UT Austin**
   - Algorithm foundation exists (just needs refresh)
   - You understand computer science fundamentals
   - Don't need to learn from scratch

3. **Blockchain background**
   - Proves you can master complex technology fast
   - Smart contracts are harder than most Android concepts
   - Shows you're not afraid of new tech
   - Great interview story (unique, interesting)

4. **SDET/QA experience**
   - Testing mindset is rare in Android devs
   - Quality-focused (FAANG values this)
   - Experience with automation (translates to Android testing)

5. **Motivation**
   - You're taking this seriously (creating 6-month plan)
   - You've identified the goal (FAANG Android)
   - You're willing to put in the work (2-3 hrs/day)

---

## The Reality of This Journey

### It Won't Be Easy:
- **Leetcode is frustrating** (especially at first)
- **Building 3 production apps is a lot of work**
- **Interviews have randomness** (good candidates get rejected sometimes)
- **Imposter syndrome is real** (you'll doubt yourself)

### But You Can Do This:
- **You've done hard things before** (UT Austin CS degree, Nike, eBay, blockchain)
- **You're rusty, not clueless** (shaking off rust is fast)
- **You have a clear plan** (this isn't guesswork)
- **You have time** (420-630 hours over 6 months is substantial)

### Some Will Get Offers Faster, Some Slower:
- **Variables:** Interview luck, timing, company needs
- **Don't compare:** Focus on your own improvement
- **Adjust as needed:** If it takes 7-8 months, that's okay
- **The goal:** Become an elite Android engineer, not just pass interviews

---

## Your Biggest Advantage: Experience

### You're Not a Fresh Grad:
- You've worked at **Nike and eBay** (brand names matter)
- You've shipped **production code** (most candidates haven't)
- You've worked in **teams** (not just solo projects)
- You've dealt with **legacy code** (most real-world work)
- You've **mentored others** (leadership potential)

### This Means:
- **Behavioral interviews:** You have real stories (STAR format)
- **System design:** You understand real-world constraints
- **Technical depth:** You've seen what matters in production (not just tutorials)
- **Product sense:** You think about users, not just code

### FAANG Wants This:
- They're tired of candidates who only know leetcode
- They want people who can **ship products**
- They want people who can **work in teams**
- They want people who can **handle ambiguity**

**You have this.** You just need to prove you can code the interview problems and architect Android systems. That's what the next 6 months are for.

---

## Motivation for Tough Days

### When Leetcode Feels Impossible:
> "I shipped production code at Nike and eBay. I learned blockchain from scratch. I can learn leetcode patterns."

### When Projects Feel Overwhelming:
> "I've built dApps for 4 blockchain platforms. I've worked on apps used by millions. I can build 3 Android apps."

### When Interviews Go Badly:
> "Every rejection is practice. I'm getting better with each interview. I only need one yes."

### When Feeling Behind:
> "I'm not competing with others. I'm competing with my past self. Am I better than yesterday? Yes."

---

## The End Goal

### What Success Looks Like (Month 6+):

**You'll be able to:**
- ✅ Build any Android app from scratch
- ✅ Explain complex concepts to anyone (junior dev, PM, manager)
- ✅ Solve medium leetcode in 20 min, hard in 30-40 min
- ✅ Design Android systems with trade-off discussions
- ✅ Interview confidently at any company
- ✅ Negotiate offers with leverage (multiple offers)

**You'll have:**
- ✅ 3 production-quality portfolio apps on GitHub
- ✅ 200 leetcode problems solved
- ✅ Deep Android knowledge (Kotlin, Compose, architecture, testing)
- ✅ Interview experience (10+ recruiter screens, 5+ phone screens)
- ✅ Confidence that you're an elite Android engineer

**You'll be:**
- ✅ **Hirable at FAANG** (Google, Meta, Amazon, Apple)
- ✅ **Hirable at elite startups** (xAI, Anthropic, Whatnot)
- ✅ **Hirable anywhere** that needs Android engineers

---

## Your Journey Starts Now

This plan is your roadmap. But **you** have to drive.

### Week 1 starts today:
- [ ] Read this entire plan
- [ ] Set up your environment (Android Studio, Kotlin, LeetCode account)
- [ ] Block time on calendar (2 hrs weekday, 6 hrs weekend)
- [ ] Start Week 1, Day 1: Kotlin syntax and scope functions
- [ ] Solve your first leetcode problem (even if it's "Two Sum")

### Remember:
- **Progress over perfection:** Better to do 80% consistently than 100% once
- **Rest is part of the plan:** Burnout helps no one
- **Ask for help:** Communities, mentors, peers (you're not alone)
- **Celebrate wins:** Completed project, solved hard problem, passed phone screen
- **Adjust as needed:** This plan is a guide, not a prison

---

## You've Got This 🚀

You're not starting from zero. You're starting with:
- Nike and eBay experience
- UT Austin CS degree
- Blockchain mastery
- This comprehensive plan

All you need to do is execute, one day at a time, one week at a time.

In 6 months, you'll look back at today and think:
> "I can't believe I was nervous. I'm now an elite Android engineer."

Let's make it happen.

**Start Date:** ___________
**Target End Date (6 months):** ___________
**First Interview Goal Date (Month 5):** ___________

---

---

# APPENDIX: Market Research & Interview Insights

## What the Market Actually Wants (Based on Real Interview Data)

The following insights come from analyzing recent Android interview trends, interviewer perspectives, and hiring manager feedback from Reddit discussions and job postings.

---

## Key Takeaways from Market Research

### 1. What Interviewers Actually Look For (Top 5)

**From practicing interviewers:**

> "I ask questions based on standard applications, not edge cases. It doesn't mean if someone is good or not if they know Bluetooth, especially when it's so easy to learn when needed."

> "Learning new libraries is easy, learning how to code properly is hard."

1. **Can you learn quickly?** (More important than knowing every library)
2. **Do you understand fundamentals?** (Not just memorization)
3. **Can you explain technical concepts?** (To juniors, to management, to stakeholders)
4. **Are you current?** (Kotlin, Coroutines, Compose basics - even if not production)
5. **Can you handle both legacy AND modern?** (Most companies have both)

---

### 2. Red Flags That Get You Rejected

- **Don't know basic concepts** (DI, architecture patterns)
- **Can't explain WHY you use tools/patterns** (just using because tutorial said so)
- **No Kotlin knowledge after 5+ years** (unacceptable in 2024-2025)
- **Can't discuss trade-offs** between approaches
- **Only theoretical knowledge, no practical understanding**

---

### 3. Green Flags That Get You Hired

- **Deep understanding, not surface memorization**
- **Can mentor/explain to others** (shows mastery)
- **Stay current with tech** (side projects, learning, not just job work)
- **Pragmatic approach** (not dogmatic about one way)
- **Communication skills** (can work with team, explain to non-technical)
- **Both breadth and depth** (know many things, expert in some)

---

## Interview Question Categories (From Real Interviews)

### Fresher Role Questions (But Asked Across Levels):
- What is application context? Difference between it and getContext
- Different types of coroutine scopes
- What is LaunchedEffect? (scenario-based)
- How to handle circular dependency in multi-module app
- MVVM architecture: MVVM vs MVC vs MVI
- How to manage versions in multi-module apps (version catalogs)

### Standard Interview Topics (Interviewer Perspective):
**Focus on standard applications, CV, and technologies used:**
- MVVM architecture (industry standard)
- Multi-module setup
- Coroutines
- Compose basics
- Dependency Injection (what it is and WHY)
- Dagger/Hilt vs Koin
- Approach to testing
- Clean Architecture
- Serializing library
- Delegates
- Gitflow

---

## Kotlin & Language Features:
- Difference between Java and Kotlin
- Features of Kotlin (what makes it better)
- Scope functions in Kotlin
- Coroutines in Kotlin
- Kotlin coroutines vs Java threads
- Difference between async and launch
- How to switch context in coroutines
- lateinit vs lazy
- Sealed class vs Enum
- Difference between normal class and data class

---

## Architecture & Patterns:
- What is MVVM architecture
- What is a ViewModel
- Where does business logic go? (ViewModel vs Repository)
- How to navigate from one screen to another
- State hoisting concept

---

## Flow & State Management:
- What is Flow
- Difference between StateFlow and SharedFlow
- How to collect latest value in StateFlow in Activity
- Flow operators
- Hot vs Cold flows

---

## Dependency Injection:
- What is Dagger Hilt
- What is @Provides, @Module, @Inject
- What is Singleton scope
- What's the advantage of using DI (WHY, not just what)

---

## Jetpack Compose:
- What are side effects in Jetpack Compose
- App crashes when you use LazyColumn in Column - how to solve?
- What is DisposableEffect
- How to set 3 elements in a row in a grid in Compose
- State hoisting
- Recomposition (what triggers it)
- @Stable annotation usage
- Theme management
- Best practices

---

## Concurrency (Critical):
**Always asked:**
- Threading, threadpools, thread safety
- Differences between threads and coroutines
- Dispatchers in coroutines (types and when to use)
- Structured concurrency
- Exception handling in coroutines

**FAANG level:** Implementation required (not just theory)

---

## Testing:
- Your approach to testing
- MockK, Turbine, other frameworks
- Unit vs Integration vs E2E
- Testing coroutines
- Compose UI testing

---

## Other Common Topics:
- How to send data from one app to another (Intents)
- What is a sealed class? Use cases in Android
- What alternatives to !! operator (null safety)
- Data classes - can you use var properties? Issues?
- lateinit vs lazy delegate - difference and Android usage
- Navigate from one screen to another - how?
- Launch coroutine on viewModelScope - UI or background thread?

---

## Interview Process Insights

### Theory vs Practical Balance:
- **Most companies:** Blend theory questions with practical coding
- **FAANG:** Primarily leetcode + system design (Android via system design)
- **Smaller companies:** More Android-specific theory
- **Trend:** Live coding becoming more common than pure Q&A

---

### Interview Formats:
- **Live coding challenges:** Debug existing buggy code (1-1.5 hours)
- **Refactoring exercises:** Clean up messy code
- **Home assignments:** Paginated list screen (4-5 hours), realistic feature
- **Pair programming:** Review code for issues (UI jank, memory leaks, ANR)
- **Small demo app from scratch**

---

### What's Working (Candidate Preference):
- **Code challenges at home:** Better than pair programming (less stress)
- **Small take-home projects:** Real-world scenarios
- **Quick-fire questions + optional coding:** Most choose coding

### What's Controversial:
- **Pair programming:** Many feel unfair due to stress/nerves
- **Pure leetcode:** Some FAANG still use, but moving away for Android-specific roles
- **Overly specific questions:** Bluetooth Low Energy, obscure APIs (unless job-relevant)

---

## Compose Adoption Reality Check

**Interviewer perspectives:**
> "Compose is still very niche, most production code is views and will be for another 5 years"

> "Not everyone on our team has Compose project experience"

> "Shouldn't be a hard requirement but good to know basics"

**Production usage:**
- Some companies: **100% Compose** (new apps, startups)
- Many companies: **Still on MVP/XML**, slowly migrating
- Migration path: **MVP → MVVM → Compose** (takes years for large apps)
- **ComposeView** for gradual migration is common

**Interview impact:**
- Questions asked to see if you know basics
- **Not a deal-breaker** if you haven't used it in production
- **France:** Becoming mandatory
- **US:** Nice to have, increasingly expected

---

## Regional Differences

### France:
- Kotlin is now the norm (mandatory)
- Compose questions are standard
- Not optional anymore

### India:
- Heavy theory focus
- Need to memorize internals (e.g., "How does ViewModel survive config changes?")
- More conceptual depth expected

### US:
- Mix of leetcode and practical
- **FAANG:** Heavy on algorithms + system design
- **Startups:** More practical coding challenges

---

## FAANG vs Startups vs Mid-Level Companies

### FAANG/Big Tech:
**Interview focus:**
- Leetcode (medium in ~20 minutes, some hard)
- System design with Android specifics
- CS fundamentals over Android trivia
- Soft skills (STAR method stories)
- "Build a class that does X" practical problems

**What they value:**
- Algorithm skills (non-negotiable)
- System thinking (scalability, trade-offs)
- Communication (can explain to team)
- Fundamentals over specific framework knowledge

---

### Elite Startups (xAI, Anthropic, Whatnot):
**Interview focus:**
- Live coding (debug buggy code, refactor, add features)
- Home assignment (realistic feature, 4-5 hours)
- Architecture discussions
- Modern stack knowledge (Kotlin, Coroutines, Compose)
- Building from scratch (0-to-1 experience)

**What they value:**
- Can you ship fast?
- Modern practices (not stuck in legacy)
- Product sense (not just code monkey)
- Independent problem-solving
- High agency (take initiative)

---

### Mid-Level Companies:
**Interview focus:**
- Mix of theory and practical
- CV-based questions (explain your projects)
- Standard app knowledge (MVVM, DI, Testing)
- Pragmatic over purely theoretical

**What they value:**
- Reliable shipping (can you deliver)
- Team player (collaboration)
- Maintenance mindset (not just greenfield)
- Adaptability (legacy AND modern)

---

## Key Quotes from Interviewers

> "I expect seniors to know most of those questions well, mids can have more troubles and it is fine."

> "10+ years experience, can still fail any interview. Interviews are random."

> "Asking about MVVM, multi-module setup, coroutines, compose, DI, testing, Clean Architecture, serializing library, delegates, gitflow - everything that the standard application has."

> "As Android development becomes easier, they're asking less Android tricky questions and more Software Engineering questions."

> "Companies still have legacy projects with Java, RxJava, MVP - but also new projects 100% Kotlin, Coroutines, Compose. Need folks who can learn both ways."

> "I have never seen a developer understanding DI, patterns, architecture, and at the same time not being able to program. Maybe I was lucky."

> "Understanding > Recitation. I don't ask 'define SOLID' - it proves memory, not understanding."

> "If someone can recite the Google modularization guide but can't solve a simple problem in the code, what's the point?"

---

## Software Engineering Focus (Trend)

**The shift:**
> "As Android development becomes easier, less Android tricky questions, more SE questions"

**What's being asked now:**
- **Architectures:** MVC vs MVVM vs MVP (when to use each and why)
- **Concurrency:** Threads, Coroutines, Flow (deep understanding)
- **Design Patterns:** Singleton, Builder, Factory, Observer, Strategy
- **Principles:** Dependency Injection, Reactive Programming, SOLID, Clean Code

**Why the shift:**
- Android tooling got better (Jetpack handles complexity)
- Companies want **software engineers who happen to do Android**, not just Android coders
- Focus on problem-solving ability over framework knowledge

---

## What Actually Matters vs What Doesn't

### What Actually Matters:
1. **Fundamentals** - Lifecycles, memory, threading, architecture
2. **Current Stack** - Kotlin, Coroutines, Flow, Compose basics
3. **Understanding over Memorization** - Why, not just what
4. **Communication** - Can explain technical concepts
5. **Pragmatism** - Shipping working code, not perfect code
6. **Learning Ability** - Tech changes, learning doesn't

### What Doesn't Matter As Much:
- Knowing every library (learn when needed)
- Perfect leetcode skills (except FAANG)
- Compose production experience (nice to have, not required)
- Overly specific edge cases (Bluetooth Low Energy, unless job-specific)
- Definition memorization (interviewers test understanding, not memory)

---

## The Ideal Candidate (What Companies Actually Want)

**Technical:**
- ✅ Solid Kotlin (not just syntax, but idiomatic usage)
- ✅ Understands Coroutines/Flow (not just using, but why and when)
- ✅ MVVM/Clean Architecture (can explain and implement)
- ✅ Compose basics (even if not production experience)
- ✅ Testing mindset (writes tests, knows how to test)
- ✅ Performance awareness (profiles, optimizes)

**Soft Skills:**
- ✅ Can learn quickly (more important than current knowledge)
- ✅ Explains well (to juniors, to non-technical)
- ✅ Pragmatic (not dogmatic)
- ✅ Team player (collaborates well)
- ✅ Product-minded (thinks about users, not just code)

**Experience:**
- ✅ Shipped production code (not just tutorials)
- ✅ Worked in teams (not just solo)
- ✅ Handled legacy code (real world)
- ✅ Mentored others (shows mastery)

---

## Job Market Realities (As of 2024-2025)

**Good news:**
- Android roles are increasing
- Companies moving to Compose (creating need for Kotlin/Compose experts)
- Remote positions expanding (more opportunities)

**Challenges:**
- FAANG hiring slowed (but still hiring top talent)
- More competition (need to stand out)
- Higher bar (companies want senior skills even for mid-level)

**Opportunities:**
- Startups hiring aggressively (xAI, Anthropic, many YC companies)
- Companies need Android expertise for AI/ML mobile apps
- Blockchain/Web3 companies need mobile devs (your background fits)

**Salary ranges (US):**
- FAANG: $200k-$400k+ (total comp, stock-heavy)
- Elite startups: $150k-$300k+ (equity can be huge)
- Mid-level companies: $120k-$200k
- Startups (early stage): $100k-$180k + significant equity

---

## Final Market Insight

The market wants **T-shaped developers:**
- **Wide knowledge** (Kotlin, Compose, testing, architecture, performance, etc.)
- **Deep expertise** in 1-2 areas (e.g., Compose performance optimization, or Clean Architecture at scale)

**You're building that with this plan:**
- **Wide:** 3 projects covering full modern Android stack
- **Deep:** 200 leetcode problems (algorithm depth), system design practice (architecture depth)

Companies don't want:
- Tutorial followers (no depth)
- Library collectors (no understanding)
- Framework zealots (not pragmatic)

They want:
- Problem solvers (you)
- Fast learners (you)
- Shippers (you, with Nike/eBay experience)

**This is why this plan works.** It's not just about passing interviews. It's about becoming the engineer companies desperately want to hire.

---

**Now go build. The market is waiting for you.** 🚀
