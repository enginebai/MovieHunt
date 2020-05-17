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
    implementation(Dependencies.Epoxy.core)
    implementation(Dependencies.Epoxy.paging)
    implementation(Dependencies.Epoxy.databinding)
    "kapt"(Dependencies.Epoxy.processor)
	implementation(Dependencies.Glide.core)
	"kapt"(Dependencies.Glide.compiler)

    implementation(project(":base"))
}
