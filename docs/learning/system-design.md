# Android System Design

Master the art of designing scalable, offline-first, production-ready Android apps for FAANG interviews.

---

## System Design Interview Format

**Typical flow (45-60 min):**
1. **Clarify requirements** (5-10 min) - Functional + non-functional
2. **Estimate scale** (5 min) - Users, requests, storage
3. **High-level design** (15-20 min) - Architecture diagram, data flow
4. **Deep dive** (15-20 min) - Pick 1-2 components to detail
5. **Trade-offs** (5-10 min) - Discuss alternatives, bottlenecks

**What interviewers look for:**
- Structured thinking (not jumping to solutions)
- Asking clarifying questions
- Understanding trade-offs (no "perfect" solution)
- Knowledge of Android patterns (offline-first, caching, sync)
- Scalability awareness
- Real-world experience (data usage, battery, crashes)

---

## Core Android System Design Patterns

### 1. Offline-First Architecture

**Problem:** Users have poor/no internet, app should still work

**Solution:**
- **Single source of truth:** Local database (Room)
- **Data flow:** Network → Repository → Local DB → UI
- **Sync strategy:** Background sync (WorkManager), conflict resolution

**Example:**
```
UI Layer (Compose)
    ↓ observes StateFlow
ViewModel
    ↓ calls
Repository (abstraction)
    ↓ coordinates
Local DB (Room) ← sync ← Network API (Retrofit)
```

**Trade-offs:**
- Pro: Fast UI (instant from cache), works offline
- Con: Stale data, sync complexity, storage limits

**FAANG apps using this:** Gmail, Google Maps, Slack

---

### 2. Pagination & Infinite Scroll

**Problem:** Loading millions of items crashes the app / uses too much memory

**Solution:**
- **Paging 3 library:** Load data in chunks (pages)
- **LazyColumn:** Only compose visible items
- **Remote mediator:** Combines network + local DB

**Example:**
```
LazyColumn (UI)
    ↓ requests pages
PagingData (ViewModel)
    ↓ from
Paging Source (Repository)
    ↓ fetches from
Network API (page 1, 2, 3...) + Room (cached pages)
```

**Trade-offs:**
- Pro: Low memory, fast initial load
- Con: Can't search all items, need backend pagination support

**FAANG apps using this:** Instagram feed, Reddit, Twitter

---

### 3. Real-Time Updates

**Problem:** Users need live updates (chat, stock prices, notifications)

**Solution approaches:**
1. **WebSockets:** Persistent connection, bidirectional
2. **Server-Sent Events (SSE):** One-way stream from server
3. **Polling:** Periodic API calls (fallback, inefficient)
4. **Firebase Realtime Database / Firestore:** Managed real-time sync

**Example (WebSocket):**
```
UI (Compose)
    ↓ observes Flow
ViewModel
    ↓ subscribes to
WebSocket Manager (Service)
    ↓ maintains connection to
WebSocket Server
```

**Trade-offs:**
- WebSocket: Low latency, battery drain, connection management
- Polling: Simple, high latency, server load
- Firebase: Easy, vendor lock-in, cost

**FAANG apps using this:** WhatsApp, Slack, stock trading apps

---

### 4. Image Loading & Caching

**Problem:** Loading images from network is slow, uses data, should cache

**Solution:**
- **Coil / Glide:** Image loading libraries with multi-level cache
- **Memory cache:** LRU cache for fast access
- **Disk cache:** Persistent across app restarts
- **Lazy loading:** Only load visible images

**Example:**
```
Coil.load(url) {
    placeholder(R.drawable.placeholder)
    error(R.drawable.error)
    transformations(CircleCropTransformation())
    memoryCachePolicy(ENABLED)
    diskCachePolicy(ENABLED)
}
```

**Trade-offs:**
- Pro: Fast, low data usage, good UX
- Con: Storage limits, cache invalidation complexity

**FAANG apps using this:** Instagram, Pinterest, Amazon

---

### 5. Background Work & Sync

**Problem:** Upload photos, sync data, periodic tasks (even when app closed)

**Solution:**
- **WorkManager:** Guaranteed execution, constraints (WiFi, charging)
- **Foreground Service:** User-visible long-running tasks (music player)
- **AlarmManager:** Exact-time tasks (less common now)

**Example (WorkManager):**
```
val constraints = Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .setRequiresCharging(true)
    .build()

val syncWork = OneTimeWorkRequestBuilder<SyncWorker>()
    .setConstraints(constraints)
    .build()

WorkManager.getInstance(context).enqueue(syncWork)
```

**Trade-offs:**
- Pro: Battery-efficient, respects device state, guaranteed
- Con: Not immediate, can be delayed by system

**FAANG apps using this:** Google Photos (backup), Spotify (download), Gmail (sync)

---

### 6. Multi-Module Architecture

**Problem:** Large codebase is slow to build, hard to navigate, tight coupling

**Solution:**
- **Feature modules:** `:feature-chat`, `:feature-profile`
- **Core modules:** `:core-network`, `:core-database`, `:core-ui`
- **Dependency rule:** Features can depend on core, not other features
- **Version catalogs:** Centralized dependency management

**Example structure:**
```
:app (navigation, DI setup)
:feature-chat (UI, ViewModel)
:feature-profile (UI, ViewModel)
:core-network (Retrofit, API clients)
:core-database (Room, DAOs)
:core-ui (design system, shared components)
:core-domain (use cases, models)
```

**Trade-offs:**
- Pro: Faster builds (parallel), clear boundaries, reusable code
- Con: Initial setup complexity, navigation between modules

**FAANG apps using this:** Nowinandroid (Google), Uber, Airbnb

---

### 7. State Management (MVI)

**Problem:** Unpredictable state changes, hard to debug, race conditions

**Solution (MVI - Model-View-Intent):**
- **Single immutable state:** `data class UiState`
- **Unidirectional data flow:** User action → Intent → Reducer → New State → UI
- **StateFlow:** Emit state updates, UI observes

**Example:**
```kotlin
// State
data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// Intent
sealed interface ChatIntent {
    data class SendMessage(val text: String) : ChatIntent
    object LoadMessages : ChatIntent
}

// ViewModel
class ChatViewModel : ViewModel() {
    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state.asStateFlow()

    fun onIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.SendMessage -> sendMessage(intent.text)
            is ChatIntent.LoadMessages -> loadMessages()
        }
    }
}
```

**Trade-offs:**
- Pro: Predictable, easy to test, time-travel debugging
- Con: More boilerplate, learning curve

**FAANG apps using this:** Airbnb (MvRx), Spotify, Uber

---

### 8. Security & Authentication

**Problem:** Store auth tokens, sensitive data, prevent MITM attacks

**Solution:**
- **EncryptedSharedPreferences:** Store tokens securely
- **Keystore:** Hardware-backed key storage (biometric)
- **SSL Pinning:** Prevent man-in-the-middle attacks
- **ProGuard/R8:** Obfuscate code, remove logs

**Example (EncryptedSharedPreferences):**
```kotlin
val masterKey = MasterKey.Builder(context)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()

val sharedPreferences = EncryptedSharedPreferences.create(
    context,
    "secure_prefs",
    masterKey,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
)
```

**Trade-offs:**
- Pro: Secure, hardware-backed, GDPR-compliant
- Con: Can't access if device compromised, complexity

**FAANG apps using this:** Banking apps, crypto wallets, health apps

---

## 10 System Designs to Practice

Practice each design in 45 minutes. Draw diagrams, discuss trade-offs, deep dive into 1-2 components.

---

### 1. Instagram Feed

**Requirements:**
- Users can post photos, like, comment
- Infinite scroll feed
- Offline viewing (last 50 posts cached)
- Real-time likes update

**Key challenges:**
- Pagination with remote mediator
- Image caching (Coil multi-level cache)
- Optimistic UI updates (like/unlike instantly, sync later)
- Background sync (WorkManager)

**Architecture:**
```
UI (LazyColumn)
    ↓
ViewModel (Paging 3 + StateFlow)
    ↓
Repository
    ├─ Remote: Retrofit (GET /feed?page=1)
    └─ Local: Room (PostEntity, cache 50 posts)
    ↓
Coil (image loading, memory + disk cache)
```

**Deep dive:** How do you handle offline likes? (Store in local queue, sync on network, conflict resolution)

---

### 2. WhatsApp Messaging

**Requirements:**
- Send/receive text messages
- Real-time delivery (WebSocket)
- Offline message queue
- End-to-end encryption
- Message history (infinite scroll)

**Key challenges:**
- WebSocket connection management (reconnect logic)
- Message encryption (Signal protocol)
- Offline queue (WorkManager retry)
- Database design (Room with chat threads)

**Architecture:**
```
UI (LazyColumn, chat bubbles)
    ↓
ViewModel
    ├─ WebSocket Manager (real-time)
    └─ Repository
        ├─ Room (MessageEntity, encrypted)
        └─ WorkManager (send queue)
```

**Deep dive:** How do you handle message delivery status? (Sent, Delivered, Read receipts via WebSocket acks)

---

### 3. Uber Driver App

**Requirements:**
- Real-time driver location updates
- Accept/reject ride requests
- Navigation integration (Google Maps SDK)
- Background location tracking
- Works in low connectivity

**Key challenges:**
- Location tracking (Foreground Service)
- Battery optimization (geofencing, adaptive location frequency)
- Offline mode (cache last request, retry)
- Real-time updates (WebSocket)

**Architecture:**
```
UI (MapView, ride request overlay)
    ↓
ViewModel
    ├─ Location Service (FusedLocationProvider)
    ├─ WebSocket Manager (ride requests)
    └─ Maps SDK (navigation)
```

**Deep dive:** How do you optimize battery for continuous location tracking? (Geofencing, adaptive frequency, batch uploads)

---

### 4. Google Maps Offline

**Requirements:**
- Download map tiles for offline use
- Search cached locations
- Turn-by-turn navigation (offline)
- Auto-update maps (WiFi only)

**Key challenges:**
- Map tile storage (SQLite or file system)
- Download manager (WorkManager with progress)
- Search indexing (FTS5 full-text search in Room)
- Routing algorithm (Dijkstra/A* on cached graph)

**Architecture:**
```
UI (MapView)
    ↓
ViewModel
    └─ Repository
        ├─ Room (TileEntity, POI with FTS)
        └─ WorkManager (download tiles)
```

**Deep dive:** How do you estimate storage for offline maps? (Tile size × zoom levels × area, compression)

---

### 5. Netflix Video Player

**Requirements:**
- Adaptive bitrate streaming (HLS/DASH)
- Offline downloads
- Resume playback across devices
- Picture-in-picture mode

**Key challenges:**
- ExoPlayer integration (adaptive streaming)
- Download management (DRM, storage limits)
- Playback position sync (API + local DB)
- Background playback (Foreground Service)

**Architecture:**
```
UI (PlayerView)
    ↓
ViewModel
    ├─ ExoPlayer (HLS manifest, adaptive bitrate)
    ├─ Download Manager (DRM-protected files)
    └─ Playback Sync (WorkManager)
```

**Deep dive:** How do you handle DRM for offline downloads? (Widevine L1/L3, license caching)

---

### 6. Twitter Timeline

**Requirements:**
- Infinite scroll feed
- Pull-to-refresh
- Real-time new tweets indicator
- Media preview (images, videos)
- Offline reading

**Key challenges:**
- Pagination (Paging 3)
- Real-time updates (polling or WebSocket)
- Media loading (Coil + video thumbnails)
- Draft tweets (local storage)

**Architecture:**
```
UI (LazyColumn)
    ↓
ViewModel (Paging 3)
    ↓
Repository
    ├─ Remote: Retrofit (GET /timeline)
    ├─ Local: Room (TweetEntity, RemoteMediator)
    └─ WebSocket (new tweets notification)
```

**Deep dive:** How do you handle "load more above" when new tweets arrive? (Invalidate paging source, prepend to cache)

---

### 7. Medium Reading App

**Requirements:**
- Article reading (rich text, images)
- Bookmark for offline
- Reading progress sync
- Dark mode
- Text-to-speech

**Key challenges:**
- Rich text rendering (WebView or Compose Markdown)
- Offline articles (download HTML + assets)
- Reading position tracking (character offset)
- TTS integration (Android TextToSpeech API)

**Architecture:**
```
UI (WebView or Markdown Compose)
    ↓
ViewModel
    └─ Repository
        ├─ Room (ArticleEntity, offline HTML)
        └─ Retrofit (fetch article)
    ↓
TTS Manager (background audio)
```

**Deep dive:** How do you sync reading progress across devices? (Store character offset, timestamp, conflict resolution)

---

### 8. Spotify Music Player

**Requirements:**
- Stream music (HLS audio)
- Offline downloads
- Background playback
- Queue management
- Crossfade between tracks

**Key challenges:**
- Media playback (MediaSession, ExoPlayer)
- Background service (Foreground Service with notification)
- Download management (encryption, storage)
- Queue persistence (Room)

**Architecture:**
```
UI (PlayerScreen, notification)
    ↓
ViewModel
    └─ MusicService (Foreground Service)
        ├─ ExoPlayer (audio streaming)
        ├─ MediaSession (Android Auto, headphones)
        └─ Download Manager
```

**Deep dive:** How do you handle crossfade? (ExoPlayer track transitions, audio mixing)

---

### 9. E-Commerce Product Listing

**Requirements:**
- Product search with filters
- Infinite scroll
- Image carousel
- Add to cart (offline)
- Price updates (real-time)

**Key challenges:**
- Search + filter (backend API, local cache)
- Pagination (Paging 3)
- Cart sync (local + remote)
- Price updates (WebSocket or polling)

**Architecture:**
```
UI (LazyGrid, filters)
    ↓
ViewModel (Paging 3 + filter state)
    ↓
Repository
    ├─ Retrofit (search API)
    └─ Room (ProductEntity, CartEntity)
```

**Deep dive:** How do you handle offline cart? (Local cart, merge on sync, conflict resolution)

---

### 10. Fitness Tracker (Strava-like)

**Requirements:**
- Record workout (GPS, heart rate)
- Display route on map
- Background tracking (even if app killed)
- Upload to cloud (WiFi only)
- Weekly stats

**Key challenges:**
- Location tracking (Foreground Service)
- Sensor data (Heart rate via BLE)
- Data visualization (MPAndroidChart)
- Background sync (WorkManager)

**Architecture:**
```
UI (MapView, charts)
    ↓
ViewModel
    └─ Repository
        ├─ Location Service (FusedLocationProvider)
        ├─ BLE Manager (heart rate monitor)
        ├─ Room (WorkoutEntity)
        └─ WorkManager (upload)
```

**Deep dive:** How do you minimize battery drain during tracking? (Adaptive location frequency, batch GPS, wake locks)

---

## System Design Practice Approach

### Step 1: Clarify Requirements (5-10 min)

**Ask these questions:**
1. **Functional:** What are the core features? (e.g., post, like, comment)
2. **Non-functional:** Offline support? Real-time? Scale (MAU)?
3. **Platform:** Android only or cross-platform sync?
4. **Constraints:** Storage limits? Battery? Data usage?

**Example:**
"For Instagram Feed, are we supporting stories, reels, or just static posts? Do we need offline posting? What's the expected user base - millions?"

---

### Step 2: Estimate Scale (5 min)

**Calculate:**
- **Users:** DAU (daily active users), MAU (monthly active)
- **Requests:** QPS (queries per second) = DAU × avg actions / 86400
- **Storage:** Posts, images, cache (user device)

**Example (Instagram Feed):**
- 10M DAU, each user views 50 posts/day
- 50 posts × 500KB avg image = 25MB cache
- Local DB: 50 posts × 2KB metadata = 100KB

---

### Step 3: High-Level Design (15-20 min)

**Draw:**
- UI Layer (Activities, Composables)
- ViewModel Layer
- Repository Layer (abstraction)
- Data Sources (Network, Local DB)
- Background services (WorkManager, Foreground Service)

**Discuss:**
- Data flow (Network → Repository → DB → UI)
- Offline-first strategy
- Caching strategy

---

### Step 4: Deep Dive (15-20 min)

**Pick 1-2 components to detail:**
- Database schema (Room entities, relationships)
- API design (endpoints, pagination)
- Conflict resolution (offline edits + sync)
- Performance optimization (pagination, image caching)

**Example:**
"Let's deep dive into how we handle offline likes. We'll store pending likes in a local queue table, upload via WorkManager when online, and handle conflicts if the post was deleted."

---

### Step 5: Discuss Trade-offs (5-10 min)

**Compare alternatives:**
- WebSocket vs Polling (for real-time)
- Room vs SQLite (for local DB)
- Paging 3 vs manual pagination
- Coil vs Glide (for images)

**Discuss:**
- Why you chose X over Y
- When you'd choose Y instead
- Bottlenecks and how to scale

---

## Common Trade-offs to Discuss

### Offline-First vs Server-First
- **Offline-first:** Fast UI, complex sync, stale data
- **Server-first:** Always fresh, requires network, slow on bad connection
- **When:** Offline-first for messaging/reading, server-first for real-time stock prices

### WebSocket vs Polling
- **WebSocket:** Low latency, battery drain, complex reconnect logic
- **Polling:** Simple, high latency, server load
- **When:** WebSocket for chat, polling for non-critical updates

### Room vs Realm vs SQLite
- **Room:** Official, Kotlin-friendly, compile-time verification
- **Realm:** Fast, reactive, mobile-optimized (but now deprecated)
- **SQLite:** Full control, more boilerplate
- **When:** Room for most cases, SQLite for very custom queries

### Coil vs Glide vs Picasso
- **Coil:** Kotlin-first, coroutines, Compose support
- **Glide:** Mature, more features, larger library
- **Picasso:** Simple, lightweight (but less active)
- **When:** Coil for modern apps, Glide if need advanced features

---

## Resources

**Books:**
- "Designing Data-Intensive Applications" by Martin Kleppmann (backend focus, still valuable)
- "System Design Interview" by Alex Xu (general, adapt to Android)

**Practice:**
- Draw diagrams on paper or [Excalidraw](https://excalidraw.com/)
- Explain out loud to yourself or a friend
- Review Nowinandroid architecture diagram

**Videos:**
- [Android Developers Channel](https://www.youtube.com/c/AndroidDevelopers) - Modern Android Development series
- [Philipp Lackner](https://www.youtube.com/c/PhilippLackner) - Clean Architecture, MVI

---

## Your Advantage: Real Projects

**When discussing system design, reference your portfolio:**
- "In my Midnight Wallet app, I used multi-module architecture with `:core-crypto` and `:feature-wallet` to separate concerns..."
- "For the AI Chat app, I implemented MVI with a single immutable state to handle streaming LLM responses predictably..."
- "In the ElevenLabs SDK, I managed WebSocket reconnection logic with exponential backoff to handle unstable connections..."

This shows you don't just know theory - you've actually built these systems.

---

**Practice 2-3 of these designs per week starting Month 3. By Month 4, you should be able to design any of these in 45 minutes.**
