package playground

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * TIMING DEMONSTRATION - Cold vs Hot Flow
 *
 * Demonstrates what late collectors receive
 */

suspend fun main() = coroutineScope {
    println("=" .repeat(60))
    println("COLD FLOW - Late Collector Gets EVERYTHING from START")
    println("=" .repeat(60))

    val coldFlow = flow {
        println("   🧊 Flow execution started")
        emit(1)
        delay(100)
        emit(2)
        delay(100)
        emit(3)
    }

    // Early collector
    println("\n⏰ Time 0ms: Collector 1 starts")
    launch {
        coldFlow.collect { value ->
            println("   👤 Collector 1 received: $value")
        }
    }

    delay(300)  // Wait until all emissions are done

    // Late collector (joins AFTER emissions would have finished)
    println("\n⏰ Time 300ms: Collector 2 starts (late!)")
    println("   Does it get anything? Watch...")
    delay(100)

    launch {
        coldFlow.collect { value ->
            println("   👤 Collector 2 received: $value")
        }
    }

    delay(500)

    println("\n   💡 Result: Collector 2 got EVERYTHING because cold Flow")
    println("              re-executed from the beginning!")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("STATEFLOW - Late Collector Gets CURRENT STATE")
    println("=" .repeat(60))

    val stateFlow = MutableStateFlow(0)

    // Early collector
    println("\n⏰ Time 0ms: Collector 1 starts")
    launch {
        stateFlow.take(4).collect { value ->
            println("   👤 Collector 1 received: $value")
        }
    }

    delay(100)

    println("\n⏰ Time 100ms: Emitting 1")
    stateFlow.value = 1

    delay(100)

    println("⏰ Time 200ms: Emitting 2")
    stateFlow.value = 2

    delay(100)

    // Late collector (joins AFTER emissions 0, 1, 2)
    println("\n⏰ Time 300ms: Collector 2 starts (late!)")
    println("   Missed emissions: 0, 1, 2")
    println("   What does it get?")

    launch {
        stateFlow.take(2).collect { value ->
            println("   👤 Collector 2 received: $value")
        }
    }

    delay(200)

    println("\n⏰ Time 500ms: Emitting 3")
    stateFlow.value = 3

    delay(500)

    println("\n   💡 Result: Collector 2 got current state (2) immediately,")
    println("              then future emissions (3)")
    println("              Missed historical emissions (0, 1)")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("SHAREDFLOW (replay=0) - Late Collector MISSES Everything")
    println("=" .repeat(60))

    val sharedFlowNoReplay = MutableSharedFlow<Int>(replay = 0)

    // Early collector
    println("\n⏰ Time 0ms: Collector 1 starts")
    launch {
        sharedFlowNoReplay.take(3).collect { value ->
            println("   👤 Collector 1 received: $value")
        }
    }

    delay(100)

    println("\n⏰ Time 100ms: Emitting event 1")
    sharedFlowNoReplay.emit(1)

    delay(100)

    println("⏰ Time 200ms: Emitting event 2")
    sharedFlowNoReplay.emit(2)

    delay(100)

    // Late collector (joins AFTER events 1, 2)
    println("\n⏰ Time 300ms: Collector 2 starts (late!)")
    println("   Missed events: 1, 2")
    println("   What does it get?")

    launch {
        sharedFlowNoReplay.take(1).collect { value ->
            println("   👤 Collector 2 received: $value")
        }
    }

    delay(200)

    println("\n⏰ Time 500ms: Emitting event 3")
    sharedFlowNoReplay.emit(3)

    delay(500)

    println("\n   💡 Result: Collector 2 MISSED events 1, 2 (not collecting yet)")
    println("              Only got event 3 (was collecting when it happened)")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("SHAREDFLOW (replay=2) - Late Collector Gets LAST 2")
    println("=" .repeat(60))

    val sharedFlowWithReplay = MutableSharedFlow<Int>(replay = 2)

    // Early collector
    println("\n⏰ Time 0ms: Collector 1 starts")
    launch {
        sharedFlowWithReplay.take(4).collect { value ->
            println("   👤 Collector 1 received: $value")
        }
    }

    delay(100)

    println("\n⏰ Time 100ms: Emitting event 1")
    sharedFlowWithReplay.emit(1)

    delay(100)

    println("⏰ Time 200ms: Emitting event 2")
    sharedFlowWithReplay.emit(2)

    delay(100)

    println("⏰ Time 300ms: Emitting event 3")
    sharedFlowWithReplay.emit(3)

    delay(100)

    // Late collector (joins AFTER events 1, 2, 3)
    println("\n⏰ Time 400ms: Collector 2 starts (late!)")
    println("   Missed events: 1, 2, 3")
    println("   But replay=2, so what does it get?")

    launch {
        sharedFlowWithReplay.take(3).collect { value ->
            println("   👤 Collector 2 received: $value")
        }
    }

    delay(200)

    println("\n⏰ Time 600ms: Emitting event 4")
    sharedFlowWithReplay.emit(4)

    delay(500)

    println("\n   💡 Result: Collector 2 got LAST 2 events (2, 3) from replay buffer")
    println("              Missed event 1 (beyond replay buffer)")
    println("              Then got future event 4")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("SUMMARY - What Late Collectors Get:")
    println("=" .repeat(60))
    println("Cold Flow:")
    println("  ✅ Gets EVERYTHING from the beginning")
    println("  ✅ Flow re-executes for each collector")
    println("  ✅ Timing doesn't matter - you always start fresh")
    println("")
    println("StateFlow (Hot):")
    println("  ⚠️  Gets CURRENT STATE immediately")
    println("  ⚠️  Misses historical state changes")
    println("  ✅ Then receives all future updates")
    println("  📌 Use for: UI state that has a \"current value\"")
    println("")
    println("SharedFlow (replay=0, Hot):")
    println("  ❌ Misses ALL past events")
    println("  ✅ Only receives future events while collecting")
    println("  📌 Use for: One-time events (navigation, clicks)")
    println("")
    println("SharedFlow (replay>0, Hot):")
    println("  ⚠️  Gets LAST N events from replay buffer")
    println("  ⚠️  Misses events beyond buffer")
    println("  ✅ Then receives all future events")
    println("  📌 Use for: Events where recent history matters")
    println("=" .repeat(60))
}
