# Interview Preparation

Complete guide to crushing FAANG Android interviews: behavioral, technical, and system design.

---

## STAR Stories (Behavioral Interviews)

### The STAR Method

**S**ituation - Context and background
**T**ask - Your responsibility or challenge
**A**ction - Specific steps you took
**R**esult - Measurable outcomes, learnings

**Interview Tip:** FAANG interviewers look for leadership, ownership, and data-driven decisions. Quantify results whenever possible.

---

### Your STAR Stories Template

Document 8-10 stories from your Nike, eBay, and blockchain experiences. Mix of successes, failures, and learnings.

#### Story 1: [Title - e.g., "Nike App Performance Crisis"]

**Situation:**
[Context: Team, project, timeline, business impact]

**Task:**
[Your specific responsibility or challenge you faced]

**Action:**
[Detailed steps you took - this is the longest section]
- Step 1: [Technical decision, why you chose it]
- Step 2: [Collaboration, communication]
- Step 3: [Implementation details]
- Step 4: [How you measured success]

**Result:**
[Quantified outcomes: performance improvement, user impact, revenue, team learning]

**Interview Variations:**
- Leadership: [How this demonstrates leadership]
- Conflict: [If applicable, how you handled disagreement]
- Failure: [What went wrong, what you learned]

---

### Story Categories (Prepare 1-2 per category)

#### 1. Technical Challenge
**Question triggers:** "Tell me about a complex technical problem you solved"

**Your experiences to mine:**
- Nike: Large-scale mobile app performance at scale
- eBay: Complex marketplace features, high-traffic systems
- Blockchain: Near/Algorand/Solana integration complexity

**Focus on:** Architecture decisions, debugging, optimization, trade-offs

---

#### 2. Leadership & Influence
**Question triggers:** "Tell me about a time you led without authority"

**Your experiences to mine:**
- Cross-team collaboration at Nike/eBay
- Introducing new technologies or processes
- Mentoring junior developers

**Focus on:** Persuasion, consensus-building, stakeholder management

---

#### 3. Conflict Resolution
**Question triggers:** "Tell me about a disagreement with a teammate/manager"

**Your experiences to mine:**
- Technical disagreements (architecture, tools, approach)
- Product vs Engineering tension
- Prioritization conflicts

**Focus on:** Active listening, finding win-win, maintaining relationships

---

#### 4. Failure & Learning
**Question triggers:** "Tell me about a time you failed"

**Your experiences to mine:**
- Missed deadline or underestimated complexity
- Production bug or outage
- Wrong technical decision you had to reverse

**Focus on:** Ownership, learning, how you improved processes after

---

#### 5. Ambiguity & Initiative
**Question triggers:** "Tell me about a time you worked without clear requirements"

**Your experiences to mine:**
- 0-to-1 projects (blockchain integrations?)
- Unclear product specs, had to define technical approach
- Exploratory work (R&D, prototypes)

**Focus on:** Problem definition, hypothesis-driven approach, iteration

---

#### 6. Scale & Impact
**Question triggers:** "Tell me about your biggest impact"

**Your experiences to mine:**
- Nike/eBay: Millions of users, high-traffic systems
- Performance improvements (load time, crash rate)
- Feature launches with measurable business impact

**Focus on:** Quantified results (revenue, engagement, reliability)

---

#### 7. Fast Learning / New Technology
**Question triggers:** "Tell me about a time you had to learn something quickly"

**Your experiences to mine:**
- Blockchain tech (Near, Algorand, Solana, Ethereum)
- New mobile frameworks or libraries
- Switching between iOS/Android or web/mobile

**Focus on:** Learning strategy, how you got productive fast, outcomes

---

#### 8. Team Collaboration
**Question triggers:** "Tell me about working with a difficult teammate"

**Your experiences to mine:**
- Cross-functional work (designers, PMs, backend engineers)
- Remote collaboration
- Different time zones or communication styles

**Focus on:** Empathy, communication adjustments, building trust

---

## Common Behavioral Questions

### Leadership Principles (Amazon)

- **Ownership:** "Tell me about a time you took on something outside your scope"
- **Bias for Action:** "Tell me about a time you made a decision with incomplete info"
- **Dive Deep:** "Tell me about a time you debugged a complex issue"
- **Deliver Results:** "Tell me about a time you delivered under a tight deadline"
- **Earn Trust:** "Tell me about a time you admitted a mistake"

### Google's Googleyness

- **Collaboration:** "Tell me about a time you worked with someone very different from you"
- **Growth Mindset:** "Tell me about a time you received critical feedback"
- **Comfort with Ambiguity:** "Tell me about a time you had to figure things out on your own"
- **Impact:** "What's the most impactful thing you've built?"

### Meta's Core Values

- **Move Fast:** "Tell me about a time you shipped something quickly"
- **Be Bold:** "Tell me about a time you took a risk"
- **Focus on Impact:** "Tell me about a time you prioritized ruthlessly"
- **Build Social Value:** "Tell me about a time you built something for users"

---

## Android Technical Flashcards

Study these daily (10-15 min) starting Month 2. Use spaced repetition (Anki app recommended).

### Kotlin Fundamentals

**Q:** When would you use `let` vs `apply` vs `run`?
**A:** `let` for null safety + transformation (returns lambda result), `apply` for object configuration (returns receiver), `run` for scoped operations (returns lambda result)

**Q:** Difference between `lateinit` and `lazy`?
**A:** `lateinit` for var (must initialize before use, can reassign), `lazy` for val (initialized on first access, thread-safe by default)

**Q:** What's the difference between `==` and `===` in Kotlin?
**A:** `==` checks structural equality (calls `.equals()`), `===` checks referential equality (same object instance)

**Q:** Explain Kotlin coroutines vs threads
**A:** Coroutines are lightweight (thousands vs hundreds of threads), suspendable (don't block threads), structured concurrency, easier error handling

### Android Architecture

**Q:** Why use ViewModel instead of storing state in Activity?
**A:** Survives configuration changes (rotation), separates UI logic from UI rendering, lifecycle-aware, testable without UI

**Q:** LiveData vs StateFlow vs SharedFlow?
**A:** LiveData (lifecycle-aware, UI only), StateFlow (hot flow, always has value, replay 1), SharedFlow (configurable replay, no initial value, can broadcast events)

**Q:** Explain MVVM vs MVI
**A:** MVVM (ViewModel exposes LiveData/StateFlow, View observes), MVI (unidirectional data flow, single immutable state, explicit intents/events)

**Q:** What is Clean Architecture?
**A:** Separation into layers (domain, data, presentation), dependency rule (inner layers don't know outer), use cases encapsulate business logic, easy to test

### Jetpack Compose

**Q:** What is recomposition? When does it happen?
**A:** Re-executing composable functions when state changes. Happens when `State<T>` objects change, skips composables whose inputs haven't changed

**Q:** Explain `remember` vs `rememberSaveable`
**A:** `remember` survives recomposition (not config changes), `rememberSaveable` survives process death + config changes (uses SavedStateHandle)

**Q:** What's state hoisting?
**A:** Moving state up to composable's caller to make composable stateless/reusable. Caller controls state, composable receives value + callbacks

**Q:** LaunchedEffect vs DisposableEffect vs SideEffect?
**A:** `LaunchedEffect` for suspend functions (cancels on key change), `DisposableEffect` for cleanup (onDispose), `SideEffect` for publishing state to non-Compose code

### Coroutines & Flow

**Q:** Difference between `launch` and `async`?
**A:** `launch` returns Job (fire-and-forget), `async` returns Deferred (can await result). Use `async` when you need return value

**Q:** What are Dispatchers? Main types?
**A:** Context for coroutine execution. `Main` (UI updates), `IO` (network/disk), `Default` (CPU-intensive), `Unconfined` (don't use in production)

**Q:** Hot vs Cold Flows?
**A:** Cold: Start producing on collect, separate for each collector (e.g., `flow {}`). Hot: Produce regardless of collectors (StateFlow, SharedFlow)

**Q:** How do you handle exceptions in coroutines?
**A:** `try-catch` in suspend functions, `CoroutineExceptionHandler`, `supervisorScope` (don't cancel siblings), structured exception propagation

### Dependency Injection (Hilt)

**Q:** Why use DI instead of manual instantiation?
**A:** Testability (swap implementations), decoupling, lifecycle management, single source of truth, compile-time verification

**Q:** Hilt scopes: `@Singleton` vs `@ActivityScoped` vs `@ViewModelScoped`?
**A:** `@Singleton` lives for app lifetime, `@ActivityScoped` per Activity instance, `@ViewModelScoped` per ViewModel instance

**Q:** What's the difference between `@Provides` and `@Binds`?
**A:** `@Provides` for instances you construct (third-party classes, builders), `@Binds` for interface-to-implementation mapping (more efficient, abstract function)

### Performance & Optimization

**Q:** How would you debug a performance issue in Android?
**A:** Android Profiler (CPU, memory, network), Systrace for frame drops, StrictMode for main thread violations, LeakCanary for memory leaks

**Q:** What causes memory leaks in Android?
**A:** Long-lived references to Activities/Fragments (listeners not unregistered, static references, Handler with implicit Activity reference, non-static inner classes)

**Q:** How do you optimize RecyclerView/LazyColumn performance?
**A:** ViewHolder pattern, DiffUtil, pagination, image loading (Coil/Glide), avoid layout nesting, use stable IDs, `` for Compose lists

**Q:** What's R8/ProGuard? Why use it?
**A:** Code shrinker + obfuscator. Removes unused code, optimizes bytecode, shrinks APK size, makes reverse engineering harder

### Testing

**Q:** Unit test vs Integration test vs UI test?
**A:** Unit (isolated, fast, ViewModel/UseCase), Integration (multiple components, Repository+DAO), UI (Espresso/Compose UI test, full flow)

**Q:** How do you test ViewModels?
**A:** JUnit + MockK, inject fake repositories, verify state changes, use Turbine for Flow testing, `InstantTaskExecutorRule` for LiveData

**Q:** How do you test Compose UI?
**A:** `createComposeRule()`, `onNodeWithText/Tag/ContentDescription`, `performClick()`, `assertIsDisplayed()`, semantic trees

### Bonus: Blockchain (Your Unique Angle)

**Q:** How would you integrate a blockchain wallet into Android?
**A:** Native libs (C/C++ via JNI) for crypto, secure key storage (Keystore), encrypted SharedPreferences, biometric auth, offline signing

**Q:** Security concerns for mobile crypto wallet?
**A:** Key management (never store plaintext), root detection, SSL pinning, code obfuscation, secure enclave usage, transaction signing offline

---

## Mock Interview Schedule

### Month 3 (Weeks 9-12): Start Practice

**Week 9:** Set up accounts (Pramp, Interviewing.io)
**Week 10:** First mock interview (coding focus)
**Week 11:** System design mock
**Week 12:** Behavioral mock

**Frequency:** 1-2 sessions per week
**Focus:** Get comfortable with interview format, identify weak areas

---

### Month 4 (Weeks 13-16): Intensive Practice

**Week 13-14:** 2 sessions per week (coding + behavioral)
**Week 15-16:** 3 sessions per week (coding, system design, behavioral rotation)

**By end of Month 4:**
- 6+ total mock interviews completed
- Confident explaining your portfolio projects
- Behavioral stories polished
- Can solve medium LeetCode in 20-30 min

---

### Month 5-6 (Active Interviewing): Real Practice

**Months 5-6:** 2-3 sessions per week (maintenance + warm-up before real interviews)

**Strategy:**
- Use "practice companies" (companies you're less excited about) for early real interviews
- Schedule mocks 1-2 days before important onsites
- Get feedback, iterate on weak areas

---

## Interview Day Checklist

### Before Interview (Day Before)

- [ ] Review company's tech blog, recent Android projects
- [ ] Review your portfolio projects (can explain architecture in 5 min)
- [ ] Practice 2-3 medium LeetCode problems (same patterns as expected)
- [ ] Prepare 2-3 questions to ask interviewer
- [ ] Get 8 hours of sleep

### Before Interview (Morning Of)

- [ ] Solve 1 easy LeetCode problem (warm up brain)
- [ ] Review your STAR stories (refresh memory)
- [ ] Set up quiet space, good internet, backup device
- [ ] Have water, scratch paper, pen ready

### During Coding Interview

- [ ] Clarify problem, ask questions, confirm understanding
- [ ] Discuss approach before coding (high-level algorithm)
- [ ] Write clean code, explain as you go
- [ ] Test with example cases, discuss edge cases
- [ ] Optimize, discuss time/space complexity

### During System Design Interview

- [ ] Clarify requirements (functional + non-functional)
- [ ] Estimate scale (users, requests, storage)
- [ ] High-level design (draw boxes, discuss data flow)
- [ ] Deep dive into 1-2 components
- [ ] Discuss trade-offs, alternatives

### During Behavioral Interview

- [ ] Use STAR method (don't ramble)
- [ ] Quantify results (numbers, impact, metrics)
- [ ] Show ownership, leadership, learning
- [ ] Be honest about failures, focus on what you learned
- [ ] Ask questions (shows interest, cultural fit)

---

## Common Interview Red Flags to Avoid

**Coding:**
- Not clarifying problem before coding
- Jumping to code without discussing approach
- Not testing your solution
- Not discussing complexity

**System Design:**
- Starting with implementation details (use Redis, Kafka) before high-level design
- Not discussing trade-offs
- Ignoring scale/performance
- Not asking clarifying questions

**Behavioral:**
- Rambling without structure
- Not quantifying results
- Blaming others for failures
- Talking only about team ("we") without your specific contributions ("I")

---

## Resources

**Behavioral Prep:**
- ["Cracking the PM Interview"](https://www.crackingthepminterview.com/) (STAR method examples)
- [Amazon Leadership Principles](https://www.amazon.jobs/en/principles) (study even if not interviewing at Amazon)

**Mock Interview Platforms:**
- [Pramp](https://www.pramp.com/) (free, peer-to-peer)
- [Interviewing.io](https://interviewing.io/) (paid, real FAANG engineers)
- [Exponent](https://www.tryexponent.com/) (videos, practice questions)

**Android Interview Prep:**
- [Android Interview Questions](https://github.com/MindorksOpenSource/android-interview-questions) (GitHub repo)
- [Coding Interview University](https://github.com/jwasham/coding-interview-university) (comprehensive CS fundamentals)

---

## Your Competitive Advantages

**In interviews, emphasize:**
1. **Nike/eBay scale** - You've worked on apps with millions of users
2. **CS degree** - Solid algorithms foundation (UT Austin)
3. **Blockchain experience** - Shows fast learning, emerging tech, cryptography understanding
4. **Portfolio projects** - ElevenLabs SDK, Midnight Wallet, AI Chat (real-world, non-tutorial)
5. **Recent upskilling** - Went from rusty to FAANG-ready in 6 months (shows drive, fast learning)

**Story framing:**
"At Nike, I worked on an Android app with 10M+ users where we reduced crash rate from 2% to 0.3% through systematic profiling and architecture improvements. Now I'm deepening my Android expertise by building production apps like a multi-LLM chat app with voice integration and a zero-knowledge crypto wallet."

This positions you as: experienced, impact-driven, current with latest tech (AI, blockchain), and committed to Android mastery.

---

**Start preparing STAR stories NOW (Month 1). By interview time, these should be second nature.**
