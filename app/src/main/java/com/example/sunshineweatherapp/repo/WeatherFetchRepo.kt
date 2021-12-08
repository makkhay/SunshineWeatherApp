package com.example.sunshineweatherapp.repo

import com.example.sunshineweatherapp.model.CurrentWeatherResponse
import com.example.sunshineweatherapp.model.WeatherResponse
import com.example.sunshineweatherapp.util.WeatherFetchResult


/**
 *  Repo which has access to remote data fetching services
 */
interface WeatherFetchRepo {
    /**
     * takes user input and fetches data from the help service and outputs the data in list
     */
    suspend fun fetchMultipleDaysWeather(
        location: String,
        mode: String,
        dayCount: Int,
        units: String,
        apiKey: String
    ): WeatherFetchResult<WeatherResponse>

    /**
     * takes user input and fetches data from the help service and outputs the data in list
     */
    suspend fun fetchSingleDaysWeather(
        location: String,
        units: String,
        apiKey: String
    ): WeatherFetchResult<CurrentWeatherResponse>
}