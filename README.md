
# Chorboagenda

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->
[![Code test coverage](https://codecov.io/gh/dalodev/ChorboAgenda/branch/develop/graph/badge.svg?token=ZR5BT2T598)](https://codecov.io/gh/dalodev/ChorboAgenda)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://user-images.githubusercontent.com/9118197/145686559-fa1ddfb0-4e97-40d1-af73-ee097f11553f.png">
    <img src="https://www.w3schools.com/images/w3schools_green.jpg" alt="Logo" width="80" height="80">
  </a>

<h3 align="center"></h3>

  <p align="center">
    An awesome App to store your contacts safe!
    <br />
    <a href="https://github.com/dalodev/ChorboAgenda"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/dalodev/ChorboAgenda">View Demo</a>
    ·
    <a href="https://github.com/dalodev/ChorboAgenda/issues">Report Bug</a>
    ·
    <a href="https://github.com/dalodev/ChorboAgenda/issues">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project
[![Product Name Screen Shot][product-screenshot]](https://user-images.githubusercontent.com/9118197/145686559-fa1ddfb0-4e97-40d1-af73-ee097f11553f.png)

Chorboagenda is a free app for **[Android](https://example.com)** that has been developed   
to be able to organize your contacts, special friends, dates, or any person you decide

### Built With

- [Android Studio](https://developer.android.com/studio)
- [Kotlin](https://kotlinlang.org) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html#asynchronous-flow) - Official Kotlin's tooling for performing asynchronous work.
- [MVVM/MVI Architecture](https://developer.android.com/jetpack/guide) - Official recommended architecture for building robust, production-quality apps.
- [Android Jetpack](https://developer.android.com/jetpack)
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - The Room library provides an abstraction layer over SQLite to allow for more robust database access.
  - [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
  - [Navigation](https://developer.android.com/guide/navigation) - Navigation is a framework for navigating between screens within an Android application.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - The ViewModel is designed to store and manage UI-related data in a lifecycle conscious way.
  - [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#stateflow) - StateFlow is a state-holder observable flow that emits the current and new state updates to its collectors.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - View binding is a feature that allows you to more easily write code that interacts with views.
  - [MotionLayout](https://developer.android.com/training/constraint-layout/motionlayout) - MotionLayout allows you to create beautiful animations in your app without too much hassle.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [KotlinX Serialization](https://github.com/Kotlin/kotlinx.serialization) - A multiplatform Kotlin serialization library.
- [Picasso](https://github.com/square/picasso) - An image loading library.
- [PhotoView](https://github.com/Baseflow/PhotoView) - A library that allows to zoom Android images.
- [Detekt](https://github.com/detekt/detekt) - A static code analysis library for Kotlin.
- [Ktlint](https://github.com/pinterest/ktlint) - A library for formatting Kotlin code according to official guidelines.
- [Kotlin Kover](https://github.com/Kotlin/kotlinx-kover) - Gradle plugin for Kotlin code coverage agents: IntelliJ and JaCoCo.
- [Dokka](https://github.com/Kotlin/dokka) - Dokka is a documentation engine for Kotlin.
- [Testing](https://developer.android.com/training/testing) - The app is currently covered with unit tests and instrumentation tests.
  - [JUnit](https://junit.org/junit5) - JUnit is a unit testing framework for the Java programming language.
  - [AssertJ](https://assertj.github.io/doc) - AssertJ is a java library providing a rich set of assertions.
  - [MockK](https://github.com/mockk/mockk) - MockK is a mocking library for Kotlin.
  - [Coroutines Test](https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test) - A library for testing Kotlin coroutines.
  - [Turbine](https://github.com/cashapp/turbine) - A testing library for Kotlin Flows.
  - [Dagger Hilt Test](https://developer.android.com/training/dependency-injection/hilt-testing) - A testing library for modifying the Dagger bindings in instrumented tests.
  - [Room Testing](https://developer.android.com/training/data-storage/room/migrating-db-versions#test) - A library for testing Room migrations.
- [Gradle's Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - Gradle’s Kotlin DSL is an alternative syntax to the Groovy DSL with an enhanced editing experience.
- [buildSrc](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources) - A special module within the project to manage dependencies and whatnot. 
- [Github Actions](https://github.com/features/actions) -  To automate all software workflow CI/CD
- [Firebase](https://firebase.google.com/) - For app distribution, analytics and testing
  
For more information about used dependencies, see [this](/buildSrc/src/main/java/Dependencies.kt) file.

<!-- GETTING STARTED -->
## Getting Started

### Prerequisites
![Min API](https://img.shields.io/badge/API-21%2B-orange.svg?style=flat)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.5-purple?longCache=true&style=popout-square)](https://kotlinlang.org)
[![Android Studio](https://img.shields.io/badge/Android_Studio-4.2-blue.svg?longCache=true&style=popout-square)](https://developer.android.com/studio)
[![Android](https://img.shields.io/badge/Android-5-green.svg?longCache=true&style=popout-square)](https://www.android.com)

* Por razones de seguridad debes añadir tu propio fichero de configuración de Firebase `google-services.json`

### Installation

* It's enough to open and run the project from Android Studio


<!-- ROADMAP -->
## Roadmap

- [ ] Add Changelog
- [x] Multi-language Support
  - [x] English
  - [x] Spanish

See the [open issues](https://github.com/dalodev/Chorboagenda/issues) for a full list of proposed features (and known issues).

<!-- LICENSE -->
## License

Chorboagenda is licensed under the [Apache 2.0 License](LICENSE).
