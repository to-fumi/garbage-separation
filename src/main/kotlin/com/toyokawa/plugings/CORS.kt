package com.toyokawa.plugings

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.util.logging.KtorSimpleLogger

fun Application.configureCORS() {
    val logger = KtorSimpleLogger(this::class.java.name)

    install(CORS) {
        logger.info("TODO: Implementation of CORS")
    }
}
