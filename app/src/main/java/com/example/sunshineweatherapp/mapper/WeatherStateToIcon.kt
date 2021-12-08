package com.example.sunshineweatherapp.mapper

import com.example.sunshineweatherapp.R

/**
 * A function to determine which drawable icon to use based on iconState
 *
 * @param : iconsState string to determine which drawable icon to return
 */
fun getWeatherIcon(iconState: String): Int {
    return when(iconState){
        "11d" -> R.drawable.ic_weather_stormy
        "09d" -> R.drawable.ic_weather_rainy
        "10d" -> R.drawable.ic_weather_rainy_2
        "13d" -> R.drawable.ic_weather_rainy_2
        "50d" -> R.drawable.ic_weather_sunny
        "01d" -> R.drawable.ic_weather_sunny
        "01n" -> R.drawable.ic_weather_moon
        "02d" -> R.drawable.ic_weather_cloudy
        "02n" -> R.drawable.ic_weather_cloudy_night
        else -> {
            R.drawable.ic_default_weather
        }
    }
}