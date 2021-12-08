package com.example.sunshineweatherapp.model

data class CurrentWeatherResponse(
    val base: String,
    val clouds: Clouds?,
    val cod: Int,
    val coord: Coord?,
    val dt: Int,
    val id: Int,
    val main: Main?,
    val name: String,
    val sys: Sys?,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind?
) {
    constructor() : this("", null, -1, null, -1, -1, null, "", null, -1, -1, emptyList(), null)

}

data class Wind(
    val deg: Int,
    val speed: Double
)

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)

data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)


data class Clouds(
    val all: Int
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)