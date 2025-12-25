package com.toyokawa.plugings

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.util.logging.KtorSimpleLogger

fun Application.configureCORS() {
    val logger = KtorSimpleLogger(this::class.java.name)

    install(CORS) {
        maxAgeInSeconds = 3600

        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Delete)

        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)

        allowHost("frontend-side-domain", schemes = listOf("http", "https"))

        logger.info("Installed CORS Settings.")
    }
}
