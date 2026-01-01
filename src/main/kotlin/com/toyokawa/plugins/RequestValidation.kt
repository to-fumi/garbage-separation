package com.toyokawa.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.util.logging.KtorSimpleLogger

fun Application.configureRequestValidation() {
    val logger = KtorSimpleLogger(this::class.java.name)

    install(RequestValidation) {
        logger.info("RequestValidation enabled")
    }
}
