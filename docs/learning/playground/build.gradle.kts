plugins {
    kotlin("jvm") version "2.3.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
}

kotlin {
    jvmToolchain(17)
}

// Configure which example to run
// Change the mainClass to run different examples
application {
    mainClass.set("playground.MainKt")
}

// Task to run each example individually
tasks.register<JavaExec>("run1") {
    group = "examples"
    description = "Run 1_ColdFlowVsHotFlow.kt"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("playground._1_ColdFlowVsHotFlowKt")
}

tasks.register<JavaExec>("run2") {
    group = "examples"
    description = "Run 2_SharedFlowExample.kt"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("playground._2_SharedFlowExampleKt")
}

tasks.register<JavaExec>("run3") {
    group = "examples"
    description = "Run 3_CollectVsCollectLatest.kt"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("playground._3_CollectVsCollectLatestKt")
}

tasks.register<JavaExec>("run4") {
    group = "examples"
    description = "Run 4_AsyncParallelExecution.kt"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("playground._4_AsyncParallelExecutionKt")
}

tasks.register<JavaExec>("run5") {
    group = "examples"
    description = "Run 5_CoroutineScopeComparison.kt"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("playground._5_CoroutineScopeComparisonKt")
}

tasks.register<JavaExec>("run6") {
    group = "examples"
    description = "Run 6_TimingDemonstration.kt"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("playground._6_TimingDemonstrationKt")
}
