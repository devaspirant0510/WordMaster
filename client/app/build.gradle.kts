import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    buildFeatures {
        dataBinding = true
        viewBinding = true
        buildConfig = true
    }
    namespace = "ngod.project.wordmaster"
    compileSdk = 35

    defaultConfig {
        applicationId = "ngod.project.wordmaster"
        minSdk = 24
        targetSdk = 35
        versionCode = 2
        versionName = "0.0.1-beta"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        defaultConfig {
            val localProperties = Properties()
            val localPropertiesFile = rootProject.file("local.properties")
            if (localPropertiesFile.exists()) {
                localProperties.load(localPropertiesFile.inputStream())
            }
            val admobBannerId = localProperties.getProperty("ADMOB_BANNER_ID", "")
            buildConfigField("String", "ADMOB_BANNER_ID", "\"$admobBannerId\"")
            resValue("string","admob_banner_id",admobBannerId)
            val admobAppId = localProperties.getProperty("ADMOB_APP_ID","")
            buildConfigField("String", "ADMOB_APP_ID", "\"$admobAppId\"")
            resValue("string","admob_app_id",admobAppId)

        }
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    val roomVersion = "2.6.1" // 최신 버전 확인 필요

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion") // Java용
    implementation ("com.google.android.gms:play-services-ads:22.6.0")

}