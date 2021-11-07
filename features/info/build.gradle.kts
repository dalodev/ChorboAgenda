/*
 * Copyright 2021 dalodev
 */
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
    implementation(project(Deps.Local.librariesImageLoading))

    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.AndroidX.constraintLayout)
    implementation(Deps.AndroidX.fragmentKtx)
    implementation(Deps.Google.materialComponents)
    implementation(Deps.Misc.kotlinResult)
    implementation(Deps.Misc.dexter)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)
    implementation(Deps.Misc.hiltBinder)
    kapt(Deps.Misc.hiltBinderCompiler)

    testImplementation(project(Deps.Local.librariesTest))
    testImplementation(Deps.Testing.jUnit)
    testImplementation(Deps.Testing.mockk)
    testImplementation(Deps.Testing.coroutines)
    testImplementation(Deps.Testing.archCore)
    testImplementation(Deps.Testing.turbine)
    testImplementation(Deps.Testing.assertJ)
}
