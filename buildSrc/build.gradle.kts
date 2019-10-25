repositories {
    mavenCentral()
    google()
    jcenter()
}

plugins {
    `kotlin-dsl`
}

//gradlePlugin {
//    plugins {
//        register("config-plugin") {
//            id = "config"
//            implementationClass = "ConfigPlugin"
//        }
//    }
//}

dependencies {
    // Depend on the android gradle plugin, since we want to access it in our plugin
    implementation("com.android.tools.build:gradle:3.5.1")

    // Depend on the kotlin plugin, since we want to access it in our plugin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")

    // Depend on the default Gradle API since we want to build a custom plugin
    implementation(gradleApi())
    implementation(localGroovy())
}