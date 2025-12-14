# AI Chat Android App

**Month 3-4 Project** | 50-60 hours | Weeks 9-16

Build a production-quality AI chat app with multi-LLM support, voice integration, and advanced Android features. Your FAANG-level masterpiece project.

---

## Project Overview

**What:** Multi-LLM chat app for Android supporting OpenAI GPT-4, Anthropic Claude, and Google Gemini. Features streaming responses, voice input/output, chat history, and offline mode.

**Why this project:**
- **On-trend:** AI is the hottest market in 2025, FAANG is hiring for AI + mobile
- **Showcases elite skills:** Advanced Compose, state management, real-time streaming, testing, CI/CD
- **Ties your portfolio together:** Uses ElevenLabs SDK from Month 1 for voice
- **Interview gold:** Can discuss LLM APIs, streaming architecture, performance optimization
- **Differentiator:** Not just a ChatGPT clone - multi-LLM abstraction shows architecture skills

**Tech Stack:**
- Kotlin (100%)
- Multi-module Clean Architecture
- Jetpack Compose (advanced: animations, custom components, performance)
- MVI architecture (unidirectional data flow)
- Coroutines & Flow (advanced patterns: streaming, cancellation)
- Hilt (dependency injection)
- Room (encrypted chat history)
- Retrofit + OkHttp (LLM API clients, Server-Sent Events for streaming)
- ElevenLabs SDK (voice TTS from your Month 1 project)
- Speech-to-Text (Google Speech API)
- WorkManager (background message sync)
- Comprehensive testing (Unit 80%+, Integration, Compose UI, Screenshot tests)
- GitHub Actions CI/CD (build, test, lint, performance benchmarks)
- Performance optimization (profiling, benchmarking, lazy loading)

---

## Features (MVP)

### 1. Multi-LLM Support
- Select LLM provider: OpenAI (GPT-4), Anthropic (Claude), Google (Gemini)
- Switch between models mid-conversation
- Store API keys securely (EncryptedSharedPreferences)
- Fallback to different LLM if one fails

### 2. Streaming Chat
- Real-time typing effect (stream tokens as they arrive)
- Cancel ongoing generation
- Retry failed messages
- Copy message text

### 3. Voice Integration
- **Voice Input:** Speech-to-Text (Google Speech API)
- **Voice Output:** ElevenLabs TTS (using your Month 1 SDK!)
- Push-to-talk or toggle mode
- Voice playback controls (pause, stop, speed)

### 4. Chat History
- Save conversations to Room (encrypted)
- Search chat history (full-text search)
- Delete conversations
- Export chat (share as text file)

### 5. Advanced Features
- **Code Highlighting:** Syntax highlighting for code blocks (markdown rendering)
- **Image Generation:** DALL-E integration (stretch goal)
- **Dark Mode:** System + manual toggle
- **Offline Mode:** View cached conversations
- **Accessibility:** TalkBack, content descriptions, large text support

---

## Multi-Module Architecture

```
ai-chat-android/
├── app/                           # Main app module (navigation, DI setup)
├── feature/
│   ├── chat/                      # Main chat UI (conversation screen)
│   ├── conversations/             # Conversation list screen
│   ├── settings/                  # LLM selection, API keys, voice settings
│   └── onboarding/                # Welcome, API key setup
├── core/
│   ├── llm/                       # LLM abstraction (OpenAI, Anthropic, Gemini)
│   ├── voice/                     # STT + TTS integration (ElevenLabs)
│   ├── network/                   # HTTP clients, streaming (SSE)
│   ├── database/                  # Room (chats, messages, encrypted)
│   ├── datastore/                 # DataStore (settings, API keys)
│   ├── ui/                        # Design system, shared Compose components
│   ├── domain/                    # Use cases, repositories (interfaces)
│   ├── common/                    # Extensions, utils, constants
│   └── testing/                   # Test utilities, fakes, fixtures
├── build-logic/                   # Convention plugins
└── gradle/libs.versions.toml      # Version catalog
```

### Module Dependencies

```
:app
  ├─ :feature:chat
  ├─ :feature:conversations
  ├─ :feature:settings
  └─ :feature:onboarding

:feature:* (any feature module)
  ├─ :core:domain
  ├─ :core:ui
  └─ :core:common

:core:llm
  ├─ :core:network
  ├─ :core:domain
  └─ :core:common

:core:voice
  └─ :core:common

:core:database
  ├─ :core:domain
  └─ :core:common
```

---

## Clean Architecture Layers

### Domain Layer (:core:domain)

**Entities:**
```kotlin
data class Conversation(
    val id: String,
    val title: String,
    val llmProvider: LLMProvider,
    val createdAt: Long,
    val updatedAt: Long
)

data class Message(
    val id: String,
    val conversationId: String,
    val content: String,
    val role: MessageRole,  // USER or ASSISTANT
    val timestamp: Long,
    val isStreaming: Boolean = false
)

enum class LLMProvider { OPENAI, ANTHROPIC, GEMINI }
enum class MessageRole { USER, ASSISTANT }
```

**Repositories:**
```kotlin
interface ChatRepository {
    suspend fun sendMessage(
        conversationId: String,
        message: String,
        provider: LLMProvider
    ): Flow<StreamingResponse>  // Emits tokens as they stream

    suspend fun getConversation(id: String): Conversation
    suspend fun getMessages(conversationId: String): Flow<List<Message>>
    suspend fun deleteConversation(id: String)
}

interface VoiceRepository {
    suspend fun speechToText(audioData: ByteArray): Result<String>
    suspend fun textToSpeech(text: String): Result<ByteArray>
}
```

**Use Cases:**
```kotlin
class SendMessageUseCase(private val repo: ChatRepository) {
    operator fun invoke(
        conversationId: String,
        message: String,
        provider: LLMProvider
    ): Flow<StreamingResponse> {
        return repo.sendMessage(conversationId, message, provider)
    }
}

sealed class StreamingResponse {
    data class Token(val text: String) : StreamingResponse()
    data class Complete(val message: Message) : StreamingResponse()
    data class Error(val error: String) : StreamingResponse()
}
```

---

### Data Layer (:core:llm)

**LLM Abstraction:**
```kotlin
interface LLMClient {
    suspend fun streamChat(
        messages: List<Message>,
        systemPrompt: String? = null
    ): Flow<String>  // Emits tokens
}

class OpenAIClient(private val api: OpenAIApi) : LLMClient {
    override suspend fun streamChat(messages: List<Message>, systemPrompt: String?): Flow<String> = flow {
        val request = ChatCompletionRequest(
            model = "gpt-4",
            messages = messages.toOpenAIFormat(),
            stream = true
        )

        api.streamChatCompletion(request).collect { chunk ->
            chunk.choices.firstOrNull()?.delta?.content?.let { emit(it) }
        }
    }
}

class AnthropicClient(private val api: AnthropicApi) : LLMClient {
    override suspend fun streamChat(messages: List<Message>, systemPrompt: String?): Flow<String> = flow {
        val request = MessagesRequest(
            model = "claude-3-5-sonnet-20241022",
            messages = messages.toAnthropicFormat(),
            stream = true
        )

        api.streamMessages(request).collect { event ->
            when (event) {
                is ContentBlockDelta -> emit(event.delta.text)
            }
        }
    }
}

class GeminiClient(private val api: GeminiApi) : LLMClient {
    override suspend fun streamChat(messages: List<Message>, systemPrompt: String?): Flow<String> = flow {
        val request = GenerateContentRequest(
            contents = messages.toGeminiFormat()
        )

        api.streamGenerateContent(request).collect { response ->
            response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text?.let { emit(it) }
        }
    }
}
```

**LLMClientFactory:**
```kotlin
class LLMClientFactory(
    private val openAIClient: OpenAIClient,
    private val anthropicClient: AnthropicClient,
    private val geminiClient: GeminiClient
) {
    fun getClient(provider: LLMProvider): LLMClient {
        return when (provider) {
            LLMProvider.OPENAI -> openAIClient
            LLMProvider.ANTHROPIC -> anthropicClient
            LLMProvider.GEMINI -> geminiClient
        }
    }
}
```

---

### Streaming Implementation (:core:network)

**Server-Sent Events (SSE) for OpenAI/Anthropic:**
```kotlin
class SSEParser {
    fun parse(response: ResponseBody): Flow<String> = flow {
        response.source().use { source ->
            while (!source.exhausted()) {
                val line = source.readUtf8Line() ?: break

                if (line.startsWith("data: ")) {
                    val data = line.substring(6)
                    if (data == "[DONE]") break

                    val json = Json.decodeFromString<SSEData>(data)
                    emit(json.content)
                }
            }
        }
    }
}
```

---

### Presentation Layer (:feature:chat)

**ViewModel (MVI):**
```kotlin
data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val currentInput: String = "",
    val isStreaming: Boolean = false,
    val selectedProvider: LLMProvider = LLMProvider.OPENAI,
    val isVoiceMode: Boolean = false,
    val error: String? = null
)

sealed interface ChatIntent {
    data class SendMessage(val text: String) : ChatIntent
    data class UpdateInput(val text: String) : ChatIntent
    object StartVoiceInput : ChatIntent
    object StopVoiceInput : ChatIntent
    data class PlayVoice(val text: String) : ChatIntent
    object CancelStreaming : ChatIntent
    data class SwitchProvider(val provider: LLMProvider) : ChatIntent
}

class ChatViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val voiceRepository: VoiceRepository,
    private val conversationId: String
) : ViewModel() {
    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state.asStateFlow()

    private var streamingJob: Job? = null

    fun onIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.SendMessage -> sendMessage(intent.text)
            is ChatIntent.UpdateInput -> updateInput(intent.text)
            is ChatIntent.CancelStreaming -> cancelStreaming()
            is ChatIntent.SwitchProvider -> switchProvider(intent.provider)
            is ChatIntent.StartVoiceInput -> startVoiceInput()
            is ChatIntent.PlayVoice -> playVoice(intent.text)
        }
    }

    private fun sendMessage(text: String) {
        if (text.isBlank()) return

        // Add user message immediately
        val userMessage = Message(
            id = UUID.randomUUID().toString(),
            conversationId = conversationId,
            content = text,
            role = MessageRole.USER,
            timestamp = System.currentTimeMillis()
        )
        _state.update { it.copy(
            messages = it.messages + userMessage,
            currentInput = "",
            isStreaming = true
        )}

        // Stream assistant response
        streamingJob = viewModelScope.launch {
            val assistantMessage = Message(
                id = UUID.randomUUID().toString(),
                conversationId = conversationId,
                content = "",
                role = MessageRole.ASSISTANT,
                timestamp = System.currentTimeMillis(),
                isStreaming = true
            )

            sendMessageUseCase(conversationId, text, _state.value.selectedProvider)
                .collect { response ->
                    when (response) {
                        is StreamingResponse.Token -> {
                            // Append token to assistant message
                            _state.update {
                                val updatedMessages = it.messages.toMutableList()
                                val lastIndex = updatedMessages.indexOfLast { msg -> msg.id == assistantMessage.id }
                                if (lastIndex != -1) {
                                    updatedMessages[lastIndex] = updatedMessages[lastIndex].copy(
                                        content = updatedMessages[lastIndex].content + response.text
                                    )
                                } else {
                                    updatedMessages.add(assistantMessage.copy(content = response.text))
                                }
                                it.copy(messages = updatedMessages.toList())
                            }
                        }
                        is StreamingResponse.Complete -> {
                            _state.update { it.copy(isStreaming = false) }
                        }
                        is StreamingResponse.Error -> {
                            _state.update { it.copy(isStreaming = false, error = response.error) }
                        }
                    }
                }
        }
    }

    private fun cancelStreaming() {
        streamingJob?.cancel()
        _state.update { it.copy(isStreaming = false) }
    }
}
```

**Compose UI:**
```kotlin
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ChatTopBar(
                provider = state.selectedProvider,
                onProviderChange = { viewModel.onIntent(ChatIntent.SwitchProvider(it)) }
            )
        },
        bottomBar = {
            ChatInputBar(
                input = state.currentInput,
                onInputChange = { viewModel.onIntent(ChatIntent.UpdateInput(it)) },
                onSend = { viewModel.onIntent(ChatIntent.SendMessage(state.currentInput)) },
                onVoiceClick = { viewModel.onIntent(ChatIntent.StartVoiceInput) },
                isStreaming = state.isStreaming
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            reverseLayout = true  // New messages at bottom
        ) {
            items(state.messages.reversed(), key = { it.id }) { message ->
                MessageBubble(
                    message = message,
                    onPlayVoice = { viewModel.onIntent(ChatIntent.PlayVoice(message.content)) }
                )
            }
        }

        if (state.isStreaming) {
            StreamingIndicator()
        }
    }
}

@Composable
fun MessageBubble(
    message: Message,
    onPlayVoice: () -> Unit
) {
    val isUser = message.role == MessageRole.USER

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                MarkdownText(text = message.content)  // Render markdown with code highlighting

                if (!isUser) {
                    Row {
                        IconButton(onClick = onPlayVoice) {
                            Icon(Icons.Default.VolumeUp, "Play voice")
                        }
                        IconButton(onClick = { /* copy to clipboard */ }) {
                            Icon(Icons.Default.ContentCopy, "Copy")
                        }
                    }
                }
            }
        }
    }
}
```

---

## Implementation Timeline

### Week 9-10: Architecture + Core LLM (16 hours)

**Tasks:**
- Set up multi-module project structure
- Define domain entities, repository interfaces
- Implement OpenAI client (streaming with SSE)
- Implement Anthropic client (streaming)
- Implement Gemini client (streaming)
- LLMClientFactory abstraction
- Unit tests for LLM clients (mock API responses)

**Deliverable:** Multi-LLM abstraction working, can stream from all 3 providers

---

### Week 11-12: Chat UI + Voice Integration (16 hours)

**Tasks:**
- Build Compose UI (LazyColumn, message bubbles, input bar)
- Implement ChatViewModel (MVI pattern, streaming state)
- Markdown rendering (code syntax highlighting)
- Integrate Speech-to-Text (Google Speech API)
- Integrate ElevenLabs TTS (using your Month 1 SDK!)
- Voice playback controls
- Room database (save conversations, messages)

**Deliverable:** Full chat flow working with voice input/output

---

### Week 13: Testing + CI/CD (12 hours)

**Tasks:**
- Unit tests (ViewModels, UseCases, LLM clients) - 80%+ coverage
- Integration tests (Repository + Room + API)
- Compose UI tests (send message, streaming, voice)
- Screenshot tests (Paparazzi or Roborazzi)
- GitHub Actions CI/CD (build, test, lint, detekt)
- Performance profiling (Android Profiler, identify bottlenecks)

**Deliverable:** Comprehensive test suite, CI/CD pipeline running

---

### Week 14-15: Polish + Performance (12 hours)

**Tasks:**
- Security hardening (API key storage, ProGuard)
- Performance optimization:
  - LazyColumn performance (key, contentType)
  - Streaming debounce (avoid too many recompositions)
  - Image caching (if DALL-E integration)
- Accessibility (TalkBack, content descriptions)
- Dark mode (dynamic theming)
- Architecture documentation (diagrams, README)

**Deliverable:** Production-ready app, optimized, accessible

---

### Week 16: System Design + Launch Prep (4 hours)

**Tasks:**
- Write system design doc (architecture decisions, trade-offs)
- Architecture diagrams (draw.io or Mermaid)
- README (features, setup, screenshots)
- GitHub published with tags/releases
- Demo video (optional, for portfolio)

**Deliverable:** Published on GitHub, ready to showcase in interviews

---

## Testing Strategy

### Unit Tests (80%+ coverage)

**:core:llm:**
- Test each LLM client (mock API responses)
- Test streaming token parsing (SSE, JSON)
- Test error handling (network errors, API errors)

**:core:domain:**
- Test UseCases (mock repositories)
- Test business logic (e.g., message validation)

**ViewModels:**
- Test state transitions (send message → streaming → complete)
- Test cancellation (cancel ongoing stream)
- Test error states

### Integration Tests

- Repository + Room + LLM API (Hilt test module)
- End-to-end flow: Send message → stream response → save to DB

### Compose UI Tests

- Test send message flow
- Test streaming indicator appears/disappears
- Test voice button triggers STT
- Test provider switching

### Screenshot Tests

- Paparazzi or Roborazzi
- Capture message bubbles, streaming state, error state
- Regression testing (UI doesn't break)

---

## Performance Optimization

### 1. LazyColumn Optimization
```kotlin
LazyColumn {
    items(
        items = messages,
        key = { it.id },  // Stable key for recomposition optimization
        contentType = { it.role }  // Recycle similar items
    ) { message ->
        MessageBubble(message)
    }
}
```

### 2. Streaming Debounce
```kotlin
// Avoid recomposing on every token (60+ FPS)
// Batch tokens every 16ms (one frame)
flow.debounce(16)
```

### 3. derivedStateOf for Expensive Computations
```kotlin
val formattedMessages by remember {
    derivedStateOf {
        messages.map { formatMarkdown(it.content) }
    }
}
```

### 4. Benchmark Tests
```kotlin
@RunWith(AndroidJUnit4::class)
class ChatBenchmark {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun scrollMessages() {
        benchmarkRule.measureRepeated {
            // Scroll LazyColumn with 100 messages
            // Ensure < 16ms per frame (60 FPS)
        }
    }
}
```

---

## Security Considerations

### API Key Storage
- Store in EncryptedSharedPreferences (never plaintext)
- Never hardcode in BuildConfig (user provides their own)
- Validate API keys before making requests

### ProGuard/R8
- Obfuscate code in release builds
- Remove logs
- Minify APK

### Network Security
- Use HTTPS only (SSL pinning if needed)
- Certificate validation

---

## Trade-offs & Design Decisions

### Multi-LLM Abstraction vs Single Provider
**Chosen:** Multi-LLM abstraction
**Why:** Shows architecture skills, future-proof (can add more LLMs)
**Trade-off:** More complexity, each provider has different API

### MVI vs MVVM
**Chosen:** MVI
**Why:** Unidirectional data flow perfect for streaming state
**Trade-off:** More boilerplate, but easier to reason about state

### Room vs Firebase
**Chosen:** Room (encrypted)
**Why:** Offline-first, no vendor lock-in, user privacy
**Trade-off:** No automatic cloud sync

### SSE vs WebSocket
**Chosen:** SSE (Server-Sent Events) for OpenAI/Anthropic
**Why:** Simpler for one-way streaming, built into OkHttp
**Trade-off:** Not bidirectional (but don't need it)

---

## Resources

**LLM APIs:**
- [OpenAI API Docs](https://platform.openai.com/docs/api-reference/chat)
- [Anthropic API Docs](https://docs.anthropic.com/claude/reference/getting-started-with-the-api)
- [Google Gemini API Docs](https://ai.google.dev/docs)

**Streaming:**
- [OkHttp SSE](https://square.github.io/okhttp/4.x/okhttp/okhttp3/-response-body/)
- [Kotlin Flow](https://kotlinlang.org/docs/flow.html)

**Markdown Rendering:**
- [Markwon](https://github.com/noties/Markwon) - Markdown renderer for Android
- [Compose Markdown](https://github.com/jeziellago/compose-markdown) - Markdown in Compose

**Voice:**
- [Google Speech-to-Text](https://cloud.google.com/speech-to-text/docs)
- ElevenLabs SDK (your Month 1 project!)

**Testing:**
- [Paparazzi](https://github.com/cashapp/paparazzi) - Screenshot testing
- [Turbine](https://github.com/cashapp/turbine) - Flow testing

---

## Interview Story Template

**"Tell me about your most complex project"**

> "I built an AI chat app for Android that supports OpenAI, Anthropic, and Google Gemini with real-time streaming responses and voice integration.
>
> The main architectural challenge was designing a clean abstraction layer for three different LLM APIs. Each provider has different request/response formats and streaming mechanisms. I created an LLMClient interface with streaming via Kotlin Flow, then implemented OpenAIClient, AnthropicClient, and GeminiClient using Server-Sent Events for real-time token streaming.
>
> For state management, I chose MVI over traditional MVVM because streaming requires predictable, unidirectional data flow. Each streamed token emits a new state, and I needed to ensure no race conditions when users cancel generation or switch providers mid-stream.
>
> The performance challenge was rendering streaming text without janky UI. Streaming 60+ tokens per second caused excessive recompositions. I optimized by debouncing token emissions to 16ms (one frame), using stable keys in LazyColumn, and derivedStateOf for expensive markdown parsing. This brought frame time from 40ms to 12ms (60 FPS).
>
> For voice, I integrated Google Speech-to-Text for input and my own ElevenLabs SDK (built in Month 1) for text-to-speech output. This created a hands-free mode where users can have voice conversations with AI.
>
> The app has 85% test coverage with unit tests for LLM clients, integration tests for streaming flows, Compose UI tests for user interactions, and screenshot tests for regression prevention. CI/CD runs on GitHub Actions with automated builds, tests, and performance benchmarks.
>
> This project demonstrates my ability to architect complex systems, optimize performance, and integrate cutting-edge AI into mobile apps."

**Follow-up questions:**
- "How did you handle API failures?" → Retry with exponential backoff, fallback to different LLM provider
- "How did you test streaming?" → Mock Flow emissions, Turbine for Flow testing, verify state transitions
- "Performance bottlenecks?" → LazyColumn recomposition, fixed with stable keys and debouncing
- "Why three LLMs?" → Shows abstraction skills, each LLM has strengths (GPT-4 for general, Claude for coding, Gemini for multimodal)

---

**This is your masterpiece. It ties together everything you've learned: Android expertise, architecture, AI integration, performance optimization, and production-quality testing. Interviewers will be impressed.** 🚀🤖
