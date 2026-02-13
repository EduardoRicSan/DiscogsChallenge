package com.tech.discogschallenge.buildlogic.extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.register
import org.gradle.testing.jacoco.tasks.JacocoReport

internal fun Project.configureJacocoMultiModule(subProjects: List<Project>) {
    rootProject.tasks.findByName("jacocoMergeReport") ?: rootProject.tasks.register<JacocoReport>("jacocoMergeReport") {
        // Dependemos de los tests de todos los submodulos
        dependsOn(subProjects.flatMap { it.tasks.matching { t -> t.name.contains("test") } })
        // Ejecutables .exec de todos los módulos
        val execFiles = subProjects.flatMap {
            it.fileTree(it.buildDir.resolve("jacoco")) { include("**/*.exec") }.files
        }
        executionData.setFrom(execFiles)
        // Clases compiladas de todos los módulos
        val classDirs = subProjects.flatMap {
            it.fileTree(it.buildDir.resolve("tmp/kotlin-classes/debug")) {
                exclude("**/R.class", "**/BuildConfig.*", "**/Manifest*")
            }.files
        }
        classDirectories.setFrom(classDirs)
        // Directorios de código fuente
        val srcDirs = subProjects.flatMap {
            listOf(
                it.file("src/main/java"),
                it.file("src/main/kotlin")
            )
        }
        sourceDirectories.setFrom(srcDirs)
        // Reportes
        reports {
            xml.required.set(true)
            xml.outputLocation.set(file("$buildDir/reports/jacoco/jacocoMergeReport/jacoco.xml"))
            html.required.set(true)
            html.outputLocation.set(file("$buildDir/reports/jacoco/jacocoMergeReport/html"))
        }
    }
}
