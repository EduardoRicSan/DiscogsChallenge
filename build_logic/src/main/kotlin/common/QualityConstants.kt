package com.tech.discogschallenge.buildlogic.common

internal object QualityConstants {

    object Plugins {
        const val KTLINT = "org.jlleitschuh.gradle.ktlint"
        const val DETEKT = "io.gitlab.arturbosch.detekt"
        const val JACOCO = "jacoco"

        const val ANDROID_APPLICATION = "com.android.application"
        const val ANDROID_LIBRARY = "com.android.library"
    }

    object Tasks {
        const val CHECK = "check"
        const val DETEKT = "detekt"
        const val KTLINT_CHECK = "ktlintCheck"
        const val JACOCO_REPORT = "jacocoDebugReport"
    }

    object Detekt {
        const val CONFIG = "config/detekt/detekt.yml"
        const val DOMAIN_CONFIG = "config/detekt/domain-detekt.yml"
        const val BASELINE = "config/detekt/baseline.xml"
    }

    object Jacoco {

        // ---- Tasks ----
        const val TEST_DEBUG_UNIT = "testDebugUnitTest"
        const val COMPILE_DEBUG_KOTLIN = "compileDebugKotlin"

        // ---- Exec files (AGP paths) ----
        const val EXEC_FILE_1 =
            "jacoco/testDebugUnitTest.exec"

        const val EXEC_FILE_2 =
            "outputs/unit_test_code_coverage/debugUnitTest/testDebugUnitTest.exec"

        // ---- Classes ----
        const val KOTLIN_CLASSES_DEBUG =
            "tmp/kotlin-classes/debug"

        const val JAVA_CLASSES_DEBUG =
            "intermediates/javac/debug/classes"
        const val CLASS_PATTERN = "**/*.class"

        val EXCLUDES = listOf(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*",
            "**/*\$Companion.class",
            "**/*\$Lambda$*.*",
            "**/*\$inlined$*.*",
            "**/*Preview*.*",
            "**/di/**",
            "**/hilt_aggregated_deps/**",
            "**/*Hilt*.*",
            "**/*_Factory.*",
            "**/*_MembersInjector.*"
        )

        // ---- Sources ----
        val SOURCE_DIRS = listOf(
            "src/main/java",
            "src/main/kotlin"
        )
    }

    object QualityModule {
        const val DOMAIN = "domain"
    }
}

