plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.orchardmanagementsystem"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.orchardmanagementsystem"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // Android core libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Calendar view library
    implementation("com.applandeo:material-calendar-view:1.9.2")

    // Google Maps dependency
    implementation("com.google.android.gms:play-services-maps:18.1.0")

    // JUnit and Espresso for testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
