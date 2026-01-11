package com.toyokawa.plugins

import com.toyokawa.routes.authRoute
import com.toyokawa.routes.garbageRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import io.ktor.server.routing.route

fun Application.configureRouting() {
    routing {
        route("/auth") {
            authRoute()
        }
        route("/api") {
            garbageRoutes()
        }
    }
}
