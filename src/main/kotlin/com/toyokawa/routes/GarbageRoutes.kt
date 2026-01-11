package com.toyokawa.routes

import com.toyokawa.data.domain.dto.GarbageCategory
import com.toyokawa.data.domain.dto.LanguageCode
import com.toyokawa.data.repositories.interfaces.IGarbageRepository
import com.toyokawa.data.repositories.interfaces.IUserRepository
import com.toyokawa.routes.requests.UpsertGarbageRequest
import com.toyokawa.routes.extensions.getLimitParameter
import com.toyokawa.routes.extensions.getOffsetParameter
import com.toyokawa.routes.extensions.requireUserId
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.util.logging.KtorSimpleLogger
import org.koin.ktor.ext.get
import kotlin.text.toLong

fun Route.garbageRoutes() {
    val logger = KtorSimpleLogger(this::class.java.name)

    val garbageRepository = get<IGarbageRepository>()
    val userRepository = get<IUserRepository>()

    route("/") {
        get {
            call.respondRedirect("/garbages", permanent = true)
        }
    }

    route("/garbages") {
        get {
            val lang = LanguageCode.fromValue(
                call.queryParameters["lang"] ?: LanguageCode.JA.value
            )
            val limit = call.getLimitParameter()
            val offset = call.getOffsetParameter()
            val garbages = garbageRepository.findAll(lang, limit, offset)
            call.respond(garbages)
        }
    }

    authenticate("auth-jwt") {
        route("/admin/garbages") {
            get {
                val lang = LanguageCode.fromValue(call.queryParameters["lang"] ?: LanguageCode.JA.value)
                val limit = call.getLimitParameter()
                val offset = call.getOffsetParameter()

                if (call.queryParameters["category"]!!.isNotEmpty()) {
                    val category = GarbageCategory.fromValue(call.queryParameters["category"].toString())
                    val garbages = garbageRepository.findByCategory(lang, category, limit, offset)
                    call.respond(garbages)
                }

                val garbages = garbageRepository.findAll(lang, limit, offset)
                call.respond(garbages)
            }
            get("/{id}") {
                val lang = LanguageCode.fromValue(call.queryParameters["lang"] ?: LanguageCode.JA.value)
                val id = call.parameters["id"]!!.toLong()

                val garbage = garbageRepository.findById(lang, id)
                call.respond(garbage)
            }
            post {
                val userId = call.requireUserId()
                userRepository.requireAdmin(userId)

                val garbage = call.receive<UpsertGarbageRequest>()
                garbageRepository.create(garbage)
                call.respond(HttpStatusCode.NoContent)
            }
            put("/{id}") {
                val userId = call.requireUserId()
                userRepository.requireAdmin(userId)

                val id = call.parameters["id"]!!.toLong()
                val garbage = call.receive<UpsertGarbageRequest>()
                garbageRepository.update(id, garbage)
                call.respond(HttpStatusCode.OK)
            }
            delete("/{id}") {
                val userId = call.requireUserId()
                userRepository.requireAdmin(userId)

                val id = call.parameters["id"]!!.toLong()
                garbageRepository.delete(id)
                call.respond(HttpStatusCode.NoContent)
            }
        }
    }
}
