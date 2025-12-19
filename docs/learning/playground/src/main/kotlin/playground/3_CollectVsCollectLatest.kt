package playground

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * COLLECT vs COLLECT LATEST
 *
 * collect: Processes every value (waits for each to finish)
 * collectLatest: Cancels previous, only processes latest
 */

suspend fun main() = coroutineScope {
    println("=" .repeat(60))
    println("COLLECT - Processes Every Value")
    println("=" .repeat(60))

    val fastFlow = flow {
        emit(1)
        delay(100)
        emit(2)
        delay(100)
        emit(3)
        delay(100)
        emit(4)
    }

    println("\n⚡ Fast emissions (100ms), slow processing (300ms)")
    println("   Using collect (waits for each):\n")

    var collectStartTime = System.currentTimeMillis()
    fastFlow.collect { value ->
        val elapsed = System.currentTimeMillis() - collectStartTime
        println("   [$elapsed ms] Started processing: $value")
        delay(300)  // Slow processing
        val doneElapsed = System.currentTimeMillis() - collectStartTime
        println("   [$doneElapsed ms] ✅ Done processing: $value")
    }

    println("\n   Result: All 4 values processed (took ~1200ms)")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("COLLECT LATEST - Cancels Previous, Takes Latest")
    println("=" .repeat(60))

    println("\n⚡ Fast emissions (100ms), slow processing (300ms)")
    println("   Using collectLatest (cancels slow ones):\n")

    var latestStartTime = System.currentTimeMillis()
    fastFlow.collectLatest { value ->
        val elapsed = System.currentTimeMillis() - latestStartTime
        println("   [$elapsed ms] Started processing: $value")
        try {
            delay(300)  // Slow processing
            val doneElapsed = System.currentTimeMillis() - latestStartTime
            println("   [$doneElapsed ms] ✅ Done processing: $value")
        } catch (e: CancellationException) {
            val cancelElapsed = System.currentTimeMillis() - latestStartTime
            println("   [$cancelElapsed ms] ❌ Cancelled: $value (new value arrived)")
        }
    }

    println("\n   Result: Only value 4 processed (earlier ones cancelled)")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("REAL-WORLD EXAMPLE: Search")
    println("=" .repeat(60))

    val searchQuery = MutableStateFlow("")

    // Simulate search with collectLatest
    launch {
        searchQuery.collectLatest { query ->
            if (query.isNotEmpty()) {
                println("\n🔍 Searching for: '$query'")
                delay(500)  // Simulate API call
                println("   ✅ Results for: '$query'")
            }
        }
    }

    delay(200)
    println("\n⌨️  User types 'L'")
    searchQuery.value = "L"

    delay(200)
    println("⌨️  User types 'Lo'")
    searchQuery.value = "Lo"

    delay(200)
    println("⌨️  User types 'Lon'")
    searchQuery.value = "Lon"

    delay(200)
    println("⌨️  User types 'Lond'")
    searchQuery.value = "Lond"

    delay(200)
    println("⌨️  User types 'London'")
    searchQuery.value = "London"

    delay(1000)  // Wait for search to complete

    println("\n   With collectLatest: Only searched 'London' (others cancelled)")
    println("   Without it: Would search L, Lo, Lon, Lond, London (wasteful!)")

    delay(500)

    println("\n" + "=" .repeat(60))
    println("WHEN TO USE EACH:")
    println("=" .repeat(60))
    println("collect:")
    println("  ✅ Every value matters (state changes, data updates)")
    println("  ✅ Can't skip processing")
    println("")
    println("collectLatest:")
    println("  ✅ Only latest matters (search, autocomplete)")
    println("  ✅ Can cancel outdated work")
    println("  ✅ User typing, rapid updates")
    println("=" .repeat(60))
}
