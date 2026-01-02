package com.norman.weatherapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * JUnit rule to disable animations before tests run
 *
 * NOTE: This requires WRITE_SECURE_SETTINGS permission which is hard to grant.
 * It's MUCH EASIER to just disable animations manually in Developer Options!
 *
 * To use this rule, you'd need to run:
 * adb shell pm grant com.norman.weatherapp android.permission.WRITE_SECURE_SETTINGS
 *
 * But honestly, just disable animations manually. It's simpler.
 */
class DisableAnimationsRule : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

                // Disable animations
                // NOTE: This requires WRITE_SECURE_SETTINGS permission
                try {
                    device.executeShellCommand("settings put global window_animation_scale 0")
                    device.executeShellCommand("settings put global transition_animation_scale 0")
                    device.executeShellCommand("settings put global animator_duration_scale 0")
                } catch (e: Exception) {
                    // Permission denied - just log and continue
                    // Tests will warn about animations but may still pass
                    println("Could not disable animations programmatically: ${e.message}")
                    println("Please disable animations manually in Developer Options")
                }

                try {
                    base.evaluate()
                } finally {
                    // Re-enable animations after test (optional - comment out if you want them off)
                    try {
                        device.executeShellCommand("settings put global window_animation_scale 1")
                        device.executeShellCommand("settings put global transition_animation_scale 1")
                        device.executeShellCommand("settings put global animator_duration_scale 1")
                    } catch (e: Exception) {
                        // Ignore
                    }
                }
            }
        }
    }
}
