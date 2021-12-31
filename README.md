<h1 align="center">MovieHunt</h1>

<p align="center">
MovieHunt is a sample Android project using <a href="https://www.themoviedb.org/">The Movie DB</a> API based on MVVM architecture. It showcases the latest Android tech stacks with well-designed architecture and best practices.

> The new v2 re-design is available. 🎉 Check it out now! 

<img src='art/home.png' width='25%'/><img src = 'art/home2.png' width='25%'/><img src='art/list.png' width='25%'/><img src ='art/movie_detail.png' width='25%'/>

</p>

## Features
* 100% Kotlin
* MVVM architecture
* Reactive pattern
* Android Architecture Components and Jetpack Compose.
* Kotlin Coroutines + Flow (Upcoming)
* Single activity pattern
* Dependency injection
* CI support (Upcoming)
* Testing (Upcoming)

<img src="./art/moviehunt-demo.gif" align="right" width="32%"/>

## Tech Stacks
* [Retrofit](http://square.github.io/retrofit/) + [OkHttp](http://square.github.io/okhttp/) - RESTful API and networking client.
* [Koin](https://insert-koin.io/) - Dependency injection.
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - A collections of libraries that help you design rebust, testable and maintainable apps.
    * [Room](https://developer.android.com/training/data-storage/room) - Local persistence database.
    * [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - Pagination loading for RecyclerView.
    * [ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel) - UI related data holder, lifecycle aware.
    * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Observable data holder that notify views when underlying data changes.
    * [Data Binding](https://developer.android.com/topic/libraries/data-binding) - Declarative way to bind data to UI layout.
    * [Navigation component](https://developer.android.com/guide/navigation) - Fragment routing handler. (Upcoming)
    * [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - Tasks scheduler in background jobs. (Upcoming)
* (Implementing) [Jetpack Compose](https://developer.android.com/jetpack/compose) - Declarative and simplified way for UI development.
* ~[Epoxy](https://github.com/airbnb/epoxy) - Simplified way to build complex layout in RecyclerView.~ Replaced by Jetpack Compose.
* (Implementing) [Coroutine](https://developer.android.com/kotlin/coroutines) Concurrency design pattern for asynchronous programming.
* (Upcoming) [Flow](https://developer.android.com/kotlin/flow) Stream of value that returns from suspend function.
* ~[RxJava](https://github.com/ReactiveX/RxJava) - Asynchronous programming with observable streams.~ (Upcoming) Replaced by Coroutine + Flow.
* [Coil](https://github.com/coil-kt/coil) - Image loading.
* [Timber](https://github.com/JakeWharton/timber) - Extensible API for logging.

## Architectures

![MVVM](./art/MovieHunt_Architecture.png)

We follow Google recommended [Guide to app architecture](https://developer.android.com/jetpack/guide) to structure our architecture based on MVVM, reactive UI using LiveData / RxJava observables and data binding.

* **View**: Activity/Fragment with UI-specific logics only.
* **ViewModel**: It keeps the logic away from View layer, provides data streams for UI and handle user interactions.
* **Model**: Repository pattern, data layers that provide interface to manipulate data from both the local and remote data sources. The local data sources will serve as [single source of truth](https://en.wikipedia.org/wiki/Single_source_of_truth).

## Package Structures

```
com.enginebai.moviehunt # Root Package
├── data                # For data modeling layer
│   ├── local           # Local persistence database
|   │   ├── dao         # Data Access Object for Room
|   |   ├── model       # Model classes
│   ├── remote          # Remote data source
│   └── repo            # Repositories for single source of data
|
├── di                  # Dependency injection modules
│
├── ui                  # Fragment / View layer
│   ├── list            # List screen Fragment and ViewModel
│   ├── home            # Main screen Fragment and ViewModel
|   │   ├── controller  # Epoxy controller for RecyclerView
|   │   └── models      # Epoxy models for RecyclerView
│   └── details         # Detail screen Fragment and ViewModel
|
├── utils               # Utility Classes / Kotlin extensions
├── MainActivity        # Single activity
├── AppContext          # Application
└── NavigationRouter    # Navigation controller

```


## API Key 🔑
You will need to provide developer key to fetch the data from TMDB API.
* Generate a new key (v3 auth) from [here](https://www.themoviedb.org/settings/api). Copy the key and go back to Android project.
* Create a new kotlin file `ApiKey.kt` in path `./buildSrc/src/main/kotlin/`.
* Define a constant `TMDB_API_KEY` with the double quotes, it looks like

```kotlin
const val TMDB_API_KEY = "\"90c05******************655\""
```

* Add the key to build config in `./buildSrc/src/main/kotlin/Config.kt`:

```kotlin
defaultConfig {
    ...
    buildConfigField("String", "TMDB_API_KEY", TMDB_API_KEY)
    ...
}
```

* Perform gradle sync.

> **NOTE**: It's important to keep the double quotes for this value, since it's used as String type build config fields, the field name in quotes, the field value in escaped quotes additionally. If you're missing the double quotes, it will build fail.

## LICENSE

```
Copyright (c) 2020 Engine Bai

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

