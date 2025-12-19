package playground

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * ASYNC - Parallel Execution
 *
 * launch: Fire and forget (returns Job)
 * async: Get result back (returns Deferred<T>)
 */

// Simulate slow API calls
suspend fun fetchUser(): String {
    println("   🌐 Fetching user...")
    delay(1000)  // 1 second
    println("   ✅ User fetched!")
    return "User{name=John}"
}

suspend fun fetchPosts(): String {
    println("   🌐 Fetching posts...")
    delay(1000)  // 1 second
    println("   ✅ Posts fetched!")
    return "Posts[10]"
}

suspend fun fetchComments(): String {
    println("   🌐 Fetching comments...")
    delay(1000)  // 1 second
    println("   ✅ Comments fetched!")
    return "Comments[25]"
}

suspend fun main() = coroutineScope {
    println("=" .repeat(60))
    println("SEQUENTIAL EXECUTION - Slow (waits for each)")
    println("=" .repeat(60))

    val sequentialTime = measureTimeMillis {
        println("\n📥 Loading dashboard sequentially...\n")

        val user = fetchUser()          // Wait 1 second
        val posts = fetchPosts()        // Wait 1 second
        val comments = fetchComments()  // Wait 1 second

        println("\n📊 Dashboard loaded:")
        println("   $user")
        println("   $posts")
        println("   $comments")
    }

    println("\n⏱️  Total time: ${sequentialTime}ms (~3000ms)")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("PARALLEL EXECUTION - Fast (all at once)")
    println("=" .repeat(60))

    val parallelTime = measureTimeMillis {
        println("\n📥 Loading dashboard in parallel...\n")

        // Start all 3 requests at the same time!
        val userDeferred = async { fetchUser() }
        val postsDeferred = async { fetchPosts() }
        val commentsDeferred = async { fetchComments() }

        // Wait for all results
        val user = userDeferred.await()
        val posts = postsDeferred.await()
        val comments = commentsDeferred.await()

        println("\n📊 Dashboard loaded:")
        println("   $user")
        println("   $posts")
        println("   $comments")
    }

    println("\n⏱️  Total time: ${parallelTime}ms (~1000ms)")
    println("\n🚀 Speed improvement: ${sequentialTime / parallelTime}x faster!")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("LAUNCH vs ASYNC")
    println("=" .repeat(60))

    println("\n🔹 launch - Fire and forget (no result)")
    val job = launch {
        delay(500)
        println("   Task completed (but can't return value)")
    }
    job.join()  // Wait for it to finish
    // val result = job.???  ❌ Can't get result!

    delay(500)

    println("\n🔹 async - Get result back")
    val deferred = async {
        delay(500)
        "Task result!"  // Returns value
    }
    val result = deferred.await()  // ✅ Get result!
    println("   Received: $result")

    delay(1000)

    println("\n" + "=" .repeat(60))
    println("REAL-WORLD EXAMPLE: Weather Dashboard")
    println("=" .repeat(60))

    println("\n📥 Loading weather, news, stocks in parallel...\n")

    val dashboardTime = measureTimeMillis {
        coroutineScope {
            val weatherJob = async {
                delay(800)
                "☀️  22°C"
            }

            val newsJob = async {
                delay(600)
                "📰 10 new articles"
            }

            val stocksJob = async {
                delay(900)
                "📈 +2.5%"
            }

            // All running in parallel!
            val weather = weatherJob.await()
            val news = newsJob.await()
            val stocks = stocksJob.await()

            println("📊 Dashboard:")
            println("   Weather: $weather")
            println("   News: $news")
            println("   Stocks: $stocks")
        }
    }

    println("\n⏱️  Loaded in: ${dashboardTime}ms (max of all, not sum!)")

    delay(500)

    println("\n" + "=" .repeat(60))
    println("KEY TAKEAWAYS:")
    println("=" .repeat(60))
    println("launch:")
    println("  ✅ Fire and forget")
    println("  ✅ Returns Job (no result)")
    println("  ✅ Use for: Background tasks, logging, analytics")
    println("")
    println("async:")
    println("  ✅ Get result back")
    println("  ✅ Returns Deferred<T>")
    println("  ✅ Use for: Parallel data fetching")
    println("")
    println("Sequential: Tasks run one after another (slow)")
    println("Parallel: Tasks run simultaneously (fast)")
    println("=" .repeat(60))
}
