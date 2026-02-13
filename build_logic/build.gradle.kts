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
}

tasks.withType(org.gradle.api.tasks.Copy::class.java) {
    duplicatesStrategy = org.gradle.api.file.DuplicatesStrategy.EXCLUDE
}
