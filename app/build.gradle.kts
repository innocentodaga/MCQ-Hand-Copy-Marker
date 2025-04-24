plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.mcqhardcopymarker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mcqhardcopymarker"
        minSdk = 21
        targetSdk = 35
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
        viewBinding = true
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

    // To recognize Latin script
    implementation("com.google.mlkit:text-recognition:16.0.1")

    // To recognize Chinese script
    implementation ("com.google.mlkit:text-recognition-chinese:16.0.1")

    // To recognize Devanagari script
    implementation ("com.google.mlkit:text-recognition-devanagari:16.0.1")

    // To recognize Japanese script
    implementation ("com.google.mlkit:text-recognition-japanese:16.0.1")

    // To recognize Korean script
    implementation ("com.google.mlkit:text-recognition-korean:16.0.1")

    // Google lens and volley and glide
    implementation ("com.google.mlkit:image-labeling:17.0.9")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    //Room
    implementation("androidx.room:room-runtime:2.7.0")
    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp("androidx.room:room-compiler:2.7.0")
    // If this project only uses Java source, use the Java annotationProcessor
    // No additional plugins are necessary
    annotationProcessor("androidx.room:room-compiler:2.7.0")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.7.0")
    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:2.7.0")
    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:2.7.0")
    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:2.7.0")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:2.7.0")




    //Splash Screen
    implementation("androidx.core:core-splashscreen:1.0.0")


    // Material 3 Design
    implementation ("com.google.android.material:material:1.12.0")

    // View pager 2 dependencies
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    // CameraX core library using the camera2 implementation
    val camerax_version = "1.5.0-alpha06"
    // The following line is optional, as the core library is included indirectly by camera-camera2
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    // If you want to additionally use the CameraX Lifecycle library
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    // If you want to additionally use the CameraX VideoCapture library
    implementation("androidx.camera:camera-video:${camerax_version}")
    // If you want to additionally use the CameraX View class
    implementation("androidx.camera:camera-view:${camerax_version}")
    // If you want to additionally add CameraX ML Kit Vision Integration
    implementation("androidx.camera:camera-mlkit-vision:${camerax_version}")
    // If you want to additionally use the CameraX Extensions library
    implementation("androidx.camera:camera-extensions:${camerax_version}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    //View model
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")

    //Navigation
    implementation ("androidx.navigation:navigation-runtime-ktx:2.8.9")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.8.9")
    implementation ("androidx.navigation:navigation-ui-ktx:2.8.9")

    //working with fragments
    implementation ("androidx.fragment:fragment-ktx:1.8.6")

    //Graph
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")


}