# Android Interview Questions - Recent Trends (Last 6 months)

**Source:** Reddit r/androiddev discussion - Developer with 4 years experience preparing for interviews

## Key Questions from Recent Interviews

### Fresher Role Interview Questions (ananda3b):
- What is application context? Difference between it and getContext
- Different types of coroutine scopes
- What is LaunchedEffect? (Scenario-based question)
- How to handle circular dependency in multi module app and how can we avoid it
- Difference between Bluetooth and Bluetooth low energy
- How to send data from one app to another (intents)
- MVVM architecture: Mvvm vs mvc vs mvi - which is better for what case and why
- How to manage versions in multi module apps (version catalogs)
- Questions on app projects and implementation details

### Standard Interview Topics (MindCrusader - Interviewer Perspective):
**Focus on standard applications, CV, and technologies used:**
- MVVM architecture (industry standard)
- Multi module setup
- Coroutines
- Compose basics
- Dependency Injection (DI) - what it is and why
- Dagger/Hilt vs Koin - differences
- Approach to testing
- Clean Architecture
- Serializing library
- Delegates
- Gitflow

**Note:** Avoid overly specific questions like Bluetooth implementation unless relevant to the role. Focus on concepts that apply to standard applications.

### Company 1 Questions (abhay-cloud, 2024 Graduate):
**Kotlin & Language Features:**
- Difference between Java and Kotlin
- Features of Kotlin
- Scope functions in Kotlin
- Coroutines in Kotlin
- Kotlin coroutines vs Java threads
- Difference between async and launch
- How to switch context in coroutines
- What is lazy, lateinit
- What is a sealed class
- Difference between normal class and data class

**Architecture & Patterns:**
- What is MVVM architecture
- What is a viewmodel
- Do we write business logic in viewmodel

**Flow & State Management:**
- What is flow
- Difference between stateflow and sharedflow
- How to collect the latest value in stateflow in activity

**Dependency Injection:**
- What is dagger hilt
- What is @provides, @module
- What is singleton

**Jetpack Compose:**
- What are side effects in jetpack compose
- App crashes when we use lazyColumn in column - how to solve
- What is disposable effect
- How to set 3 elements in a row in a grid in compose

**Data Passing:**
- How to pass data between activities

### Company 2 Questions:
- What is intent? Why do we use it
- What is service & types of service
- Why does android app lag
- What is Dispatcher in coroutine and types of Dispatcher
- What is launchedEffect
- How to improve performance of recyclerView, lazyColumn
- What is flow

### Advanced Interview Topics (For Mid-Senior Positions):
**Concurrency Deep Dive:**
- Threading, threadpools, thread safety
- Differences between threads and coroutines
- Theory and implementation (FAANG level: implementation required)

**Compose Questions:**
- Theme managing
- State hoisting
- Best practices
- Compose layouts and alignment
- Constraint layout in compose

### Other Interview Formats Mentioned:
- Live coding challenges (debugging existing code with bugs)
- Refactoring exercises (1.5 hour sessions)
- Home assignments (paginated list screen - 4-5 hours)
- Pair programming (reviewing code for issues like UI jank, memory leaks, ANR)
- Small demo app from scratch

## Interview Process Insights

### Theory vs Practical Balance:
- Most companies blend theory questions with practical coding challenges
- FAANG: Primarily leetcode + system design (Android-specific questions via system design)
- Smaller companies: More Android-specific theory
- Live coding becoming more common than pure Q&A

### What Interviewers Actually Look For:
1. **Understanding over memorization** - Can you explain why you use DI, not just what it is
2. **Standard application knowledge** - Technologies used in most apps
3. **Ability to learn** - More important than knowing every library
4. **Problem-solving approach** - How you tackle challenges under discussion
5. **Communication skills** - Explaining technical concepts to different audiences

### Red Flags to Avoid:
- Not knowing basic concepts like DI, architecture patterns
- Unable to explain why you use certain tools/patterns
- No knowledge of Kotlin after 5+ years experience
- Can't discuss trade-offs between different approaches

### Compose Adoption Status:
- Still not universal standard (many companies on legacy XML views)
- Compose questions becoming common but often not hard requirements
- Companies still migrating MVP → MVVM → Compose
- Knowing both XML and Compose is valuable

### Key Takeaways:
- Focus on MVVM, Coroutines, Flow, DI as baseline
- Compose basics are increasingly important
- System design matters more at senior levels
- Leetcode still common at FAANG/big tech
- Practical coding challenges > pure trivia questions
- Know your fundamentals (threads, memory management, lifecycles)
