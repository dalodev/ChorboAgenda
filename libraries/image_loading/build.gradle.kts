/*
 * Copyright 2021 dev.id
 */
plugins {
    androidLibrary()
    chorboagendaAndroid()
    kotlinKapt()
}

dependencies {
    implementation(Deps.Square.picasso)
    implementation(Deps.Commons.ktx)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)

    implementation(Deps.Misc.hiltBinder)
    kapt(Deps.Misc.hiltBinderCompiler)

    testImplementation(Deps.Testing.jUnit)
    androidTestImplementation(Deps.Testing.jUnitExt)
}
