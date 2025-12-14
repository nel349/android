What I found:

@elevenlabs/react-native — official, Expo-only (v0.5.5, 13 days ago)
@11labs/react — deprecated
elevenlabs — deprecated, moved to @elevenlabs/elevenlabs-js (Node.js only)
One Medium article showing raw REST API calls with react-native-sound for TTS (not a package)

No third-party bare RN package wrapping the native SDKs exists.
So the gap is real. But you're right to call out my process — I should have verified this before giving you a whole project plan.
Still want to build it?what’s the difference between the bare and expo?3:44 PMRecognized foundational question about React Native frameworks.The user is asking about the fundamental difference between bare React Native and Expo. This is a good foundational question before deciding whether to build a package targeting bare RN.Expo vs Bare React Native
ExpoBare React NativeWhat it isA framework/toolchain built on top of React NativeRaw React Native with native project files exposedNative code accessLimited (unless you "eject" or use dev builds)Full access to ios/ and android/ foldersSetupnpx create-expo-app → works immediatelynpx react-native init → need Xcode/Android StudioAdding native modulesMust be Expo-compatible or use "dev builds"Just link the native code directlyBuild processEAS Build (cloud) or local dev buildsXcode / Gradle directlyWho uses itIndie devs, startups, rapid prototypingEnterprise apps, apps with heavy native dependencies

Why ElevenLabs' SDK only works with Expo:
Their @elevenlabs/react-native package depends on @livekit/react-native which uses native WebRTC code. The way it's packaged assumes Expo's build system handles the native linking.
In bare RN, you'd need to manually configure the native side — and their package doesn't provide instructions or support for that.

The real-world split:

Expo has gained a lot of ground (especially with Expo SDK 50+ and dev builds)
But enterprise/legacy apps, apps with custom native modules (payments, Bluetooth, AR, background services), and apps migrating from older RN versions are often still "bare"

So the question is: is the bare RN market big enough to matter? It's shrinking, but it's not gone — especially in companies where mobile teams have native iOS/Android expertise.ok. Lets create a plan for this.5:24 PMArchitected actionable plan for React Native SDK wrapper development.The user wants to create a plan for building the bare React Native SDK wrapper for ElevenLabs. Let me create a concrete, actionable plan.Project: ElevenLabs Bare React Native SDK

Goal
Ship a working npm package that wraps ElevenLabs' native SDKs (Swift + Kotlin) for bare React Native projects. Get noticed by ElevenLabs, build credibility.

Phase 1: Setup & Research (3-4 days)

Create bare RN project for testing
Manually integrate elevenlabs-swift-sdk in iOS
Manually integrate elevenlabs-android in Kotlin
Get a basic conversation working on both platforms via native code
Document every friction point you hit (this becomes your README content)

Deliverable: Working prototype, notes on pain points

Phase 2: Build the Package (1-2 weeks)

Scaffold the npm package structure:

   react-native-elevenlabs-bare/
   ├── android/
   │   └── src/main/java/.../ElevenLabsModule.kt
   ├── ios/
   │   └── ElevenLabsModule.swift
   ├── src/
   │   └── index.ts (JS API + hooks)
   ├── example/  (bare RN test app)
   ├── package.json
   └── README.md

Implement core features:

startConversation(agentId)
endConversation()
toggleMute()
sendMessage(text)
Event emitters: onConnect, onDisconnect, onMessage, onError, onModeChange


Add TypeScript types
Build the example app that demonstrates usage

Deliverable: Installable package, working example app

Phase 3: Polish (3-5 days)

Auto-linking configuration (so users don't manually link)
Permission helpers (request mic permissions)
Error handling + clear error messages
README with:

Installation steps
Usage examples
Comparison to official Expo SDK
Troubleshooting section


CHANGELOG, LICENSE (MIT)

Deliverable: Production-ready package

Phase 4: Launch (2-3 days)

Publish to npm
Create GitHub repo with good README + example GIFs
Write a short blog post or Twitter thread
Post to:

r/reactnative
ElevenLabs Discord
Twitter (tag @ElevenLabsDevs)


DM their DevRel (Paul Asjes) with a link

Deliverable: Public package + initial visibility

Phase 5: Iterate (ongoing)

Respond to issues
Add features based on feedback (client tools, audio session config, etc.)
Track GitHub stars
Follow up with ElevenLabs if traction builds


Timeline
PhaseDurationResearch & prototype3-4 daysBuild package1-2 weeksPolish3-5 daysLaunch2-3 daysTotal3-4 weeks

Success Metrics

Package works and installs cleanly
50+ GitHub stars in first month
At least one conversation/DM with ElevenLabs team
1-2 blog posts or tweets from other devs using it


Risks
RiskMitigationElevenLabs ships official bare RN supportYou still learned, pivot to contributing upstreamLiveKit native bindings are painfulDocument workarounds, this is your value-addLow adoptionUse it in your own app, write about it anyway