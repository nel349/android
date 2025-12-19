package playground

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * SHARED FLOW - Event Broadcasting
 *
 * SharedFlow vs StateFlow:
 * - StateFlow: Always has a value (state)
 * - SharedFlow: Events stream (no required initial value)
 */

suspend fun main() = coroutineScope {
    println("=" .repeat(60))
    println("SHARED FLOW WITH REPLAY")
    println("=" .repeat(60))

    // SharedFlow with replay = 2 (new collectors get last 2 emissions)
    val eventFlow = MutableSharedFlow<String>(
        replay = 2,
        extraBufferCapacity = 10
    )

    // Emit some events BEFORE any collectors
    println("\n📢 Emitting events (no collectors yet)...")
    eventFlow.emit("Event 1")
    println("   Emitted: Event 1")
    eventFlow.emit("Event 2")
    println("   Emitted: Event 2")
    eventFlow.emit("Event 3")
    println("   Emitted: Event 3")

    delay(500)

    // Collector joins NOW (gets last 2 due to replay)
    println("\n👤 Collector 1 joining (will get last 2 events due to replay)...")
    launch {
        eventFlow.take(5).collect { event ->
            println("   Collector 1 received: $event")
        }
    }

    delay(500)

    // More events (collector receives them)
    println("\n📢 Emitting new events...")
    eventFlow.emit("Event 4")
    println("   Emitted: Event 4")
    delay(200)
    eventFlow.emit("Event 5")
    println("   Emitted: Event 5")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("STATEFLOW VS SHAREDFLOW COMPARISON")
    println("=" .repeat(60))

    // StateFlow
    val stateFlow = MutableStateFlow("Initial State")
    println("\n🔴 StateFlow:")
    println("   Current value: ${stateFlow.value}")  // ✅ Has .value
    stateFlow.value = "New State"  // ✅ Can set directly
    println("   New value: ${stateFlow.value}")

    // SharedFlow
    val sharedFlow = MutableSharedFlow<String>()
    println("\n🔵 SharedFlow:")
    // println(sharedFlow.value)  ❌ No .value property!
    println("   No .value property (events only)")
    sharedFlow.emit("Event!")  // ✅ Emit events
    println("   Emitted event (no current value to read)")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("WHEN TO USE EACH:")
    println("=" .repeat(60))
    println("StateFlow:")
    println("  ✅ UI state (weather data, user profile)")
    println("  ✅ Always needs current value")
    println("  ✅ Latest value matters")
    println("")
    println("SharedFlow:")
    println("  ✅ Events (button clicks, navigation, snackbar messages)")
    println("  ✅ One-time emissions")
    println("  ✅ Replay for late subscribers")
    println("=" .repeat(60))
}
