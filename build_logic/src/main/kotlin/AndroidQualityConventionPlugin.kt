package com.tech.discogschallenge.buildlogic

import com.tech.discogschallenge.buildlogic.extensions.configureDetekt
import com.tech.discogschallenge.buildlogic.extensions.configureJacocoMultiModule
import com.tech.discogschallenge.buildlogic.extensions.configureKtLint
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidQualityConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jlleitschuh.gradle.ktlint")
        pluginManager.apply("io.gitlab.arturbosch.detekt")
        pluginManager.apply("jacoco")

        configureKtLint()
        configureDetekt()
        configureJacocoMultiModule(rootProject.subprojects.toList())
        wireQualityToCheck()

    }
}
private fun Project.wireQualityToCheck() {
    tasks.matching { it.name == "check" }.configureEach {
        dependsOn("ktlintCheck")
        dependsOn("detekt")
    }
}
