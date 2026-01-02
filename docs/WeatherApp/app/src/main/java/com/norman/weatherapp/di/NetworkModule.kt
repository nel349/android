package com.norman.weatherapp.di

import com.norman.weatherapp.data.api.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * NetworkModule - Tells Hilt how to provide Retrofit dependencies
 *
 * WHY SEPARATE MODULE?
 * - Separation of concerns (database vs network)
 * - Easier to test (can replace NetworkModule with fake)
 * - Cleaner code organization
 */
@Module
@InstallIn(SingletonComponent::class)  // App-wide singleton
object NetworkModule {

    /**
     * Provides Retrofit instance
     *
     * @Singleton:
     * - One Retrofit instance for entire app
     * - Reuses HTTP client, connection pool
     * - Memory efficient
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides WeatherApiService
     *
     * Dependency chain:
     * Repository needs WeatherApiService
     *   → WeatherApiService needs Retrofit
     *     → Hilt calls provideRetrofit()
     *       → Hilt calls retrofit.create()
     *         → Returns WeatherApiService
     *
     * Hilt manages it all automatically!
     */
    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }
}
