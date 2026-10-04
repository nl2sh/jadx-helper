# JADX helper development

This module builds an unsigned Android APK and repackages its runtime entries into `jadx-helper.jar`. It contains `classes.dex` and a narrow `com.nl2sh.jadx.Main` entrypoint for `app_process`; it is never installed as an Android application. The entrypoint accepts an APK path, an exact class name, and an output Java file path. nl2sh provides bounded arguments and output, a timeout, SHA-256 verification, and strong confirmation.

The build pins JADX core and DEX input to 1.5.1, matching the upstream Android library example, and targets Android API 26+. It uses Android Gradle Plugin 8.7.3, Gradle Wrapper 8.9, JDK 17, and Android SDK API 35. Run `./build-helper.sh` on Linux/macOS or `.\build-helper.ps1` in Windows PowerShell; no system Gradle installation is required. The Wrapper verifies the downloaded Gradle distribution with its published SHA-256. Gradle repackages all required runtime entries with reproducible ordering, timestamps, and compression; Windows and Linux builds therefore produce the same pinned JAR. Both scripts write `dist/jadx-helper.jar`, its SHA-256 file, and `metadata.json`; `dist/` is not committed. Include the applicable JADX and dependency licenses when distributing the JAR.

The helper has passed single-class smoke tests through `app_process` on an Android API 35 x86_64 emulator and an API 28 ARMv7 device. Release `v1.0.4` publishes [`jadx-helper.jar`](https://github.com/nl2sh/nl2sh/releases/download/v1.0.4/jadx-helper.jar) with SHA-256 `b733944a9588abbafee1d9b9d77cb78c02bb95f056301f115c0fcb77307c7328`; nl2sh uses that pinned asset by default after strong confirmation. The runtime supplies a private writable `java.io.tmpdir`; a manual invocation must do the same, for example `CLASSPATH=/data/local/tmp/jadx-helper.jar /system/bin/app_process -Djava.io.tmpdir=/data/local/tmp/jadx-work / com.nl2sh.jadx.Main <apk> <class> <output>`. API 26 devices, larger multidex APKs, memory use, and timeout cleanup still need broader coverage. JADX's own Android example warns that library behavior may differ from a desktop JVM.

Reproducibility comparisons require the same Git revision and pinned toolchain: the packaged APK includes AGP Git revision metadata. Extracting module history changes that metadata, so an independently rebuilt JAR need not match the historical nl2sh `v1.0.4` digest. Keep the existing runtime pin until a separately verified release is explicitly selected.

## Entrypoint contract and failures

Invoke `com.nl2sh.jadx.Main` with exactly three arguments: input APK, original fully qualified class name, and output Java file. The output parent and `java.io.tmpdir` must already exist and be writable. Output is UTF-8 and an existing output file is overwritten. The helper uses one worker, SIMPLE decompilation, no code cache, no imports/debug information/inlining/renaming, and a filter for the exact class plus its `$` inner classes. Resources are skipped and XML parsing is explicitly disabled. It does not export an entire project or guarantee recompilable source.

| Exit | Meaning |
| --- | --- |
| 0 | Source was written |
| 2 | Argument count is not three |
| 3 | JADX returned empty source |
| 4 | Original class name was not found |
| 5 | Loading, decompilation or file writing raised an exception; inspect stderr |

These codes describe the helper itself. Native nl2sh applies its own timeout and bounded output handling. A manual `app_process` invocation does not pass through nl2sh approval or inherit its bounds; use the native tool for the normal Agent workflow. For a missing class, verify its original name rather than its display/deobfuscated alias. A DEX/JAR loading error requires checking the artifact format and device compatibility; a temporary-directory error requires a private writable directory.
