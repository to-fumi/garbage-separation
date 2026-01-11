package com.toyokawa.routes.extensions

import com.toyokawa.data.exceptions.UnauthorizedException
import com.toyokawa.data.exceptions.ValidationException
import com.toyokawa.routes.extensions.PaginationConfig.DEFAULT_LIMIT
import com.toyokawa.routes.extensions.PaginationConfig.DEFAULT_OFFSET
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal

object PaginationConfig {
    const val DEFAULT_LIMIT = 10
    const val MIN_LIMIT = 1
    const val MAX_LIMIT = 100
    const val DEFAULT_OFFSET = 0L
    const val MIN_OFFSET = 0L
    const val MAX_OFFSET = 10000L
}

fun ApplicationCall.getLimitParameter(): Int {
    val limit = request.queryParameters["limit"]?.toInt() ?: DEFAULT_LIMIT

    return when {
        limit < PaginationConfig.MIN_LIMIT ->
            throw ValidationException("Limit must be at least ${PaginationConfig.MIN_LIMIT}")
        limit > PaginationConfig.MAX_LIMIT ->
            throw ValidationException("Limit must be at most ${PaginationConfig.MAX_LIMIT}")
        else -> limit
    }
}

fun ApplicationCall.getOffsetParameter(): Long {
    val offset = request.queryParameters["offset"]?.toLong() ?: DEFAULT_OFFSET

    return when {
        offset < PaginationConfig.MIN_OFFSET ->
            throw ValidationException("Offset must be at least ${PaginationConfig.MIN_OFFSET}")
        offset > PaginationConfig.MAX_OFFSET ->
            throw ValidationException("Offset must be at most ${PaginationConfig.MAX_OFFSET}")
        else -> offset
    }
}

fun ApplicationCall.requireUserId(): Int {
    val userId = principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asInt()
    return userId ?: throw UnauthorizedException("Unauthorized")
}
