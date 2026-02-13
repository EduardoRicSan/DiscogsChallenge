package com.tech.discogschallenge.buildlogic.extensions

import com.tech.discogschallenge.buildlogic.common.QualityConstants
import org.gradle.api.Project

internal fun Project.configurePrePRCheck() {
    tasks.register(QualityConstants.Tasks.PRE_PR_CHECK) {
        group = QualityConstants.TaskMetadata.VERIFICATION_GROUP
        description = QualityConstants.TaskMetadata.PRE_PR_DESCRIPTION
        dependsOn(QualityConstants.Tasks.KTLINT_CHECK)
        dependsOn(QualityConstants.Tasks.DETEKT)
        dependsOn(QualityConstants.Tasks.CHECK)
    }
}