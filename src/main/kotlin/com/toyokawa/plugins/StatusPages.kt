package com.toyokawa.plugins

import com.toyokawa.data.exceptions.ConflictException
import com.toyokawa.data.exceptions.ForbiddenException
import com.toyokawa.data.exceptions.InternalServerException
import com.toyokawa.data.exceptions.NotFoundException
import com.toyokawa.data.exceptions.UnauthorizedException
import com.toyokawa.data.exceptions.ValidationException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.util.logging.KtorSimpleLogger
import kotlinx.serialization.SerializationException

data class ErrorResponse(
    val statusCode: Int,
    val error: String,
    val message: String
)

fun Application.configureStatusPages() {
    val logger = KtorSimpleLogger(this::class.java.name)

    install(StatusPages) {
        logger.info("Status Pages initialized")
        exception<Throwable> { call, cause ->
            when (cause) {
                is SerializationException ->
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(
                            statusCode = HttpStatusCode.BadRequest.value,
                            error = "Bad Request",
                            message = "Invalid JSON format"
                        )
                    )
                is ValidationException ->
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(
                            statusCode = HttpStatusCode.BadRequest.value,
                            error = "Validation Error",
                            message = cause.message ?: "Invalid input"
                        )
                    )
                is RequestValidationException ->
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(
                            statusCode = HttpStatusCode.BadRequest.value,
                            error = "Validation Error",
                            message = cause.reasons.joinToString()
                        )
                    )
                is IllegalArgumentException ->
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(
                            statusCode = HttpStatusCode.BadRequest.value,
                            error = "Bad Request",
                            message = cause.message ?: "Invalid argument"
                        )
                    )
                is UnauthorizedException -> {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        ErrorResponse(
                            statusCode = HttpStatusCode.Unauthorized.value,
                            error = "Unauthorized",
                            message = cause.message ?: "Authentication required"
                        )
                    )
                }
                is ForbiddenException -> {
                    call.respond(
                        HttpStatusCode.Forbidden,
                        ErrorResponse(
                            statusCode = HttpStatusCode.Forbidden.value,
                            error = "Forbidden",
                            message = cause.message ?: "Access denied"
                        )
                    )
                }
                is NotFoundException -> {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ErrorResponse(
                            statusCode = HttpStatusCode.NotFound.value,
                            error = "Not found",
                            message = cause.message ?: "Resource not found"
                        )
                    )
                }
                is ConflictException -> {
                    call.respond(
                        HttpStatusCode.Conflict,
                        ErrorResponse(
                            statusCode = HttpStatusCode.Conflict.value,
                            error = "Conflict",
                            message = cause.message ?: "Resource already exists"
                        )
                    )
                }
                is InternalServerException -> {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        ErrorResponse(
                            statusCode = HttpStatusCode.InternalServerError.value,
                            error = "Internal Server Error",
                            message = cause.message ?: "An unexpected error occurred"
                        )
                    )
                }
                else -> {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        ErrorResponse(
                            statusCode = HttpStatusCode.InternalServerError.value,
                            error = "Internal Server Error",
                            message = "An unexpected error occurred"
                        )
                    )
                }
            }
        }
    }
}
