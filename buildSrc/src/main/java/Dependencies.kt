import org.gradle.api.JavaVersion

object AppConfig {

    const val compileSdkVersion = 30
    const val targetSdkVersion = 30
    const val minSdkVersion = 21
    const val buildToolsVersion = "30.0.2"
    const val applicationId = "es.devid.chorboagenda"
    const val versionCode = 1
    const val versionName = "1.0.0"

    val javaCompatibilityVersion = JavaVersion.VERSION_1_8
    val kotlinCompatibilityVersion = JavaVersion.VERSION_1_8

}

object Versions {

    const val kotlin = "1.5.31" // also in buildSrc build.gradle.kts file
    const val gradleVersionsPlugin = "0.38.0"
    const val protobufPlugin = "0.8.15"
    const val navigation = "2.3.5"
    const val daggerHilt = "2.38.1"
    const val coroutines = "1.5.2"
    const val room = "2.3.0-rc01"
    const val spotless = "4.0.0"
    const val detekt = "1.18.1"
    const val ktlint = "0.41.0"
    const val jacoco = "0.8.7"
    const val dokka = "1.5.31"
}

object Deps {

    object Plugins {

        private const val gradlePluginVersion = "7.0.3" // also in buildSrc build.gradle.kts file

        const val androidGradle = "com.android.tools.build:gradle:${gradlePluginVersion}"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val navSafeArgs =
            "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
        const val daggerHiltGradle =
            "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHilt}"
        const val gradleVersions =
            "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersionsPlugin}"
        const val spotless = "com.diffplug.spotless:spotless-plugin-gradle:${Versions.spotless}"
        const val detekt = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
        const val ktlint = "com.pinterest:ktlint:${Versions.ktlint}"
        const val jacoco = "org.jacoco:org.jacoco.core:${Versions.jacoco}"
        const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokka}"
    }

    object Local {

        const val app = ":app"
        const val librariesTest = ":libraries:test_utils"
        const val librariesImageLoading = ":libraries:image_loading"
        const val commonsUi = ":commons:ui"
        const val commonsUiWidgets = ":commons:ui-widgets"
        const val core = ":core"
        const val domain = ":domain"
        const val data = ":data"
        const val database = ":database"
        const val featuresSplash = ":features:splash"
        const val featuresDashboard = ":features:dashboard"
        const val featuresContacts = ":features:contacts"
        const val featuresSearch = ":features:search"
        const val featuresNew = ":features:new"
        const val featuresInfo = ":features:info"
        const val featuresImageViewer = ":features:image_viewer"
        const val featuresLikes = ":features:likes"
        const val featuresAdd = ":features:add"
        const val featuresEdit = ":features:edit"
    }

    object Kotlin {

        private const val serializationVersion = "1.3.0"

        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion"

    }

    object AndroidX {

        private const val appCompatVersion = "1.2.0"
        private const val constraintLayoutVersion = "2.0.4"
        private const val recyclerViewVersion = "1.2.0"
        private const val viewPager2Version = "1.0.0"
        private const val swipeRefreshLayoutVersion = "1.1.0"
        private const val lifecycleVersion = "2.4.0-alpha01"
        private const val browserVersion = "1.3.0"
        private const val coreKtxVersion = "1.6.0"
        private const val fragmentKtxVersion = "1.3.2"
        private const val dataStoreVersion = "1.0.0-alpha08"
        private const val pagingVersion = "2.1.2"

        const val appCompat = "androidx.appcompat:appcompat:${appCompatVersion}"
        const val navFragmentKtx =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${constraintLayoutVersion}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${recyclerViewVersion}"
        const val viewPager2 = "androidx.viewpager2:viewpager2:${viewPager2Version}"
        const val swipeRefreshLayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${swipeRefreshLayoutVersion}"
        const val lifecycleCommonJava8 =
            "androidx.lifecycle:lifecycle-common-java8:${lifecycleVersion}"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${lifecycleVersion}"
        const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${lifecycleVersion}"
        const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel:${lifecycleVersion}"
        const val browser = "androidx.browser:browser:${browserVersion}"
        const val room = "androidx.room:room-runtime:${Versions.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        const val coreKtx = "androidx.core:core-ktx:${coreKtxVersion}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${fragmentKtxVersion}"
        const val prefsDataStore = "androidx.datastore:datastore-preferences:${dataStoreVersion}"
        const val protoDataStore = "androidx.datastore:datastore-core:${dataStoreVersion}"
        const val paging = "androidx.paging:paging-runtime:${pagingVersion}"

    }

    object Google {

        private const val materialComponentsVersion = "1.3.0"
        private const val protobufVersion = "3.15.8"
        private const val daggerVersion = "2.28.3"
        private const val databindingVersion = "3.1.4"

        const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
        const val daggerHiltCompiler =
            "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
        const val materialComponents =
            "com.google.android.material:material:${materialComponentsVersion}"
        const val protobuf = "com.google.protobuf:protobuf-javalite:${protobufVersion}"
        const val protobufCompiler = "com.google.protobuf:protoc:${protobufVersion}"

        const val dagger = "com.google.dagger:dagger:$daggerVersion"
        const val dataBinding = "com.android.databinding:cinouker:$databindingVersion"

    }

    object Square {

        private const val okHttpVersion = "4.9.1"
        private const val retrofitVersion = "2.9.0"
        private const val retrofitKotlinxSerializationConverterVersion = "0.8.0"
        private const val picassoVersion = "2.8"

        const val okHttpLoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${okHttpVersion}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${retrofitVersion}"
        const val retrofitScalarsConverter =
            "com.squareup.retrofit2:converter-scalars:${retrofitVersion}"
        const val retrofitKotlinxSerializationConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:$retrofitKotlinxSerializationConverterVersion"
        const val picasso = "com.squareup.picasso:picasso:${picassoVersion}"

    }

    object Commons {

        private const val coreVersion = "1.0.2"
        private const val ktxVersion = "1.0.2"
        private const val widgetsVersion = "1.0.1"
        private const val navigationVersion = "1.0.1"
        private const val materialVersion = "1.0.1"
        private const val networkVersion = "1.0.1"
        private const val recyclerViewVersion = "1.0.0"
        private const val windowAnimsVersion = "1.0.0"
        private const val deviceInfoVersion = "1.0.0"

        const val core = "com.paulrybitskyi.commons:commons-core:${coreVersion}"
        const val ktx = "com.paulrybitskyi.commons:commons-ktx:${ktxVersion}"
        const val widgets = "com.paulrybitskyi.commons:commons-widgets:${widgetsVersion}"
        const val navigation = "com.paulrybitskyi.commons:commons-navigation:${navigationVersion}"
        const val material = "com.paulrybitskyi.commons:commons-material:${materialVersion}"
        const val network = "com.paulrybitskyi.commons:commons-network:${networkVersion}"
        const val recyclerView =
            "com.paulrybitskyi.commons:commons-recyclerview:${recyclerViewVersion}"
        const val windowAnims =
            "com.paulrybitskyi.commons:commons-window-anims:${windowAnimsVersion}"
        const val deviceInfo = "com.paulrybitskyi.commons:commons-device-info:${deviceInfoVersion}"

    }

    object Testing {

        private const val jUnitVersion = "4.13.2"
        private const val jUnitExtVersion = "1.1.2"
        private const val assertJVersion = "3.21.0"
        private const val mockkVersion = "1.11.0"
        private const val turbineVersion = "0.6.0"
        private const val testRunnerVersion = "1.3.0"
        private const val archCoreVersion = "2.1.0"
        private const val mockWebServerVersion = "4.9.1"
        private const val roboelectricVersion = "4.6.1"
        private const val mockitoVersion = "2.2.0"
        private const val mockitoKotlinVersion = "4.0.0"
        private const val rulesVersion = "1.3.0"
        private const val testCoreVersion = "1.3.0"
        private const val fragmentVersion = "1.3.0-beta01"
        private const val playCoreVerion = "1.8.3"
        private const val mavenAntTaskVersion = "2.1.3"

        const val jUnit = "junit:junit:$jUnitVersion"
        const val jUnitExt = "androidx.test.ext:junit:$jUnitExtVersion"
        const val assertJ = "org.assertj:assertj-core:$assertJVersion"
        const val mockk = "io.mockk:mockk:$mockkVersion"
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        const val turbine = "app.cash.turbine:turbine:$turbineVersion"
        const val testRunner = "androidx.test:runner:$testRunnerVersion"
        const val archCore = "androidx.arch.core:core-testing:$archCoreVersion"
        const val daggerHilt = "com.google.dagger:hilt-android-testing:${Versions.daggerHilt}"
        const val room = "androidx.room:room-testing:${Versions.room}"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$mockWebServerVersion"
        const val testRoboelectric = "org.robolectric:robolectric:$roboelectricVersion"
        const val mockitoKotlin2 = "com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoVersion"
        const val mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion"
        const val mockitoCore = "org.mockito:mockito-core:$mockitoKotlinVersion"
        const val testRules = "androidx.test:rules:$rulesVersion"
        const val coreTest = "androidx.test:core:$testCoreVersion"
        const val fragmentTest = "androidx.fragment:fragment-testing:$fragmentVersion"
        const val playCore = "com.google.android.play:core:$playCoreVerion"
        const val mavenAntTask = "org.apache.maven:maven-ant-tasks:$mavenAntTaskVersion"
    }

    object Misc {

        private const val desugaredLibsVersion = "1.1.5"
        private const val kotlinResultVersion = "1.1.11"
        private const val expandableTextViewVersion = "1.0.5"
        private const val hiltBinderVersion = "1.0.0-alpha02"
        private const val photoViewVersion = "2.3.0"
        private const val dexterVersion = "6.2.2"
        private const val timberVersion = "4.7.1"
        private const val coilVersion = "1.2.1"
        private const val lottieVersion = "3.7.0"

        const val desugaredLibs = "com.android.tools:desugar_jdk_libs:${desugaredLibsVersion}"
        const val kotlinResult =
            "com.michael-bull.kotlin-result:kotlin-result:${kotlinResultVersion}"
        const val expandableTextView = "at.blogc:expandabletextview:${expandableTextViewVersion}"
        const val hiltBinder = "com.paulrybitskyi:hilt-binder:$hiltBinderVersion"
        const val hiltBinderCompiler = "com.paulrybitskyi:hilt-binder-compiler:$hiltBinderVersion"
        const val photoView = "com.github.chrisbanes:PhotoView:$photoViewVersion"
        const val dexter = "com.karumi:dexter:$dexterVersion"
        const val timber = "com.jakewharton.timber:timber:$timberVersion"
        const val coil = "io.coil-kt:coil:$coilVersion"
        const val lottie = "com.airbnb.android:lottie:$lottieVersion"
    }

}