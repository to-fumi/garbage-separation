package com.toyokawa.plugings

import com.toyokawa.routes.AdminRoute
import com.toyokawa.routes.UserRoute
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.routing
import io.ktor.server.routing.route

fun Application.configureRouting() {
    routing {
        authenticate("auth-bearer") {
            route("/admin") {
                AdminRoute()
            }
        }
        route("/") {
            UserRoute()
        }
    }
}
