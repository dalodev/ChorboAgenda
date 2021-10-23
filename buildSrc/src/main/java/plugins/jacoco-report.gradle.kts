/*
 * Copyright 2021 dev.id
 */
package plugins

import PLUGIN_JACOCO

object BuildTaskGroups {
    const val VERIFICATION = "verification"
}

plugins.apply(PLUGIN_JACOCO)

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        this.excludes?.add("jdk.internal.*")
    }
}

val fileFilter = mutableSetOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*",
    "**/*\$Lambda$*.*", // Jacoco can not handle several "$" in class name.
    "**/*\$inlined$*.*" // Kotlin specific, Jacoco can not handle several "$" in class name.
)

private val classDirectoriesTree = fileTree(project.buildDir) {
    include(
        "**/classes/**/main/**",
        "**/intermediates/classes/debug/**",
        "**/intermediates/javac/debug/*/classes/**", // Android Gradle Plugin 3.2.x support.
        "**/tmp/kotlin-classes/debug/**"
    )

    exclude(fileFilter)
}

private val sourceDirectoriesTree = fileTree("${project.buildDir}") {
    include(
        "src/main/java/**",
        "src/main/kotlin/**",
        "src/debug/java/**",
        "src/debug/kotlin/**"
    )
}

private val executionDataTree = fileTree(project.buildDir) {
    include(
        "outputs/code_coverage/**/*.ec",
        "jacoco/jacocoTestReportDebug.exec",
        "jacoco/testDebugUnitTest.exec",
        "jacoco/test.exec"
    )
}

fun JacocoReportsContainer.reports() {
    xml.isEnabled = true
    html.isEnabled = true
    xml.destination = file("${buildDir}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml")
    html.destination = file("${buildDir}/reports/jacoco/jacocoTestReport/html")
}

fun JacocoCoverageVerification.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}

fun JacocoReport.setDirectories() {
    sourceDirectories.setFrom(sourceDirectoriesTree)
    classDirectories.setFrom(classDirectoriesTree)
    executionData.setFrom(executionDataTree)
}

tasks.register<JacocoReport>("jacocoAndroidTestReport") {
    group = BuildTaskGroups.VERIFICATION
    description = "Code coverage report for both and Unit tests."
    dependsOn("testDebugUnitTest") //createDebugCoverageReport for android test
    reports {
        reports()
    }
    setDirectories()
    doLast {
        println("Jacoco coverage report: file://${reports.html.destination}/index.html")
    }
}

tasks.register<JacocoCoverageVerification>("jacocoAndroidCoverageVerification") {
    group = BuildTaskGroups.VERIFICATION
    description = "Code coverage verification for Android both Android and Unit tests."
    dependsOn("testDebugUnitTest")//createDebugCoverageReport for android test
    violationRules {
        rule {
            limit {
                counter = "INSTRUCTIONAL"
                value = "COVEREDRATIO"
                minimum = "0.5".toBigDecimal()
            }
        }
    }
    setDirectories()
}
