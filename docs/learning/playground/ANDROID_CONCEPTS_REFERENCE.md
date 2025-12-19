# Android Framework Concepts - Quick Reference

These concepts require the Android framework and can't be run as standalone Kotlin files. Use this as a reference when you encounter them in your Weather app or Week 2 projects.

---

## 📱 Configuration Changes & State Saving

### Configuration Changes (Screen Rotation)

When the user rotates the device, Android **destroys and recreates** the Activity by default.

```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "onCreate called")
        // Activity is being created (or recreated after rotation)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "onDestroy called")
        // Activity is being destroyed (rotation will trigger this!)
    }
}
```

**What happens on rotation:**
1. `onDestroy()` is called
2. Activity is destroyed
3. `onCreate()` is called again
4. New Activity instance is created
5. **All non-saved data is lost!**

### Saving State with onSaveInstanceState

To preserve data across configuration changes:

```kotlin
class MainActivity : AppCompatActivity() {

    private var counter = 0  // This will be lost on rotation!

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save data before destruction
        outState.putInt("counter", counter)
        Log.d("State", "Saved counter: $counter")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Restore data after recreation
        counter = savedInstanceState?.getInt("counter") ?: 0
        Log.d("State", "Restored counter: $counter")
    }
}
```

**Bundle types you can save:**
- Primitives: `putInt()`, `putString()`, `putBoolean()`, etc.
- Arrays: `putIntArray()`, `putStringArray()`, etc.
- Parcelable: `putParcelable()` for custom objects (requires `@Parcelize`)
- Serializable: `putSerializable()` (slower than Parcelable)

### Better Solution: ViewModel

ViewModel survives configuration changes automatically!

```kotlin
class WeatherViewModel : ViewModel() {
    // This StateFlow survives rotation!
    private val _weatherState = MutableStateFlow<Result<WeatherData>>(Result.Loading)
    val weatherState: StateFlow<Result<WeatherData>> = _weatherState

    // This data is NOT lost on rotation
    var lastSearchedCity: String = ""
}

class MainActivity : AppCompatActivity() {
    // ViewModel is retained across configuration changes
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewModel still has the same data after rotation!
    }
}
```

---

## 📋 RecyclerView Fundamentals

RecyclerView is used to display scrolling lists efficiently (only renders visible items).

### Components:

1. **RecyclerView** - The container
2. **Adapter** - Binds data to views
3. **ViewHolder** - Holds view references (performance optimization)
4. **LayoutManager** - Controls layout (LinearLayoutManager, GridLayoutManager)

### Basic Setup:

**XML Layout:**
```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

**Data Class:**
```kotlin
data class WeatherForecast(
    val day: String,
    val temperature: Int,
    val description: String
)
```

**ViewHolder:**
```kotlin
class ForecastViewHolder(private val binding: ItemForecastBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(forecast: WeatherForecast) {
        binding.dayText.text = forecast.day
        binding.temperatureText.text = "${forecast.temperature}°C"
        binding.descriptionText.text = forecast.description
    }
}
```

**Adapter:**
```kotlin
class ForecastAdapter(private val forecasts: List<WeatherForecast>) :
    RecyclerView.Adapter<ForecastViewHolder>() {

    // 1. Create ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ItemForecastBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ForecastViewHolder(binding)
    }

    // 2. Bind data to ViewHolder
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(forecasts[position])
    }

    // 3. Return item count
    override fun getItemCount() = forecasts.size
}
```

**Activity Setup:**
```kotlin
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ForecastAdapter(getForecastData())
        }
    }

    private fun getForecastData(): List<WeatherForecast> {
        return listOf(
            WeatherForecast("Monday", 22, "Sunny"),
            WeatherForecast("Tuesday", 20, "Cloudy"),
            WeatherForecast("Wednesday", 18, "Rainy")
        )
    }
}
```

### RecyclerView vs ListView:

| RecyclerView | ListView |
|--------------|----------|
| ✅ ViewHolder pattern enforced | ❌ ViewHolder optional (bad performance) |
| ✅ Flexible layouts (Linear, Grid, Staggered) | ❌ Only vertical list |
| ✅ Item animations | ❌ No animations |
| ✅ Better performance | ❌ Slower with large lists |

**Modern Android uses RecyclerView exclusively!**

---

## 📐 Layout Types - Quick Comparison

### LinearLayout

Arranges children in a single row or column.

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="16dp">

    <!-- Children stack vertically -->
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Name:" />

    <EditText
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Enter name" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Submit" />
</LinearLayout>
```

**Key Properties:**
- `android:orientation="vertical"` or `"horizontal"`
- `android:layout_weight` - Distribute space proportionally
- `android:gravity` - Align children within LinearLayout
- `android:layout_gravity` - Align view within parent

**When to use:**
- Simple vertical or horizontal stacking
- Equal spacing with weights
- Quick forms or simple lists

### ConstraintLayout (What We Used in Weather App)

Flexible layout - position views relative to each other or parent.

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/titleText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Weather"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <Button
        android:id="@+id/fetchButton"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toBottomOf="@+id/titleText"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

**Key Concepts:**
- Each view needs constraints in horizontal AND vertical direction
- `layout_width="0dp"` means "match constraints"
- Use `@+id/` for forward references (referencing views defined later)
- Chains: Link multiple views together
- Guidelines: Invisible reference lines

**When to use:**
- Complex layouts
- Flat view hierarchies (better performance)
- Responsive designs
- Modern Android (Google recommends it)

### FrameLayout

Stacks children on top of each other.

```xml
<FrameLayout
    android:layout_width="match_parent"
    android:layout_height="200dp">

    <!-- Background image -->
    <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:src="@drawable/weather_bg" />

    <!-- Overlay text (appears on top) -->
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="22°C" />
</FrameLayout>
```

**When to use:**
- Overlaying views
- Fragment containers
- Simple single-child containers

---

## 🎯 Quick Decision Guide

**Choose RecyclerView when:**
- Displaying lists or grids
- Data can grow large
- Need scrolling
- Want animations

**Choose LinearLayout when:**
- Simple vertical/horizontal stacking
- Small number of views
- Need weight distribution

**Choose ConstraintLayout when:**
- Complex positioning
- Flat hierarchy needed
- Responsive design
- Modern best practice (use this most!)

**Choose FrameLayout when:**
- Overlaying views
- Fragment container
- Single child with simple positioning

---

## 🚀 Week 2 Preview

You'll use these concepts extensively:
- **ViewModel** - Survive configuration changes
- **RecyclerView** - Display weather forecasts
- **Room Database** - Cache weather data locally
- **Navigation** - Multi-screen apps with Fragments
- **MVVM Pattern** - Clean architecture

**You've already touched:**
- ✅ StateFlow (ViewModel's best friend)
- ✅ Coroutines (Room uses suspend functions)
- ✅ Repository pattern (separates data from UI)
- ✅ ConstraintLayout (used in Weather app)

---

**📝 Note:** These are reference examples. You'll implement them hands-on in Week 2!
