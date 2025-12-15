# ElevenLabs Android SDK

**Month 1 Project** | 15-20 hours | Weeks 1-4

Build a native Android SDK wrapper for ElevenLabs voice AI. Demonstrate SDK integration, Kotlin/Coroutines mastery, and real-world problem solving.

---

## Project Overview

**What:** Android SDK that wraps ElevenLabs' native Java/Kotlin SDK (or REST API if no native SDK exists) for voice AI conversational agents.

**Why this project:**
- **Portfolio differentiator:** No official bare Android SDK exists (only Expo for React Native)
- **Real-world usefulness:** Developers need this, potential for actual users/stars
- **Skills showcase:** Native SDK integration, event-driven architecture, Kotlin Coroutines
- **Interview story:** "I built an SDK wrapper to fill a gap in the Android ecosystem"
- **Sets up Month 3-4 project:** You'll use this SDK in your AI Chat app (voice integration)

**Tech Stack:**
- Kotlin (primary language)
- Coroutines & Flow (async operations, event streams)
- Retrofit + OkHttp (if wrapping REST API)
- WebSocket / LiveKit (for real-time voice streaming)
- MVVM architecture (ViewModel for example app)
- JUnit + MockK (unit tests)

---

## Features (MVP)

### Core SDK (15 hours)

1. **startConversation(agentId: String)**
   - Initialize voice conversation with ElevenLabs agent
   - Return Flow<ConversationEvent> for real-time updates
   - Handle WebSocket connection (or native SDK initialization)

2. **endConversation()**
   - Close connection gracefully
   - Clean up resources (mic, WebSocket)

3. **toggleMute()**
   - Mute/unmute user's microphone
   - Update conversation state

4. **sendTextMessage(text: String)**
   - Send text input to agent (if supported)
   - Useful for text-only mode or mixed input

5. **Event Handling (Flow-based)**
   ```kotlin
   sealed class ConversationEvent {
       object Connected : ConversationEvent()
       object Disconnected : ConversationEvent()
       data class AgentMessage(val text: String, val audioUrl: String?) : ConversationEvent()
       data class UserMessage(val text: String) : ConversationEvent()
       data class Error(val message: String, val code: Int?) : ConversationEvent()
       data class ModeChange(val mode: ConversationMode) : ConversationEvent()
   }
   ```

6. **Permission Handling**
   - Request microphone permission
   - Handle permission denial gracefully

### Example App (3-5 hours)

- Simple Jetpack Compose UI
- "Start Conversation" button
- Display agent messages (text bubbles)
- Mute/unmute toggle
- Error handling UI

---

## Architecture

### SDK Module Structure

```
elevenlabs-android-sdk/
├── sdk/
│   ├── src/main/java/com/elevenlabs/sdk/
│   │   ├── ElevenLabsClient.kt          // Main SDK entry point
│   │   ├── ConversationManager.kt       // Manages conversation lifecycle
│   │   ├── models/
│   │   │   ├── ConversationEvent.kt     // Sealed class for events
│   │   │   ├── ConversationConfig.kt    // Agent ID, API key, etc.
│   │   │   └── ConversationMode.kt      // Enum (Listening, Speaking, Idle)
│   │   ├── network/
│   │   │   ├── ElevenLabsApi.kt         // Retrofit interface (if REST)
│   │   │   └── WebSocketManager.kt      // WebSocket connection
│   │   └── audio/
│   │       ├── AudioRecorder.kt         // Mic input
│   │       └── AudioPlayer.kt           // Play agent audio
│   └── build.gradle.kts                 // SDK dependencies
├── app/
│   └── src/main/java/com/example/demo/
│       ├── MainActivity.kt              // Compose UI
│       └── ConversationViewModel.kt     // State management
└── README.md                            // Installation, usage, examples
```

### Data Flow

```
User (UI)
    ↓ clicks "Start Conversation"
ConversationViewModel
    ↓ calls
ElevenLabsClient.startConversation(agentId)
    ↓ initializes
ConversationManager
    ├─ WebSocketManager (connect to ElevenLabs)
    ├─ AudioRecorder (start mic input)
    └─ AudioPlayer (play agent responses)
    ↓ emits
Flow<ConversationEvent>
    ↓ collected by
ConversationViewModel
    ↓ updates
UiState (displayed in Compose)
```

---

## Implementation Timeline

### Week 1: Research & Setup (3-4 hours)

**Tasks:**
- Study ElevenLabs API docs (REST or WebSocket endpoints)
- Check if native Android SDK exists (GitHub, npm)
- Set up Android project (multi-module: `:sdk`, `:app`)
- Test ElevenLabs API manually (Postman/curl)
- Document API flow (authentication, WebSocket handshake, audio format)

**Deliverable:** Project structure, API research notes

---

### Week 2-3: Core SDK Implementation (8-10 hours)

**Tasks:**
- **Day 1-2:** ElevenLabsClient (main API, init, config)
- **Day 3-4:** WebSocketManager (connect, send/receive, reconnect logic)
- **Day 5-6:** AudioRecorder (mic input, PCM → base64 encoding if needed)
- **Day 7:** AudioPlayer (decode agent audio, play via MediaPlayer)
- **Day 8:** ConversationManager (orchestrate all components, emit events)
- **Day 9:** Permission handling (mic permissions, fallback states)
- **Day 10:** Error handling (network errors, API errors, timeout)

**Deliverable:** Working SDK (can start conversation, send audio, receive responses)

---

### Week 4: Example App + Polish (4-6 hours)

**Tasks:**
- **Day 1-2:** Compose UI (start/end button, message list, mute toggle)
- **Day 3:** ViewModel (collect Flow, update UiState)
- **Day 4:** Testing (unit tests for ConversationManager, mocked API)
- **Day 5:** README (installation, quick start, API reference)
- **Day 6:** Publish to GitHub (clean commit history, screenshots)

**Deliverable:** Example app, README, GitHub published

---

## Code Examples

### SDK Usage (from example app)

```kotlin
// ViewModel
class ConversationViewModel : ViewModel() {
    private val client = ElevenLabsClient(apiKey = BuildConfig.ELEVENLABS_API_KEY)

    private val _uiState = MutableStateFlow(ConversationUiState())
    val uiState: StateFlow<ConversationUiState> = _uiState.asStateFlow()

    fun startConversation(agentId: String) {
        viewModelScope.launch {
            client.startConversation(agentId)
                .collect { event ->
                    when (event) {
                        is ConversationEvent.Connected -> {
                            _uiState.update { it.copy(isConnected = true) }
                        }
                        is ConversationEvent.AgentMessage -> {
                            _uiState.update {
                                it.copy(messages = it.messages + Message.Agent(event.text))
                            }
                        }
                        is ConversationEvent.Error -> {
                            _uiState.update { it.copy(error = event.message) }
                        }
                        // ... handle other events
                    }
                }
        }
    }

    fun endConversation() {
        viewModelScope.launch {
            client.endConversation()
        }
    }

    fun toggleMute() {
        client.toggleMute()
        _uiState.update { it.copy(isMuted = !it.isMuted) }
    }
}
```

### SDK Implementation (ConversationManager)

```kotlin
class ConversationManager(
    private val webSocketManager: WebSocketManager,
    private val audioRecorder: AudioRecorder,
    private val audioPlayer: AudioPlayer
) {
    private val _events = MutableSharedFlow<ConversationEvent>()
    val events: Flow<ConversationEvent> = _events.asSharedFlow()

    suspend fun start(agentId: String) {
        try {
            // Connect WebSocket
            webSocketManager.connect(agentId)
            _events.emit(ConversationEvent.Connected)

            // Start mic recording
            audioRecorder.start { audioData ->
                // Send audio to server
                webSocketManager.sendAudio(audioData)
            }

            // Listen for server messages
            webSocketManager.messages.collect { message ->
                when (message.type) {
                    "audio" -> {
                        audioPlayer.play(message.audioData)
                        _events.emit(ConversationEvent.AgentMessage(message.text, null))
                    }
                    "error" -> {
                        _events.emit(ConversationEvent.Error(message.error, null))
                    }
                }
            }
        } catch (e: Exception) {
            _events.emit(ConversationEvent.Error(e.message ?: "Unknown error", null))
        }
    }

    suspend fun stop() {
        audioRecorder.stop()
        webSocketManager.disconnect()
        _events.emit(ConversationEvent.Disconnected)
    }
}
```

---

## Learning Integration

This project aligns perfectly with your Month 1 learning goals:

**Week 1 (Kotlin Refresh):**
- Use scope functions (`let`, `apply`, `run`) for config objects
- Null safety (`?.`, `?:`) for API responses
- `lateinit` for SDK initialization
- Coroutines basics (`launch`, `async`) for async operations
- Flow basics for event streams

**Week 2 (MVVM + Components):**
- ViewModel for example app (survive config changes)
- LiveData vs Flow (you'll use Flow for real-time events)
- StateFlow for UI state management

**Week 3 (DI + Testing):**
- Hilt for dependency injection (inject ElevenLabsClient into ViewModel)
- JUnit + MockK for testing ConversationManager
- Mock WebSocketManager, test event emissions

**Week 4 (Compose):**
- Build example app UI in Compose
- State hoisting (ViewModel → UI)
- LaunchedEffect for collecting Flow

---

## Success Metrics

**Technical:**
- [ ] SDK compiles and runs on Android 8.0+ (API 26+)
- [ ] Can start/end conversation successfully
- [ ] Audio input/output works (mic → server → speaker)
- [ ] Events emitted correctly (Connected, AgentMessage, Error, etc.)
- [ ] Example app demonstrates all features
- [ ] Unit tests for ConversationManager (80%+ coverage)

**Portfolio:**
- [ ] Published on GitHub with clean README
- [ ] Screenshots/GIF in README showing usage
- [ ] API documentation (KDoc comments)
- [ ] Example code snippets in README
- [ ] At least one real user or GitHub star (stretch goal)

**Interview Value:**
- [ ] Can explain architecture in 5 minutes
- [ ] Can discuss trade-offs (WebSocket vs polling, Flow vs LiveData)
- [ ] Can explain how you debugged WebSocket connection issues
- [ ] Ties to AI Chat app (Month 3-4): "I'll use this SDK for voice integration"

---

## Trade-offs & Design Decisions

### WebSocket vs REST Polling
**Chosen:** WebSocket (if supported by ElevenLabs)
**Why:** Real-time voice needs low latency, polling adds 500ms+ delay
**Trade-off:** More complex (reconnect logic, connection management)

### Flow vs LiveData
**Chosen:** Flow
**Why:** More flexible, composable, Kotlin-first, better for streams
**Trade-off:** Slightly more boilerplate (collect in coroutine)

### Native SDK vs REST Wrapper
**Chosen:** Wrap REST API directly (if no native SDK exists)
**Why:** Full control, easier to debug, no dependency on third-party SDK
**Trade-off:** More work (handle WebSocket, audio encoding yourself)

### Foreground Service vs Regular Service
**Chosen:** Not using a Service for MVP (just in-app)
**Why:** Keep it simple, most use cases are in-app voice chat
**Future:** Add Foreground Service for background voice (Month 3-4 AI Chat app)

---

## Common Challenges & Solutions

### Challenge 1: WebSocket Connection Drops
**Solution:** Exponential backoff reconnect logic
```kotlin
suspend fun reconnect(attempt: Int = 0) {
    val delay = min(1000L * (2.0.pow(attempt)).toLong(), 30000L)
    delay(delay)
    try {
        connect()
    } catch (e: Exception) {
        if (attempt < 5) reconnect(attempt + 1)
        else throw MaxRetriesException()
    }
}
```

### Challenge 2: Audio Format Mismatch
**Problem:** Server expects PCM 16kHz mono, mic gives PCM 44.1kHz stereo
**Solution:** Resample audio using Android AudioRecord settings
```kotlin
val audioRecord = AudioRecord(
    MediaRecorder.AudioSource.MIC,
    16000, // 16kHz sample rate
    AudioFormat.CHANNEL_IN_MONO,
    AudioFormat.ENCODING_PCM_16BIT,
    bufferSize
)
```

### Challenge 3: Permission Handling
**Problem:** Mic permission denied, SDK crashes
**Solution:** Request permission before starting, emit error event if denied
```kotlin
if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
    != PackageManager.PERMISSION_GRANTED) {
    _events.emit(ConversationEvent.Error("Microphone permission denied", 403))
    return
}
```

---

## Extensions (Post-MVP, if time permits)

1. **Custom Wake Word:** "Hey Agent" to start conversation
2. **Conversation History:** Save messages to Room database
3. **Multiple Agents:** Switch between different ElevenLabs agents
4. **Text-Only Mode:** Send text instead of audio (fallback for no mic)
5. **Speaker Identification:** Detect which user is speaking (multi-user)

---

## Resources

**ElevenLabs Docs:**
- [ElevenLabs API Reference](https://elevenlabs.io/docs/api-reference)
- [Conversational AI Docs](https://elevenlabs.io/docs/conversational-ai/overview)

**Android Resources:**
- [WebSocket (OkHttp)](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-web-socket/)
- [AudioRecord](https://developer.android.com/reference/android/media/AudioRecord)
- [MediaPlayer](https://developer.android.com/guide/topics/media/mediaplayer)
- [Kotlin Flow](https://kotlinlang.org/docs/flow.html)

**Similar Projects (for inspiration):**
- [Agora Android SDK](https://github.com/AgoraIO/Agora-RTC-SDK-Android) - Real-time voice/video
- [Stream Chat Android](https://github.com/GetStream/stream-chat-android) - Chat SDK architecture

---

## Interview Story Template

**"Tell me about a challenging project you built"**

> "I built an Android SDK wrapper for ElevenLabs voice AI because no official Android SDK existed. The main challenge was managing the WebSocket connection lifecycle - handling reconnects, audio streaming, and state synchronization between the server and client.
>
> I designed the SDK with a Flow-based event system so developers could reactively handle conversation events like agent messages, errors, and connection state changes. This made the API clean and composable.
>
> The trickiest part was audio format compatibility - the server expected 16kHz mono PCM, but Android's default mic input is 44.1kHz stereo. I used AudioRecord to configure the exact format needed, which eliminated resampling overhead.
>
> The SDK is now published on GitHub with an example app, and I'll be using it in my AI Chat app for voice integration. This project taught me a lot about real-time systems, audio processing, and designing developer-friendly APIs."

**Follow-up questions:**
- "How did you handle network failures?" → Exponential backoff reconnect
- "How did you test this?" → JUnit + MockK, mocked WebSocketManager
- "What would you do differently?" → Add Foreground Service for background voice

---

**This project sets you apart. Most candidates build CRUD apps. You built an SDK that solves a real problem.** 🚀
