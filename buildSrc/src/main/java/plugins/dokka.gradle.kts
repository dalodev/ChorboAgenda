package plugins

import PLUGIN_DOKKA
import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins.apply(PLUGIN_DOKKA)

tasks.withType<DokkaTaskPartial>().configureEach {
    println("Dokka task partial for module -> ${project.name}")
    dokkaSourceSets {
        configureEach {
            includes.from("Module.md")
        }
    }
}
