package com.toyokawa.routes

import com.toyokawa.data.repositories.IGarbageRepository
import com.toyokawa.routes.dto.CreateGarbageDto
import com.toyokawa.routes.dto.LanguageCode
import com.toyokawa.routes.dto.UpdateGarbageDto
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.util.logging.KtorSimpleLogger
import org.koin.ktor.ext.get

fun Route.adminRoute() {
    val logger = KtorSimpleLogger(this::class.java.name)

    val repository = get<IGarbageRepository>()

    route("/garbages") {
        get {
            val response = repository.findAll()
            call.respond(response)

            if (call.queryParameters["search"]!!.isNotEmpty()) {
                call.respondText("Searching data with word ${call.queryParameters["search"]}")
            }
        }
        get("/{id}") {
            val id = call.parameters["id"]!!.toLong()
            val response = repository.findById(id)
            call.respond(response)
        }
        post {
            try {
                val garbage = call.receive<CreateGarbageDto>()
                repository.create(garbage)
                call.respond(HttpStatusCode.NoContent)
            } catch (_: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (_: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        put("/{id}") {
            try {
                val id = call.parameters["id"]!!.toLong()
                val lang = call.parameters["lang"] ?: "ja"
                val garbage = call.receive<UpdateGarbageDto>()
                repository.update(id, LanguageCode.fromCode(lang), garbage)
                call.respond(HttpStatusCode.OK)
            } catch (_: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (_: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
        delete("/{id}") {
            try {
                val id = call.parameters["id"]!!.toLong()
                if (repository.delete(id)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            } catch (_: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (_: JsonConvertException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
