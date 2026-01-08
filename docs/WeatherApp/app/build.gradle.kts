import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)  // Compose Compiler (required for Kotlin 2.0+)
    alias(libs.plugins.google.ksp)  // KSP for Room and Hilt
    alias(libs.plugins.google.hilt)  // Hilt for Dependency Injection
    id("androidx.navigation.safeargs.kotlin") version "2.7.6"  // SafeArgs for type-safe navigation
}

// Load local.properties
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.norman.weatherapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.norman.weatherapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.norman.weatherapp.HiltTestRunner"

        // Build config field for API key from local.properties
        buildConfigField(
            "String",
            "WEATHER_API_KEY",
            "\"${localProperties.getProperty("WEATHER_API_KEY", "")}\""
        )
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true  // Enable Jetpack Compose
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    testOptions {
        unitTests.isReturnDefaultValues = true  // Mock Android framework classes (Log, etc.)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coroutines for async operations
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    // Lifecycle components (for coroutines in Activity)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-process:2.7.0")  // For ProcessLifecycleOwner

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.8.2")

    // Room Database
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")  // Coroutines support
    ksp("androidx.room:room-compiler:$roomVersion")       // Annotation processor (KSP)

    // DataStore (modern replacement for SharedPreferences)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Navigation Component
    val navVersion = "2.7.6"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Hilt - Dependency Injection
    implementation("com.google.dagger:hilt-android:2.51")
    ksp("com.google.dagger:hilt-compiler:2.51")  // Hilt code generation (uses KSP - modern!)

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2024.02.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Compose - Core
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Compose - Lifecycle integration
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Compose - Activity integration
    implementation("androidx.activity:activity-compose:1.8.2")

    // Compose - Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Compose - Debug tools
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Testing dependencies
    testImplementation(libs.junit)

    // MockK - Kotlin-friendly mocking library
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("io.mockk:mockk-android:1.13.8")

    // Turbine - Flow testing library
    testImplementation("app.cash.turbine:turbine:1.0.0")

    // Coroutines Test - for testing suspend functions
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Robolectric - Android unit tests without emulator
    testImplementation("org.robolectric:robolectric:4.11.1")

    // AndroidX Test - Core testing utilities
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("androidx.test:core-ktx:1.5.0")
    testImplementation("androidx.test.ext:junit:1.1.5")

    // Arch Core Testing - InstantTaskExecutorRule for LiveData/ViewModel
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    // Hilt Testing (for unit tests)
    testImplementation("com.google.dagger:hilt-android-testing:2.51")
    kspTest("com.google.dagger:hilt-compiler:2.51")

    // Instrumented testing dependencies
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // MockK for instrumented tests
    androidTestImplementation("io.mockk:mockk-android:1.13.8") {
        exclude(group = "org.junit.jupiter")
    }

    // Fragment testing
    debugImplementation("androidx.fragment:fragment-testing:1.6.2") {
        exclude(group = "org.junit.jupiter")
    }
    debugImplementation("androidx.fragment:fragment-testing-manifest:1.6.2")

    // Navigation testing
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.6") {
        exclude(group = "org.junit.jupiter")
    }

    // Espresso Contrib (for RecyclerView testing)
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1") {
        exclude(group = "org.junit.jupiter")
    }

    // Coroutines test for instrumented tests
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") {
        exclude(group = "org.junit.jupiter")
    }

    // UIAutomator (for programmatic animation control - optional)
    androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0") {
        exclude(group = "org.junit.jupiter")
    }

    // Hilt Testing (for instrumented tests)
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51") {
        exclude(group = "org.junit.jupiter")
    }
    kspAndroidTest("com.google.dagger:hilt-compiler:2.51")
}