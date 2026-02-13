package com.tech.discogschallenge.buildlogic

import com.tech.discogschallenge.buildlogic.common.QualityConstants.Plugins
import com.tech.discogschallenge.buildlogic.common.QualityConstants.Tasks
import com.tech.discogschallenge.buildlogic.extensions.configureDetekt
import com.tech.discogschallenge.buildlogic.extensions.configureJacocoMultiModule
import com.tech.discogschallenge.buildlogic.extensions.configureKtLint
import com.tech.discogschallenge.buildlogic.extensions.configurePrePRCheck
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidQualityConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(Plugins.KTLINT)
        pluginManager.apply(Plugins.DETEKT)
        pluginManager.apply(Plugins.JACOCO)

        configureKtLint()
        configureDetekt()
        configureJacocoMultiModule(rootProject.subprojects.toList())
        wireQualityToCheck()
        configurePrePRCheck()

        if (this == rootProject) {
            tasks.matching { it.name == Tasks.PRE_PR_CHECK }.configureEach {
                val jacocoMergeReportTask = rootProject.tasks.matching {
                    it.name == Tasks.JACOCO_MERGE_REPORT
                }
                dependsOn(jacocoMergeReportTask)
            }
        }
    }
}

// Wire quality checks to "check" task
private fun Project.wireQualityToCheck() {
    tasks.matching { it.name == Tasks.CHECK }.configureEach {
        dependsOn(Tasks.KTLINT_CHECK)
        dependsOn(Tasks.DETEKT)
    }
}
