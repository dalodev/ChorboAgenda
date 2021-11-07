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
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(Deps.Local.domain))

    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.room)
    implementation(Deps.AndroidX.roomKtx)
    implementation(Deps.AndroidX.lifecycleLiveData)
    implementation(Deps.Misc.timber)
    implementation(Deps.Misc.dexter)
    implementation(Deps.Misc.kotlinResult)
    implementation(Deps.Kotlin.serialization)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)

    implementation(Deps.Misc.hiltBinder)
    kapt(Deps.Misc.hiltBinderCompiler)

    kapt(Deps.AndroidX.roomCompiler)
    coreLibraryDesugaring(Deps.Misc.desugaredLibs)

    //Test
    testImplementation(project(Deps.Local.librariesTest))
    testImplementation(Deps.Testing.jUnit)
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
    testImplementation(Deps.Testing.archCore)
    testImplementation(Deps.Testing.room)
    testImplementation(Deps.Testing.mockk)

    testImplementation(Deps.Testing.jUnitExt)
    testImplementation(Deps.Testing.mockitoKotlin2)
}
