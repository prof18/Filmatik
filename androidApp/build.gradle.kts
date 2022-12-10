import java.util.*

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.prof18.filmatik.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.prof18.filmatik"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        buildTypes {
            getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
                // Network API key
                buildConfigField(
                    "String",
                    "TMDB_KEY",
                    "\"${"tmdbKey".byProperty ?: ""}\""
                )
            }
            getByName("debug") {
                // Network API key
                buildConfigField(
                    "String",
                    "TMDB_KEY",
                    "\"${"tmdbKey".byProperty ?: ""}\""
                )
            }
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.bundles.compose)
    implementation(libs.com.google.accompanist.system.ui)
    implementation(libs.bundles.koin)
}

val String.byProperty: String?
    get() {
        val local = Properties()
        val localProperties = rootProject.file("local.properties")
        if (localProperties.exists()) {
            localProperties.inputStream().use { local.load(it) }
            return local.getProperty(this)
        }
        return null
    }