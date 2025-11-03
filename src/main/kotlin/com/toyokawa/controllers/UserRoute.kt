package com.toyokawa.controllers

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.util.logging.KtorSimpleLogger

fun Route.UserRoute() {
    val logger = KtorSimpleLogger(this::class.java.name)

    route("/garbage") {
        get {
            call.respondText("Hello World!")
            logger.info("Calling garbage routing by user")
        }
    }
}
