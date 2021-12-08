package com.example.sunshineweatherapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.sunshineweatherapp.R
import com.example.sunshineweatherapp.databinding.FragmentFirstBinding
import com.example.sunshineweatherapp.mapper.getWeatherIcon
import com.example.sunshineweatherapp.model.WeatherList
import com.example.sunshineweatherapp.model.WeatherX
import com.example.sunshineweatherapp.util.Constants
import com.example.sunshineweatherapp.view.adapter.OnItemClickListener
import com.example.sunshineweatherapp.view.adapter.WeatherAdapter
import com.example.sunshineweatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment(), OnItemClickListener {

    companion object {
        private const val TAG = "FirstFragment"
    }

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private val viewModel : WeatherViewModel by activityViewModels()

    private lateinit var adapter : WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchCurrentWeatherForecast(Constants.SEARCH_QUERY,Constants.IMPERIAL_UNIT, Constants.API_KEY)
        viewModel.fetchWeatherForecast(Constants.SEARCH_QUERY, Constants.JSON_DATA_MODE, 10, Constants.IMPERIAL_UNIT, Constants.API_KEY)

        //observe the muiltipledaysweather data and set the adapter to our recyclerview
        viewModel.multipleDaysWeatherData.observe(viewLifecycleOwner, {
            adapter = WeatherAdapter(it, this)
            binding.weatherRecyclerView.adapter = adapter
        })

        //observe the singleDayWeatherData data and use it to set the weather data
        viewModel.singleDayWeatherData.observe(viewLifecycleOwner, {
            binding.locationTv.text = String.format(
                binding.root.context.getString(R.string.location_placeholder),
                it.name
            )
            binding.descriptionTv.text = it.weather[0].description
            binding.temperatureTv.text = String.format(
                binding.root.context.getString(R.string.current_temp_placeholder),
                it.main?.temp?.roundToInt().toString()
            )
            binding.feelslikeTv.text = String.format(
                binding.root.context.getString(R.string.feelslike_temp_placeholder),
                it.main?.feels_like?.roundToInt().toString()
            )
            binding.currentWeatherIcon.setImageResource(getWeatherIcon(it.weather[0].icon))
        })

    }

    /**
     * RecyclerView onItem click listener callback function
     *
     * @weather : clicked item WeatherList
     */
    override fun onItemClicked(weather: WeatherList) {}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}