# لغت‌نامه

## Open the project

Open this `LughatNama` folder in Android Studio. Use JDK 17 or newer and install Android SDK Platform 35. The Gradle wrapper is included.

## Build

On Windows:

```powershell
.\gradlew.bat assembleDebug
```

On macOS or Linux:

```bash
./gradlew assembleDebug
```

For a publishable release, choose **Build > Generate Signed Bundle / APK** in Android Studio and sign it with your own release key.

## APK output

The included, installation-ready universal APK is at:

```text
release/LughatNama.apk
```

A local debug build is written to:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Install the APK

Copy `release/LughatNama.apk` to an Android device, allow installation from the app used to open it, then tap the file and choose **Install**.

With Android Debug Bridge:

```bash
adb install -r release/LughatNama.apk
```
