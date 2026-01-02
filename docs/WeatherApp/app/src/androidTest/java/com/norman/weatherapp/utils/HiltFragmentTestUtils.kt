package com.norman.weatherapp.utils

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.core.util.Preconditions
import androidx.fragment.app.Fragment
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.norman.weatherapp.HiltTestActivity
import com.norman.weatherapp.R

/**
 * Utility functions for testing Hilt Fragments
 *
 * WHY THIS EXISTS:
 * - Hilt Fragments (@AndroidEntryPoint) must be attached to @AndroidEntryPoint Activities
 * - Standard launchFragmentInContainer() uses EmptyFragmentActivity (no @AndroidEntryPoint)
 * - This causes: "Hilt Fragments must be attached to an @AndroidEntryPoint Activity"
 *
 * SOLUTION:
 * - Use custom HiltTestActivity with @AndroidEntryPoint
 * - This helper launches fragments in that Hilt-enabled activity
 *
 * USAGE:
 * ```kotlin
 * launchFragmentInHiltContainer<MyFragment> {
 *     // Optional: interact with fragment
 * }
 * ```
 */

/**
 * Launch a Fragment in a Hilt-enabled container for testing
 *
 * @param T The Fragment class to launch
 * @param fragmentArgs Optional arguments to pass to the fragment
 * @param themeResId Theme resource ID to apply (defaults to app theme)
 * @param action Optional lambda to interact with the fragment after launch
 *
 * @return ActivityScenario for the HiltTestActivity containing the fragment
 */
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.Theme_WeatherApp,
    crossinline action: Fragment.() -> Unit = {}
): ActivityScenario<HiltTestActivity> {
    // Create intent to launch HiltTestActivity with theme
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    ).putExtra(
        "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
        themeResId
    )

    // Launch activity and attach fragment
    return ActivityScenario.launch<HiltTestActivity>(startActivityIntent).also { scenario ->
        scenario.onActivity { activity ->
            // Instantiate fragment
            val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
                Preconditions.checkNotNull(T::class.java.classLoader),
                T::class.java.name
            )

            // Set arguments if provided
            fragment.arguments = fragmentArgs

            // Add fragment to activity
            activity.supportFragmentManager
                .beginTransaction()
                .add(android.R.id.content, fragment, "")
                .commitNow()

            // Execute optional action on fragment
            fragment.action()
        }
    }
}

/**
 * NOTES:
 * - This function uses inline + reified to preserve Fragment type information at runtime
 * - The fragment is added to android.R.id.content (root view container)
 * - commitNow() ensures synchronous commit (important for immediate Espresso assertions)
 * - The scenario is returned so tests can interact with activity if needed
 */
