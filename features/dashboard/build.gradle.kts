plugins {
    androidLibrary()
    chorboagendaAndroid()
    kotlinKapt()
    daggerHiltAndroid()
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(Deps.Local.domain))
    implementation(project(Deps.Local.core))
    implementation(project(Deps.Local.commonsUi))
    implementation(project(Deps.Local.commonsUiWidgets))

    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.AndroidX.navFragmentKtx)
    implementation(Deps.AndroidX.constraintLayout)
    implementation(Deps.AndroidX.viewPager2)
    implementation(Deps.AndroidX.fragmentKtx)

    implementation(Deps.Google.materialComponents)

    implementation(Deps.Commons.core)
    implementation(Deps.Commons.ktx)
    implementation(Deps.Commons.widgets)
    implementation(Deps.Commons.windowAnims)
    implementation(Deps.Commons.material)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)
    implementation(Deps.Misc.hiltBinder)
    kapt(Deps.Misc.hiltBinderCompiler)

    //Test
    testImplementation(project(Deps.Local.librariesTest))
    testImplementation(Deps.Testing.jUnit)
    testImplementation(Deps.Testing.mockk)
    testImplementation(Deps.Testing.archCore)
    testImplementation(Deps.Testing.turbine)
    testImplementation(Deps.Testing.assertJ)

}