# Kotlin & Android Concepts in HiltFragmentTestUtils

This document explains the advanced Kotlin and Android concepts used in `HiltFragmentTestUtils.kt`.

## Table of Contents
1. [inline & reified](#1-inline--reified)
2. [@StyleRes Annotation](#2-styleres-annotation)
3. [crossinline & Function Types](#3-crossinline--function-types)
4. [Fragment Theme Configuration](#4-fragment-theme-configuration)
5. [Extension Function Invocation](#5-extension-function-invocation)

---

## 1. `inline` & `reified`

### The Problem: Type Erasure

In Kotlin/Java, generic types are **erased at runtime** due to type erasure:

```kotlin
// ❌ This DOESN'T work - can't access T at runtime
fun <T> createInstance(): T {
    return T()  // ERROR: Cannot instantiate type parameter T
}

// ❌ This DOESN'T work - T is erased at runtime
fun <T> getClassName(): String {
    return T::class.java.name  // ERROR: Cannot use T::class
}
```

**Why?** At runtime, `T` is erased. The JVM doesn't know what `T` is.

### The Solution: `inline` + `reified`

```kotlin
// ✅ This WORKS - reified preserves type information
inline fun <reified T> createInstance(): T {
    return T::class.java.newInstance()  // Works!
}

// ✅ This WORKS - can access T::class
inline fun <reified T> getClassName(): String {
    return T::class.java.name  // Works!
}
```

### How It Works

#### `inline` Keyword
Copies the function body directly into the call site at compile time.

**Without inline:**
```kotlin
fun regularFunction() {
    println("Hello")
}

fun main() {
    regularFunction()  // Function call (overhead)
}

// Compiles to:
// main() calls regularFunction()
```

**With inline:**
```kotlin
inline fun inlineFunction() {
    println("Hello")
}

fun main() {
    inlineFunction()  // No function call!
}

// Compiles to:
// main() {
//     println("Hello")  // Function body copied here
// }
```

#### `reified` Keyword
Preserves generic type information at compile time (only works with `inline`).

**How it works:**
1. Function is `inline` → body is copied to call site
2. Type `T` is known at call site → compiler substitutes actual type
3. Type information is preserved in the copied code

### Our Usage in HiltFragmentTestUtils

```kotlin
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    // ...
): ActivityScenario<HiltTestActivity> {
    // ...
    val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
        Preconditions.checkNotNull(T::class.java.classLoader),  // ✅ Can access T::class!
        T::class.java.name                                        // ✅ Can get T's name!
    )
    // ...
}
```

**When called:**
```kotlin
// At call site:
launchFragmentInHiltContainer<CityListFragment>()

// Compiler transforms to (conceptually):
{
    val fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
        CityListFragment::class.java.classLoader,  // T replaced with CityListFragment
        CityListFragment::class.java.name          // T replaced with CityListFragment
    )
}
```

### Real-World Example

**Without reified:**
```kotlin
// ❌ Must pass class explicitly
fun <T : Fragment> launchFragment(fragmentClass: Class<T>) {
    val fragment = fragmentClass.newInstance()  // Need the class object
}

// Verbose call:
launchFragment(CityListFragment::class.java)  // Must pass class manually
```

**With reified:**
```kotlin
// ✅ Clean API
inline fun <reified T : Fragment> launchFragment() {
    val fragment = T::class.java.newInstance()  // Can access T::class directly
}

// Clean call:
launchFragment<CityListFragment>()  // Type inference!
```

---

## 2. `@StyleRes` Annotation

### What is `@StyleRes`?

A **resource type annotation** that indicates a parameter should be a style resource ID.

```kotlin
@StyleRes themeResId: Int = R.style.Theme_WeatherApp
```

### Resource Type Annotations in Android

Android provides many resource annotations:

```kotlin
@StringRes  val stringId: Int = R.string.app_name
@DrawableRes val iconId: Int = R.drawable.ic_launcher
@LayoutRes  val layoutId: Int = R.layout.activity_main
@ColorRes   val colorId: Int = R.color.purple_500
@DimenRes   val sizeId: Int = R.dimen.padding_large
@StyleRes   val themeId: Int = R.style.Theme_WeatherApp
```

### Why Use These Annotations?

#### 1. **Type Safety**
```kotlin
// ❌ Without annotation - accepts any Int
fun setTheme(themeId: Int) { ... }

setTheme(42)              // Compiles, but wrong!
setTheme(R.string.app_name)  // Compiles, but wrong!

// ✅ With annotation - IDE/Lint warns about wrong type
fun setTheme(@StyleRes themeId: Int) { ... }

setTheme(42)              // ⚠️ Warning: Expected style resource
setTheme(R.string.app_name)  // ⚠️ Warning: Expected style, got string
setTheme(R.style.Theme_WeatherApp)  // ✅ Correct
```

#### 2. **Better IDE Support**
- **Auto-completion** suggests only style resources
- **Quick documentation** shows it expects a style
- **Lint checks** catch errors at compile time

#### 3. **Code Clarity**
```kotlin
// ❌ Unclear - what kind of Int is this?
fun configure(id: Int, value: Int, size: Int)

// ✅ Clear - exactly what each parameter expects
fun configure(
    @IdRes id: Int,
    @ColorRes color: Int,
    @DimenRes size: Int
)
```

### Our Usage

```kotlin
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.Theme_WeatherApp,  // Must be a style resource!
    crossinline action: Fragment.() -> Unit = {}
)
```

**What it does:**
- Documents that `themeResId` should be a style resource
- IDE warns if you pass wrong resource type
- Default value is the app theme

**Example:**
```kotlin
// ✅ Correct usage
launchFragmentInHiltContainer<MyFragment>(
    themeResId = R.style.Theme_MyCustomTheme
)

// ⚠️ IDE Warning
launchFragmentInHiltContainer<MyFragment>(
    themeResId = R.color.purple_500  // Warning: Expected @StyleRes, got @ColorRes
)
```

---

## 3. `crossinline` & Function Types

### Understanding the Syntax

```kotlin
crossinline action: Fragment.() -> Unit = {}
```

Let's break this down piece by piece:

### Part 1: Function Types

```kotlin
// Basic function type
val myFunction: () -> Unit = { println("Hello") }
//              ^^^^^^ ^^^^
//              params return

// Function with parameters
val greet: (String) -> Unit = { name -> println("Hello $name") }
//         ^^^^^^^^    ^^^^
//         param     return

// Function with multiple parameters
val add: (Int, Int) -> Int = { a, b -> a + b }
```

### Part 2: Extension Function Types

```kotlin
// Extension function type
val extensionFn: String.() -> Int = { this.length }
//               ^^^^^^    ^   ^^^
//               receiver  |   return
//                         no params

// Using it:
val result = "Hello".extensionFn()  // this = "Hello", result = 5
```

**Key concept:** `String.() -> Int` means:
- Function can be called on a `String` (receiver)
- Inside function, `this` is the `String` instance
- Returns `Int`

### Part 3: Default Values

```kotlin
val action: Fragment.() -> Unit = {}
//                                ^^
//                                empty lambda (does nothing)
```

### Part 4: `crossinline`

**The Problem:**
```kotlin
inline fun myFunction(action: () -> Unit) {
    action()  // ✅ OK

    someOtherFunction {
        action()  // ❌ ERROR: Can't use non-crossinline lambda in different context
    }
}
```

**The Solution:**
```kotlin
inline fun myFunction(crossinline action: () -> Unit) {
    //                 ^^^^^^^^^^^
    //                 Allows using lambda in different contexts

    action()  // ✅ OK

    someOtherFunction {
        action()  // ✅ OK with crossinline
    }
}
```

**What `crossinline` does:**
- Allows lambda to be passed to non-inline functions
- Prevents non-local returns (return from the lambda returns from the lambda, not the enclosing function)

### Our Usage: Complete Breakdown

```kotlin
crossinline action: Fragment.() -> Unit = {}
```

**Translation:**
- `crossinline` = Can be used in callbacks/other contexts
- `action` = Parameter name
- `Fragment.()` = Extension function on Fragment (receiver = Fragment instance)
- `-> Unit` = Returns nothing
- `= {}` = Default empty implementation (optional callback)

### Real-World Examples

#### Example 1: Basic Usage
```kotlin
launchFragmentInHiltContainer<MyFragment> {
    // 'this' is the Fragment instance
    // Can call Fragment methods directly
    view?.findViewById<TextView>(R.id.myText)?.text = "Test"
}

// Expands to:
launchFragmentInHiltContainer<MyFragment>(
    action = {
        // this: Fragment
        this.view?.findViewById<TextView>(R.id.myText)?.text = "Test"
    }
)
```

#### Example 2: No Action (Default)
```kotlin
// Uses default {} - does nothing
launchFragmentInHiltContainer<MyFragment>()
```

#### Example 3: Configure Fragment After Launch
```kotlin
launchFragmentInHiltContainer<MyFragment> {
    // 'this' is MyFragment instance
    (this as MyFragment).somePublicMethod()

    // Or access properties
    requireActivity()  // Can call Fragment methods
    viewLifecycleOwner  // Can access Fragment properties
}
```

### Why This Pattern?

**Without extension function:**
```kotlin
// ❌ Verbose - need to use 'it'
launchFragment<MyFragment>(action = { fragment ->
    fragment.view?.findViewById<TextView>(R.id.myText)?.text = "Test"
})
```

**With extension function:**
```kotlin
// ✅ Clean - implicit 'this'
launchFragment<MyFragment> {
    view?.findViewById<TextView>(R.id.myText)?.text = "Test"
}
```

---

## 4. Fragment Theme Configuration

### The Code

```kotlin
.putExtra(
    "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
    themeResId
)
```

### What Is This?

This is **FragmentScenario's internal mechanism** for applying themes to test fragments.

### Background: How FragmentScenario Works

When you use AndroidX's `launchFragmentInContainer()`:

1. Launches `EmptyFragmentActivity` (a special testing activity)
2. Inflates your fragment inside that activity
3. Applies theme from extras

**The Problem:**
`EmptyFragmentActivity` looks for a specific extra key to apply the theme.

**The Solution:**
We replicate this mechanism in our custom launcher.

### The Key Name Explained

```kotlin
"androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY"
```

Breaking it down:
- `androidx.fragment.app.testing` = Package name
- `FragmentScenario` = The class
- `EmptyFragmentActivity` = The test activity
- `THEME_EXTRAS_BUNDLE_KEY` = The constant name

**Where it comes from:**
```java
// In FragmentScenario source code (AndroidX):
public final class FragmentScenario<F extends Fragment> {
    private static final String THEME_EXTRAS_BUNDLE_KEY =
        "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY";

    // Uses this key to pass theme to the activity
}
```

### Why We Need This

**Our custom HiltTestActivity** needs to behave like `EmptyFragmentActivity`:

```kotlin
// Standard FragmentScenario (can't use with Hilt)
launchFragmentInContainer<MyFragment>(themeResId = R.style.MyTheme)
// Internally: puts theme in extras with this key
// EmptyFragmentActivity reads it and applies theme

// Our custom Hilt launcher (mimics the same behavior)
launchFragmentInHiltContainer<MyFragment>(themeResId = R.style.MyTheme)
// We put theme in extras with the SAME key
// HiltTestActivity could read it and apply theme (if implemented)
```

### Full Context in Our Code

```kotlin
val startActivityIntent = Intent.makeMainActivity(
    ComponentName(
        ApplicationProvider.getApplicationContext(),
        HiltTestActivity::class.java
    )
).putExtra(
    // This is the key EmptyFragmentActivity looks for
    "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
    themeResId  // The style resource ID (e.g., R.style.Theme_WeatherApp)
)
```

**What happens:**
1. Create Intent to launch `HiltTestActivity`
2. Add theme resource ID as an extra
3. Activity could read this extra and apply the theme
4. Maintains compatibility with FragmentScenario's expectations

### Note on Implementation

Currently, our `HiltTestActivity` doesn't actually read this extra:

```kotlin
@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity()
// Just an empty activity
```

**To actually use the theme**, we'd need:
```kotlin
@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Read theme from extras
        val themeId = intent.getIntExtra(
            "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY",
            0
        )

        // Apply theme if provided
        if (themeId != 0) {
            setTheme(themeId)
        }

        super.onCreate(savedInstanceState)
    }
}
```

But we include it for:
- **Compatibility** with FragmentScenario patterns
- **Future extensibility** if theme switching is needed
- **Documentation** of intent

---

## 5. Extension Function Invocation

### The Code

```kotlin
fragment.action()
```

### What Is This?

Invoking the extension function lambda on the fragment instance.

### Understanding Extension Functions

```kotlin
// Define extension function type
val action: String.() -> Unit = {
    println("Length: ${this.length}")
}

// Invoke it
"Hello".action()  // Calls the lambda with "Hello" as 'this'
// Output: Length: 5
```

### In Our Context

```kotlin
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    @StyleRes themeResId: Int = R.style.Theme_WeatherApp,
    crossinline action: Fragment.() -> Unit = {}
    //          ^^^^^^ Fragment is the receiver
) {
    // ... create and add fragment ...

    // Execute the action on the fragment
    fragment.action()
    //      ^^^^^^^^
    //      Calls the lambda with 'fragment' as 'this'
}
```

### Step-by-Step Execution

#### Step 1: User calls with lambda
```kotlin
launchFragmentInHiltContainer<MyFragment> {
    // Inside here, 'this' will be the Fragment
    view?.findViewById<TextView>(R.id.text)?.text = "Test"
}
```

#### Step 2: Lambda is stored in `action` parameter
```kotlin
action: Fragment.() -> Unit = {
    view?.findViewById<TextView>(R.id.text)?.text = "Test"
}
```

#### Step 3: Fragment is created
```kotlin
val fragment: Fragment = activity.supportFragmentManager
    .fragmentFactory.instantiate(...)
// fragment is now a MyFragment instance
```

#### Step 4: `fragment.action()` is called
```kotlin
fragment.action()
// Equivalent to:
action.invoke(fragment)
// Or:
with(fragment) {
    view?.findViewById<TextView>(R.id.text)?.text = "Test"
}
```

### Why At The End?

**Execution Order Matters:**

```kotlin
ActivityScenario.launch<HiltTestActivity>(startActivityIntent).also { scenario ->
    scenario.onActivity { activity ->
        // 1. Create fragment
        val fragment: Fragment = activity.supportFragmentManager
            .fragmentFactory.instantiate(...)

        // 2. Set arguments
        fragment.arguments = fragmentArgs

        // 3. Add fragment to activity
        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()  // ⚠️ Fragment is now attached and view is created

        // 4. NOW we can interact with the fragment
        fragment.action()  // ✅ Fragment is ready, view exists
    }
}
```

**If we called `action()` too early:**
```kotlin
// ❌ BAD ORDER
val fragment = createFragment()
fragment.action()  // ERROR: view is null! Fragment not attached yet!
addFragmentToActivity(fragment)
```

**Correct order:**
```kotlin
// ✅ GOOD ORDER
val fragment = createFragment()
addFragmentToActivity(fragment)
fragment.action()  // ✅ Fragment is attached, view exists
```

### Real-World Example

```kotlin
// Test that wants to interact with fragment after launch
launchFragmentInHiltContainer<CityListFragment> {
    // At this point:
    // ✅ Fragment is created
    // ✅ Fragment is added to activity
    // ✅ View is inflated (commitNow() ensures this)
    // ✅ onViewCreated() has been called

    // Can safely access views
    val recyclerView = view?.findViewById<RecyclerView>(R.id.cityRecyclerView)
    assertNotNull(recyclerView)

    // Can call fragment methods
    (this as CityListFragment).somePublicMethod()
}
```

### Optional Callback Pattern

Because it has a default value `= {}`, it's **optional**:

```kotlin
// Without callback - uses default {}
launchFragmentInHiltContainer<MyFragment>()
// fragment.action() calls empty lambda - does nothing

// With callback
launchFragmentInHiltContainer<MyFragment> {
    // Custom setup code
}
// fragment.action() executes custom code
```

---

## Summary Table

| Concept | What It Does | Why We Use It |
|---------|--------------|---------------|
| `inline` | Copies function body to call site | Enables `reified`, reduces overhead |
| `reified` | Preserves generic type at runtime | Access `T::class` for fragment creation |
| `@StyleRes` | Indicates style resource parameter | Type safety, better IDE support |
| `Fragment.() -> Unit` | Extension function type | Clean syntax, implicit `this` |
| `crossinline` | Allows lambda in callbacks | Use lambda in `onActivity { }` |
| `= {}` | Default empty lambda | Makes callback optional |
| `THEME_EXTRAS_BUNDLE_KEY` | FragmentScenario's theme key | Compatibility with testing framework |
| `fragment.action()` | Invoke extension lambda | Execute custom setup after fragment is ready |

---

## Further Reading

- [Kotlin Inline Functions](https://kotlinlang.org/docs/inline-functions.html)
- [Reified Type Parameters](https://kotlinlang.org/docs/inline-functions.html#reified-type-parameters)
- [Function Types](https://kotlinlang.org/docs/lambdas.html#function-types)
- [Android Resource Annotations](https://developer.android.com/studio/write/annotations#resource-annotations)
- [Fragment Testing](https://developer.android.com/guide/fragments/test)
