plugins {
    id("com.android.application")
    id("com.google.gms.google-services") // Ensure this plugin is applied for Firebase services
}

android {
    namespace = "com.example.demo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.demo"
        minSdk = 30    // Update to 23 to resolve the Manifest merger error
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
    implementation(libs.appcompat) // androidx.appcompat:appcompat:1.7.0
    implementation(libs.material) // com.google.android.material:1.12.0
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")


    implementation ("com.google.firebase:firebase-firestore:24.2.1")
    implementation ("com.google.firebase:firebase-analytics")

    // Firebase BoM - it manages versions automatically for all Firebase libraries
    implementation(platform("com.google.firebase:firebase-bom:32.1.0"))

    // Firebase Firestore
    implementation("com.google.firebase:firebase-firestore")

    // Firebase Storage
    implementation("com.google.firebase:firebase-storage")

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth") // Firebase authentication dependency

        implementation ("com.google.firebase:firebase-firestore:24.7.0") // Update to the latest version if necessary

    implementation ("com.google.firebase:firebase-auth:latest_version")
    implementation ("com.google.firebase:firebase-auth:21.0.1") // Or the latest version
    implementation ("com.google.firebase:firebase-firestore:24.3.0") // or the latest version
    implementation ("com.google.firebase:firebase-analytics:21.0.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

// Ensure to apply the Google services plugin
apply(plugin = "com.google.gms.google-services")
