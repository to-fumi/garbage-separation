package com.toyokawa.routes

import com.toyokawa.data.repositories.IGarbageRepository
import com.toyokawa.common.PaginationConfig.DEFAULT_LIMIT
import com.toyokawa.common.PaginationConfig.DEFAULT_OFFSET
import com.toyokawa.common.PaginationConfig.MAX_LIMIT
import com.toyokawa.common.PaginationConfig.MAX_OFFSET
import com.toyokawa.common.PaginationConfig.MIN_LIMIT
import com.toyokawa.common.PaginationConfig.MIN_OFFSET
import com.toyokawa.routes.dto.GarbageCategory
import com.toyokawa.routes.dto.LanguageEnum
import com.toyokawa.routes.dto.UpsertGarbageDto
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
            val lang = LanguageEnum.fromCode(
                call.queryParameters["lang"] ?: LanguageEnum.JA.value
            )
            val limit = call.queryParameters["limit"]
                ?.toIntOrNull()
                ?.coerceIn(MIN_LIMIT, MAX_LIMIT)
                ?: DEFAULT_LIMIT

            val offset = call.queryParameters["offset"]
                ?.toLongOrNull()
                ?.coerceIn(MIN_OFFSET, MAX_OFFSET)
                ?: DEFAULT_OFFSET

            if (call.queryParameters["category"]!!.isNotEmpty()) {
                val category = GarbageCategory.fromCategory(
                    call.queryParameters["category"].toString()
                )
                val garbages = repository.findByCategory(lang, category, limit, offset)
                call.respond(garbages)
            }

            if (call.queryParameters["search"]!!.isNotEmpty()) {
                call.respondText("Searching data with word ${call.queryParameters["search"]}")
            }

            val garbages = repository.findAll(lang, limit, offset)
            call.respond(garbages)
        }
        get("/{id}") {
            val lang = LanguageEnum.fromCode(
                call.queryParameters["lang"] ?: LanguageEnum.JA.value
            )
            val id = call.parameters["id"]!!.toLong()

            val response = repository.findById(lang, id)
            if (response != null) {
                call.respond(response)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        post {
            try {
                val garbage = call.receive<UpsertGarbageDto>()
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
                val garbage = call.receive<UpsertGarbageDto>()
                repository.update(id, garbage)
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
