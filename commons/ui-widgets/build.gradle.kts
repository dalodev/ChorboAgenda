/*
 * Copyright 2021 dev.id
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

    // https://dagger.dev/hilt/gradle-setup#classpath-aggregation
    lintOptions {
        isCheckReleaseBuilds = false
    }
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {
    implementation(project(Deps.Local.domain))
    implementation(project(Deps.Local.core))
    implementation(project(Deps.Local.commonsUi))

    implementation(Deps.AndroidX.recyclerView)
    implementation(Deps.AndroidX.constraintLayout)
    api(Deps.AndroidX.swipeRefreshLayout)
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.Google.materialComponents)

    implementation(Deps.Commons.core)
    implementation(Deps.Commons.ktx)
    implementation(Deps.Commons.widgets)
    implementation(Deps.Commons.recyclerView)
    implementation(Deps.Commons.material)
    implementation(Deps.Commons.deviceInfo)

    implementation(Deps.Misc.expandableTextView)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)

    implementation(Deps.Misc.hiltBinder)
    kapt(Deps.Misc.hiltBinderCompiler)
}
