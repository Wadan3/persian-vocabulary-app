# Build verification

Verified on 2026-08-22.

## Application

- Release build: successful with R8 code shrinking and resource shrinking
- Package: `com.lughatnama.dictionary`
- Version: `1.0.0` (`versionCode` 1)
- Minimum Android SDK: 24
- Target and compile Android SDK: 35
- Screen orientation: portrait
- APK type: universal (`arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`)
- APK size: 1,259,839 bytes
- APK SHA-256: `AC4DE05D414D485125F12979D52641D9BF369329724D89B9D560BD9C43844802`
- APK Signature Scheme v2 verification: passed
- Signing note: the included APK uses a local test certificate for direct installation; use your own protected release key before store publication

## Offline and data checks

- `android.permission.INTERNET`: absent
- Embedded `assets/dictionary.json`: present
- Embedded entry count: 3,403
- Embedded data matches `dictionary_final.json`: byte-identical before packaging
- Manual-review queue: 0 entries

## Automated checks

- Unit tests: 10 passed, 0 failed
- Android lint: passed with 0 errors
- Lint advisories: 4 (newer SDK/Gradle versions available and the intentional portrait orientation lock)
- Release lint-vital checks: passed
