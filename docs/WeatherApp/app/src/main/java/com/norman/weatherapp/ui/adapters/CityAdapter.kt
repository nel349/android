package com.norman.weatherapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norman.weatherapp.data.local.WeatherEntity
import com.norman.weatherapp.databinding.ItemCityBinding

/**
 * RecyclerView Adapter for City List
 *
 * RECYCLERVIEW CONCEPTS:
 * - RecyclerView = Efficient list/grid widget (only renders visible items)
 * - Adapter = Binds data to views
 * - ViewHolder = Holds view references (performance optimization)
 * - ListAdapter = Smart adapter that calculates diffs automatically
 * - DiffUtil = Calculates minimal changes between lists (smooth animations)
 *
 * WHY ListAdapter over RecyclerView.Adapter?
 * - Automatic diff calculation
 * - Smooth animations when list changes
 * - Less boilerplate
 *
 * TEMPERATURE UNIT SUPPORT:
 * - Adapter now accepts isCelsius parameter
 * - When user changes preference, fragment updates adapter and calls notifyDataSetChanged()
 */
class CityAdapter(
    private val onCityClick: (WeatherEntity) -> Unit,  // Callback when city clicked
    private var isCelsius: Boolean = true             // Temperature unit preference
) : ListAdapter<WeatherEntity, CityAdapter.CityViewHolder>(CityDiffCallback()) {

    /**
     * Update temperature unit preference and refresh display
     */
    fun updateTemperatureUnit(isCelsius: Boolean) {
        this.isCelsius = isCelsius
        notifyDataSetChanged()  // Refresh all visible items
    }

    /**
     * ViewHolder - Holds references to views in item layout
     * Created once per visible item, then reused as you scroll
     */
    class CityViewHolder(
        private val binding: ItemCityBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind data to views
         * Called for each visible item
         */
        fun bind(city: WeatherEntity, onCityClick: (WeatherEntity) -> Unit, isCelsius: Boolean) {
            binding.cityNameText.text = city.cityName

            // Format temperature based on user preference
            binding.temperatureText.text = if (isCelsius) {
                "${city.temperatureCelsius.toInt()}°C"
            } else {
                val fahrenheit = (city.temperatureCelsius * 9 / 5) + 32
                "${fahrenheit.toInt()}°F"
            }

            binding.descriptionText.text = city.description.replaceFirstChar { it.uppercase() }

            // Handle item click
            binding.root.setOnClickListener {
                onCityClick(city)
            }
        }
    }

    /**
     * Create ViewHolder
     * Called when RecyclerView needs a new ViewHolder (not enough to reuse)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityViewHolder(binding)
    }

    /**
     * Bind data to ViewHolder
     * Called for each visible item as you scroll
     */
    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position)  // getItem() from ListAdapter
        holder.bind(city, onCityClick, isCelsius)
    }

    /**
     * DiffUtil.ItemCallback - Calculates differences between lists
     *
     * WHY?
     * - When list changes, RecyclerView needs to know what changed
     * - DiffUtil calculates minimal diff (what to add/remove/move)
     * - Results in smooth animations
     *
     * METHODS:
     * - areItemsTheSame: Same item? (check ID/primary key)
     * - areContentsTheSame: Same data? (check all fields)
     */
    private class CityDiffCallback : DiffUtil.ItemCallback<WeatherEntity>() {
        override fun areItemsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
            // Same city? (compare primary key)
            return oldItem.cityName == newItem.cityName
        }

        override fun areContentsTheSame(oldItem: WeatherEntity, newItem: WeatherEntity): Boolean {
            // Same data? (compare all fields)
            return oldItem == newItem
        }
    }
}
