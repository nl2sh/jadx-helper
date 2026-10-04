# Third-party notices / 第三方声明

Project source is MIT-licensed; dependencies retain their licenses. / 本项目源码采用 MIT，依赖保留原有许可证。

JADX 1.5.1 and the Apache-licensed dependencies are covered by [Apache-2.0](licenses/Apache-2.0.txt). Original license and additional-notice resources from the pinned dependency JARs are retained under `licenses/`, including SLF4J, Smali, Guava, and Checker Framework. APK packaging does not retain these resources in the DEX JAR, so tagged releases distribute them separately as `third-party-licenses.tar.gz`.

JADX 1.5.1 及采用 Apache 许可证的依赖适用 [Apache-2.0](licenses/Apache-2.0.txt)。固定依赖 JAR 中的原始许可及补充声明保存在 `licenses/`，包括 SLF4J、Smali、Guava 和 Checker Framework。Android 打包未将这些声明保留到 DEX JAR，因此标签发布另外附带 `third-party-licenses.tar.gz`。

Resolved runtime dependencies / 已解析运行时依赖：

- `io.github.skylot:jadx-core:1.5.1`
- `io.github.skylot:jadx-input-api:1.5.1`
- `io.github.skylot:jadx-dex-input:1.5.1`
- `org.slf4j:slf4j-api:2.0.16`
- `com.google.code.gson:gson:2.11.0`
- `com.google.errorprone:error_prone_annotations:2.28.0`
- `com.android.tools.smali:smali-baksmali:3.0.8`
- `com.android.tools.smali:smali-util:3.0.8`
- `com.android.tools.smali:smali-dexlib2:3.0.8`
- `com.google.code.findbugs:jsr305:3.0.2`
- `com.google.guava:guava:33.3.1-jre`
- `com.google.guava:failureaccess:1.0.2`
- `com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava`
- `org.checkerframework:checker-qual:3.43.0`
- `com.google.j2objc:j2objc-annotations:3.0.0`

Before changing dependencies, run `./gradlew --no-daemon :app:dependencies --configuration releaseRuntimeClasspath` and update these notices and license files. Include all applicable notices when distributing the helper.

更改依赖前执行上述命令，同步更新声明与许可文件；分发 helper 时附带全部适用声明。

JADX source / 源码：https://github.com/skylot/jadx/tree/v1.5.1
