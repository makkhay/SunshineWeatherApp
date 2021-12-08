package com.example.sunshineweatherapp.di

import com.example.sunshineweatherapp.repo.WeatherFetchRepo
import com.example.sunshineweatherapp.repo.WeatherFetchRepoImpl
import com.example.sunshineweatherapp.service.WeatherFetchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherFetchModule {

    @Singleton
    @Provides
    fun provideSearchApi(retrofit: Retrofit): WeatherFetchApi {
        return retrofit.create(WeatherFetchApi::class.java)
    }

    @Singleton
    @Provides
    fun provideYelpSearchRepository(api: WeatherFetchApi): WeatherFetchRepo {
        return WeatherFetchRepoImpl(api)
    }

}