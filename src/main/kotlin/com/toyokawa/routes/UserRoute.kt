package com.toyokawa.routes

import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.util.logging.KtorSimpleLogger

fun Route.UserRoute() {
    val logger = KtorSimpleLogger(this::class.java.name)

    route("/") {
        get {
            call.respondRedirect("/garbage", permanent = true)
        }
    }

    route("/garbage") {
        get {
            val garbages = listOf("burnable", "non-burnable")
            call.respond(garbages)
        }
    }
}
