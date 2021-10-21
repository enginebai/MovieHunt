import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

object Config {
    const val API_ROOT = "\"https://api.themoviedb.org/3/\""
    const val IMAGE_API_ROOT = "\"https://image.tmdb.org/t/p/\""
    const val YOUTUBE_THUMBNAIL_URL = "\"https://img.youtube.com/vi/\""
    const val YOUTUBE_VIDEO_URL = "\"https://www.youtube.com/watch?v=\""
}

fun Project.configAndroid() = this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(Versions.Android.sdk)
    defaultConfig {
        minSdk = Versions.Android.minSdk
        targetSdk = Versions.Android.sdk
        versionCode = Versions.App.versionCode
        versionName = Versions.App.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_ROOT", Config.API_ROOT)
        buildConfigField("String", "TMDB_API_KEY", TMDB_API_KEY)
        buildConfigField("String", "IMAGE_API_KEY", Config.IMAGE_API_ROOT)
        buildConfigField("String", "YOUTUBE_THUMBNAIL_URL", Config.YOUTUBE_THUMBNAIL_URL)
        buildConfigField("String", "YOUTUBE_VIDEO_URL", Config.YOUTUBE_VIDEO_URL)

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}