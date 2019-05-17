package com.example

import org.gradle.api.provider.Property

abstract class MyPluginExtension() {
    abstract val minSdkVersion: Property<Int>
}