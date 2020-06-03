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
			    arguments = mapOf("room.schemaLocation" to "$projectDir/db")
		    }
	    }
    }
}

dependencies {
    implementation(Dependencies.Epoxy.core)
    implementation(Dependencies.Epoxy.paging)
    implementation(Dependencies.Epoxy.databinding)
    "kapt"(Dependencies.Epoxy.processor)

	implementation(Dependencies.Glide.core)
	"kapt"(Dependencies.Glide.compiler)

	implementation(Dependencies.Room.runtime)
	implementation(Dependencies.Room.ktx)
	implementation(Dependencies.Room.rxjava2)
	"kapt"(Dependencies.Room.annotation)

    implementation(project(":base"))
}
