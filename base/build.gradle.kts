plugins {
    id("com.android.library")
}

importCommonPlugins()
configAndroid()
importCommonDependencies()

dependencies {
    implementation(Dependencies.okhttpLogging)
    implementation(Dependencies.Retrofit.gsonConverter)
    implementation(Dependencies.Retrofit.rxJavaAdapter)
}

