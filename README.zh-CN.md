# nl2sh jadx-helper

[简体中文](README.zh-CN.md) | [English](README.md)

面向 [nl2sh](https://github.com/nl2sh/nl2sh) 的 Android API 26+ 单 DEX JADX helper，固定 JADX 1.5.1。DEX JAR 通过 Android `app_process` 运行，不安装为 APK，也不通过桌面 Java 执行。

构建要求：JDK 17、Android SDK Platform 35 / Build Tools 35.0.0 和 `ANDROID_HOME`；项目自带 Gradle Wrapper 8.9，helper 打包脚本还需 Python 3。

```bash
git clone https://github.com/nl2sh/jadx-helper.git
cd jadx-helper
./build-helper.sh
```

[构建、使用与安全说明](docs/zh/guide.md) · [Release](docs/zh/release.md) · [MIT License](LICENSE)
