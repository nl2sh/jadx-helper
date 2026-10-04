# JADX helper 开发

模块把未签名 Android APK 的运行时内容重封装为 `jadx-helper.jar`，包含 classes.dex 与 `com.nl2sh.jadx.Main`；不安装为应用，不使用设备 Java 命令。入口接收 APK、精确类名、输出 Java 路径，native 端提供超时、输出限额、摘要验证与强确认。

固定 JADX core/DEX input 1.5.1，Android API 26+，AGP 8.7.3，Gradle Wrapper 8.9，JDK 17，SDK API 35。Linux/macOS 执行 `./build-helper.sh`，Windows 执行 `./build-helper.ps1`，无需系统 Gradle。

Wrapper 验证 Gradle 发行包摘要；重封装条目顺序、时间戳与压缩方式固定。两个入口输出 dist/jadx-helper.jar、摘要和 metadata.json，dist 不提交。分发时附适用 JADX 和依赖许可证。

已验证 API35 x86_64 模拟器和 API28 ARMv7 单类反编译；API26 真机、大型 multidex、内存峰值与超时清理需扩展覆盖。固定 v1.0.4 资产摘要见 [用户工具指南](https://nl2sh.github.io/nl2sh/tools/apk-jadx/)。手工 app_process 必须提供私有可写临时目录：

```bash
CLASSPATH=/data/local/tmp/jadx-helper.jar /system/bin/app_process   -Djava.io.tmpdir=/data/local/tmp/jadx-work / com.nl2sh.jadx.Main APK CLASS OUTPUT
```

以上路径是设备路径，先创建自己的私有工作目录；不要使用常规 JVM JAR 替代 DEX JAR。

可复现摘要比较须使用同一 Git revision 与固定工具链：APK 包含 AGP 的 Git revision 元数据，提取模块历史会改变此元数据，因此独立工程重建的 JAR 不一定与历史 nl2sh `v1.0.4` 摘要相同。运行时继续保留历史固定资产，使用另一个经过验证的版本需显式选择。

## 入口契约与失败处理

调用 `com.nl2sh.jadx.Main` 时必须提供三个参数：输入 APK、原始完整类名、输出 Java 文件。输出父目录和 `java.io.tmpdir` 必须事先存在且可写。输出为 UTF-8，已有输出文件会被覆盖。实现使用单线程、SIMPLE 反编译、无代码缓存，不生成 imports，不使用调试信息、内联或重命名；过滤器匹配指定类及其 `$` 内部类。跳过资源，明确禁用 XML 解析，不导出整个工程，也不保证结果可以重新编译。

| 退出码 | 含义 |
| --- | --- |
| 0 | 已写入源码 |
| 2 | 参数数量不是三个 |
| 3 | JADX 返回空源码 |
| 4 | 找不到指定原始类名 |
| 5 | 加载、反编译或文件写入抛出异常；查看 stderr |

这些退出码属于 helper 自身，原生 nl2sh 另行提供超时和有界输出处理。手工 `app_process` 调用不经过 nl2sh 审批，也不继承其限制；正常 Agent 工作流应使用原生工具。找不到类时核对原始类名，而非展示或反混淆别名；DEX/JAR 加载失败时检查产物格式和设备兼容性；临时目录错误时提供私有可写目录。
