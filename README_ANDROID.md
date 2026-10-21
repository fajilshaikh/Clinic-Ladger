# Clinic Ledger — Native Android Application

A high-performance, offline-first digital ledger for multi-location doctors to record daily patient collections.

## Tech Stack
* **Language:** Kotlin 2.0+
* **UI Toolkit:** Jetpack Compose & Material 3
* **Database:** Room SQLite Database with Coroutines & StateFlow
* **Architecture:** MVVM + Repository Pattern
* **Currency:** Indian Rupee (INR / ₹) with formatting

## How to Build & Generate APK in Android Studio

1. Open **Android Studio** (Ladybug / Hedgehog or newer).
2. Click **Open** and select this directory.
3. Allow Gradle to sync dependencies.
4. To run directly on an Android Device or Emulator:
   * Click **Run (Shift+F10)** or the green Play icon.
5. To generate a shareable **Debug or Release APK**:
   * In Android Studio top menu, click **Build** -> **Generate Signed Bundle / APK...** (or **Build** -> **Build Bundle(s) / APK(s)** -> **Build APK(s)**).
   * The generated APK file will be placed in:
     `app/build/outputs/apk/debug/app-debug.apk`
   * Transfer this APK to any Android phone (Android 8.0+ / API 26+) and tap to install!

## Features
* ⚡ **<10s Patient Entry:** Enter Name, Amount in ₹, and 1-tap Save.
* 🏥 **Multi-Clinic Support:** Switch seamlessly between clinics without internet.
* 📊 **Live Dashboard:** Real-time Today's Patient Count & Today's Total Collection in ₹.
* 🔍 **Instant History & Search:** Filter by Today, Yesterday, This Week, This Month, or Custom Date Range.
* 📈 **Clinic-Wise Reports:** Summary and breakdown for every clinic location.
* 💾 **Local Backup & Restore:** Zero cloud dependency, 100% offline data ownership.
