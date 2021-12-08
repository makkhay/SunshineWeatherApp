package com.example.sunshineweatherapp.util


/**
 * A custom class to show/determine the status of our api request
 */
sealed class WeatherFetchResult<out R> {
    data class Success<out T>(val data: T) : WeatherFetchResult<T>()
    data class Error(val exception: WeatherFetchException) : WeatherFetchResult<Nothing>()
}
// a class to display the exception message
data class WeatherFetchException(val errorMessage: String) : Exception()