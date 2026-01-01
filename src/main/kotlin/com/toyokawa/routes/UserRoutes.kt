package com.toyokawa.routes

import com.toyokawa.data.repositories.IGarbageRepository
import com.toyokawa.routes.PaginationConfig.DEFAULT_LIMIT
import com.toyokawa.routes.PaginationConfig.DEFAULT_OFFSET
import com.toyokawa.routes.PaginationConfig.MAX_LIMIT
import com.toyokawa.routes.PaginationConfig.MAX_OFFSET
import com.toyokawa.routes.PaginationConfig.MIN_LIMIT
import com.toyokawa.routes.PaginationConfig.MIN_OFFSET
import com.toyokawa.routes.dto.LanguageEnum
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.util.logging.KtorSimpleLogger
import org.koin.ktor.ext.get

fun Route.userRoute() {
    val logger = KtorSimpleLogger(this::class.java.name)

    val repository = get<IGarbageRepository>()

    route("/") {
        get {
            call.respondRedirect("/garbage", permanent = true)
        }
    }

    route("/garbages") {
        get {
            val lang = LanguageEnum.fromCode(
                call.queryParameters["lang"] ?: LanguageEnum.JA.toString()
            )
            val limit = call.queryParameters["limit"]
                ?.toIntOrNull()
                ?.coerceIn(MIN_LIMIT, MAX_LIMIT)
                ?: DEFAULT_LIMIT

            val offset = call.queryParameters["offset"]
                ?.toLongOrNull()
                ?.coerceIn(MIN_OFFSET, MAX_OFFSET)
                ?: DEFAULT_OFFSET

            val garbages = repository.findAll(lang, limit, offset)
            call.respond(garbages)
        }
    }
}
