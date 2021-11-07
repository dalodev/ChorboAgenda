/*
 * Copyright 2021 dalodev
 */
package plugins

import PLUGIN_DOKKA
import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins.apply(PLUGIN_DOKKA)

tasks.withType<DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        configureEach {
            includes.from("Module.md")
        }
    }
}
