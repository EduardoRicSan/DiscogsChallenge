package com.tech.discogschallenge.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidQualityConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jlleitschuh.gradle.ktlint")
        pluginManager.apply("io.gitlab.arturbosch.detekt")
        pluginManager.apply("jacoco")
    }
}