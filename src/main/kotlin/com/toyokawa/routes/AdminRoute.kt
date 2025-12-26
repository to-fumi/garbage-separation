package com.toyokawa.routes

import com.toyokawa.domain.CreateGarbageDto
import com.toyokawa.domain.UpdateGarbageDto
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.util.logging.KtorSimpleLogger

fun Route.adminRoute() {
    val logger = KtorSimpleLogger(this::class.java.name)

    route("/garbages") {
        get {
            val garbages = listOf("burnable", "non-burnable", "oversized")
            call.respond(garbages)

            if (call.queryParameters["search"]!!.isNotEmpty()) {
                call.respondText("Searching data with word ${call.queryParameters["search"]}")
            }
        }
        get("/{id}") {
            val id = call.parameters["id"]!!
            call.respondText("Getting data with ID $id")
        }
        post {
            val garbage = call.receive<CreateGarbageDto>()
            call.respondText("Posting Garbage by Administrators", status = HttpStatusCode.Created)
        }
        put("/{id}") {
            val id = call.parameters["id"]!!
            val lang = call.queryParameters["lang"] ?: "ja"
            val garbage = call.receive<UpdateGarbageDto>()
            call.respondText(
                "Putting Garbage by Administrators with id = ${call.parameters["id"]}",
                status = HttpStatusCode.OK,
            )
        }
    }
}
