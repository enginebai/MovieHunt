
object Dependencies {

    const val gradlePlugin = "com.android.tools.build:gradle:3.5.1"

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.androidX}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.androidX}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val runner = "androidx.test:runner:1.2.0"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }
}