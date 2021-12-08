package com.example.sunshineweatherapp.repo

import com.example.sunshineweatherapp.model.CurrentWeatherResponse
import com.example.sunshineweatherapp.model.WeatherList
import com.example.sunshineweatherapp.model.WeatherResponse
import com.example.sunshineweatherapp.service.WeatherFetchApi
import com.example.sunshineweatherapp.util.Constants
import com.example.sunshineweatherapp.util.WeatherFetchException
import com.example.sunshineweatherapp.util.WeatherFetchResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

/**
 * WeatherFetchRepoImpl class which has access to remote data fetching services
 */
class WeatherFetchRepoImpl @Inject constructor(private val api: WeatherFetchApi) : WeatherFetchRepo {

    companion object {
        private const val TAG = "WeatherFetchRepoImpl"
    }

    override suspend fun fetchMultipleDaysWeather(
        location: String,
        mode: String,
        dayCount: Int,
        units: String,
        apiKey: String
    ): WeatherFetchResult<WeatherResponse> {
        val weatherFetchResponse: WeatherResponse

        // fetch weather data from the openweather server with our location, mode, dayCount , and units
        val weatherDiffered = CoroutineScope(Dispatchers.IO).async {
            api.getMultipleDaysWeather(location, mode, dayCount, units, apiKey)
        }

        // accessing the deferred value from the async call by calling await
        val multipleWeatherResult = weatherDiffered.await()
        if (multipleWeatherResult.isSuccessful) {
            weatherFetchResponse = multipleWeatherResult.body()!!
        }else{
            return WeatherFetchResult.Error(WeatherFetchException("Error making your request!!"))
        }

        return WeatherFetchResult.Success(weatherFetchResponse)
    }

    override suspend fun fetchSingleDaysWeather(
        location: String,
        units: String,
        apiKey: String
    ): WeatherFetchResult<CurrentWeatherResponse> {
        val currentWeatherResponse: CurrentWeatherResponse

        // fetch weather data from the openweather server with our location and units
        val weatherDiffered = CoroutineScope(Dispatchers.IO).async {
            api.getCurrentWeather(location, units, apiKey)
        }

        // accessing the deferred value from the async call by calling await
        val singleDayWeatherResult = weatherDiffered.await()
        if (singleDayWeatherResult.isSuccessful) {
            currentWeatherResponse = singleDayWeatherResult.body()!!
        }else{
            return WeatherFetchResult.Error(WeatherFetchException("Error making your request!!"))
        }

        return WeatherFetchResult.Success(currentWeatherResponse)
    }

}