# Releases

This repository owns the helper source and future releases. Push a `v*` tag to build and lint the single-DEX helper using JDK 17, Gradle Wrapper 8.9, SDK 35, and JADX 1.5.1. The workflow publishes `jadx-helper.jar`, `jadx-helper.jar.sha256`, `metadata.json`, `LICENSE`, and `THIRD_PARTY_NOTICES.md` and `third-party-licenses.tar.gz`. Keep the tag aligned with `versionName` and the `helper_version` emitted by both packaging scripts.

Build on Linux/macOS with `./build-helper.sh`, or Windows with `./build-helper.ps1`. Outputs are under `dist/`. The scripts verify the DEX entrypoint and generate a checksum; run `(cd dist && sha256sum -c jadx-helper.jar.sha256)` to verify the built JAR. The JAR is unsigned because it runs through `app_process` rather than APK installation.

Existing nl2sh versions retain their pinned `nl2sh/nl2sh` release `v1.0.4` URL and SHA-256. The repository split does not change that default or automatically select the latest helper. To use a new release, explicitly set `NL2SH_JADX_ANDROID_HELPER_URL` to its HTTPS asset URL and `NL2SH_JADX_ANDROID_HELPER_SHA256` to the independently verified digest, or configure `NL2SH_JADX_ANDROID_HELPER_PATH` for an offline copy. Strong confirmation and runtime limits still apply. See the [APK/JADX integration guide](https://nl2sh.github.io/nl2sh/en/tools/apk-jadx/).

Review [third-party notices](../../THIRD_PARTY_NOTICES.md) and include applicable dependency license files before distributing a changed dependency set. API 26 device testing and large multidex input coverage remain open; see the [guide](guide.md).
