package com.example

import com.android.build.gradle.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
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