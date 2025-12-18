package com.norman.weatherapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object to provide Retrofit instance
 * 'object' in Kotlin = singleton (only one instance exists)
 */
object RetrofitInstance {

    /**
     * Lazy initialization - created only when first accessed
     * 'by lazy' = computed once, then cached
     */
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(WeatherApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // JSON converter
            .build()
    }

    /**
     * API service instance
     * Retrofit generates implementation of WeatherApiService interface
     */
    val api: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}
