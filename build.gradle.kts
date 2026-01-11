import io.github.cdimascio.dotenv.dotenv

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
}

group = "com.toyokawa"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.github.cdimascio:dotenv-kotlin:6.5.1")
    }
}

tasks.named<JavaExec>("run") {
    val dotenv = dotenv {
        directory = rootProject.projectDir.absolutePath
        filename = ".env"
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }
    dotenv.entries().forEach { entry ->
        environment(entry.key, entry.value)
    }
}

dependencies {
    // Ktor
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.config.yaml)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.status.pages)
    implementation(libs.ktor.server.request.validation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.logback.classic)

    // Dependency Injection
    implementation(libs.ktor.koin)
    implementation(libs.ktor.koin.logger)

    // Database
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    implementation(libs.exposed.kotlin.datetime)
    implementation(libs.postgres)
    implementation(libs.hikari)
    implementation(libs.flyway.core)
    implementation(libs.flyway.postgres)
    implementation(libs.jbcrypt)

    // OpenTelemetry
    implementation(libs.ktor.opentelemetry)
    implementation(libs.opentelemetry.sdk)

    // Ktor Test
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
}
