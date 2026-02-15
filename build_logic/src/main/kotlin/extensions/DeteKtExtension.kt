package com.tech.discogschallenge.buildlogic.extensions

import com.tech.discogschallenge.buildlogic.common.QualityConstants
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

// Detekt configuration
internal fun Project.configureDetekt() {
    extensions.configure<DetektExtension> {
        buildUponDefaultConfig = true
        allRules = false
        autoCorrect = true
        config.setFrom(
            files(
                rootProject.file(QualityConstants.Detekt.CONFIG)
            )
        )
        baseline = rootProject.file(
            QualityConstants.Detekt.BASELINE
        )
        // Domain module strict rules
        if (name == QualityConstants.QualityModule.DOMAIN) {
            config.setFrom(
                files(
                    rootProject.file(
                        QualityConstants.Detekt.DOMAIN_CONFIG
                    )
                )
            )
            allRules = true
        }
    }
}
