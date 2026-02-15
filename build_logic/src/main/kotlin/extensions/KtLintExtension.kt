package com.tech.discogschallenge.buildlogic.extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

// KtLint configuration
internal fun Project.configureKtLint() {
    extensions.configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        android.set(true)
        ignoreFailures.set(false)
        outputToConsole.set(true)
    }
}
