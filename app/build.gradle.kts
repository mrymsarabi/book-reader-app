plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt") // Apply the Kotlin annotation processing plugin
}

android {
    namespace = "com.example.bookreaderapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bookreaderapp"
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

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")

    // Use only Kapt for annotation processing
    kapt("androidx.room:room-compiler:$room_version")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$room_version")

    // RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$room_version")

    // Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$room_version")

    // Test helpers
    testImplementation("androidx.room:room-testing:$room_version")

    // Paging 3 Integration
    implementation("androidx.room:room-paging:$room_version")

}
