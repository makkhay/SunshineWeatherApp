package com.example.sunshineweatherapp.service

import com.example.sunshineweatherapp.model.CurrentWeatherResponse
import com.example.sunshineweatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The WeatherFetchApi interface which will contain the semantics of all the REST calls.
 */
interface WeatherFetchApi {

    /**
     * Get current weather of city
     * @param q String city
     * @param units String units
     * @param apikey String apiKey
     * @return instance of [Response<CurrentWeatherResponse>]
     */

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("apikey") apiKey: String
    ): Response<CurrentWeatherResponse>

    /**
     * Get multiple days weather
     *
     * @param q String city
     * @param mode String mode of response
     * @param cnt  Int dayCount
     * @param units String units
     * @param apikey String apiKey
     * @return instance of [ Response<WeatherResponse>]
     */
    @GET("forecast/daily")
    suspend fun getMultipleDaysWeather(
        @Query("q") city: String,
        @Query("mode") mode: String,
        @Query("cnt") dayCount: Int,
        @Query("units") units: String?,
        @Query("apikey") apiKey: String?
    ): Response<WeatherResponse>


}