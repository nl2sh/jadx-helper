# nl2sh jadx-helper

[简体中文](README.zh-CN.md) | [English](README.md)

Android API 26+ single-DEX JADX helper for [nl2sh](https://github.com/nl2sh/nl2sh), built with JADX 1.5.1. Its DEX JAR runs through Android `app_process`; do not install it as an APK or run it with desktop Java.

Build requirements: JDK 17, Android SDK Platform 35 / Build Tools 35.0.0, and `ANDROID_HOME`. Gradle Wrapper 8.9 is included; Python 3 is required for the helper packaging script.

```bash
git clone https://github.com/nl2sh/jadx-helper.git
cd jadx-helper
./build-helper.sh
```

[Guide and safety boundaries](docs/en/guide.md) · [Release](docs/en/release.md) · [MIT License](LICENSE)
