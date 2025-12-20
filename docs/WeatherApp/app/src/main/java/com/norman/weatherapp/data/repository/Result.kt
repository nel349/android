package com.norman.weatherapp.data.repository

/**
 * Sealed class for representing operation results
 * Sealed classes = Limited set of subclasses (like enum but with data)
 */
sealed class Result<out T> {
    /**
     * Success state with data
     * 'data class' inside sealed class
     */
    data class Success<T>(val data: T) : Result<T>()

    /**
     * Error state with message
     */
    data class Error(val message: String) : Result<Nothing>()

    /**
     * Loading state (operation in progress)
     */
    data object Loading : Result<Nothing>()

    /**
     * Idle state (no operation started yet)
     */
    data object Idle : Result<Nothing>()
}
