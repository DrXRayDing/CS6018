plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.marble"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.marble"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2" // Update this to a stable Compose version
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.ui.geometry.android)
    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.foundation.layout.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.activity:activity-compose:1.7.0") // Ensure this is included
    implementation ("androidx.compose.ui:ui:1.5.2") // Latest Compose UI version
    implementation ("androidx.compose.material:material:1.5.2")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2") // For ViewModel integration
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")
}