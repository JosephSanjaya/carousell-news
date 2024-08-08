package sg.carousell.news.conventions.utils

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import kotlin.jvm.optionals.getOrNull

fun DependencyHandlerScope.implementationWithLog(notation: Provider<MinimalExternalModuleDependency>) {
    add("implementation", notation)
    printMessage("Adding library implementation: ${notation.orNull?.name}")
}

fun DependencyHandlerScope.kspWithLog(notation: Provider<MinimalExternalModuleDependency>) {
    add("ksp", notation)
    printMessage("Adding ksp implementation: ${notation.orNull?.name}")
}

fun DependencyHandlerScope.implementationPlatformWithLog(notation: Provider<MinimalExternalModuleDependency>) {
    add("implementation", platform(notation))
    printMessage("Adding platform library implementation: ${notation.orNull?.name}")
}

fun DependencyHandlerScope.androidTestImplementationPlatformWithLog(notation: Provider<MinimalExternalModuleDependency>) {
    add("androidTestImplementation", platform(notation))
    printMessage("Adding android test platform library implementation: ${notation.orNull?.name}")
}

fun DependencyHandlerScope.androidTestImplementationWithLog(notation: Provider<MinimalExternalModuleDependency>) {
    add("androidTestImplementation", notation)
    printMessage("Adding android test library implementation: ${notation.orNull?.name}")
}

fun DependencyHandlerScope.debugImplementationWithLog(notation: Provider<MinimalExternalModuleDependency>) {
    add("debugImplementation", notation)
    printMessage("Adding debug library implementation: ${notation.orNull?.name}")
}

fun DependencyHandlerScope.testImplementationWithLog(notation: Provider<MinimalExternalModuleDependency>) {
    add("testImplementation", notation)
    printMessage("Adding test library implementation: ${notation.orNull?.name}")
}

fun Project.findLibs(
    alias: String,
    isPrintError: Boolean = false,
): Provider<MinimalExternalModuleDependency>? {
    val dependency = libs.findLibrary(alias).getOrNull()
    if (dependency == null) {
        if (isPrintError) printMessage("Cannot find $alias on buildtools version catalog, please check version")
        return null
    }
    return dependency
}

fun Project.testDependencies() {
    dependencies {
        findLibs("junit")?.also { testImplementationWithLog(it) }
        findLibs("androidx-junit")?.also { androidTestImplementationWithLog(it) }
        findLibs("androidx-espresso-core")?.also { androidTestImplementationWithLog(it) }
    }
}

fun Project.applyHilt() {
    findPlugin("hilt", true)?.let { pluginManager.apply(it) }
    dependencies {
        findLibs("hilt")?.also { implementationWithLog(it) }
        findLibs("hilt-compiler")?.also { kspWithLog(it) }
    }
}