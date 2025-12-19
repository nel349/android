package playground

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * COROUTINE SCOPES - Understanding Lifecycle and Cancellation
 *
 * GlobalScope: Lives forever (⚠️ Use sparingly!)
 * Custom CoroutineScope: You control the lifecycle
 * Structured Concurrency: Parent-child relationship
 */

suspend fun main() = coroutineScope {
    println("=" .repeat(60))
    println("GLOBAL SCOPE - Lives Forever (Use Sparingly!)")
    println("=" .repeat(60))

    println("\n⚠️  GlobalScope doesn't respect cancellation:")

    val globalJob = GlobalScope.launch {
        println("   🌍 GlobalScope job started")
        delay(500)
        println("   🌍 GlobalScope job still running...")
        delay(500)
        println("   🌍 GlobalScope job still running...")
        delay(500)
        println("   🌍 GlobalScope job completed")
    }

    delay(200)
    println("   ⏹️  Trying to cancel main coroutine...")
    // Even if we leave this scope, GlobalScope continues!
    // This is why it's dangerous - no automatic cleanup

    globalJob.join()  // Wait for it to complete

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("CUSTOM SCOPE - You Control Lifecycle")
    println("=" .repeat(60))

    val customScope = CoroutineScope(Dispatchers.Default)

    println("\n🔧 Creating custom scope with 3 jobs:")

    customScope.launch {
        println("   Job 1 started")
        delay(1000)
        println("   Job 1 completed")
    }

    customScope.launch {
        println("   Job 2 started")
        delay(1000)
        println("   Job 2 completed")
    }

    customScope.launch {
        println("   Job 3 started")
        delay(1000)
        println("   Job 3 completed")
    }

    delay(300)

    println("\n🛑 Cancelling entire scope...")
    customScope.cancel()  // Cancel ALL jobs in this scope!

    delay(500)
    println("   All jobs cancelled (none completed)")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("STRUCTURED CONCURRENCY - Parent-Child Relationship")
    println("=" .repeat(60))

    println("\n👨‍👦 Parent scope with child jobs:")

    val parentTime = measureTimeMillis {
        coroutineScope {  // Parent scope
            println("   Parent started")

            launch {  // Child 1
                println("      Child 1 started")
                delay(500)
                println("      Child 1 done")
            }

            launch {  // Child 2
                println("      Child 2 started")
                delay(700)
                println("      Child 2 done")
            }

            launch {  // Child 3
                println("      Child 3 started")
                delay(300)
                println("      Child 3 done")
            }

            println("   Parent waiting for all children...")
            // coroutineScope waits for ALL children before completing
        }
        println("   Parent completed (all children done)")
    }

    println("\n⏱️  Total time: ${parentTime}ms (max child delay, not sum!)")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("CHILD FAILURE - Parent Gets Cancelled")
    println("=" .repeat(60))

    println("\n💥 When child fails, parent and siblings cancel:")

    try {
        coroutineScope {
            launch {
                println("   Child 1: Working...")
                delay(1000)
                println("   Child 1: Done!")  // Won't reach
            }

            launch {
                println("   Child 2: Working...")
                delay(300)
                println("   Child 2: Throwing exception!")
                throw Exception("Child 2 failed!")
            }

            launch {
                println("   Child 3: Working...")
                delay(1000)
                println("   Child 3: Done!")  // Won't reach
            }
        }
    } catch (e: Exception) {
        println("\n   ⚠️  Exception caught: ${e.message}")
        println("   All siblings were cancelled due to Child 2's failure")
    }

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("SUPERVISED SCOPE - Children Fail Independently")
    println("=" .repeat(60))

    println("\n🛡️  supervisorScope: One child failing doesn't cancel others:")

    supervisorScope {
        launch {
            println("   Child 1: Working...")
            delay(500)
            println("   Child 1: Done! ✅")
        }

        launch {
            try {
                println("   Child 2: Working...")
                delay(200)
                throw Exception("Child 2 failed!")
            } catch (e: Exception) {
                println("   Child 2: Failed ❌ (${e.message})")
            }
        }

        launch {
            println("   Child 3: Working...")
            delay(500)
            println("   Child 3: Done! ✅")
        }

        delay(700)  // Wait for all to complete
    }

    println("\n   Result: Child 1 and 3 completed despite Child 2 failing")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("REAL-WORLD: Android ViewModel Scope")
    println("=" .repeat(60))

    println("\n📱 Simulating ViewModel lifecycle:")

    class FakeViewModel {
        // In real Android: viewModelScope
        private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

        fun loadUserData() {
            viewModelScope.launch {
                println("   Loading user profile...")
                delay(500)
                println("   ✅ User loaded")
            }
        }

        fun loadPosts() {
            viewModelScope.launch {
                println("   Loading posts...")
                delay(700)
                println("   ✅ Posts loaded")
            }
        }

        fun loadNotifications() {
            viewModelScope.launch {
                println("   Loading notifications...")
                delay(300)
                println("   ✅ Notifications loaded")
            }
        }

        fun onCleared() {
            println("\n   🗑️  ViewModel cleared (user left screen)")
            viewModelScope.cancel()  // Cancel all ongoing work
            println("   All network calls cancelled")
        }
    }

    val viewModel = FakeViewModel()

    println("\n📥 User opens screen (ViewModel created):")
    viewModel.loadUserData()
    viewModel.loadPosts()
    viewModel.loadNotifications()

    delay(400)

    println("\n⬅️  User presses back (ViewModel cleared):")
    viewModel.onCleared()

    delay(500)
    println("   No unnecessary network calls completed")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("KEY TAKEAWAYS:")
    println("=" .repeat(60))
    println("GlobalScope:")
    println("  ⚠️  Lives forever - no automatic cleanup")
    println("  ⚠️  Use ONLY for app-lifetime operations")
    println("")
    println("Custom CoroutineScope:")
    println("  ✅ You control when it starts and stops")
    println("  ✅ Cancel all jobs with scope.cancel()")
    println("  ✅ Use SupervisorJob() to prevent cascading failures")
    println("")
    println("coroutineScope (structured):")
    println("  ✅ Waits for all children before completing")
    println("  ✅ If child fails, parent and siblings cancel")
    println("  ✅ Automatic cleanup")
    println("")
    println("supervisorScope:")
    println("  ✅ Children fail independently")
    println("  ✅ One failure doesn't cancel siblings")
    println("  ✅ Good for parallel independent operations")
    println("")
    println("Android:")
    println("  📱 lifecycleScope - tied to Activity/Fragment lifecycle")
    println("  📱 viewModelScope - tied to ViewModel lifecycle")
    println("  📱 Both auto-cancel when destroyed")
    println("=" .repeat(60))
}
