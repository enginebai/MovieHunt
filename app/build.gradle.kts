plugins {
    id("com.android.application")
}

importCommonPlugins()
configAndroid()
importCommonDependencies()

android {
    defaultConfig {
        applicationId = Versions.App.id

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
                argument("enableParallelEpoxyProcessing", "true")
            }
        }
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(Dependencies.Epoxy.core)
    implementation(Dependencies.Epoxy.paging)
    implementation(Dependencies.Epoxy.databinding)
    "kapt"(Dependencies.Epoxy.processor)

    implementation(Dependencies.coil)

    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.ktx)
    implementation(Dependencies.Room.rxjava2)
    "kapt"(Dependencies.Room.annotation)

    implementation(Dependencies.androidBase)

    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpLogging)
    implementation(Dependencies.Retrofit.core)
    implementation(Dependencies.Retrofit.gsonConverter)
    implementation(Dependencies.Retrofit.rxJavaAdapter)
}
