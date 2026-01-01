package com.toyokawa.plugins

import com.toyokawa.routes.adminRoute
import com.toyokawa.routes.userRoute
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.routing
import io.ktor.server.routing.route

fun Application.configureRouting() {
    routing {
        authenticate("auth-bearer") {
            route("/admin") {
                adminRoute()
            }
        }
        route("/") {
            userRoute()
        }
    }
}
