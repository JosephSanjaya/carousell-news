plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    alias(libs.plugins.ksp)
}

dependencies {
    compileOnly(libs.plugin.agp)
    compileOnly(libs.plugin.kgp)
    compileOnly(libs.plugin.ksp)
    compileOnly(libs.plugin.compose)
}

gradlePlugin {
    val androidTarget by plugins.creating {
        id = "sg.carousell.news.conventions.target"
        implementationClass = "sg.carousell.news.conventions.AndroidTargetConventionPlugin"
    }
    val app by plugins.creating {
        id = "sg.carousell.news.conventions.app"
        implementationClass = "sg.carousell.news.conventions.AndroidAppConventionPlugin"
    }
    val lib by plugins.creating {
        id = "sg.carousell.news.conventions.lib"
        implementationClass = "sg.carousell.news.conventions.AndroidLibConventionPlugin"
    }
    val compose by plugins.creating {
        id = "sg.carousell.news.conventions.compose"
        implementationClass = "sg.carousell.news.conventions.AndroidComposeConventionPlugin"
    }
}
