# Android Interview Questions - Evolution Over 2-3 Years

**Source:** Reddit r/androiddev discussion - Focus on how Android interviews have changed with new technologies

## Key Themes

### Major Technology Shifts:
- **Kotlin is now mandatory** - No longer optional, expected across all experience levels
- **Jetpack Compose adoption** - Every interviewer asks about it (though not universally used in production yet)
- **MVVM/Clean Architecture** - Still the standard after 3+ years
- **Moving away from:** RxJava → Coroutines/Flow, Dagger2 → Hilt
- **Emerging:** Kotlin Multiplatform (KMM)

## Interview Question Categories

### Interviewer Perspective - What I Ask (From practicing interviewer):

**Network & API:**
- What third-party lib do you use for network calls? (Retrofit, OkHttp, etc.)
- Follow-ups based on the library chosen

**Kotlin Specifics:**
- lateinit vs lazy delegate - difference and usage in Android
- Sealed classes - what are they for? Android use cases
- Data classes - can you use var properties? What issues could that cause?
- What alternatives to the !! operator?
- Scope functions usage

**Navigation & Architecture:**
- How would you navigate from one screen to another?
- MVVM architecture implementation
- State hoisting concept
- What is @stable annotation for?

**Coroutines & Threading:**
- Launch a coroutine on viewModelScope - UI thread or background?
- Dispatcher types and when to use each
- Difference between async and launch
- How coroutines differ from threads

**State Management:**
- How do you communicate data from viewmodel to UI?
  - LiveData, Flow, Kotlin Channels, Callbacks, RxJava
  - Follow-up questions based on choice

**Dependency Injection:**
- What DI frameworks have you worked with?
- What's the advantage of using DI?
- Dagger/Hilt vs Koin differences

**Jetpack Compose:**
- State hoisting
- Recomposition concept
- @stable annotation usage
- Compose layouts (Row, Column, LazyColumn)
- Theme management
- Best practices

**Testing:**
- Approach to testing
- MockK, Turbine, other testing frameworks

**Other:**
- Clean Architecture understanding
- Serialization libraries (Gson, Moshi, kotlinx.serialization)
- Delegates
- Gitflow

### Basic Android Fundamentals (Still Asked):
- Activity vs Fragment
- Activity lifecycle vs Fragment lifecycle
- What is XML (in context of Android)
- What is MVVM
- Memory leaks - what and where stored
- Handling database calls from Activity (keeping UI responsive)
- Running app asynchronously

**Coding Tests:**
- HashMap operations (overwrite existing data vs adding new)
- Sorting/ordering lists
- Data structure problems
- Platform: CoderPad or similar

### Software Engineering Focus (Trend):
"As Android development becomes easier, less Android tricky questions, more SE questions"

**Architectures:**
- MVC vs MVVM vs MVP
- When to use each and why

**Concurrency:**
- Threads (yes, still asked)
- Coroutines
- Flow

**Design Patterns:**
- Singleton
- Builder
- Factory

**Principles:**
- Dependency Injection
- Reactive Programming
- SOLID Principles
- Clean Code

## Interview Format Trends

### What's Working:
- **Code challenges at home** - Better than pair programming
- **Small take-home projects** - Real-world scenarios (4-5 hours max)
- **Quick-fire questions + optional coding** - Most choose coding
- **Scenario-based discussions** - "How would you implement X?"

### What's Controversial:
- **Pair programming** - Many feel it's unfair due to stress/nerves
- **Pure leetcode** - Some FAANG still use it, but Android-specific companies moving away
- **Overly specific questions** - Bluetooth Low Energy, specific APIs unless job-relevant

### What Interviewers Actually Care About:
1. Can you learn quickly?
2. Do you understand fundamentals (not just memorize)?
3. Can you explain technical concepts to non-technical stakeholders?
4. Have you kept up with modern Android (Kotlin, Coroutines, Compose basics)?
5. Can you work with legacy code AND modern code?

## Regional Differences

### France (mentioned):
- Kotlin is now the norm
- Compose questions are standard
- Not optional anymore

### India (mentioned):
- Heavy theory focus
- Need to memorize internals (e.g., "How does ViewModel survive config changes?")
- More conceptual depth expected

### US (implied):
- Mix of leetcode and practical
- FAANG: Heavy on algorithms + system design
- Startups: More practical coding challenges

## Compose Adoption Reality Check

**Interviewer Perspective:**
- "Compose is still very niche, most production code is views and will be for another 5 years"
- "Not everyone on our team has Compose project experience"
- "Shouldn't be a hard requirement but good to know basics"

**Production Usage:**
- Some companies: 100% Compose (new apps, 500k+ users)
- Many companies: Still on MVP/XML, slowly migrating
- Migration path: MVP → MVVM → Compose (takes years for large apps)
- ComposeView for gradual migration common

**Interview Impact:**
- Questions asked to see if you know basics
- Not a deal-breaker if you haven't used it in production
- France: Becoming mandatory
- US: Nice to have, not required

## Elite Interview Preparation (Based on Insights)

### For FAANG/Big Tech:
- Leetcode medium in ~20 minutes
- System design with Android specifics
- Some practical: "Build a class that does X"
- Soft skills stories (STAR method)
- Less Android trivia, more CS fundamentals

### For Startups/Scale-ups:
- Live coding (debug buggy code, refactor, add features)
- Home assignment (realistic feature, 4-5 hours)
- Architecture discussions
- Experience with building from scratch
- Modern stack knowledge (Kotlin, Coroutines, Compose)

### For Mid-Level Companies:
- Mix of theory and practical
- Standard app knowledge (MVVM, DI, Testing, Coroutines)
- Questions based on your CV/experience
- Pragmatic over theoretical

## Key Insights

### What Makes a Strong Candidate:
- "I ask questions based on standard applications, not edge cases"
- "I check if they understand what they're doing, not just using libraries"
- "Learning new libraries is easy, learning how to code properly is hard"
- "I expect seniors to know most answers well, mids can have more trouble and that's fine"

### Interview Philosophy (Good Interviewers):
- No "describe definition of SOLID" - proves memory, not understanding
- No overly specific questions unless job-relevant (Bluetooth, etc.)
- Understanding > Recitation
- Communication skills matter
- Stress-resistant ≠ Good developer (hence moving away from pair programming)

### Reality Check:
- "10+ years experience, can still fail any interview" - Interviews are random
- "Random people for random employer" - Lot of variability
- Post-interview regret: "I would do things differently now"
- Feedback loops important

## Job Market Notes (As of discussion time):
- Market getting traction again
- Facebook contracts in London (lower end of spectrum pay)
- Experience curve: After 5+ years without Kotlin = red flag
- Management → IC transition difficult after 2+ years away from coding
