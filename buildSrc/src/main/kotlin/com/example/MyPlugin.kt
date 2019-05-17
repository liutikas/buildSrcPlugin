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
                    }
                }
            }
        }
    }

    private fun BaseExtension.setCommonAndroidOptions() {
    }
}