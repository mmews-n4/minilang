plugins {
    kotlin("jvm") version "2.0.21"
}

group = "de.marcusmews"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.antlr:antlr4:4.13.2")
    implementation(kotlin("reflect"))
    implementation("org.apache.commons:commons-collections4:4.4")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}