package sg.carousell.news.conventions

import sg.carousell.news.conventions.utils.applyKotlinPlugins
import sg.carousell.news.conventions.utils.applyKspPlugins
import sg.carousell.news.conventions.utils.applyNetwork
import sg.carousell.news.conventions.utils.applyPluginsWithLog
import sg.carousell.news.conventions.utils.testDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import sg.carousell.news.conventions.utils.applyHilt

class AndroidAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                applyPluginsWithLog("com.android.application")
                applyKotlinPlugins()
                applyKspPlugins()
                applyHilt()
                applyNetwork()
                applyPluginsWithLog("sg.carousell.news.conventions.target")
                testDependencies()
            }
        }
    }
}
