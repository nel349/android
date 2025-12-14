# ElevenLabs React Native SDK (Bare)

**Month 1 Project** | 15-20 hours | Weeks 1-4

Build a React Native package with native modules (iOS + Android) that wraps ElevenLabs voice AI SDKs for bare React Native projects.

---

## Project Overview

**What:** npm package that wraps ElevenLabs' native SDKs (Swift for iOS, Kotlin for Android) for bare React Native projects. Bridges native voice AI functionality to JavaScript.

**Why this project:**
- **Portfolio differentiator:** No official bare RN SDK exists (only Expo-compatible)
- **Real-world gap:** Enterprise/legacy RN apps need bare RN support
- **Skills showcase:** Native module development, Swift + Kotlin bridges, cross-platform API design
- **Interview story:** "I built a cross-platform SDK bridging native iOS/Android code to React Native"
- **Unique:** Shows you can work across mobile platforms, not just Android

**Tech Stack:**
- **React Native** (JavaScript/TypeScript API)
- **iOS Native Module:** Swift (wrapping elevenlabs-swift-sdk)
- **Android Native Module:** Kotlin (wrapping elevenlabs-android SDK or REST API)
- **Bridge:** React Native TurboModules or legacy Native Modules
- **Event Emitters:** For real-time conversation events
- **Auto-linking:** RN 0.60+ auto-linking support

---

## Features (MVP)

### JavaScript API

```typescript
import ElevenLabs from 'react-native-elevenlabs-bare';

// Start conversation
await ElevenLabs.startConversation(agentId: string);

// End conversation
await ElevenLabs.endConversation();

// Toggle mute
await ElevenLabs.toggleMute();

// Send text message
await ElevenLabs.sendMessage(text: string);

// Event listeners
ElevenLabs.addListener('onConnect', () => console.log('Connected'));
ElevenLabs.addListener('onDisconnect', () => console.log('Disconnected'));
ElevenLabs.addListener('onMessage', (data) => console.log(data.text));
ElevenLabs.addListener('onError', (error) => console.error(error));
```

### Core Features

1. **startConversation(agentId: string)** - Initialize voice conversation
2. **endConversation()** - Close connection gracefully
3. **toggleMute()** - Mute/unmute microphone
4. **sendMessage(text: string)** - Send text to agent
5. **Event Emitters:**
   - `onConnect` - Connection established
   - `onDisconnect` - Connection closed
   - `onMessage` - Agent message received
   - `onError` - Error occurred
   - `onModeChange` - Conversation mode changed

---

## Package Structure

```
react-native-elevenlabs-bare/
├── android/
│   ├── src/main/java/com/elevenlabs/
│   │   ├── ElevenLabsModule.kt         // RN bridge to Kotlin
│   │   ├── ConversationManager.kt      // Manages conversation lifecycle
│   │   ├── WebSocketManager.kt         // WebSocket connection
│   │   └── AudioHandler.kt             // Mic input/audio playback
│   └── build.gradle
├── ios/
│   ├── ElevenLabsModule.swift          // RN bridge to Swift
│   ├── ElevenLabsModule.m              // Objective-C bridge
│   ├── ConversationManager.swift       // Conversation lifecycle
│   └── ElevenLabsBare.podspec
├── src/
│   ├── index.ts                        // Main JS API
│   ├── types.ts                        // TypeScript definitions
│   └── NativeElevenLabs.ts             // Native module interface
├── example/
│   ├── ios/                            // Example iOS app
│   ├── android/                        // Example Android app
│   └── App.tsx                         // Example React Native app
├── package.json
├── react-native.config.js              // Auto-linking config
└── README.md
```

---

## Implementation Timeline

### Week 1: Research & Setup (3-4 hours)

**Tasks:**
- Study ElevenLabs API (REST or native SDKs for iOS/Android)
- Set up bare React Native project for testing
- Research React Native native modules (TurboModules vs legacy)
- Manually integrate elevenlabs-swift-sdk in iOS (if exists)
- Manually integrate elevenlabs-android in Kotlin (if exists)
- Get basic conversation working on both platforms

**Deliverable:** Working prototype (native code only), friction points documented

---

### Week 2-3: Build the Package (8-10 hours)

**iOS Native Module (Swift):**
```swift
@objc(ElevenLabsModule)
class ElevenLabsModule: RCTEventEmitter {
    private var conversationManager: ConversationManager?

    @objc
    func startConversation(_ agentId: String,
                          resolver: @escaping RCTPromiseResolveBlock,
                          rejecter: @escaping RCTPromiseRejectBlock) {
        conversationManager = ConversationManager()
        conversationManager?.start(agentId: agentId) { result in
            switch result {
            case .success:
                self.sendEvent(withName: "onConnect", body: nil)
                resolver(true)
            case .failure(let error):
                rejecter("ERROR", error.localizedDescription, error)
            }
        }
    }

    @objc
    override func supportedEvents() -> [String]! {
        return ["onConnect", "onDisconnect", "onMessage", "onError", "onModeChange"]
    }
}
```

**Android Native Module (Kotlin):**
```kotlin
class ElevenLabsModule(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {

    private var conversationManager: ConversationManager? = null

    override fun getName() = "ElevenLabsModule"

    @ReactMethod
    fun startConversation(agentId: String, promise: Promise) {
        try {
            conversationManager = ConversationManager(reactApplicationContext)
            conversationManager?.start(agentId) { event ->
                when (event) {
                    is ConversationEvent.Connected -> {
                        sendEvent("onConnect", null)
                        promise.resolve(true)
                    }
                    is ConversationEvent.Error -> {
                        promise.reject("ERROR", event.message)
                    }
                }
            }
        } catch (e: Exception) {
            promise.reject("ERROR", e.message)
        }
    }

    private fun sendEvent(eventName: String, params: WritableMap?) {
        reactApplicationContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }
}
```

**JavaScript API:**
```typescript
// src/index.ts
import { NativeModules, NativeEventEmitter } from 'react-native';

const { ElevenLabsModule } = NativeModules;
const eventEmitter = new NativeEventEmitter(ElevenLabsModule);

export default {
  async startConversation(agentId: string): Promise<void> {
    await ElevenLabsModule.startConversation(agentId);
  },

  async endConversation(): Promise<void> {
    await ElevenLabsModule.endConversation();
  },

  async toggleMute(): Promise<void> {
    await ElevenLabsModule.toggleMute();
  },

  async sendMessage(text: string): Promise<void> {
    await ElevenLabsModule.sendMessage(text);
  },

  addListener(event: string, callback: (data: any) => void) {
    return eventEmitter.addListener(event, callback);
  },

  removeAllListeners(event: string) {
    eventEmitter.removeAllListeners(event);
  },
};
```

**Deliverable:** Installable npm package, working on both iOS and Android

---

### Week 4: Polish + Publish (4-6 hours)

**Tasks:**
- Auto-linking configuration (react-native.config.js)
- Permission helpers (mic permissions for iOS/Android)
- Error handling + clear error messages
- TypeScript definitions
- Example app (demonstrates all features)
- README:
  - Installation steps
  - Usage examples
  - Comparison to official Expo SDK
  - Troubleshooting (common linking issues)
- Publish to npm
- GitHub repo with screenshots/GIFs

**Deliverable:** Published npm package, GitHub repo

---

## Learning Integration

This project teaches you cross-platform mobile development:

**Week 1 (Kotlin Refresh):**
- Kotlin for Android native module
- Coroutines for async operations
- Flow for event streams

**Week 2 (React Native Bridges):**
- React Native native modules architecture
- Promises vs callbacks in native code
- Event emitters for real-time events

**Week 3 (iOS Development):**
- Swift basics (closures, optionals, async/await)
- iOS frameworks (AVFoundation for audio)
- CocoaPods integration

**Week 4 (Cross-Platform APIs):**
- Designing consistent APIs across platforms
- Handling platform differences gracefully
- Testing on both iOS and Android

---

## Success Metrics

**Technical:**
- [ ] Package installs cleanly on bare RN projects (iOS + Android)
- [ ] Can start/end conversation on both platforms
- [ ] Audio input/output works (mic → server → speaker)
- [ ] Events emitted correctly (onConnect, onMessage, onError)
- [ ] Example app demonstrates all features
- [ ] Auto-linking works (no manual native setup)

**Portfolio:**
- [ ] Published on npm
- [ ] GitHub repo with clean README
- [ ] Screenshots/GIF showing iOS + Android working
- [ ] TypeScript types included
- [ ] At least one real user or GitHub star (stretch goal)

**Interview Value:**
- [ ] Can explain React Native native modules architecture
- [ ] Can discuss iOS vs Android differences in implementation
- [ ] Can explain bridge communication (JS ↔ Native)
- [ ] Shows cross-platform expertise (not just Android)

---

## Trade-offs & Design Decisions

### TurboModules vs Legacy Native Modules
**Chosen:** Legacy Native Modules (for now)
**Why:** Broader compatibility (RN 0.60+), simpler setup
**Trade-off:** TurboModules are faster, but require RN 0.68+ and more setup

### Promises vs Callbacks
**Chosen:** Promises for async operations, Event Emitters for streams
**Why:** Modern JavaScript pattern, easier to use
**Trade-off:** Must handle errors properly (reject promises)

### Swift vs Objective-C for iOS
**Chosen:** Swift
**Why:** Modern, safer, better async support
**Trade-off:** Need Objective-C bridge file (ElevenLabsModule.m)

### Wrapping Native SDKs vs REST API
**Chosen:** Wrap native SDKs if they exist, otherwise REST API
**Why:** Native SDKs handle WebSocket/audio complexity
**Trade-off:** Dependency on ElevenLabs' native SDKs

---

## Common Challenges & Solutions

### Challenge 1: iOS Audio Permissions
**Problem:** AVAudioSession permissions required for mic
**Solution:**
```swift
// Request mic permission
AVAudioSession.sharedInstance().requestRecordPermission { granted in
    if granted {
        // Start conversation
    } else {
        self.sendEvent(withName: "onError", body: ["message": "Mic permission denied"])
    }
}
```

### Challenge 2: Android Native Module Registration
**Problem:** Module not found by React Native
**Solution:**
```kotlin
// Create ReactPackage
class ElevenLabsPackage : ReactPackage {
    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        return listOf(ElevenLabsModule(reactContext))
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }
}
```

### Challenge 3: Event Emitter Memory Leaks
**Problem:** Listeners not removed, memory leak
**Solution:**
```typescript
// In React component
useEffect(() => {
  const subscription = ElevenLabs.addListener('onMessage', handleMessage);

  return () => {
    subscription.remove();  // Clean up on unmount
  };
}, []);
```

---

## Resources

**React Native Native Modules:**
- [React Native Native Modules Docs](https://reactnative.dev/docs/native-modules-intro)
- [iOS Native Modules Guide](https://reactnative.dev/docs/native-modules-ios)
- [Android Native Modules Guide](https://reactnative.dev/docs/native-modules-android)

**ElevenLabs:**
- [ElevenLabs API Reference](https://elevenlabs.io/docs/api-reference)
- [Conversational AI Docs](https://elevenlabs.io/docs/conversational-ai/overview)

**Swift Resources:**
- [Swift Language Guide](https://docs.swift.org/swift-book/LanguageGuide/TheBasics.html)
- [AVFoundation](https://developer.apple.com/av-foundation/) (Audio on iOS)

**Similar Projects:**
- [react-native-voice](https://github.com/react-native-voice/voice) - Speech recognition
- [react-native-webrtc](https://github.com/react-native-webrtc/react-native-webrtc) - WebRTC wrapper
- [@react-native-community/audio-toolkit](https://github.com/react-native-audio-toolkit/react-native-audio-toolkit) - Audio recording

---

## Interview Story Template

**"Tell me about a challenging cross-platform project"**

> "I built a React Native package that wraps ElevenLabs voice AI for bare React Native projects. The challenge was creating a unified JavaScript API that bridged to native iOS (Swift) and Android (Kotlin) code.
>
> On iOS, I used Swift to wrap the elevenlabs-swift-sdk, handling AVAudioSession for microphone permissions and WebSocket connections. On Android, I used Kotlin with similar functionality but had to handle different audio APIs and permission models.
>
> The trickiest part was designing event emitters that worked consistently across platforms. iOS and Android handle threading differently - iOS requires dispatching events to the main thread, while Android's React Native bridge handles this automatically. I used RCTEventEmitter on iOS and DeviceEventManagerModule on Android to maintain a consistent JavaScript API.
>
> I published the package to npm with auto-linking support, so developers can install it with one command and it works on both platforms without manual native configuration. This taught me a lot about cross-platform mobile development and designing developer-friendly APIs."

**Follow-up questions:**
- "How did you handle platform differences?" → Abstract common interface, platform-specific implementations
- "How did you test this?" → Example app testing on both iOS simulators and Android emulators
- "What would you do differently?" → Use TurboModules for better performance (RN 0.68+)

---

**This project shows you can work across the full mobile stack: JavaScript, Swift, Kotlin, and React Native architecture. Perfect for demonstrating cross-platform expertise.** 🚀
