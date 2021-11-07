/*
 * Copyright 2021 dalodev
 */
plugins {
    androidLibrary()
    chorboagendaAndroid()
    kotlinKapt()
    kotlinxSerialization()
    daggerHiltAndroid() // does not compile instrumented tests without the plugin
}

android {
    defaultConfig {
        testInstrumentationRunner = "es.littledavity.testUtils.ChorboagendaTestRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    sourceSets {
        getByName("androidTest").assets.srcDirs("$projectDir/schemas")
    }
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {
    implementation(project(Deps.Local.data))
    implementation(project(Deps.Local.core))

    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.Kotlin.serialization)
    implementation(Deps.Misc.kotlinResult)

    implementation(Deps.AndroidX.room)
    implementation(Deps.AndroidX.roomKtx)
    kapt(Deps.AndroidX.roomCompiler)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)

    implementation(Deps.Misc.hiltBinder)
    kapt(Deps.Misc.hiltBinderCompiler)

    testImplementation(project(Deps.Local.librariesTest))
    testImplementation(Deps.Testing.jUnit)
    testImplementation(Deps.Testing.assertJ)
    testImplementation(Deps.Testing.mockk)
    testImplementation(Deps.Testing.coroutines)
    testImplementation(Deps.Testing.turbine)
    testImplementation(Deps.Testing.mockitoKotlin2)
    testImplementation(Deps.Testing.mavenAntTask)

    androidTestImplementation(project(Deps.Local.librariesTest))
    androidTestImplementation(Deps.Testing.testRunner)
    androidTestImplementation(Deps.Testing.jUnitExt)
    androidTestImplementation(Deps.Testing.assertJ)
    androidTestImplementation(Deps.Testing.archCore)
    androidTestImplementation(Deps.Testing.coroutines)
    androidTestImplementation(Deps.Testing.turbine)
    androidTestImplementation(Deps.Testing.room)
    androidTestImplementation(Deps.Testing.mockitoKotlin2)
    androidTestImplementation(Deps.Testing.testRoboelectric) {
        exclude("org.apache.maven", "maven-artifact")
        exclude("org.apache.maven", "maven-artifact-manager")
        exclude("org.apache.maven", "maven-model")
        exclude("org.apache.maven", "maven-plugin-registry")
        exclude("org.apache.maven", "maven-profile")
        exclude("org.apache.maven", "maven-project")
        exclude("org.apache.maven", "maven-settings")
        exclude("org.apache.maven", "maven-error-diagnostics")
        exclude("org.apache.maven", "maven-ant-tasks")
        exclude("org.apache.maven.wagon")
        exclude("org.codehaus.plexus")
    }

    androidTestImplementation(Deps.Testing.daggerHilt)
    kaptAndroidTest(Deps.Google.daggerHiltCompiler)

}
