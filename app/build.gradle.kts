/*
 * Copyright 2021 dev.id
 */
plugins {
    androidApplication()
    chorboagendaAndroid()
    kotlinKapt()
    daggerHiltAndroid()
    navSafeArgsKotlin()
    spotless()
    detekt()
}


android {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    buildFeatures {
        dataBinding = true
    }
    // https://dagger.dev/hilt/gradle-setup#classpath-aggregation
    lintOptions {
        isCheckReleaseBuilds = false
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

    implementation(Deps.AndroidX.navFragmentKtx)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)
    implementation(Deps.Misc.hiltBinder)
    kapt(Deps.Misc.hiltBinderCompiler)
    coreLibraryDesugaring(Deps.Misc.desugaredLibs)
    implementation(Deps.Commons.windowAnims)
    implementation(Deps.Misc.timber)

    testImplementation(Deps.Testing.jUnit)
    androidTestImplementation(Deps.Testing.jUnitExt)
}
