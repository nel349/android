package com.norman.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.norman.weatherapp.data.preferences.PreferencesRepository
import com.norman.weatherapp.data.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SettingsViewModel - Manages settings state (STATE HOISTING pattern)
 *
 * STATE HOISTING CONCEPT:
 * - BEFORE: AboutScreen owned state with remember { mutableStateOf() }
 * - AFTER: ViewModel owns state, screen just displays it
 * - Benefits: State survives configuration changes, easier to test
 *
 * UNIDIRECTIONAL DATA FLOW:
 * 1. Screen displays state from ViewModel
 * 2. User interacts (clicks toggle)
 * 3. Screen calls ViewModel function
 * 4. ViewModel updates state
 * 5. New state flows back to Screen
 * 6. Screen recomposes with new state
 *
 * WHY STATEFLOW:
 * - StateFlow always has a current value (unlike regular Flow)
 * - Perfect for UI state (UI always needs current state)
 * - Hot flow (stays active, caches latest value)
 *
 * STATEFLOW vs FLOW:
 * - Flow = cold (starts when collected, no cached value)
 * - StateFlow = hot (always active, has current value)
 * - StateFlow perfect for UI state, Flow perfect for one-time operations
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    /**
     * User preferences as StateFlow
     *
     * STATEFLOW CREATION:
     * - preferencesRepository.userPreferences is Flow<UserPreferences>
     * - stateIn() converts Flow -> StateFlow
     * - SharingStarted.WhileSubscribed(5000) = keep active for 5 seconds after last collector
     * - initialValue = UserPreferences() = default while loading
     *
     * WHY STATEFLOW:
     * - Compose needs current value immediately (can't wait for Flow emission)
     * - StateFlow.value gives current state
     * - collectAsStateWithLifecycle() in Compose converts StateFlow -> State
     */
    val userPreferences: StateFlow<UserPreferences> = preferencesRepository.userPreferences
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserPreferences()
        )

    /**
     * Update dark mode (called from UI)
     *
     * STATE HOISTING IN ACTION:
     * - UI calls this function (event goes UP)
     * - ViewModel updates repository
     * - Repository updates DataStore
     * - DataStore emits new value through Flow
     * - Flow becomes StateFlow
     * - New state flows DOWN to UI
     * - UI recomposes
     */
    fun updateDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            preferencesRepository.updateDarkMode(isDarkMode)
        }
    }

    /**
     * Update temperature unit (called from UI)
     */
    fun updateTemperatureUnit(isCelsius: Boolean) {
        viewModelScope.launch {
            preferencesRepository.updateTemperatureUnit(isCelsius)
        }
    }
}
