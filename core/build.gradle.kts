plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.quality)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.tech.core"
    compileSdk {
        version = release(36)
    }
    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // ------------------- Core Android ----------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    // ------------------- DataStore ------------------------
    implementation(libs.datastore.preferences)
    // ------------------- Serialization ---------------------
    implementation(libs.kotlinx.serialization.json)
    // ------------------- Hilt -----------------------------
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    // ------------------- Ktor -----------------------------
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.serialization.json)
    // ------------------- Hitl Navigation ------------------
    implementation(libs.hilt.navigation.compose)
    // ------------------- Tests ----------------------------
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
}
