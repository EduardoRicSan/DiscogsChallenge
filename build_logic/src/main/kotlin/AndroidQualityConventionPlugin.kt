package com.tech.discogschallenge.buildlogic

import com.tech.discogschallenge.buildlogic.common.QualityConstants
import com.tech.discogschallenge.buildlogic.common.QualityConstants.Plugins
import com.tech.discogschallenge.buildlogic.common.QualityConstants.Tasks
import com.tech.discogschallenge.buildlogic.extensions.configureDetekt
import com.tech.discogschallenge.buildlogic.extensions.configureJacoco
import com.tech.discogschallenge.buildlogic.extensions.configureKtLint
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidQualityConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(Plugins.KTLINT)
        pluginManager.apply(Plugins.DETEKT)
        pluginManager.apply(Plugins.JACOCO)

        configureKtLint()
        configureDetekt()
        plugins.withId(QualityConstants.Plugins.ANDROID_APPLICATION) {
            configureJacoco()
        }

        plugins.withId(QualityConstants.Plugins.ANDROID_LIBRARY) {
            configureJacoco()
        }
        wireQualityToCheck()
    }
}

// Wire quality checks to "check" task
private fun Project.wireQualityToCheck() {
    tasks.matching { it.name == Tasks.CHECK }.configureEach {
        dependsOn(Tasks.KTLINT_CHECK)
        dependsOn(Tasks.DETEKT)
        dependsOn(Tasks.JACOCO_REPORT)
    }
}
