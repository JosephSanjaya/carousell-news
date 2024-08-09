plugins {
    id("sg.carousell.news.conventions.lib")
    id("sg.carousell.news.conventions.compose")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ktorfit)
}

android {
    namespace = "sg.carousell.news.core"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    coreLibraryDesugaring(libs.androidx.desugar)
}