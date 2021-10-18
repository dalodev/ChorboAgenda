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

object PluginsVersions {
    const val GRADLE_VERSIONS = "7.0.0"
    const val KOTLIN = "1.5.21"
    const val SPOTLESS = "4.0.0"
    const val DETEKT = "1.18.1"
    const val KTLINT = "0.41.0"
    const val JACOCO = "0.8.7"
}

dependencies {
    implementation("com.android.tools.build:gradle:7.0.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${PluginsVersions.DETEKT}")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:${PluginsVersions.SPOTLESS}")
    implementation("com.pinterest:ktlint:${PluginsVersions.KTLINT}")
    implementation("org.jacoco:org.jacoco.core:${PluginsVersions.JACOCO}")
    implementation(gradleApi()) // for custom plugins
}
