plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinKsp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.dependency.analysis)
}

android {
    namespace = "com.sonozaki.deezerplayer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sonozaki.deezerplayer"
        minSdk = 24
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
}

dependencies {
    implementation(libs.media3.session)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.hilt.android)
    implementation(libs.shimmer)
    implementation(project(":data:player"))
    implementation(project(":data:deezertracks"))
    implementation(project(":core:tracklist"))
    implementation(project(":data:localtracks"))
    ksp(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(project(":features:player"))
    implementation(project(":features:localtracks"))
    implementation(project(":features:deezertracks"))
    implementation(project(":core:presentation"))
    implementation(project(":player:controller"))
    implementation(project(":core:network"))
}