import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "2.0.21"
    id("org.jetbrains.compose") version "1.7.3"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "de.marcusmews.minilang"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation(compose.desktop.currentOs)
    implementation("org.antlr:antlr4:4.13.2")
    implementation("org.apache.commons:commons-collections4:4.5.0-M3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.compose.components:components-splitpane-desktop:1.7.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("minilang")
    archiveVersion.set("1.0.0")
    archiveClassifier.set("")
    manifest {
        attributes(
            "Main-Class" to "de.marcusmews.minilang.MainKt"
        )
    }
}
compose.desktop {
    application {
        mainClass = "de.marcusmews.minilang.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "minilang"
            packageVersion = "1.0.0"
        }
    }
}

kotlin {
    jvmToolchain(21)
}
