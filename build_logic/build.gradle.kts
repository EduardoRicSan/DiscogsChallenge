plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("android.quality") {
            id = "android.quality"
            implementationClass = "com.tech.discogschallenge.buildlogic.AndroidQualityConventionPlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

tasks.withType(org.gradle.api.tasks.Copy::class.java) {
    duplicatesStrategy = org.gradle.api.file.DuplicatesStrategy.EXCLUDE
}

dependencies {
    implementation(libs.ktlint.gradle)
    implementation(libs.detekt.gradle)
}
