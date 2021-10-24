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
    gradlePluginPortal()
}

object PluginsVersions {
    const val GRADLE_VERSIONS = "7.0.3"
    const val KOTLIN = "1.5.31"
    const val SPOTLESS = "4.0.0"
    const val DETEKT = "1.18.1"
    const val KTLINT = "0.41.0"
    const val JACOCO = "0.8.7"
    const val DOKKA = "1.5.31"
    const val UPDATE_DEPENDENCIES = "0.39.0"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.GRADLE_VERSIONS}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginsVersions.DETEKT}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${PluginsVersions.SPOTLESS}")
    implementation("com.pinterest:ktlint:${PluginsVersions.KTLINT}")
    implementation("org.jacoco:org.jacoco.core:${PluginsVersions.JACOCO}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${PluginsVersions.DOKKA}")
    implementation("com.github.ben-manes:gradle-versions-plugin:${PluginsVersions.UPDATE_DEPENDENCIES}")
    implementation(gradleApi()) // for custom plugins
}
