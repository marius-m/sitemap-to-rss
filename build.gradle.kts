import java.net.URI

plugins {
    kotlin("plugin.serialization") version "1.9.20"
    kotlin("jvm") version "1.9.20"
    application
}

group = "lt.markmerkk.utils"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = URI.create("https://s01.oss.sonatype.org/content/repositories/releases/")
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("io.github.pdvrieze.xmlutil:core-jvm:0.86.3")
    implementation("io.github.pdvrieze.xmlutil:serialization-jvm:0.86.3")

    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("ch.qos.logback:logback-classic:1.4.14")

    testImplementation(kotlin("test"))
    testImplementation("com.google.truth:truth:1.1.4")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}