/*
 * Copyright 2021 dalodev
 */
plugins {
    androidLibrary()
    chorboagendaAndroid()
    kotlinKapt()
}

dependencies {
    implementation(project(Deps.Local.data))
    implementation(project(Deps.Local.core))
    implementation(project(Deps.Local.domain))
    implementation(project(Deps.Local.core))

    implementation(Deps.Misc.kotlinResult)
    implementation(Deps.AndroidX.appCompat)

    // Unit tests
    implementation(Deps.Testing.jUnit)
    implementation(Deps.Testing.assertJ)
    implementation(Deps.Testing.mockk)
    implementation(Deps.Testing.coroutines)

    // Instrumentation tests
    implementation(Deps.Testing.testRunner)
    implementation(Deps.Testing.mockWebServer)

    implementation(Deps.Testing.daggerHilt)
    implementation(Deps.Testing.room)
    implementation(Deps.Testing.archCore)
    implementation(Deps.Testing.turbine)
    implementation(Deps.Testing.testRoboelectric) {
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
    implementation(Deps.Testing.mockitoKotlin2)
    implementation(Deps.Testing.testRules)
    implementation(Deps.Testing.mockitoKotlin)
    implementation(Deps.AndroidX.paging)
    kapt(Deps.Google.daggerHiltCompiler)
}
