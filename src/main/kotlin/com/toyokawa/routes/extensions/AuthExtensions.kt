package com.toyokawa.routes.extensions

import com.toyokawa.data.exceptions.ForbiddenException
import com.toyokawa.data.exceptions.UnauthorizedException
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal

fun ApplicationCall.requireUserId(): Long {
    val userId = principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asLong()
    return userId ?: throw UnauthorizedException("Unauthorized")
}

fun ApplicationCall.requireAdmin() {
    val role = principal<JWTPrincipal>()?.payload?.getClaim("role")?.asString()
    if (role != "ADMIN") {
        throw ForbiddenException("Admin role required")
    }
}
