import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.trimble.ttm.mepsampleapp"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}



dependencies {
    implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation ("androidx.appcompat:appcompat:1.0.2")
    implementation ("androidx.core:core-ktx:1.0.1")
    implementation ("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation ("androidx.lifecycle:lifecycle-reactivestreams-ktx:2.0.0")
    implementation("com.google.android.material:material:1.0.0")
    testImplementation ("junit:junit:4.12")
    //testImplementation ("androidx.test:core:1.1.0")
    //testImplementation ("androidx.test.ext:junit:1.1.0")
    testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0")
    //testImplementation ("org.robolectric:robolectric:4.0.2")
    //testImplementation("com.squareup.moshi:moshi:1.8.0")
    //testImplementation ("com.squareup.moshi:moshi-adapters:1.8.0")
    androidTestImplementation ("androidx.test:runner:1.1.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.1.1")

    implementation ("com.trimble:ttm-backbone-api:1.0")
    implementation ("com.google.code.gson:gson:2.8.5")

    kapt("androidx.lifecycle:lifecycle-compiler:2.0.0")
}
