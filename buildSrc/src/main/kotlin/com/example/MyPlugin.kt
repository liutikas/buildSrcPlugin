package com.example

import com.android.build.gradle.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val myConfig = project.extensions.create("myConfig",
                MyPluginExtension::class.java)

        project.afterEvaluate {
            project.extensions.findByType(LibraryExtension::class.java)?.apply {
                defaultConfig.minSdkVersion(myConfig.minSdkVersion.get())
                println("aurimas minSdkVersion ${myConfig.minSdkVersion.get()}")
            }
        }

        project.plugins.all { plugin ->
            when (plugin) {
                is LibraryPlugin -> {
                    project.extensions.getByType(LibraryExtension::class.java).apply {
                        setCommonAndroidOptions(project, myConfig)
                    }
                }
                is AppPlugin -> {
                    project.extensions.getByType(AppExtension::class.java).apply {
                        setCommonAndroidOptions(project, myConfig)
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

    private fun TestedExtension.setCommonAndroidOptions(
            project: Project,
            myConfig: MyPluginExtension
    ) {
        compileSdkVersion(28)
        defaultConfig.apply {
            //minSdkVersion(24)
            targetSdkVersion(28)
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        project.afterEvaluate {
            defaultConfig.minSdkVersion(myConfig.minSdkVersion.get())
            println("aurimas minSdkVersion ${myConfig.minSdkVersion.get()}")
        }
    }
}