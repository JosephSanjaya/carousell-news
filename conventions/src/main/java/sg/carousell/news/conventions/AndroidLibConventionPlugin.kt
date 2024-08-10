package sg.carousell.news.conventions

import sg.carousell.news.conventions.utils.applyKotlinPlugins
import sg.carousell.news.conventions.utils.applyKspPlugins
import sg.carousell.news.conventions.utils.applyNetwork
import sg.carousell.news.conventions.utils.applyPluginsWithLog
import sg.carousell.news.conventions.utils.testDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType
import sg.carousell.news.conventions.utils.applyHilt

class AndroidLibConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                applyPluginsWithLog("com.android.library")
                applyKotlinPlugins()
                applyKspPlugins()
                applyHilt()
                applyNetwork()
                applyPluginsWithLog("sg.carousell.news.conventions.target")
                testDependencies()
            }
            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
            }
        }
    }
}
