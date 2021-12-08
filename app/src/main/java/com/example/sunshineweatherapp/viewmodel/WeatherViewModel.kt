package com.example.sunshineweatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunshineweatherapp.model.CurrentWeatherResponse
import com.example.sunshineweatherapp.model.WeatherList
import com.example.sunshineweatherapp.model.WeatherResponse
import com.example.sunshineweatherapp.repo.WeatherFetchRepo
import com.example.sunshineweatherapp.util.WeatherFetchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor( private  val repository : WeatherFetchRepo): ViewModel() {

    companion object {
        private const val TAG = "WeatherViewModel"
    }

    // MutableLiveData to store the response coming from the server.
    var multipleDaysWeatherData = MutableLiveData<List<WeatherList>>()
    var singleDayWeatherData = MutableLiveData<CurrentWeatherResponse>()

    /**
     * Fetches multiple days ( 10 days)  weather forecast for the given location
     *
     * @param: location of the desired place to search for. The default location is set to Atlanta
     * @param : mode of the desired data format
     * @param : dayCount ; a number of days , which will be returned in the API response
     * @units: units of measurement, set to imperial
     * @apiKey: apiKey
     */
    fun fetchWeatherForecast(location: String, mode: String, dayCount : Int, units: String, apiKey : String) {
        viewModelScope.launch {
            repository.fetchMultipleDaysWeather(location, mode, dayCount, units, apiKey).let { weatherResult ->
                when (weatherResult) {
                    is WeatherFetchResult.Success -> {
                        val weatherList : ArrayList<WeatherList> = weatherResult.data.list as ArrayList<WeatherList>
                        //remove first item from the weather list data
                        weatherList.removeAt(0)
                        multipleDaysWeatherData.postValue(weatherList)
                    }
                    is WeatherFetchResult.Error -> {
                        Log.d(TAG, "fetchWeatherForecast: Error ....")
                    }
                }
            }
        }
    }

    /**
     * Fetches today's weather forecast for the given location
     *
     * @param: location of the desired place to search for. The default location is set to Atlanta
     * @units: units of measurement, set to imperial
     * @apiKey: apiKey
     */
    fun fetchCurrentWeatherForecast(location: String, units : String, apiKey: String){
        viewModelScope.launch {
            repository.fetchSingleDaysWeather(location, units, apiKey).let { weatherResult ->
                when (weatherResult) {
                    is WeatherFetchResult.Success -> {
                        singleDayWeatherData.postValue(weatherResult.data!!)
                    }
                    is WeatherFetchResult.Error -> {
                        Log.d(TAG, "fetchWeatherForecast: Error ....")
                    }
                }
            }
        }
    }

}