plugins {
    id("com.android.application")
}

//apply(from = "../dependencies.gradle.kts")
importCommonPlugins()
configAndroid()
importCommonDependencies()

android {
    defaultConfig {
        applicationId = Versions.App.id
    }
}

dependencies {
    implementation(project(":base"))
}
