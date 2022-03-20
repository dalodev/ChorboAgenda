/*
 * Copyright 2021 dalodev
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
    const val GRADLE_VERSIONS = "7.1.2"
    const val KOTLIN = "1.6.10"
    const val SPOTLESS = "4.0.0"
    const val DETEKT = "1.18.1"
    const val KTLINT = "0.44.0"
    const val DOKKA = "1.6.10"
    const val UPDATE_DEPENDENCIES = "0.39.0"
    const val POET = "1.13.0"
}

dependencies {
    implementation("com.android.tools.build:gradle:${PluginsVersions.GRADLE_VERSIONS}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginsVersions.DETEKT}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${PluginsVersions.SPOTLESS}")
    implementation("com.pinterest:ktlint:${PluginsVersions.KTLINT}")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:${PluginsVersions.DOKKA}")
    implementation("com.github.ben-manes:gradle-versions-plugin:${PluginsVersions.UPDATE_DEPENDENCIES}")
    implementation("com.squareup:javapoet:${PluginsVersions.POET}")
    implementation(gradleApi()) // for custom plugins
}
