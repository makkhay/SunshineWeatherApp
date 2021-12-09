package com.example.sunshineweatherapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.sunshineweatherapp.R
import com.example.sunshineweatherapp.databinding.WeatherRowLayoutBinding
import com.example.sunshineweatherapp.mapper.getWeatherIcon
import com.example.sunshineweatherapp.model.WeatherList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeatherAdapter(
    private val dataSet: List<WeatherList>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    //String ArrayList to store the days
    private val days = arrayListOf<String>()

    //Icon list
    private val icons = arrayListOf<Int>()

    init {
        // Get the next 9 days ( Sunday , monday...)
        val sdf = SimpleDateFormat("EEEE", Locale.US)
        for (i in 1..9) {
            val calendar: Calendar = GregorianCalendar()
            calendar.add(Calendar.DATE, i)
            days.add(sdf.format(calendar.time))
        }
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(
        private val binding: WeatherRowLayoutBinding,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: WeatherList, days: ArrayList<String>, icons: ArrayList<Int>) {
            binding.apply {

                binding.day.text = days[adapterPosition]
                binding.descriptionConditionTv.text = weather.weather[0].main

                binding.highTempTv.text = String.format(
                    binding.root.context.getString(R.string.max_temp_placeholder),
                    weather.temp.max.roundToInt().toString()
                )
                binding.lowTempTv.text = String.format(
                    binding.root.context.getString(R.string.min_temp_placeholder),
                    weather.temp.min.roundToInt().toString()
                )
                binding.humidityTv.text = String.format(
                    binding.root.context.getString(R.string.humidity_placeholder),
                    weather.humidity.toString()
                )
                binding.pressureTv.text = String.format(
                    binding.root.context.getString(R.string.pressure_placeholder),
                    weather.pressure.toString()
                )

                binding.windTv.text = String.format(
                    binding.root.context.getString(R.string.wind_placeholder),
                    weather.speed.toString()
                )
                binding.weatherIcon.setImageResource(icons[adapterPosition])

                binding.root.setOnClickListener {
                    onItemClickListener.onItemClicked(weather)
                    //hide or show views
                    if (binding.humidityTv.isVisible || binding.pressureTv.isVisible || binding.windTv.isVisible) {
                        binding.humidityTv.visibility = View.GONE
                        binding.pressureTv.visibility = View.GONE
                        binding.windTv.visibility = View.GONE
                    } else {
                        binding.humidityTv.visibility = View.VISIBLE
                        binding.pressureTv.visibility = View.VISIBLE
                        binding.windTv.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = WeatherRowLayoutBinding
            .inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding, onItemClickListener)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val weatherItem = dataSet[position]

        //populate icon arraylist based on icon string value
        dataSet.forEach {
            icons.add(getWeatherIcon(it.weather[0].icon))
        }

        //bind data
        viewHolder.bind(weatherItem, days, icons)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

interface OnItemClickListener {
    fun onItemClicked(weather: WeatherList)
}