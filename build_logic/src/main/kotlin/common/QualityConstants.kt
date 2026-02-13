package com.tech.discogschallenge.buildlogic.common

internal object QualityConstants {

    object Plugins {
        const val KTLINT = "org.jlleitschuh.gradle.ktlint"
        const val DETEKT = "io.gitlab.arturbosch.detekt"
        const val JACOCO = "jacoco"
    }

    object Tasks {
        const val CHECK = "check"
        const val DETEKT = "detekt"
        const val KTLINT_CHECK = "ktlintCheck"
        const val JACOCO_MERGE_REPORT = "jacocoMergeReport"
        const val PRE_PR_CHECK = "prePRCheck"
    }

    object Detekt {
        const val CONFIG = "config/detekt/detekt.yml"
        const val DOMAIN_CONFIG = "config/detekt/domain-detekt.yml"
        const val BASELINE = "config/detekt/baseline.xml"
    }

    object Jacoco {
        const val EXEC_FOLDER = "jacoco"
        const val EXEC_PATTERN = "**/*.exec"
        const val TEST_TASK_KEYWORD = "test"
        const val JACOCO_AGENT = "jacocoAgent"
        const val JACOCO_ANT = "jacocoAnt"

        const val KOTLIN_CLASSES_DEBUG = "tmp/kotlin-classes/debug"

        val EXCLUDES = listOf(
            "**/R.class",
            "**/BuildConfig.*",
            "**/Manifest*"
        )

        val SOURCE_DIRS = listOf(
            "src/main/java",
            "src/main/kotlin"
        )

        const val REPORT_XML =
            "reports/jacoco/jacocoMergeReport/jacoco.xml"

        const val REPORT_HTML =
            "reports/jacoco/jacocoMergeReport/html"
    }

    object TaskMetadata {
        const val VERIFICATION_GROUP = "verification"

        const val PRE_PR_DESCRIPTION =
            "Runs ktlint, detekt, tests and merged JaCoCo coverage before opening a PR"
    }

    object QualityModule {
        const val DOMAIN = "domain"
    }
}

