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
        dataBinding = true
    }
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {
    implementation(project(Deps.Local.domain))
    implementation(project(Deps.Local.core))
    implementation(project(Deps.Local.commonsUi))
    implementation(project(Deps.Local.librariesImageLoading))

    implementation(Deps.AndroidX.recyclerView)
    implementation(Deps.AndroidX.constraintLayout)
    api(Deps.AndroidX.swipeRefreshLayout)
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.Google.materialComponents)

    implementation(Deps.Misc.expandableTextView)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)

    implementation(Deps.Misc.hiltBinder)
    kapt(Deps.Misc.hiltBinderCompiler)
}
