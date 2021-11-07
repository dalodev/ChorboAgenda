/*
 * Copyright 2021 dalodev
 */
package plugins

import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

apply<DetektPlugin>()

configure<DetektExtension> {
    toolVersion = "1.18.1"
    source = project.files("src/main/kotlin")
    config = files("$rootDir/.detekt/config.yml")
    buildUponDefaultConfig = true
    reports {
        xml {
            enabled = true
            destination = project.file("build/reports/detekt/report.xml")
        }
        html {
            enabled = true
            destination = project.file("build/reports/detekt/report.html")
        }
    }
}
