package sg.carousell.news.conventions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import sg.carousell.news.conventions.utils.findLibs
import sg.carousell.news.conventions.utils.implementationWithLog
import sg.carousell.news.conventions.utils.isApp
import sg.carousell.news.conventions.utils.isAppOrLib
import sg.carousell.news.conventions.utils.isLib
import sg.carousell.news.conventions.utils.libs
import sg.carousell.news.conventions.utils.printMessage
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the

class AndroidTargetConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            if (!isAppOrLib()) return@with
            val type =
                when {
                    isApp() -> {
                        the<ApplicationExtension>().apply {
                            defaultConfig {
                                multiDexEnabled = true
                                printMessage("Set Multidex enabled")
                            }
                        }
                        ApplicationExtension::class
                    }

                    isLib() -> LibraryExtension::class
                    else -> null
                } ?: return@with

            the(type).apply {
                val targetSdk = libs.findVersion("sdk-target").get().toString().toInt()
                val minSdkVersion = libs.findVersion("sdk-min").get().toString().toInt()
                if (this is ApplicationExtension) {
                    defaultConfig.targetSdk = targetSdk
                }
                compileSdk = libs.findVersion("sdk-compile").get().toString().toInt()
                defaultConfig {
                    minSdk = minSdkVersion
                }

                printMessage("Setting up target android sdk to: $targetSdk")
                printMessage("Setting up min android sdk to: $minSdkVersion")
                the<JavaPluginExtension>().toolchain {
                    languageVersion.set(
                        JavaLanguageVersion.of(
                            libs.findVersion("jvm-target").get().toString()
                        )
                    )
                }
                compileOptions {
                    isCoreLibraryDesugaringEnabled = true
                }
            }
            dependencies {
                findLibs("androidx-core-ktx")?.also { implementationWithLog(it) }
            }
        }
    }
}
