/*
 * Copyright 2021 dev.id
 */
plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    mavenCentral()
    google()
    maven("https://plugins.gradle.org/m2/")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

object PluginsVersions {
    const val GRADLE_VERSIONS = "4.2.0"
    const val KOTLIN = "1.4.32"
    const val SPOTLESS = "4.0.0"
    const val DETEKT = "1.17.1"
    const val KTLINT = "0.41.0"
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

dependencies {
    implementation("com.android.tools.build:gradle:4.2.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginsVersions.DETEKT}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${PluginsVersions.SPOTLESS}")
    implementation("com.pinterest:ktlint:${PluginsVersions.KTLINT}")
    implementation(gradleApi()) // for custom plugins
}
