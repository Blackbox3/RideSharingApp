plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ridesharing"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ridesharing"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Existing dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("com.googlecode.libphonenumber:libphonenumber:8.12.52")
    // Google Sign-In dependency
    implementation("com.google.android.gms:play-services-auth:20.0.0")
    implementation ("com.hbb20:ccp:2.7.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0'")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

}
