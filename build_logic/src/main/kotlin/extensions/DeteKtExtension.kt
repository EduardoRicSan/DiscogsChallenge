package com.tech.discogschallenge.buildlogic.extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureDetekt() {
    extensions.configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
        buildUponDefaultConfig = true
        allRules = false
        autoCorrect = true
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        baseline = file("$rootDir/config/detekt/baseline.xml")

        if (name == "domain") {
            config.setFrom(files("$rootDir/config/detekt/domain-detekt.yml"))
            allRules = true
        }
    }
}

