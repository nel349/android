package playground

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * COLD FLOW vs HOT FLOW (StateFlow)
 *
 * Run this file to see the difference!
 *
 * HOW TO RUN:
 * 1. Open in IntelliJ IDEA or Android Studio
 * 2. Right-click → Run 'MainKt'
 * OR from terminal:
 * kotlinc 1_ColdFlowVsHotFlow.kt -include-runtime -d cold-flow.jar && java -jar cold-flow.jar
 */

// ========== COLD FLOW ==========
fun coldFlow(): Flow<Int> = flow {
    println("🧊 COLD Flow started (new stream for each collector)")
    emit(1)
    delay(500)
    emit(2)
    delay(500)
    emit(3)
}

// ========== HOT FLOW (StateFlow) ==========
val hotFlow = MutableStateFlow(0).apply {
    println("🔥 HOT Flow (StateFlow) created with initial value: $value")
}

suspend fun main() = coroutineScope {
    println("=" .repeat(60))
    println("COLD FLOW DEMO - Each collector gets independent stream")
    println("=" .repeat(60))

    // Collector 1
    launch {
        println("\n👤 Collector 1 starting...")
        coldFlow().collect { value ->
            println("   Collector 1 received: $value")
        }
        println("   Collector 1 done")
    }

    delay(1000) // Wait a bit

    // Collector 2 (starts its OWN stream)
    launch {
        println("\n👤 Collector 2 starting...")
        coldFlow().collect { value ->
            println("   Collector 2 received: $value")
        }
        println("   Collector 2 done")
    }

    delay(3000) // Wait for cold flows to finish

    println("\n" + "=" .repeat(60))
    println("HOT FLOW (StateFlow) DEMO - Shared stream")
    println("=" .repeat(60))

    // Collector 1
    launch {
        println("\n👤 Collector 1 collecting StateFlow...")
        hotFlow.take(4).collect { value ->
            println("   Collector 1 received: $value")
        }
    }

    delay(500)

    // Collector 2 joins the ongoing stream
    launch {
        println("\n👤 Collector 2 collecting StateFlow...")
        hotFlow.take(4).collect { value ->
            println("   Collector 2 received: $value")
        }
    }

    delay(500)

    // Emit new values (both collectors receive them)
    println("\n📢 Emitting value 1")
    hotFlow.value = 1
    delay(500)

    println("📢 Emitting value 2")
    hotFlow.value = 2
    delay(500)

    println("📢 Emitting value 3")
    hotFlow.value = 3
    delay(500)

    println("\n" + "=" .repeat(60))
    println("KEY DIFFERENCES:")
    println("=" .repeat(60))
    println("COLD Flow:")
    println("  ✅ Each collector gets its own stream")
    println("  ✅ Starts producing when someone collects")
    println("  ✅ Like Netflix - starts when you press play")
    println("")
    println("HOT Flow (StateFlow):")
    println("  ✅ Shared stream - all collectors get same values")
    println("  ✅ Always active, always has a value")
    println("  ✅ Like live TV - join broadcast in progress")
    println("=" .repeat(60))
}
