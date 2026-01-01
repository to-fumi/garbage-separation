package com.toyokawa.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText
import io.ktor.util.logging.KtorSimpleLogger

fun Application.configureStatus() {
    val logger = KtorSimpleLogger(this::class.java.name)

    install(StatusPages) {
        logger.info("Status Pages initialized")
        exception<Throwable> { call, cause ->
            if (cause is Exception) {
                call.respondText(text = "403: $cause", status = HttpStatusCode.Forbidden)
            } else {
                call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
            }
        }
    }
}
