import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream


val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties().apply {
    load(FileInputStream(localPropertiesFile))
}
val apiKey = localProperties.getProperty("API_KEY") ?: ""
val baseUrl = localProperties.getProperty("BASE_URL") ?: ""
val imageBaseUrl = localProperties.getProperty("IMAGE_BASE_URL") ?: ""
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    alias(libs.plugins.compose.compiler)
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("/Users/asma.ansari/Desktop/wat2watch/wat2watch_debug.keystore")
            storePassword = "test1234"
            keyAlias = "debug"
            keyPassword = "test1234"
        }
        create("wat2watch_hassan_PC") {
            storeFile =
                file("C:\\Users\\Hashm\\Desktop\\Coding Projects\\wat2watch\\hassanMac.keystore")
            keyAlias = "hassanMac"
            keyPassword = "123456"
            storePassword = "123456"
        }
    }
    namespace = "com.team1.wat2watch"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.team1.wat2watch"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        signingConfig = signingConfigs.getByName("wat2watchDebug")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        buildConfigField("String", "IMAGE_BASE_URL", "\"$imageBaseUrl\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            signingConfig = signingConfigs.getByName("wat2watchDebug")
        }
        getByName("debug") {
//            signingConfig = signingConfigs.getByName("wat2watchDebug")
            signingConfig = signingConfigs.getByName("wat2watch_hassan_PC")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.firebase.auth.ktx)
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.play.services.auth)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.database.ktx)
    implementation(libs.androidx.core.splashscreen)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.activity.compose) // Use the latest version
    implementation(libs.androidx.material3) // Use the latest version
    implementation(libs.firebase.auth.ktx)
    implementation(libs.google.firebase.firestore.ktx)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    // API dependencies
    implementation(libs.retrofit)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation(libs.okhttp)

    // Image loading from url
    implementation(libs.coil.compose)

    // ... other dependencies
}