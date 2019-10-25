val implementation by configurations

dependencies {
    implementation(Dependencies.Kotlin.stdLib)

    implementation(Dependencies.rxJava)
    implementation(Dependencies.rxAndroid)

    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.viewModel)
}