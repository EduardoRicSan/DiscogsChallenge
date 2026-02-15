package com.tech.discogschallenge.buildlogic.extensions

import com.tech.discogschallenge.buildlogic.common.QualityConstants
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.tasks.JacocoReport

// JaCoCo Multi-module configuration
internal fun Project.configureJacoco() {

    tasks.register<JacocoReport>(QualityConstants.Tasks.JACOCO_REPORT) {

        val testTask =
            tasks.named(QualityConstants.Jacoco.TEST_DEBUG_UNIT)

        val compileTask =
            tasks.named(QualityConstants.Jacoco.COMPILE_DEBUG_KOTLIN)

        // Explicit task graph (Gradle 9 requirement)
        dependsOn(testTask)
        dependsOn(compileTask)

        // ---------- EXECUTION DATA ----------
        executionData.from(
            layout.buildDirectory.file(
                QualityConstants.Jacoco.EXEC_FILE_1
            ),
            layout.buildDirectory.file(
                QualityConstants.Jacoco.EXEC_FILE_2
            )
        )

        // ---------- CLASS FILES ----------
        classDirectories.from(
            files(
                // Kotlin compiled classes
                layout.buildDirectory.dir(
                    QualityConstants.Jacoco.KOTLIN_CLASSES_DEBUG
                ),

                // Java / AGP compiled classes (AGP 8+)
                layout.buildDirectory.dir(
                    QualityConstants.Jacoco.JAVA_CLASSES_DEBUG
                )
            ).asFileTree.matching {
                include(QualityConstants.Jacoco.CLASS_PATTERN)
                exclude(QualityConstants.Jacoco.EXCLUDES)
            }
        )


        // ---------- SOURCE FILES ----------
        sourceDirectories.from(
            QualityConstants.Jacoco.SOURCE_DIRS.map {
                layout.projectDirectory.dir(it)
            }
        )

        // ---------- REPORTS ----------
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }
}

