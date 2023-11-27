plugins {
    `maven-publish`

    kotlin("multiplatform") version libs.versions.kotlin
    kotlin("plugin.serialization") version libs.versions.kotlin
}

group = "net.lsafer"
version = "1.0.0-SNAPSHOT"

tasks.wrapper {
    gradleVersion = "8.2.1"
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

kotlin {
    jvm {
        withJava()
    }
    js {
        binaries.library()
        browser()
        nodejs()
    }
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.serialization.json)

                implementation(libs.ktor.serialization.json)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.contentNegotiation)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        jsMain {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }
    }
}
