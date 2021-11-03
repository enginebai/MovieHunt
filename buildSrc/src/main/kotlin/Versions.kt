object Versions {
    const val kotlin = "1.5.30"
    const val androidGradle = "7.0.2"
    const val material = "1.4.0"

    // TODO: Deprecated, to migrate to coroutine + flow
    const val rxJava = "2.2.19"
    const val rxAndroid = "2.1.1"

    // TODO: Deprecated, to migrate to kotlin.serialization
    const val gson = "2.8.6"

    const val okhttp = "4.9.0"
    const val retrofit = "2.9.0"
    const val koin = "3.1.2"
    const val timber = "5.0.1"
    const val logger = "2.2.0"

    const val epoxy = "4.6.3"

    const val coil = "1.3.2"
    const val androidBase = "1.0.3"

    // Test frameworks
    const val junit = "4.12"
    const val espresso = "3.2.0"
    const val testRunner = "1.2.0"

    object Android {
        const val sdk = 29
        const val minSdk = 26
    }

    object AndroidX {
        const val appCompat = "1.3.1"
        const val core = "1.3.0"
        const val constraintLayout = "2.1.0"
        const val swipeRefreshLayout = "1.1.0"
    }

    object App {
        const val id = "com.enginebai.moviehunt"
        const val versionCode = 2
        const val versionName = "2.0.0"
    }

    object ArchitectureComponents {
        const val lifecycle = "2.3.1"

        // TODO: migrate to v3
        const val paging = "2.1.2"
        const val room = "2.3.0"
    }

}