/*
 * Copyright 2021 dev.id
 */
plugins {
    androidLibrary()
    chorboagendaAndroid()
    kotlinKapt()
}
android {
    buildFeatures {
        dataBinding = true
    }
}
dependencies {
    implementation(project(Deps.Local.core))

    implementation(Deps.AndroidX.appCompat)
    implementation(Deps.AndroidX.navFragmentKtx)
    implementation(Deps.AndroidX.recyclerView)
    implementation(Deps.AndroidX.viewPager2)
    implementation(Deps.AndroidX.swipeRefreshLayout)
    implementation(Deps.AndroidX.lifecycleRuntime)
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.fragmentKtx)
    implementation(Deps.AndroidX.paging)
    implementation(Deps.Misc.coil)

    implementation(Deps.Google.materialComponents)

    implementation(Deps.Misc.timber)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)
    //test
    testImplementation(project(Deps.Local.librariesTest))
    testImplementation(Deps.Testing.jUnit)
    testImplementation(Deps.Testing.archCore)
    testImplementation(Deps.Testing.mockk)
    testImplementation(Deps.Testing.mockitoKotlin2)
    testImplementation(Deps.Testing.testRoboelectric) {
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
    testImplementation(Deps.Testing.fragmentTest)

}
