# MCQ-Hand-Copy-Marker
he Hard Copy Marker for Handwritten MCQ is an Android-based grading system that automates marking by using OCR to extract student responses from scanned answer sheets. It compares them against a predefined answer key in a database, ensuring fast, accurate, and efficient grading.
# 📝 MCQ Hard Copy Marker App

![Platform](https://img.shields.io/badge/platform-android-blue)
![Language](https://img.shields.io/badge/language-kotlin-orange)
![License](https://img.shields.io/github/license/your-username/mcq-marker-app)

An Android application built using Kotlin that marks handwritten MCQ answers from hard copy answer sheets. The app uses **CameraX** to capture images, **ML Kit** to extract text via OCR, and **Room** as the local database to store answers and compute scores.

---

## 📱 Features

- 📷 Capture handwritten MCQ answer sheets using CameraX
- 🔍 Extract answers like `"1 A", "2 B"` using ML Kit OCR
- 💾 Store extracted answers and answer keys with Room DB
- ✅ Grade responses by comparing with the correct answers
- 📊 View scores in a searchable and dynamic list
- 🧾 Easily manage students and answer keys from the app

---

## 📸 Screenshots

> Add your screenshots in a folder called `screenshots/` in your GitHub repo

<p float="left">
  <img src="screenshots/camera_view.png" width="250" />
  <img src="screenshots/extracted_text.png" width="250" />
  <img src="screenshots/student_scores.png" width="250" />
</p>

---

## ⚙️ How It Works

1. User opens the camera and captures a photo of a student's MCQ answer sheet.
2. ML Kit OCR reads the answers (e.g., `"1 A", "2 B"`).
3. The extracted answers are saved to a Room database.
4. The app compares these answers against a predefined answer key.
5. A score is computed and stored.
6. The result is displayed in a structured, scrollable list of students.

---

## 🛠️ Installation and Setup

### 📋 Requirements

- Android Studio (Arctic Fox or newer)
- Kotlin plugin
- Android device/emulator (API level 24+)

### 📦 Dependencies

Make sure you include these in your `build.gradle (Module: app)`:

```kotlin
// CameraX
implementation "androidx.camera:camera-camera2:1.3.0"
implementation "androidx.camera:camera-lifecycle:1.3.0"
implementation "androidx.camera:camera-view:1.3.0"

// ML Kit Text Recognition
implementation 'com.google.mlkit:text-recognition:16.0.0'

// Room DB
implementation "androidx.room:room-runtime:2.6.1"
kapt "androidx.room:room-compiler:2.6.1"

// Lifecycle
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0"
implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.7.0"

// Kotlin Coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
