package com.example

import com.android.build.gradle.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val myConfig = project.extensions.create("myConfig",
                MyPluginExtension::class.java)

        // Fun fun hackery time... Android Gradle Plugin ignores changes to defaultConfig after
        // it runs its afterEvaluate blocks. We need to read the value from our extension which
        // is only populated by afterEvaluate, so... we need to run our change in an afterEvaluate
        // before Android Gradle Plugin's after evaluate. To summarize, we rely on the following:
        // - MyPlugin is applied before Android Gradle Plugin (in each build.gradle)
        // - afterEvaluate below runs before Android Gradle Plugin afterEvaluates.
        project.afterEvaluate {
            project.extensions.findByType(TestedExtension::class.java)?.apply {
                defaultConfig.minSdkVersion(myConfig.minSdkVersion.get())
            }
        }

        project.plugins.all { plugin ->
            when (plugin) {
                is LibraryPlugin -> {
                    project.extensions.getByType(LibraryExtension::class.java).apply {
                        setCommonAndroidOptions()
                    }
                }
                is AppPlugin -> {
                    project.extensions.getByType(AppExtension::class.java).apply {
                        setCommonAndroidOptions()
                        defaultConfig.apply {
                            versionCode = 1
                            versionName = "1.0"
                        }
                        buildTypes.getByName("release").apply {
                            isMinifyEnabled = false
                            proguardFiles(
                                    getDefaultProguardFile("proguard-android-optimize.txt"),
                                    "proguard-rules.pro"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun TestedExtension.setCommonAndroidOptions() {
        compileSdkVersion(28)
        defaultConfig.apply {
            minSdkVersion(24)
            targetSdkVersion(28)
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
}