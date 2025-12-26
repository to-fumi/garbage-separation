package com.toyokawa.routes

import com.toyokawa.services.interfaces.IGarbageService
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.util.logging.KtorSimpleLogger
import org.koin.ktor.ext.get

fun Route.userRoute() {
    val logger = KtorSimpleLogger(this::class.java.name)

    val garbageService = get<IGarbageService>()

    route("/") {
        get {
            call.respondRedirect("/garbage", permanent = true)
        }
    }

    route("/garbages") {
        get {
            val garbages = garbageService.getAll()
            call.respond(garbages)
        }
    }
}
