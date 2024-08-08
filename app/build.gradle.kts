plugins {
    id("sg.carousell.news.conventions.app")
    id("sg.carousell.news.conventions.compose")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "sg.carousell.news"
    defaultConfig {
        applicationId = "sg.carousell.news"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.androidx.desugar)
}
