/*
 * Copyright 2021 dev.id
 */
package plugins

import es.littledavity.chorboagenda.utils.isLinuxOrMacOs

object BuildTasksGroups {
    const val GIT_HOOKS = "git hooks"
}

tasks {
    register<Copy>("copyGitHooks") {
        description = "Copies the git hooks from scripts/git-hooks to the .git folder."
        group = BuildTasksGroups.GIT_HOOKS
        from("$rootDir/scripts/git-hooks/") {
            include("**/*.sh")
            rename("(.*).sh", "$1")
        }
        into("$rootDir/.git/hooks")
    }

    register<Exec>("installGitHooks") {
        description = "Installs the pre-commit git hooks from scripts/git-hooks."
        group = BuildTasksGroups.GIT_HOOKS
        workingDir(rootDir)
        commandLine("chmod")
        args("-R", "+x", ".git/hooks/")
        dependsOn(named("copyGitHooks"))
        onlyIf {
            isLinuxOrMacOs()
        }
        doLast {
            logger.info("Git hooks installed successfully.")
        }
    }

    register<Delete>("deleteGitHooks") {
        description = "Delete the pre-commit git hooks."
        group = BuildTasksGroups.GIT_HOOKS
        delete(fileTree(".git/hooks/"))
    }

    /*afterEvaluate {
        tasks["clean"].dependsOn(tasks.named("installGitHooks"))
    }*/
}
