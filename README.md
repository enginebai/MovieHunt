![Language](https://img.shields.io/badge/language-kotlin-blue?logo=kotlin) ![License](https://img.shields.io/badge/License-MIT-brightgreen) 

The `Base` project provides a Android app project template that includes the base modules/classes, setups for **Gradle Kotlin DSL** and eliminates boilerplate code.

It helps you to quickly create a well configured Android starter application with the most popular libraries (Ex: Android Architecuture Component, Retrofit/OkHttp, RxJava, Logging...etc.). It creates and configures your project for you. Just focus on code development! 

## Setup
1. Just click on [![Clone this template](https://img.shields.io/badge/-Clone%20template-brightgreen)](https://github.com/enginebai/Base/generate) button to create a new repo starting from this template. Or you can clone this project by `git clone git@github.com:enginebai/Base.git` .
2. Set your application ID in `Versions.kt`
3. Set the package name in `AndroidManifest.xml` file of `:app` module .
4. Select `com.enginebai.project` directory in "Project" tool window and rename package for your app.
5. Create your application class which extends `BaseApplication` in `:app` module, implement abstract methods and add to `AndroidManifest.xml` file.
6. Specify your retrofit base URL in `NetworkModule.kt` file.
7. Start to design your main layout xml file `fragment_main.xml` and fragment class.
8. That's all. Start your app development journey now 🎉.

## Good Practices
* Add all dependencies version in `Versions.kt` 

```kotlin
object Versions {
    const val kotlin = "1.3.50"
    const val awesomeLibrary = "x.y.z"
    ...
}
```
* Add all 3rd-party dependencies in `Dependencies.kt`

```kotlin
object Dependencies {
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    // TODO: add standalone dependency here!
    ...

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    }

    // TODO: add inner object for sub-modules of library
    object AndroidX {
        ...
    }
    ...
}
```
* Always import dependency from `Dependencies.kt` in `build.gradle.kts` file.

```kotlin
dependencies {
	implementation(Dependencies.Glide.core)
	"kapt"(Dependencies.Glide.compiler)
    implementation(project(":base"))
    // TODO: add by using dependency imported from `Dependencies.kt` file
    ...
}
```

* Configure android build script in `Config.kt`.

```kotlin
fun Project.configAndroid() = this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(Versions.Android.sdk)
    defaultConfig {
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.sdk)
        versionCode = Versions.App.versionCode
        versionName = Versions.App.versionName
        // TODO: add your configurations
        ...
    }
    ...
}
```

It's equalivent to the old way `android { ... }` block in `build.gradle` file
```groovy
android {
    compileSdkVersion 21
    buildToolsVersion "21.1.2"
    defaultConfig {
        applicationId "com.enginebai.moviehunt"
        // TODO: add your configurations
        ...
    }
    ...
}
``` 


* Add all configuration variables inside `Config` object in `Config.kt`, and add `buildConfigField(...)` to include.

```kotlin
object Config {
    const val API_ROOT = "\"https://api.themoviedb.org/3/\""
    const val IMAGE_API_ROOT = "\"https://image.tmdb.org/t/p/\""
    // TODO: add your constants here, make sure to add extra double quotes for string value.
}

fun Project.configAndroid() = this.extensions.getByType<BaseExtension>().run {
    compileSdkVersion(Versions.Android.sdk)
    defaultConfig {
        ...

        buildConfigField("String", "API_ROOT", Config.API_ROOT)
        buildConfigField("String", "IMAGE_API_KEY", Config.IMAGE_API_ROOT)
        // TODO: add your varialbes here imported from `Config` object
        ... 
    }
    ...
}
```

* Add the common dependencies that share between modules to `Dependencies.kt`

```kotlin
fun Project.importCommonDependencies() {
    dependencies {
        ...
        implementation(Dependencies.material)
        // TODO: add your dependencies
        .. 
    }
}
```

> **Note:** Remember to perform Gradle Sync to apply your changes when updating any files in `buildSrc`.

## Modules Structure
* `:base` module: It defines the base, common and utilities classes.
* `:app` module: That's your app module, just like a normal Android app project. You puts all resources that app used, including strings, colors, dimensions, drawables. Or you can create `:common` modules for that if you use multi-modules project. 

> **Note**: Don't put the resources inside `:base` module since it can be updated from remote repo, please treat `:base` module as library.

## Included Libraries
* [Android Architecture Components](https://developer.android.com/topic/libraries/architecture), part of Android Jetpack for give to project a robust design, testable and maintainable.
* [Retrofit](https://github.com/square/retrofit) / [OkHttp](https://github.com/square/okhttp), Square open-source RESTful API and http client.
* [RxJava](https://github.com/ReactiveX/RxJava/) / [RxAndroid](https://github.com/ReactiveX/RxAndroid), reactive programming for JVM.
* [Koin](https://github.com/InsertKoinIO/koin), kotlin light-weight dependency injection.
* [Timber](https://github.com/JakeWharton/timber), for logging.
* [Epoxy](https://github.com/airbnb/epoxy), for RecyclerView complex view layout.

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


