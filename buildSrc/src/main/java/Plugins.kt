import org.gradle.kotlin.dsl.version
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

const val PLUGIN_CHORBOAGENDA_ANDROID = "es.littledavity.chorboagenda"
const val PLUGIN_GRADLE_VERSIONS = "com.github.ben-manes.versions"
const val PLUGIN_KOTLIN_KAPT = "kotlin-kapt"
const val PLUGIN_ANDROID_APPLICATION = "com.android.application"
const val PLUGIN_NAV_SAFE_ARGS_KOTLIN = "androidx.navigation.safeargs.kotlin"
const val PLUGIN_DAGGER_HILT_ANDROID = "dagger.hilt.android.plugin"
const val PLUGIN_KOTLIN_ANDROID = "kotlin-android"
const val PLUGIN_ANDROID_LIBRARY = "com.android.library"
const val PLUGIN_DETEKT = "io.gitlab.arturbosch.detekt"
const val PLUGIN_SPOTLESS = "com.diffplug.gradle.spotless"
const val PLUGIN_GIT_HOKS = "plugins.git-hooks"
const val PLUGIN_KOTLIN = "kotlin"
const val PLUGIN_KOTLINX_SERIALIZATION = "org.jetbrains.kotlin.plugin.serialization"
const val PLUGIN_PROTOBUF = "com.google.protobuf"

fun PluginDependenciesSpec.chorboagendaAndroid(): PluginDependencySpec {
    return id(PLUGIN_CHORBOAGENDA_ANDROID)
}

fun PluginDependenciesSpec.androidLibrary(): PluginDependencySpec {
    return id(PLUGIN_ANDROID_LIBRARY)
}

fun PluginDependenciesSpec.gradleVersions(): PluginDependencySpec {
    return (id(PLUGIN_GRADLE_VERSIONS) version Versions.gradleVersionsPlugin)
}

fun PluginDependenciesSpec.androidApplication(): PluginDependencySpec {
    return id(PLUGIN_ANDROID_APPLICATION)
}

fun PluginDependenciesSpec.kotlinKapt(): PluginDependencySpec {
    return id(PLUGIN_KOTLIN_KAPT)
}

fun PluginDependenciesSpec.navSafeArgsKotlin(): PluginDependencySpec {
    return id(PLUGIN_NAV_SAFE_ARGS_KOTLIN)
}

fun PluginDependenciesSpec.daggerHiltAndroid(): PluginDependencySpec {
    return id(PLUGIN_DAGGER_HILT_ANDROID)
}

fun PluginDependenciesSpec.detekt(): PluginDependencySpec {
    return id(PLUGIN_DETEKT)
}

fun PluginDependenciesSpec.spotless(): PluginDependencySpec {
    return id(PLUGIN_SPOTLESS)
}

fun PluginDependenciesSpec.gitHooks(): PluginDependencySpec {
    return id(PLUGIN_GIT_HOKS)
}

fun PluginDependenciesSpec.kotlin(): PluginDependencySpec {
    return id(PLUGIN_KOTLIN)
}

fun PluginDependenciesSpec.kotlinxSerialization(): PluginDependencySpec {
    return (id(PLUGIN_KOTLINX_SERIALIZATION) version Versions.kotlin)
}

fun PluginDependenciesSpec.protobuf(): PluginDependencySpec {
    return (id(PLUGIN_PROTOBUF) version Versions.protobufPlugin)
}