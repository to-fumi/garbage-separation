package com.toyokawa.controllers

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.util.logging.KtorSimpleLogger

fun Route.AdminRoute() {
    val logger = KtorSimpleLogger(this::class.java.name)

    route("/garbage") {
        get {
            call.respondText("Routing for Operating from Admins")
            logger.info("Calling garbage routing by admin")
        }
        post {
            call.respondText("Posting Garbage by Administrators")
        }
        put("/{id}") {
            call.respondText("Putting Garbage by Administrators with id = ${call.parameters["id"]}")
        }
    }
}