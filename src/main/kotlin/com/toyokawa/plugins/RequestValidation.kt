package com.toyokawa.plugins

import com.toyokawa.routes.requests.LoginRequest
import com.toyokawa.routes.requests.RegisterRequest
import com.toyokawa.routes.requests.UpsertGarbageRequest
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.util.logging.KtorSimpleLogger

fun Application.configureRequestValidation() {
    val logger = KtorSimpleLogger(this::class.java.name)

    install(RequestValidation) {
        logger.info("RequestValidation enabled")
        validate<LoginRequest> { req ->
            when {
                req.accountName.isBlank() ->
                    ValidationResult.Invalid("Username or email is required")
                req.password.isBlank() ->
                    ValidationResult.Invalid("Password is required")
                req.password.length < 8 ->
                    ValidationResult.Invalid("Password must be at least 8 characters")
                else -> ValidationResult.Valid
            }
        }

        validate<RegisterRequest> { req ->
            when {
                req.username.isBlank() ->
                    ValidationResult.Invalid("Username is required")
                req.username.length > 50 ->
                    ValidationResult.Invalid("Username must be at most 50 characters")

                req.email.isBlank() ->
                    ValidationResult.Invalid("Email is required")
                req.email.length > 100 ->
                    ValidationResult.Invalid("Email must be at most 100 characters")
                !req.email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) ->
                    ValidationResult.Invalid("Invalid email format")

                req.password.isBlank() ->
                    ValidationResult.Invalid("Password is required")
                req.password.length < 8 ->
                    ValidationResult.Invalid("Password must be at least 8 characters")
                req.password.length > 100 ->
                    ValidationResult.Invalid("Password must be at most 100 characters")
                else -> ValidationResult.Valid
            }
        }
        
        validate<UpsertGarbageRequest> { req ->
            when {
                req.name.isBlank() ->
                    ValidationResult.Invalid("Name is required")
                req.name.length > 100 ->
                    ValidationResult.Invalid("Name must be at most 100 characters")
                req.disposalNotes != null && req.disposalNotes.length > 255 ->
                    ValidationResult.Invalid("Disposal Notes must be at least 255 characters")
                else -> ValidationResult.Valid
            }
        }
    }
}
