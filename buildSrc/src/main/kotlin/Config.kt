
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

object Config {
    const val apiRoot = "\"https://api.themoviedb.org/3/\""
}

fun Project.configAndroid() = this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(Versions.Android.sdk)
    defaultConfig {
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.sdk)
        versionCode = Versions.App.versionCode
        versionName = Versions.App.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        dataBinding.isEnabled = true

        buildConfigField("String", "API_ROOT", Config.apiRoot)
        buildConfigField("String", "TMDB_API_KEY", tmdbApiKey)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}