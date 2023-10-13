plugins {
    alias(libs.plugins.android.application)
    kotlin("android")
    kotlin("kapt")
}

android {
    defaultConfig {
        applicationId = "com.slack.autocomplete"
        compileSdk = 34
        defaultConfig {
            minSdk = 24
        }
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        freeCompilerArgs += listOf("-Xdebug")
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    namespace = "com.slack.exercise"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.androidx.preference.ktx)
    //Android Support Libraries
    api(libs.lifecycle.viewmodel.ktx)

    implementation(libs.coil)
    //Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.ui.text.android)
    implementation(libs.androidx.ui.viewbinding)
    implementation(libs.androidx.activity.compose)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.navigation.compose)
    //Coroutines
    implementation(libs.kotlinx.coroutines.android)
    //Datastore
    implementation(libs.androidx.datastore.preferences)
    //Hilt
    implementation(libs.hilt.android)
    //Koin
    implementation(libs.koin.android)
    //OkHttp
    implementation(libs.okhttp.logging.interceptor)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlin.coroutines.adapter)
    implementation(libs.retrofit.kotlinx.serialization.converter)

    //ksp(libs.hilt)

    //testImplementation("junit:junit:${versions.junit}")
    //androidTestImplementation("androidx.test.ext:junit:${versions.androidxJunit}")
    //androidTestImplementation("androidx.test.espresso:espresso-core:${versions.espresso}")
}
