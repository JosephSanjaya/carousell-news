package sg.carousell.news.conventions.utils

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

fun Project.applyNetwork() {
    dependencies {
        findLibs("chucker")?.let { add("debugImplementation", it) }
        findLibs("chucker-no-op")?.let { add("releaseImplementation", it) }
        findLibs("ktor-core")?.let { add("implementation", it) }
        findLibs("ktor-cio")?.let { add("implementation", it) }
        findLibs("ktor-okhttp")?.let { add("implementation", it) }
        findLibs("ktor")?.let { add("implementation", it) }
        findLibs("ktorfit")?.let { add("implementation", it) }
    }
}
