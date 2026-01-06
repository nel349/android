package com.norman.weatherapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.norman.weatherapp.R
import com.norman.weatherapp.databinding.FragmentCityListBinding
import com.norman.weatherapp.ui.activities.ComposeActivity
import com.norman.weatherapp.ui.adapters.CityAdapter
import com.norman.weatherapp.ui.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * CityListFragment - Shows all cached cities
 *
 * FRAGMENT CONCEPTS:
 * - Fragment = Reusable UI component (like mini-Activity)
 * - Has its own lifecycle (onCreate, onCreateView, onViewCreated, etc.)
 * - Can be added/removed/replaced dynamically
 * - Lives inside an Activity
 *
 * VIEWMODEL SHARING:
 * - activityViewModels() = Shares ViewModel with Activity + other Fragments
 * - Allows fragments to communicate via shared ViewModel
 * - ViewModel survives fragment recreation
 *
 * WITH HILT:
 * - @AndroidEntryPoint enables Hilt in this Fragment
 * - activityViewModels() now gets Hilt-injected ViewModel
 */
@AndroidEntryPoint
class CityListFragment : Fragment() {

    private var _binding: FragmentCityListBinding? = null
    private val binding get() = _binding!!  // Non-null accessor

    // Shared ViewModel (shared with Activity and other fragments)
    private val viewModel: WeatherViewModel by activityViewModels()

    private lateinit var cityAdapter: CityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupMenu()
        setupRecyclerView()
        observeCachedCities()
        setupFabClick()
    }

    /**
     * Setup toolbar as the ActionBar
     *
     * CONCEPT:
     * - MaterialToolbar in fragment needs to be set as ActionBar
     * - This allows the menu (overflow dots) to appear
     * - setSupportActionBar() tells the Activity to use this toolbar
     */
    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    /**
     * Setup options menu (modern way with MenuProvider)
     *
     * XML → Compose navigation:
     * Menu click → Intent → ComposeActivity → AboutScreen
     */
    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.city_list_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_about -> {
                        // Navigate to Compose screen via Intent
                        val intent = Intent(requireContext(), ComposeActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    /**
     * Setup RecyclerView with adapter
     */
    private fun setupRecyclerView() {
        // Create adapter with click listener
        cityAdapter = CityAdapter { city ->
            // Navigate to WeatherDetailFragment when city clicked
            val action = CityListFragmentDirections
                .actionCityListToWeatherDetail(city.cityName)
            findNavController().navigate(action)
        }

        // Set adapter to RecyclerView
        binding.cityRecyclerView.adapter = cityAdapter
    }

    /**
     * Observe cached cities from Room database
     * Updates RecyclerView automatically when data changes
     *
     * FLOW COLLECTION:
     * - viewModel.cachedCities is a Flow from Room
     * - Automatically emits new list when database changes
     * - collect {} runs forever (until viewLifecycleOwner destroyed)
     */
    private fun observeCachedCities() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cachedCities.collect { cities ->
                if (cities.isEmpty()) {
                    showEmptyState()
                } else {
                    showCityList()
                    cityAdapter.submitList(cities)  // Update RecyclerView
                }
            }
        }
    }

    /**
     * Handle FAB click - show search dialog
     */
    private fun setupFabClick() {
        binding.searchFab.setOnClickListener {
            showSearchDialog()
        }
    }

    /**
     * Show dialog to search for a new city
     */
    private fun showSearchDialog() {
        val input = EditText(requireContext())
        input.hint = getString(R.string.city_hint)

        AlertDialog.Builder(requireContext())
            .setTitle("Search City")
            .setView(input)
            .setPositiveButton("Search") { _, _ ->
                val cityName = input.text.toString().trim()
                if (cityName.isNotEmpty()) {
                    // Fetch weather and navigate to detail
                    viewModel.fetchWeather(cityName)

                    // Navigate to detail fragment
                    val action = CityListFragmentDirections
                        .actionCityListToWeatherDetail(cityName)
                    findNavController().navigate(action)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    /**
     * Show empty state when no cities cached
     */
    private fun showEmptyState() {
        binding.emptyStateText.visibility = View.VISIBLE
        binding.cityRecyclerView.visibility = View.GONE
    }

    /**
     * Show city list
     */
    private fun showCityList() {
        binding.emptyStateText.visibility = View.GONE
        binding.cityRecyclerView.visibility = View.VISIBLE
    }

    /**
     * Clean up binding when view is destroyed
     * IMPORTANT: Prevents memory leaks
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
