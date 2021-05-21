plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    jcenter()
    google()
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

object PluginsVersions {
    const val GRADLE_VERSIONS = "4.2.0"
    const val KOTLIN = "1.4.32"
    const val SPOTLESS = "3.24.1"
    const val DETEKT = "1.0.1"
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