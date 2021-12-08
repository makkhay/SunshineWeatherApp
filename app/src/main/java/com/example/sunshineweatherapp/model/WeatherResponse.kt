package com.example.sunshineweatherapp.model

data class WeatherResponse(
    val city: City?,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherList>,
    val message: Double
) {
    constructor() : this(null, -1, "", emptyList(), - 1.0)
}

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)