# 6-Month Android Engineer Roadmap

**Start:** [Fill in your start date]
**Target:** FAANG Android Engineer
**Time:** 2-3 hrs/day (420-630 hours total)
**Portfolio:** ElevenLabs SDK, Midnight Wallet, AI Chat App
**Leetcode:** 200 problems (40 easy / 120 medium / 40 hard)

---

## 📊 Current Progress

**Week 1: COMPLETED ✅**
- ✅ Kotlin: Scope functions, null safety, lateinit/lazy, coroutines, Flow
- ✅ Android: Activity lifecycle, ViewBinding, ConstraintLayout, Retrofit
- ✅ Project: Weather app (OpenWeatherMap API, StateFlow, Coroutines)
- ✅ LeetCode: 7/7 Arrays & Strings complete!

**Week 2: 60% COMPLETE (PAUSED)** - MVVM + Components
- ✅ ViewModel (survives rotation, viewModelScope)
- ✅ Room Database (offline-first, Entity, DAO, @Volatile, synchronized)
- ✅ Offline caching working perfectly
- ⏸️ **PAUSED** - Fragments + Navigation remaining
- 🎯 LeetCode: 0/7 Week 2 target (do when ready)

**When you return: Continue Week 2** - Add Fragments + Navigation for multi-screen app

---

## Deep Dive Resources
- **Learning Resources:** [docs/learning/resources.md](docs/learning/resources.md)
- **Interview Prep:** [docs/learning/interview-prep.md](docs/learning/interview-prep.md)
- **System Design:** [docs/learning/system-design.md](docs/learning/system-design.md)
- **Leetcode Strategy:** [docs/learning/leetcode-strategy.md](docs/learning/leetcode-strategy.md)
- **Progress Tracker:** [progress-tracker.md](progress-tracker.md)

---

## MONTH 1: Foundation + ElevenLabs SDK

### Week 1: Kotlin Refresh (15 hrs)
**Focus:** Shake off the rust on Kotlin fundamentals

**Tasks:**
- Scope functions (let, run, with, apply, also) - when to use each
- Null safety (avoid !!, use ?. and ?:)
- lateinit vs lazy
- Coroutines basics (launch vs async, dispatchers)
- Flow basics (StateFlow, SharedFlow)

**Deliverable:** Weather app mini-project (OpenWeatherMap API, Retrofit, Flow)
**Leetcode:** 7 easy (Arrays & Strings)

---

### Week 2: MVVM + Components (15 hrs)
**Focus:** Modern Android architecture patterns

**Tasks:**
- ViewModel (what it is, why it survives config changes)
- LiveData vs Flow for UI state
- Room database (Entities, DAOs, migrations)
- Navigation Component (SafeArgs, deep linking)
- Expand Weather app (Room caching, offline-first, multi-screen)

**Deliverable:** Weather app with MVVM + offline mode
**Leetcode:** 7 easy-medium mix (HashMaps, Two Pointers)

---

### Week 3: DI + Testing (15 hrs)
**Focus:** Dependency Injection and testing mindset

**Tasks:**
- Hilt (@Module, @Provides, @Inject, scopes)
- JUnit + MockK + Turbine
- Add Hilt to Weather app, write ViewModel tests
- **Start ElevenLabs SDK research**
  - Read ElevenLabs API docs
  - Study native SDK integration patterns

**Deliverable:** Weather app with Hilt + tests
**Leetcode:** 7 medium (Linked Lists, Stacks)
**Project:** ElevenLabs SDK Phase 1 (Research & Setup)

---

### Week 4: Compose + SDK Start (18 hrs)
**Focus:** Jetpack Compose basics, start real project

**Tasks:**
- Compose fundamentals (state, recomposition, remember)
- Side effects (LaunchedEffect, DisposableEffect)
- Layouts (Row, Column, LazyColumn)
- State hoisting patterns
- **ElevenLabs SDK:** Core implementation begins

**Deliverable:** Weather app UI in Compose
**Leetcode:** 7 medium (Trees, Binary Search)
**Project:** ElevenLabs SDK Phase 2 start (Core wrapper)

**Month 1 Success:** ✅ 1 mini-project + SDK in progress + 28 leetcode problems

---

## MONTH 2: Clean Architecture + Midnight Wallet

### Week 5: Clean Arch + Multi-Module (15 hrs)
**Focus:** Production-grade architecture patterns

**Tasks:**
- Clean Architecture (domain, data, presentation layers)
- Multi-module setup (feature vs core modules)
- Version catalogs
- Study: Nowinandroid, Tivi architecture
- **Complete ElevenLabs SDK**, publish to GitHub

**Deliverable:** ElevenLabs SDK published with README
**Leetcode:** 7 medium (DFS/BFS)
**Project:** Plan Midnight Wallet architecture

---

### Week 6: Advanced Coroutines + Data Layer (18 hrs)
**Focus:** Complex async patterns for wallet

**Tasks:**
- Structured concurrency (supervisorScope, exception handling)
- Advanced Flow (hot vs cold, SharedFlow config)
- Flow operators (combine, flatMap, etc.)
- **Midnight Wallet:** Setup multi-module, implement crypto layer

**Deliverable:** Midnight Wallet - Key generation, crypto working
**Leetcode:** 7 medium (DP intro)

---

### Week 7: Advanced Compose + Wallet UI (18 hrs)
**Focus:** Build beautiful, secure wallet interface

**Tasks:**
- Custom Compose layouts, animations
- Performance (@Stable, derivedStateOf)
- Compose testing
- **Midnight Wallet:** Full Compose UI, wallet features

**Deliverable:** Midnight Wallet - Send/receive, backup UI complete
**Leetcode:** 7 medium (More DP)

---

### Week 8: Testing + CI/CD (20 hrs)
**Focus:** Polish wallet to production quality

**Tasks:**
- Comprehensive testing (Unit, Integration, Compose UI)
- GitHub Actions CI/CD setup
- Security audit (key storage, encryption)
- **Complete Midnight Wallet**, publish to GitHub

**Deliverable:** Midnight Wallet Android published (multi-module, tests, CI/CD)
**Leetcode:** 7 medium-hard (Graphs)

**Month 2 Success:** ✅ 2 real projects published + 56 leetcode total

---

## MONTH 3: Advanced Topics + AI Chat Start

### Week 9: Performance + Profiling (15 hrs)
**Focus:** FAANG-level optimization skills

**Tasks:**
- Android Profiler (CPU, Memory, Network)
- Memory leak detection (LeakCanary)
- Performance optimization (LazyColumn, overdraw)
- Profile and optimize Midnight Wallet

**Deliverable:** Performance-optimized Midnight Wallet
**Leetcode:** 7 medium-hard (Advanced DP)
**Project:** AI Chat App - Architecture planning

---

### Week 10: Advanced Android Topics (18 hrs)
**Focus:** Fill knowledge gaps, start AI app

**Tasks:**
- Custom Views (onMeasure, onLayout, onDraw)
- WorkManager (background tasks)
- App optimization (R8, bundle)
- **AI Chat:** Multi-module setup, LLM abstraction layer

**Deliverable:** AI Chat - Core architecture, module structure
**Leetcode:** 7 hard (Graphs, advanced)

---

### Week 11: System Design Practice (20 hrs)
**Focus:** Prepare for system design interviews

**Tasks:**
- Practice 4 designs: Instagram feed, WhatsApp, Uber driver, Google Maps
- Document architecture decisions for AI Chat
- **AI Chat:** Implement core LLM integration (OpenAI, Anthropic, Gemini)

**Deliverable:** AI Chat - Multi-LLM working, chat functionality
**Leetcode:** 7 hard (Backtracking)
**Prep:** STAR stories documented

---

### Week 12: Mock Interviews Begin (18 hrs)
**Focus:** Start interview practice

**Tasks:**
- Prepare STAR stories (Nike, eBay, blockchain experiences)
- First 2 mock interviews (Pramp or Interviewing.io)
- **AI Chat:** Voice integration (STT + ElevenLabs TTS)

**Deliverable:** AI Chat - Voice working, streaming responses
**Leetcode:** 7 hard (timed practice)
**Interviews:** 2 mock sessions completed

**Month 3 Success:** ✅ AI Chat in progress + 100 leetcode total + Interview practice started

---

## MONTH 4: Polish + Interview Ready

### Week 13: Advanced Testing + CI (15 hrs)
**Focus:** Production-quality testing

**Tasks:**
- Compose UI testing, screenshot testing
- Espresso integration tests
- GitHub Actions for AI Chat
- **AI Chat:** Add comprehensive test suite

**Deliverable:** AI Chat - 80%+ test coverage
**Leetcode:** 7 timed medium (20 min target)

---

### Week 14: Security + Best Practices (18 hrs)
**Focus:** Secure, production-ready code

**Tasks:**
- API key security, ProGuard
- Authentication flows (OAuth, JWT)
- SSL pinning, encrypted storage
- **AI Chat:** Security hardening, performance optimization

**Deliverable:** AI Chat - Secure, optimized
**Leetcode:** 7 hard (30 min target)

---

### Week 15: System Design Mastery (20 hrs)
**Focus:** Deep system design preparation

**Tasks:**
- Practice 4 more designs: Netflix, Twitter, Medium, Spotify
- Write system design doc for AI Chat
- Architecture diagrams (draw.io)

**Deliverable:** AI Chat - Complete system design documentation
**Leetcode:** 7 company-tagged (Google, Meta, Amazon)

---

### Week 16: Interview Bootcamp (20 hrs)
**Focus:** Final interview polish

**Tasks:**
- 4 more mock interviews (behavioral, coding, system design)
- Identify weak areas, drill those topics
- Android flashcards (theory questions)
- **Complete AI Chat**, publish to GitHub

**Deliverable:** AI Chat Android published (full-featured, tested, documented)
**Leetcode:** 7 hard (weakness focus)
**Interviews:** 6+ mock sessions total

**Month 4 Success:** ✅ 3 elite projects published + 150 leetcode total + Interview-ready

---

## MONTH 5: Application Wave 1

### Week 17: Resume + Apply (12 hrs)
**Focus:** Enter the job market

**Tasks:**
- Resume optimization (quantify Nike/eBay achievements)
- LinkedIn polish
- Apply to 15 companies:
  - FAANG: Google, Meta, Amazon, Apple
  - Tier-2: Uber, Airbnb, Snap, Pinterest, Stripe
  - Startups: xAI, Anthropic, Whatnot
- Leetcode marathon (10-15 problems)

**Deliverable:** 15 applications submitted
**Leetcode:** 180 total (maintenance mode)

---

### Weeks 18-19: Active Interviews (20 hrs/week)
**Focus:** Recruiter screens, phone screens

**Tasks:**
- Continue applying (target 30-40 total)
- Recruiter screens (expect 10-15)
- Technical phone screens (expect 3-5)
- Mock interviews 2x/week
- Leetcode 1.5 hrs/day (stay sharp)
- System design practice 3x/week
- Android flashcards daily

**Deliverable:** 30-40 applications, 10-15 screens, 3-5 phone screens

---

### Week 20: Reflection + Adjustment (15 hrs)
**Focus:** Analyze and improve

**Tasks:**
- Analyze interview feedback
- Address weak areas (algorithms, system design, communication)
- Build mini-project if needed to fill gaps
- More mock interviews

**Deliverable:** Improved based on feedback
**Leetcode:** 200 total ✅

**Month 5 Success:** ✅ 30-40 applications + 10-15 recruiter screens + 3-5 phone screens + 200 leetcode complete

---

## MONTH 6: Close Offers

### Weeks 21-23: Interview Peak (25 hrs/week)
**Focus:** Onsites and final rounds

**Tasks:**
- Onsite interviews (1-2 per week)
- Leetcode maintenance (1 hr/day)
- System design prep before each onsite (3-4 hrs)
- Review STAR stories before behavioral rounds
- Network with Android engineers (referrals)

**Target:** 2-3 onsite loops

---

### Week 24: Negotiation
**Focus:** Close offers, negotiate compensation

**Tasks:**
- Research comp (levels.fyi, Blind)
- Leverage multiple offers
- Negotiate (base, stock, signing bonus)
- Evaluate beyond compensation (team, tech stack, growth)

**Deliverable:** 1+ offers (goal: 2-3 for leverage)

**Month 6 Success:** ✅ OFFERS IN HAND 🎉

---

## Daily Schedule

**Weekday (2 hours):**
- 0-30 min: Leetcode warm-up (1 easy problem)
- 30-90 min: Main leetcode (1 medium or hard)
- 90-120 min: Android learning or project work

**Weekend (6 hours):**
- 0-120 min: Leetcode deep session (2-3 problems, 1 hard)
- 120-240 min: Project building (hands-on coding)
- 240-300 min: System design practice or mock interview
- 300-360 min: Weekly review, adjust plan

---

## Success Metrics

**Month 2:** ✅ 2 projects + 50 leetcode + MVVM mastery
**Month 4:** ✅ 3 projects + 150 leetcode + Interview-ready
**Month 6:** ✅ 200 leetcode + OFFERS 🚀

---

## Projects Portfolio

1. **ElevenLabs Android SDK** → [docs/projects/elevenLabs.md](docs/projects/elevenLabs.md)
2. **Midnight Wallet Android** → [docs/projects/midnightWallet.md](docs/projects/midnightWallet.md)
3. **AI Chat Android App** → [docs/projects/ai-chat-android.md](docs/projects/ai-chat-android.md)

---

**Let's go build. Week 1 starts now.** 🚀
