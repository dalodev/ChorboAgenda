/*
 * Copyright 2021 dalodev
 */
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import es.littledavity.chorboagenda.BuildPlugins
import es.littledavity.chorboagenda.utils.hasTestDirectory
import es.littledavity.chorboagenda.utils.isAndroidModule

plugins {
    detekt()
    spotless()
    dokka()
    gradleVersions()
    kover()
}

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath(Deps.Plugins.androidGradle)
        classpath(Deps.Plugins.kotlinGradle)
        classpath(Deps.Plugins.navSafeArgs)
        classpath(Deps.Plugins.daggerHiltGradle)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts.kts.kts files
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        maven { setUrl("https://jitpack.io") }
    }
    plugins.apply(BuildPlugins.SPOTLESS)
    plugins.apply(BuildPlugins.DETEKT)
    plugins.apply(BuildPlugins.KTLINT)
    plugins.apply(BuildPlugins.DOKKA)
    plugins.apply(BuildPlugins.UPDATE_DEPENDENCIES)

    // Without the below block, a build failure was happening when
    // running ./gradlew connectedAndroidTest.
    // See: https://githgiub.com/mockito/mockito/issues/2007#issuecomment-689365556
    configurations.all {
        resolutionStrategy.force("org.objenesis:objenesis:2.6")
    }
}

subprojects {
    tasks.withType(KotlinCompile::class.java).all {
        sourceCompatibility = AppConfig.javaCompatibilityVersion.toString()
        targetCompatibility = AppConfig.javaCompatibilityVersion.toString()

        kotlinOptions {
            freeCompilerArgs += listOf(
                "-Xuse-experimental=kotlin.ExperimentalStdlibApi",
                "-Xuse-experimental=kotlin.time.ExperimentalTime",
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
                "-Xuse-experimental=kotlinx.serialization.ExperimentalSerializationApi"
            )

            jvmTarget = AppConfig.kotlinCompatibilityVersion.toString()
        }
    }

    plugins.withId(PLUGIN_KOTLIN_KAPT) {
        extensions.findByType<KaptExtension>()?.run {
            correctErrorTypes = true
        }
    }
}
