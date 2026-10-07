# 发布

本仓库负责 helper 源码和后续独立版本发布。推送 `v*` 标签后，使用 JDK 17、Gradle Wrapper 8.9、SDK 35、JADX 1.5.1 构建并运行 lint，发布 `jadx-helper.jar`、`jadx-helper.jar.sha256`、`metadata.json`、`LICENSE` 与 `THIRD_PARTY_NOTICES.md` 及 `third-party-licenses.tar.gz`。标签应与 `versionName` 及两个打包脚本输出的 `helper_version` 对齐。

Linux/macOS 执行 `./build-helper.sh`，Windows 执行 `./build-helper.ps1`；输出位于 `dist/`。脚本验证 DEX 入口并生成摘要，可执行 `(cd dist && sha256sum -c jadx-helper.jar.sha256)` 复核。JAR 通过 `app_process` 使用，不作为 APK 安装，因此无需 APK 签名。

现有 nl2sh 继续固定 `nl2sh/nl2sh` 的 `v1.0.4` 资产 URL 与 SHA-256。仓库拆分不改变默认来源，也不自动选择最新 helper。使用新 Release 时，显式将 `NL2SH_JADX_ANDROID_HELPER_URL` 设置为 HTTPS 资产地址，`NL2SH_JADX_ANDROID_HELPER_SHA256` 设置为独立验证的摘要；离线副本可通过 `NL2SH_JADX_ANDROID_HELPER_PATH` 配置。强确认和运行限额保持有效，见[APK/JADX 集成指南](https://nl2sh.github.io/nl2sh/tools/apk-jadx/)。

更改依赖后分发前需核对[第三方声明](../../THIRD_PARTY_NOTICES.md)并附适用依赖许可证。API 26 真机和大型 multidex 输入验证仍待扩展，见[指南](guide.md)。

语义版本标签决定构建版本与 version code。包内运行时元数据与 `--info` 使用相同构建字段，打包脚本读取包内元数据，不再硬编码历史 helper 版本。原生主发布把 JAR 记录到认证的兼容性 Manifest，并发布独立资产签名。DEX JAR 不安装为 APK。
