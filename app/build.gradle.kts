/*
 * Copyright 2021 dalodev
 */
plugins {
    androidApplication()
    chorboagendaAndroid()
    kotlinKapt()
    daggerHiltAndroid()
    navSafeArgsKotlin()
}

android {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    buildFeatures {
        dataBinding = true
    }
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {
    implementation(project(Deps.Local.commonsUi))
    implementation(project(Deps.Local.database))
    implementation(project(Deps.Local.featuresContacts))
    implementation(project(Deps.Local.featuresSplash))
    implementation(project(Deps.Local.featuresDashboard))
    implementation(project(Deps.Local.featuresSearch))
    implementation(project(Deps.Local.featuresInfo))
    implementation(project(Deps.Local.featuresImageViewer))
    implementation(project(Deps.Local.featuresLikes))
    implementation(project(Deps.Local.featuresAdd))

    implementation(Deps.AndroidX.navFragmentKtx)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)
    implementation(Deps.Misc.hiltBinder)
    kapt(Deps.Misc.hiltBinderCompiler)
    coreLibraryDesugaring(Deps.Misc.desugaredLibs)
    implementation(Deps.Misc.timber)

    testImplementation(Deps.Testing.jUnit)
    androidTestImplementation(Deps.Testing.jUnitExt)
}
