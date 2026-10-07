import org.gradle.api.tasks.bundling.Zip
import org.gradle.api.tasks.bundling.ZipEntryCompression

plugins {
    id("com.android.application")
}

val jadxVersion = "1.5.1"
val helperVersion = providers.gradleProperty("nl2shJadxVersionName").orElse("0.2.0").get()
require(helperVersion.matches(Regex("[0-9]+\\.[0-9]+\\.[0-9]+(-[A-Za-z0-9.-]+)?"))) { "Invalid helper version" }
val helperVersionCodeText = providers.gradleProperty("nl2shJadxVersionCode").orNull
val helperVersionCode = if (helperVersionCodeText == null) 200 else
    requireNotNull(helperVersionCodeText.toIntOrNull()) { "Invalid helper version code" }
require(helperVersionCode in 1..2100000000) { "Invalid helper version code" }
val helperProtocol = 1
val runtimeAssets = layout.buildDirectory.dir("generated/runtime-assets")
val generateRuntimeMetadata = tasks.register("generateRuntimeMetadata") {
    inputs.property("version", helperVersion)
    inputs.property("protocol", helperProtocol)
    inputs.property("jadx", jadxVersion)
    val metadata = runtimeAssets.map { it.file("nl2sh-runtime.json") }
    outputs.file(metadata)
    doLast {
        val output = metadata.get().asFile
        output.parentFile.mkdirs()
        output.writeText("""{"protocol":$helperProtocol,"helper_version":"$helperVersion","jadx_version":"$jadxVersion","min_android_api":26,"entrypoint":"com.nl2sh.jadx.Main","features":["single_class","inner_classes"]}""" + "\n")
    }
}

android {
    namespace = "com.nl2sh.jadx"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nl2sh.jadx.helper"
        minSdk = 26
        targetSdk = 35
        versionCode = helperVersionCode
        versionName = helperVersion
        multiDexEnabled = false
        buildConfigField("int", "HELPER_PROTOCOL_VERSION", helperProtocol.toString())
        buildConfigField("String", "JADX_VERSION", "\"$jadxVersion\"")
    }

    buildFeatures { buildConfig = true }
    sourceSets.getByName("main").assets.srcDir(runtimeAssets.get().asFile)

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("io.github.skylot:jadx-core:$jadxVersion")
    implementation("io.github.skylot:jadx-dex-input:$jadxVersion")
}

tasks.register<Zip>("packageHelper") {
    dependsOn("assembleRelease")
    from(provider {
        zipTree(layout.buildDirectory.file("outputs/apk/release/app-release-unsigned.apk").get().asFile)
    })
    destinationDirectory.set(layout.buildDirectory.dir("distributions"))
    archiveFileName.set("jadx-helper.jar")
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
    entryCompression = ZipEntryCompression.DEFLATED
}

tasks.named("preBuild").configure { dependsOn(generateRuntimeMetadata) }
