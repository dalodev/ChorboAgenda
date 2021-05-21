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

    implementation(Deps.Commons.core)
    implementation(Deps.Commons.ktx)
    implementation(Deps.Commons.widgets)
    implementation(Deps.Commons.windowAnims)
    implementation(Deps.Commons.navigation)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)
    //test
    testImplementation(project(Deps.Local.librariesTest))
    testImplementation(Deps.Testing.jUnit)
    testImplementation(Deps.Testing.archCore)
    testImplementation(Deps.Testing.mockk)
    testImplementation(Deps.Testing.mockitoKotlin2)
    testImplementation(Deps.Testing.testRoboelectric)
    testImplementation(Deps.Testing.fragmentTest)

}
