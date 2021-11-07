/*
 * Copyright 2021 dalodev
 */
import com.google.protobuf.gradle.*

plugins {
    androidLibrary()
    chorboagendaAndroid()
    kotlinKapt()
    protobuf()
}

protobuf {
    protoc {
        artifact = Deps.Google.protobufCompiler
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}

dependencies {
    implementation(project(Deps.Local.domain))
    implementation(project(Deps.Local.core))

    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.AndroidX.coreKtx)

    implementation(Deps.AndroidX.prefsDataStore)
    implementation(Deps.AndroidX.protoDataStore)

    // Protobuf-generated classes extend Protobuf's public
    // class, which is needed to be present in the classpath
    // of the dependant modules
//    api(Deps.Google.protobuf)

    implementation(Deps.Misc.kotlinResult)

    implementation(Deps.Google.daggerHilt)
    kapt(Deps.Google.daggerHiltCompiler)

    implementation(Deps.Misc.hiltBinder)
    kapt(Deps.Misc.hiltBinderCompiler)

    //Test
    testImplementation(project(Deps.Local.librariesTest))
}
