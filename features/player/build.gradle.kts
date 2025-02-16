plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.dependency.analysis)
}

android {
    namespace = "com.sonozaki.player"
    compileSdk = 35

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
    implementation(libs.media3.session)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.hilt.android)
    implementation(libs.coil)
    implementation(libs.coil.network.okhttp)
    implementation(project(":core:presentation"))
    ksp(libs.hilt.compiler)
    implementation(project(":core:resources"))
    implementation(libs.media3.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}