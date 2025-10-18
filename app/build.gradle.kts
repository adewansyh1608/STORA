@file:Suppress("DEPRECATION")

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

}

android {
    namespace = "com.example.stora"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.stora"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Dependensi Inti (dari 'libs')
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // 1. GUNAKAN BOM (BILL OF MATERIALS)
    // Ini akan MENGATUR versi untuk semua library Compose di bawahnya
    // Kita hardcode versinya untuk kepastian
    implementation(platform(libs.androidx.compose.bom.v20240500))

    // 2. IMPLEMENTASI COMPOSE (TANPA VERSI)
    // BoM akan otomatis memberi mereka versi yang kompatibel
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.foundation)
    implementation(libs.material3) // Akan memperbaiki 'outlinedTextFieldColors'
    implementation(libs.androidx.animation) // Akan memperbaiki 'AnimatedVisibility'

    // 3. DEPENDENSI LAIN (Tetap gunakan versi manual)
    implementation(libs.coil.compose)
    implementation(libs.androidx.material.icons.extended)

    // 4. DEPENDENSI TESTING & DEBUG
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BoM untuk testing
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}