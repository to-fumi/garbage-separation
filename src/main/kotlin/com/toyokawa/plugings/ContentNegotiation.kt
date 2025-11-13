package com.toyokawa.plugings

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.util.logging.KtorSimpleLogger

fun Application.configureContentNegotiation() {
    val logger = KtorSimpleLogger(this::class.java.name)

    install(ContentNegotiation) {
        logger.info("ContentNegotiation configured")
        json()
    }
}
