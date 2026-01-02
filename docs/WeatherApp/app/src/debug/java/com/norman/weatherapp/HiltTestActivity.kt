package com.norman.weatherapp

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Hilt Test Activity for Fragment Testing
 *
 * WHY WE NEED THIS:
 * - Hilt Fragments (@AndroidEntryPoint) must be attached to @AndroidEntryPoint Activities
 * - launchFragmentInContainer() uses EmptyFragmentActivity which doesn't have @AndroidEntryPoint
 * - This causes: "Hilt Fragments must be attached to an @AndroidEntryPoint Activity"
 *
 * SOLUTION:
 * - Create a simple Activity with @AndroidEntryPoint annotation
 * - Use this activity in fragment tests with launchFragmentInContainer
 *
 * NOTE: This file is in src/debug (not src/androidTest) so it's available at runtime
 * during tests but not included in release builds.
 */
@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity()
