package es.littledavity.chorboagenda.utils

import org.gradle.api.Project
import PLUGIN_ANDROID_LIBRARY
import PLUGIN_ANDROID_APPLICATION
import java.io.File

fun Project.isAndroidModule(): Boolean {
    val isAndroidLibrary = plugins.hasPlugin(PLUGIN_ANDROID_LIBRARY)
    val isAndroidApp = plugins.hasPlugin(PLUGIN_ANDROID_APPLICATION)
    return isAndroidLibrary || isAndroidApp
}

fun Project.hasTestDirectory(): Boolean {
    return File(projectDir, "src/test/java").exists() ||
            File(projectDir, "src/androidTest/java").exists()
}