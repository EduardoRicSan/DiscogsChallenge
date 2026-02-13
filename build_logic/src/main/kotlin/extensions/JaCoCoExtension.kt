package com.tech.discogschallenge.buildlogic.extensions

import com.tech.discogschallenge.buildlogic.common.QualityConstants
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.tasks.JacocoReport

// JaCoCo Multi-module configuration
 internal fun Project.configureJacocoMultiModule(subProjects: List<Project>) {
    if (this != rootProject) return

    rootProject.tasks.findByName(QualityConstants.Tasks.JACOCO_MERGE_REPORT)
        ?: rootProject.tasks.register<JacocoReport>(
            QualityConstants.Tasks.JACOCO_MERGE_REPORT
        ) {

            // Run all test tasks first
            dependsOn(
                subProjects.flatMap {
                    it.tasks.matching { t ->
                        t.name.contains(QualityConstants.Jacoco.TEST_TASK_KEYWORD)
                    }
                }
            )

            // Collect execution data
            val execFiles = subProjects.flatMap {
                it.fileTree(it.buildDir.resolve(QualityConstants.Jacoco.EXEC_FOLDER)) {
                    include(QualityConstants.Jacoco.EXEC_PATTERN)
                }.files
            }

            executionData.setFrom(execFiles)

            // Compiled classes
            val classDirs = subProjects.flatMap {
                it.fileTree(
                    it.buildDir.resolve(QualityConstants.Jacoco.KOTLIN_CLASSES_DEBUG)
                ) {
                    exclude(QualityConstants.Jacoco.EXCLUDES)
                }.files
            }

            classDirectories.setFrom(classDirs)

            // Sources
            val srcDirs = subProjects.flatMap { project ->
                QualityConstants.Jacoco.SOURCE_DIRS.map {
                    project.file(it)
                }
            }

            sourceDirectories.setFrom(srcDirs)

            // Reports
            reports {
                xml.required.set(true)
                xml.outputLocation.set(
                    file("$buildDir/${QualityConstants.Jacoco.REPORT_XML}")
                )

                html.required.set(true)
                html.outputLocation.set(
                    file("$buildDir/${QualityConstants.Jacoco.REPORT_HTML}")
                )
            }

            // Required for Gradle 9+
            jacocoClasspath = files(
                configurations.getByName(QualityConstants.Jacoco.JACOCO_AGENT),
                configurations.getByName(QualityConstants.Jacoco.JACOCO_ANT)
            )
        }
}
