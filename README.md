# 🎬 Team 01 - Wat2Watch
![Kotlin](https://img.shields.io/badge/Kotlin-%230095D5.svg?&style=for-the-badge&logo=kotlin&logoColor=white)![Firebase](https://img.shields.io/badge/Firebase-%23FFCA28.svg?&style=for-the-badge&logo=firebase&logoColor=white)![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-%234285F4.svg?style=for-the-badge&logo=android&logoColor=white)
## Project Description
Wat2Watch is an app designed to help groups of movie-watchers 
decide on a movie to watch together by facilitating live matching sessions.

## 📌 Project Description
**WatToWatch** is a mobile application designed to help groups of movie-watchers quickly agree on a film by facilitating **live matching sessions**. Whether it's a casual movie night or a group event, WatToWatch ensures that picking a movie is seamless and fun.

### 🎯 Features
✅ **Live Movie Matching** – Swipe through movie options and match with friends.\
✅ **Personalized Recommendations** – AI-driven movie suggestions based on preferences.\
✅ **Seamless Authentication** – Secure sign-in using Firebase.\
✅ **Cross-Platform Availability** – Optimized for Android devices.

---

## 📥 Installation Instructions
### Step 1: Clone repository locally
```bash
git clone <https>
```

### Step 2: Sync Gradle changes locally
File > Sync Projects with Gradle Files

### Step 3: Setup Android Emulator
Vanilla (API35)

## Setup Google Sign In
*I am unsure if this is something every team member must do but in case you guys encounter errors, here are some troubleshooting tips*

### Generate a new keystore
Open a terminal and run:
```bash
keytool -genkey -v -keystore {key_name}.keystore -alias {alias} -keyalg RSA -keysize 2048 -validity 10000
```

Replace {key_name} and {alias} with whatever you want to name your key.
Enter a password and fill in the prompts. Remember this password.

### Get SHA-1 for the new keystore
```bash
keytool -list -v -keystore wat2watch.keystore -alias wat2watch
```

Enter the password when prompted. Copy the SHA-1 fingerprint.

### Configure in Firebase
1. Go to the Firebase Console (https://console.firebase.google.com/)
2. Select the project
3. Go to Project Settings (gear icon)
4. In the "Your apps" section, find the Android app
5. Do not remove any existing SHA-1 fingerprints
6. Add the new SHA-1 fingerprint you just generated

### Configure Google Cloud Console
1. Go to Google Cloud Console (https://console.cloud.google.com/) - login with the same google account connected to your Firebase
2. Select the project
3. Go to "APIs & Services" > "Credentials"
4. Find your Android OAuth 2.0 Client ID (Firebase generates this automatically)
5. Find the one that has your SHA-1 fingerprint. If it doesn't, edit it and add it in.

### Configure Android Studio
1. Open the project in Android Studio
2. Go to File > Project Structure
3. Select the app module
4. Go to the "Signing Configs" tab
5. Do not remove any existing signing configs
6. Click the "+" button to add a new signing config
7. Name it something unique like "wat2watch_debug_key"
8. For "Key store file", browse and select the {key_name}.keystore file you created above
9. Enter the keystore password, key alias, and key password you set when creating the keystore
10. Click "Apply" and then "OK"

### Update app level `build.gradle`
Make sure your buildTypes section looks like this (do not remove existing signing configs):
```gradle
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("wat2watchDebug")
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("wat2watchDebug")
            // add here
        }
    }
```

### Update google-services.json
1. Go back to the Firebase Console
2. Go to Project Settings
3. In the "Your apps" section, click "Download google-services.json"
4. Replace the existing google-services.json in the project with this new one

Replacing it is okay as long as you do not get rid of any of the fingerprints on Firebase!

### Clean and rebuild your project:
1. In Android Studio, go to Build > Clean Project
2. Then go to Build > Rebuild Project

### Note
A few things to note are the `default_web_client_id` -> this should not cause any issues but it is still good to double check that it matches what is in Firebase in case someone's commit overides the value but it is important to make sure that does not happen in the first place!

Here is a detailed guide: https://developer.android.com/identity/sign-in/credential-manager-siwg#set-google

## API Usage Instructions

### Step 1: Add to `local.properties`
Copy the following into your local.properties file:
```bash
API_KEY = ec4dc5f4fcfb4d7318b2a172b95704fd
BASE_URL = https://api.themoviedb.org/3/
IMAGE_BASE_URL = https://image.tmdb.org/t/p/w500
```

### Step 2: Verify `build.gradle.kts (:app)`
Ensure your build.gradle.kts (:app) file includes the following (it should be there if you pull from main, but just in case):
```bash
val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties().apply {
    load(FileInputStream(localPropertiesFile))
}
val apiKey = localProperties.getProperty("API_KEY") ?: ""
val baseUrl = localProperties.getProperty("BASE_URL") ?: ""
val imageBaseUrl = localProperties.getProperty("IMAGE_BASE_URL") ?: ""

android {
    ...
    defaultConfig {
        ...
        minSdk = 26  // Change to this version
        targetSdk = 35  // Change to this version
        ...
        // Copy all of these
        buildConfigField("String", "API_KEY", "\"$apiKey\"") 
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        buildConfigField("String", "IMAGE_BASE_URL", "\"$imageBaseUrl\"")
    }
}
```

## To Run The Application

Click the play icon in the top bar or go to Run > Run app

---

## 👥 Team Members
- **[Hassan Hashmi]** – h4hashmi, 20831242, kuromaple
- **[Asma Ansari]** – a33ansar, 20879999, asma71612/a-ansari02
- **[Sungmin Kim]** – sm27kim, 20821022, sungminkim627
- **[Caitlin Kwan]** – c32kwan, 20886817, caitlinkwan
- **[Jagan Sahu]** – jsahu, 20840571, jagansahu
- **[Amena Syed]** – a74syed, 20887849, amenasyed

---

🚀 *Making movie nights effortless, one match at a time!* 🍿  
