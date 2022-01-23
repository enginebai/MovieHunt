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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.JetpackCompose.compiler
    }
}

dependencies {
    implementation(Dependencies.Epoxy.core)
    implementation(Dependencies.Epoxy.paging3)
    implementation(Dependencies.Epoxy.databinding)
    "kapt"(Dependencies.Epoxy.processor)

    implementation(Dependencies.coil)
    implementation(Dependencies.coilCompose)

    implementation(Dependencies.Room.runtime)
    implementation(Dependencies.Room.ktx)
    implementation(Dependencies.Room.rxjava2)
    "kapt"(Dependencies.Room.annotation)

    implementation(project(":base"))
    implementation(Dependencies.snapHelper)

    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpLogging)
    implementation(Dependencies.Retrofit.core)
    implementation(Dependencies.Retrofit.gsonConverter)
    implementation(Dependencies.Retrofit.rxJavaAdapter)

    implementation(Dependencies.JetpackCompose.ui)
    implementation(Dependencies.JetpackCompose.tooling)
    implementation(Dependencies.JetpackCompose.foundation)
    implementation(Dependencies.JetpackCompose.material)
    implementation(Dependencies.JetpackCompose.liveData)
    implementation(Dependencies.JetpackCompose.rxjava2)
    implementation(Dependencies.JetpackCompose.activity)
    implementation(Dependencies.JetpackCompose.appCompatTheme)
    implementation(Dependencies.JetpackCompose.ratingBar)
    implementation(Dependencies.JetpackCompose.paging)
    implementation(Dependencies.JetpackCompose.swipeRefresh)
}
